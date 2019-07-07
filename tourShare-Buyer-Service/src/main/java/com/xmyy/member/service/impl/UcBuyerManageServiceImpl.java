package com.xmyy.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.BizSequenceUtils;
import com.xmyy.member.mapper.UcBuyerMapper;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcMemberAddress;
import com.xmyy.member.model.UcMemberLog;
import com.xmyy.member.service.UcBuyerManageService;
import com.xmyy.member.service.UcMemberAddressService;
import com.xmyy.member.service.UcMemberLogService;
import com.xmyy.member.vo.BuyerManagePageParam;
import com.xmyy.member.vo.BuyerManagePageResult;
import com.xmyy.member.vo.MemberManageResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 买家后台管理  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcBuyerManageService.class)
@CacheConfig(cacheNames = "UcBuyerManage")
public class UcBuyerManageServiceImpl extends BaseServiceImpl<UcBuyer, UcBuyerMapper> implements UcBuyerManageService {

    @Resource
    private UcMemberAddressService addressService;
    @Resource
    private UcMemberLogService logService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<BuyerManagePageResult> list(BuyerManagePageParam param) {
        EntityWrapper<UcBuyer> ew = new EntityWrapper<>();
        ew.eq("is_virtual", EnumConstants.YesOrNo.NO.getValue());

        if (param.getGender() != null) {
            ew.eq("gender", param.getGender());
        }

        if (!StringUtils.isBlank(param.getMobile())) {
            ew.like("mobile", param.getMobile());
        }

        if (param.getMemberType() != null && param.getMemberType() == 2) {
            ew.eq("is_pack", EnumConstants.YesOrNo.YES.getValue());
        }/*else{
            ew.eq("is_pack", EnumConstants.YesOrNo.NO.getValue());
        }*/
        ew.orderDesc(Lists.newArrayList("create_time"));

        List<BuyerManagePageResult> list = null;

        int count = mapper.selectCount(ew);
        if (count > 0) {
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
            List<UcBuyer> mlist = mapper.selectPage(rb, ew);

            list = mlist.stream().map(o -> {

                BuyerManagePageResult r = InstanceUtil.to(o, BuyerManagePageResult.class);
                if (o.getGender() != null && EnumConstants.Gender.valueOf(o.getGender()) != null)
                    r.setGenderLabel(EnumConstants.Gender.valueOf(o.getGender()).getLabel());
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<BuyerManagePageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);


        return page;
    }

    @Override
    @Transactional(readOnly = true)
    public MemberManageResult getInfo(Long id) {
        UcBuyer cBuyer = super.queryById(id);
        cBuyer.setPasswordId(null);
        cBuyer.setUuid(null);

        MemberManageResult result = InstanceUtil.to(cBuyer, MemberManageResult.class);

        if (result.getGender() != null) result.setGenderLabel(EnumConstants.Gender.valueOf(result.getGender()).getLabel());
        if (result.getIsPassIdentity() != null) result.setIsPassIdentityLabel(EnumConstants.YesOrNo.of(result.getIsPassIdentity()).getRealLabel());
        if (result.getIsPassPassport() != null) result.setIsPassPassportLabel(EnumConstants.YesOrNo.of(result.getIsPassPassport()).getRealLabel());
        if (result.getIsPassZhima() != null) result.setIsPassZhimaLabel(EnumConstants.YesOrNo.of(result.getIsPassZhima()).getRealLabel());


        List<UcMemberAddress> addressList = addressService.list(id);
        result.setAddressList(addressList);

        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Object getLogList(Long id) {
        Map<String, Object> param = InstanceUtil.newHashMap("memberId", id);
        param.put("memberType", EnumConstants.MemberType.buyer.getValue());
        param.put("pageNumber", 1);
        param.put("pageSize", 20);

        param.put("orderBy", "create_time");

        Pagination<UcMemberLog> query = logService.query(param);
        return query != null && CollectionUtils.isNotEmpty(query.getRecords()) ? query.getRecords() : null;
    }

    @Override
    public Object addVirtual(List<String[]> data) {
        for (String[] record : data) {
            String nickName = record[1];
            String gender = record[2];
            Integer genderCode = ("男".equals(gender)) ? 1 : 2;
            Wrapper<UcBuyer> wrapper = new EntityWrapper<>();
            wrapper.where("is_virtual={0} AND nick_name = {1}", EnumConstants.YesOrNo.YES.getValue(), nickName);
            UcBuyer params = new UcBuyer();
            params.setIsVirtual(EnumConstants.YesOrNo.YES.getValue());
            params.setNickName(nickName);
            if (mapper.selectOne(params) != null) {
                continue;
            }
            String headPortrait = getRandomHeadPortrait(genderCode);
            UcBuyer buyer = new UcBuyer();
            buyer.setNickName(nickName);
            buyer.setGender(genderCode);
            // 标识为 虚拟账户
            buyer.setIsVirtual(EnumConstants.YesOrNo.YES.getValue());

            buyer.setIsPassIdentity(EnumConstants.YesOrNo.NO.getValue());
            buyer.setIsPassZhima(EnumConstants.YesOrNo.NO.getValue());
            buyer.setIsPassPassport(EnumConstants.YesOrNo.NO.getValue());
            buyer.setState(EnumConstants.State.NORMAL.value());
            buyer.setUuid(IdWorker.get32UUID().toLowerCase());

            buyer.setTrustValue(80);//默认80分
            buyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
            buyer.setIsPack(EnumConstants.YesOrNo.NO.getValue());
            if (StringUtils.isBlank(buyer.getNickName())) {
                buyer.setNickName("XBB"
                        + BizSequenceUtils.generateRandomStr(2) +
                        RandomStringUtils.randomNumeric(3) +
                        BizSequenceUtils.generateRandomStr(2));
            }

            buyer.setAvatarRsurl(headPortrait);
//            if (StringUtils.isBlank(buyer.getAvatarRsurl())) {
//                buyer.setAvatarRsurl(Constants.AVATAR_DEFAULT);
//            }

            buyer.setBuyerNo(BizSequenceUtils.generateMemberNo("BuyerNo"));
            // 不设密码
            // 不设手机
            super.update(buyer);
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public UcBuyer getRandomVirtual() {
        Wrapper<UcBuyer> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id_ as id")
                .where("is_virtual = {0}", EnumConstants.YesOrNo.YES.getValue());
        List<Object> ids = mapper.selectObjs(wrapper);
        Random random = new Random();
        int index = random.nextInt(ids.size());
        return mapper.selectById((Long) ids.get(index));
    }

    /**
     * 获取随机头像地址
     *
     * @param genderCode
     * @return
     */
    private String getRandomHeadPortrait(Integer genderCode) {
        // 产生0-300范围的图片名称
        Random random = new Random();
        int index = random.nextInt(300);
        if (genderCode == 1) {
            return "https://xiabeibao-2017.oss-cn-shenzhen.aliyuncs.com/images/virtual/men/" + index + ".jpg";
        }
        return "https://xiabeibao-2017.oss-cn-shenzhen.aliyuncs.com/images/virtual/women/" + index + ".jpg";
    }

    public static void main(String[] args) throws Exception {
//        File f = new File("C:\\Users\\71085\\Desktop\\women");
//        int index = 0;
//        for (File f1 : f.listFiles()) {
//            String newFile = f1.getParentFile().getAbsolutePath() + "/" + index++ + ".jpg";
//            f1.renameTo(new File(newFile));
//            System.out.println(newFile);
//        }

       /* String path = "E:\\workspace\\gongXiangLvYou\\文档\\开发文档\\虚拟数据\\虚拟买家信息.xlsx";
        List<String[]> data = ExcelReaderUtil.excelToArrayList(path);
        for (String[] row : data) {
           // System.out.println(row);
        }*/
    }
}
