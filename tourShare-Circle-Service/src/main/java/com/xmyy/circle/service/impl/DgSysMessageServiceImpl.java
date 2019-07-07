package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.dao.DgSysMessageDao;
import com.xmyy.circle.mapper.DgSysMessageMapper;
import com.xmyy.circle.mapper.DgSysMessageReadMapper;
import com.xmyy.circle.model.DgSysMessage;
import com.xmyy.circle.model.DgSysMessageRead;
import com.xmyy.circle.service.DgSysMessageService;
import com.xmyy.circle.vo.*;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.push.PushMessage;
import com.xmyy.common.push.PushUser;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.*;

/**
 * 系统消息  服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgSysMessageService.class)
@CacheConfig(cacheNames = "DgSysMessage")
public class DgSysMessageServiceImpl extends BaseServiceImpl<DgSysMessage, DgSysMessageMapper> implements DgSysMessageService {

    @Resource
    private DgSysMessageDao dgSysMessageDao;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private UcBuyerService buyerService;
    @Resource
    private DgSysMessageMapper sysMessageMapper;
    @Resource
    private DgSysMessageReadMapper sysMessageReadMapper;


    @Override
    @Transactional
    public Object addSysMessage(DgSysMessageParam param, Long memberId) {
        DgSysMessage dgSysMessage = new DgSysMessage();
        dgSysMessage.setTitle(param.getTitle());
        dgSysMessage.setContent(param.getContent());
        dgSysMessage.setMessageType(param.getMessageType());
        dgSysMessage.setMemberType(param.getMemberType());
        dgSysMessage.setCover(param.getCover());
        dgSysMessage.setImages(param.getImages());
        dgSysMessage.setCreateBy(memberId);
        dgSysMessage.setCreateTime(new Date());
        int sum = mapper.insert(dgSysMessage);
        Long messageId = dgSysMessage.getId();
        //构造参数
        Map<String, Object> customMap = new HashMap();
        customMap.put("operationType", EnumConstants.PushType.SYS_MSG.getValue());
        customMap.put("memberType", param.getMemberType());
        customMap.put("messageId", messageId);
        List<PushUser> sellers = null;
        List<PushUser> buyers = null;
        if (param.getMemberType().equals(EnumConstants.MemberType.buyer.getValue())) {
            List<UcBuyer> buylist = (List<UcBuyer>) buyerService.queryBuyerList(0);
            buyers = getPushList(buylist, null);
        } else if (param.getMemberType().equals(EnumConstants.MemberType.packer.getValue())) {
            List<UcBuyer> buylist = (List<UcBuyer>) buyerService.queryBuyerList(1);
            buyers = getPushList(buylist, null);
        } else if (param.getMemberType().equals(EnumConstants.MemberType.seller.getValue())) {
            List<UcSeller> sellerlist = (List<UcSeller>) sellerService.getSellerList();
            sellers = getPushList(null, sellerlist);
        }

        //买家及背包客推送
        PushMessage.getBuyerInstance().pushSysMessageToAccountList(param.getTitle(), param.getContent(), customMap, null, buyers);
        //买手推送
        PushMessage.getSellerInstance().pushSysMessageToAccountList(param.getTitle(), param.getContent(), customMap, null, sellers);
        return sum;
    }


    /**
     * 获取用户推送集合
     *
     * @param buylist
     * @param sellerlist
     * @return
     */
    public List<PushUser> getPushList(List<UcBuyer> buylist, List<UcSeller> sellerlist) {
        List<PushUser> pushUsers = new ArrayList<>();
        if (buylist != null && buylist.size() > 0) {
            buylist.stream().forEach(s -> {
                pushUsers.add(new PushUser(s.getUuid(), s.getLoginSource()));
            });
        }
        if (sellerlist != null && sellerlist.size() > 0) {
            sellerlist.stream().forEach(s -> {
                pushUsers.add(new PushUser(s.getUuid(), s.getLoginSource()));
            });
        }
        return pushUsers;
    }

    @Override
    @Transactional(readOnly = true)
    public Object list(Long memberId, DgSysMessagePageParam param) {
        List<Long> sysMessageReadIds = new ArrayList<>();
        if (param.getRead() != 0) {
            //查询用户已读消息列表
            DgSysMessageRead p = new DgSysMessageRead();
            p.setMemberId(memberId);
            p.setMemberType(param.getMemberType());
            sysMessageReadIds = dgSysMessageDao.queryReadMsgList(p);
        }

        //查询系统消息
        EntityWrapper<DgSysMessage> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("message_type", param.getMessageType());
        ew.eq("member_type", param.getMemberType());
        if (param.getRead() == 1) {
            //查询已读消息
            ew.in("id_", sysMessageReadIds);
        } else if (param.getRead() == -1) {
            //查询未读消息
            ew.notIn("id_", sysMessageReadIds);
        }
        RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
        List<DgSysMessage> sysMessageList = sysMessageMapper.selectPage(rb, ew);

        //封装返回结果
        List<DgSysMessageResult> resultList = new ArrayList<>();
        for (DgSysMessage sysMessage : sysMessageList) {
            DgSysMessageResult result = InstanceUtil.to(sysMessage, DgSysMessageResult.class);
            result.setMessageId(sysMessage.getId());
            //设置消息是否已读
            if (param.getRead() == 1) {
                result.setRead(1);
            } else if (param.getRead() == -1) {
                result.setRead(-1);
            } else {
                result.setRead(sysMessageReadIds.contains(sysMessage.getId()) ? 1 : -1);
            }
            resultList.add(result);
        }

        Pagination<DgSysMessageResult> page = new Pagination<>();
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(sysMessageList.size());
        page.setRecords(resultList);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object countNotReadMessage(Long memberId, DgSysMessageNotReadParam params) {
        //查询用户已读消息列表
        DgSysMessageRead p = new DgSysMessageRead();
        p.setMemberId(memberId);
        p.setMemberType(params.getMemberType());
        List<Long> sysMessageReadIds = dgSysMessageDao.queryReadMsgList(p);

        //查询未读系统消息数
        EntityWrapper<DgSysMessage> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("message_type", params.getMessageType());
        ew.eq("member_type", params.getMemberType());
        ew.notIn("id_", sysMessageReadIds);

        return sysMessageMapper.selectCount(ew);
    }


    @Override
    @Transactional
    public Object detail(Long memberId, DgSysMessageReadParam param) {
        //查询是否阅读过此消息
        EntityWrapper<DgSysMessageRead> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("message_id_", param.getMessageId());
        ew.eq("member_id", memberId);
        ew.eq("member_type", param.getMemberType());
        Integer count = sysMessageReadMapper.selectCount(ew);

        //如果没有阅读，插入一条阅读消息
        if (count == 0) {
            DgSysMessageRead dgSysMessageRead = new DgSysMessageRead();
            dgSysMessageRead.setMessageId(param.getMessageId());
            dgSysMessageRead.setMemberId(memberId);
            dgSysMessageRead.setMemberType(param.getMemberType());
            dgSysMessageRead.setCreateBy(memberId);
            dgSysMessageRead.setCreateTime(new Date());
            sysMessageReadMapper.insert(dgSysMessageRead);
        }

        //获取该阅读信息内容
        DgSysMessageResult result = null;
        DgSysMessage sysMessage = queryById(param.getMessageId());
        if (sysMessage != null) {
            result = InstanceUtil.to(sysMessage, DgSysMessageResult.class);
            result.setMessageId(sysMessage.getId());
            result.setRead(count == 0 ? -1 : 1);
        }
        return result;
    }
}
