package com.xmyy.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.Constants;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.util.StandardELContext;
import com.xmyy.member.dao.InviteCodeDao;
import com.xmyy.member.mapper.UcInviteCodeMapper;
import com.xmyy.member.model.UcInviteCode;
import com.xmyy.member.service.DgSmsRecordService;
import com.xmyy.member.service.UcInviteCodeService;
import com.xmyy.member.vo.InviteCodeGenerateParams;
import com.xmyy.member.vo.InviteCodePageParam;
import com.xmyy.member.vo.InviteCodePageResult;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.email.Email;
import top.ibase4j.core.util.EmailUtil;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;

/**
 * 邀请码  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcInviteCodeService.class)
//@CacheConfig(cacheNames = "UcInviteCode")
public class UcInviteCodeServiceImpl extends BaseServiceImpl<UcInviteCode, UcInviteCodeMapper> implements UcInviteCodeService {

    @Resource
    private DgSmsRecordService dgSmsRecordService;
    @Resource
    private InviteCodeDao ucInviteCodeDao;

    private ExecutorService es = Executors.newFixedThreadPool(5);

    /**
     * 批量生成邀请码
     *
     * @param code
     * @param count
     * @return
     */
    @Override
    @Transactional
    public List<UcInviteCode> exportExcel(UcInviteCode code, Integer count) {
        List<UcInviteCode> list = new ArrayList<>();
        Date now = new Date();
        for (int i = 0, size = count; i < size; i++) {
            UcInviteCode c = InstanceUtil.to(code, UcInviteCode.class);
            // reserve.setCreateTime(new Date());
            c.setInviteNo(BizSequenceUtils.generateBizStrNo(EnumConstants.BizCode.InviteCodeNO));
            c.setSendTime(now);
            c.setState(EnumConstants.State.ABNORMAL.value());
            list.add(c);
        }

        super.updateBatch(list);
        return list;
    }

    /**
     * 单账号生成
     *
     * @param params
     * @return
     */
    @Override
    @Transactional
    public Object batchSend(InviteCodeGenerateParams params) {
        List<UcInviteCode> list = params.getItems().parallelStream().map(item -> {
            UcInviteCode c = InstanceUtil.to(item, UcInviteCode.class);
            c.setInviteNo(BizSequenceUtils.generateBizStrNo(EnumConstants.BizCode.InviteCodeNO));
            c.setSendTime(new Date());
            c.setState(EnumConstants.State.ABNORMAL.value());
            return c;
        }).collect(Collectors.toCollection(ArrayList::new));

        super.updateBatch(list);

        StringBuilder sb = new StringBuilder();
        ClassPathResource c = new ClassPathResource("/template/email_vm.html");
        try (
                InputStream in = c.getInputStream();
                InputStreamReader ir = new InputStreamReader(in);
                BufferedReader br = new BufferedReader(ir);
        ) {
            String s;
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }

        } catch (IOException e) {
            logger.error("单账号生成报错" + e.getMessage());
        }


        LinkedBlockingDeque<UcInviteCode> q = new LinkedBlockingDeque<>();
        q.addAll(list);
        for (int i = 0; i < 5; i++) {
            es.execute(() -> {
                while (true) {
                    UcInviteCode l = q.pollFirst();
                    if (l == null) {
                        break;
                    }
                    //发送短信
                    dgSmsRecordService.sendSms(l.getMobile(), Constants.TEMPLATE_CODE_INVITE_CODE, InstanceUtil.newHashMap("code", l.getInviteNo()));

                    if (StringUtils.isNotBlank(l.getEmail())) {
                        //发送邮件
                        //PropertiesUtil.getString("email.invite.code")
                        String content = (String) StandardELContext.getValue(sb.toString(), "inviteNo", l.getInviteNo(),
                                "endDate", DateUtils.formatDate(DateUtils.addDay(l.getSendTime(), l.getDays())));
                        EmailUtil.sendEmail(new Email(l.getEmail(), "小背包买手通知", content));
                    }
                }
            });
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<InviteCodePageResult> list(InviteCodePageParam param) {
        Pagination<InviteCodePageResult> resultPagination = new Pagination<>();

        int totalCount = this.ucInviteCodeDao.countInviteCodePage(param);
        List<InviteCodePageResult> page = ucInviteCodeDao.listInviteCodePage(param, new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize()));

        resultPagination.setTotal(totalCount);
        resultPagination.setRecords(page);
        resultPagination.setCurrent(param.getCurrent());
        resultPagination.setSize(param.getSize());

        return resultPagination;

    }

    /**
     * 失效“过期”的邀请码
     */
    @Override
    @Transactional
    public void cancelInviteCodeOverTime() {
        //封装查询参数
        EntityWrapper<UcInviteCode> ew = new EntityWrapper();
        ew.where("date_add(send_time, interval days day) < NOW()");
        ew.eq("state", EnumConstants.State.ABNORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());

        UcInviteCode code = new UcInviteCode();
        code.setState(EnumConstants.State.REJECT.getValue());

        mapper.update(code, ew);
    }

    /**
     * 根据编号获取有效的邀请码
     */
    @Override
    @Transactional
    public UcInviteCode findByNo(String no) {
        //封装查询参数
        EntityWrapper<UcInviteCode> ew = new EntityWrapper();
        ew.where("date_add(send_time, interval days day) >= NOW()");
        ew.eq("state", EnumConstants.State.ABNORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("invite_no", no);

        List<UcInviteCode> list = mapper.selectList(ew);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
