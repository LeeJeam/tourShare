package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.mapper.DgEverydayRadioMapper;
import com.xmyy.circle.model.DgEverydayRadio;
import com.xmyy.circle.service.DgEverydayRadioService;
import com.xmyy.circle.vo.EveryDayRadioParam;
import com.xmyy.circle.vo.EveryDayRadioResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.push.PushMessage;
import com.xmyy.common.push.PushUser;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.member.service.UcSellerService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 今日提醒  服务实现类
 *
 * @author yeyu
 */
@Service(interfaceClass = DgEverydayRadioService.class)
@CacheConfig(cacheNames = "DgEverydayRadio")
public class DgEverydayRadioServiceImpl extends BaseServiceImpl<DgEverydayRadio, DgEverydayRadioMapper> implements DgEverydayRadioService {

    @Resource
    private UcSellerService sellerService;

    @Override
    @Transactional
    public Object addEveryDayRadio(EveryDayRadioParam everyDayRadioParam) {
        PushUser pushUser;
        if (EnumConstants.MemberType.packer.getValue() == everyDayRadioParam.getToMemberType()) {
            //发送给背包客
            MemberInfo memberInfo = sellerService.getMemberInfo(everyDayRadioParam.getToMemberId(), EnumConstants.MemberType.packer.getValue());
            if(memberInfo == null){
                return "获取不到背包客信息";
            }
            pushUser = new PushUser(memberInfo.getUuid(), memberInfo.getLoginSource());

        } else {
            //发送给买手
            MemberInfo memberInfo = sellerService.getMemberInfo(everyDayRadioParam.getToMemberId(), EnumConstants.MemberType.seller.getValue());
            if(memberInfo == null){
                return "获取不到买手信息";
            }
            pushUser = new PushUser(memberInfo.getUuid(), memberInfo.getLoginSource());
        }

        DgEverydayRadio everydayRadio = new DgEverydayRadio();
        everydayRadio.setToMemberId(everyDayRadioParam.getToMemberId());
        everydayRadio.setToMemberType(everyDayRadioParam.getToMemberType());
        everydayRadio.setType(everyDayRadioParam.getType());

        //获取买家的用户信息
        MemberInfo buyerInfo = sellerService.getMemberInfo(everyDayRadioParam.getMemberId(), EnumConstants.MemberType.buyer.getValue());
        if (buyerInfo == null) {
            return "获取不到买家信息";
        }
        String nickName = buyerInfo.getNickName() == null ? "***" : buyerInfo.getNickName();
        String content= String.format(EnumConstants.EveryDayRadioType.getContentByValue(everyDayRadioParam.getType()), nickName);
        everydayRadio.setContent(content);
        everydayRadio.setStatus(EnumConstants.YesOrNo.YES.getValue());
        everydayRadio.setCreateBy(everyDayRadioParam.getMemberId());
        everydayRadio.setCreateTime(new Date());

        Map<String,Object> customMap = new HashMap<>();
        customMap.put("operationType", EnumConstants.PushType.EVERYDAY_RADIO.getValue());
        customMap.put("memberType", EnumConstants.MemberType.seller.getValue());

        //推送消息
        if (EnumConstants.MemberType.seller.getValue() == everyDayRadioParam.getToMemberType()) {
            PushMessage.getSellerInstance().pushSingleMessage("今日提醒", content, customMap, pushUser, null);
        } else if (EnumConstants.MemberType.packer.getValue() == everyDayRadioParam.getToMemberType()) {
            PushMessage.getBuyerInstance().pushSingleMessage("今日提醒", content, customMap, pushUser, null);
        }

        return mapper.insert(everydayRadio);
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryEveryDayRadioList(Long memberId, Integer memberType){
        EntityWrapper<DgEverydayRadio> ew = new EntityWrapper<>();
        ew.eq("enable_",EnumConstants.YesOrNo.YES.getValue());
        ew.eq("to_member_id", memberId);
        ew.eq("to_member_type", memberType);
        ew.eq("status", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("DATE_FORMAT(create_time,'%Y-%m-%d')", DateUtils.formatDate(new Date(), DateUtils.Pattern_Date_Only));
        ew.orderDesc(Lists.newArrayList("create_time"));
        List<DgEverydayRadio> radioList = mapper.selectList(ew);
        List<EveryDayRadioResult> resultList = Lists.newArrayList();
        radioList.forEach(s->{
            EveryDayRadioResult result = new EveryDayRadioResult();
            result.setCreateTime(s.getCreateTime());
            result.setMemberId(s.getCreateBy());
            result.setContent(s.getContent());
            result.setRadioId(s.getId());
            resultList.add(result);
        });

        return resultList;
    }

}
