package com.xmyy.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.manage.model.AdminUser;
import com.xmyy.manage.service.AdminUserService;
import com.xmyy.member.mapper.UcSellerMapper;
import com.xmyy.member.model.UcMemberLog;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcMemberLogService;
import com.xmyy.member.service.UcSellerManageService;
import com.xmyy.member.vo.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 买家 服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcSellerManageService.class)
@CacheConfig(cacheNames = "UcBuyer")
public class UcSellerManageServiceImpl extends BaseServiceImpl<UcSeller, UcSellerMapper> implements UcSellerManageService {
    @Autowired
    private UcMemberLogService logService;
    @Autowired
    private AdminUserService userService;

    /**
     * 买手列表
     * @param param
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<SellerManagePageResult> list(SellerManagePageParam param) {
        //封装查询参数
        EntityWrapper<UcSeller> ew = new EntityWrapper();
        if(param.getGender() != null){
            ew.eq("gender", param.getGender());
        }

        if(!StringUtils.isBlank(param.getMobile())){
            ew.like("mobile", param.getMobile());
        }

        if(!StringUtils.isBlank(param.getRealName())){
            ew.and("(real_name like {0} or nick_name like {1})", "%"+param.getRealName()+"%","%"+param.getRealName()+"%");
        }

        if(param.getIsSelf()!=null){
            ew.eq("is_self", param.getIsSelf());
        }

        if(param.getIsTop()!=null){
            ew.eq("is_top", param.getIsTop());
        }
        ew.orderDesc(Lists.newArrayList("is_top","top_time","create_time"));

        List<SellerManagePageResult> list = null;

        int count = mapper.selectCount(ew);
        if(count > 0 ){
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
            List<UcSeller> mlist = mapper.selectPage(rb, ew);

            list = mlist.stream().map(o -> {

                SellerManagePageResult r = InstanceUtil.to(o,SellerManagePageResult.class);
                if(o.getGender() != null && EnumConstants.Gender.valueOf(o.getGender()) != null)
                    r.setGenderLabel(EnumConstants.Gender.valueOf(o.getGender()).getLabel());
               /* if(o.getIsTop().intValue() == EnumConstants.YesOrNo.YES.getValue()){

                }*/
                AdminUser adminUser = userService.queryById(o.getTopOpId());
                if(adminUser != null){
                    r.setToperName(adminUser.getUserName());
               }

                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<SellerManagePageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);


        return page;
    }

    /**
     * 买手基本信息
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public MemberManageResult getInfo(Long id) {
        UcSeller ucSeller = super.queryById(id);
        ucSeller.setPasswordId(null);
        ucSeller.setUuid(null);

        MemberManageResult result = InstanceUtil.to(ucSeller, MemberManageResult.class);

        if(result.getGender() != null) result.setGenderLabel(EnumConstants.Gender.valueOf(result.getGender()).getLabel());
        if(result.getIsPassIdentity() != null)  result.setIsPassIdentityLabel(EnumConstants.YesOrNo.of(result.getIsPassIdentity()).getRealLabel());
        if(result.getIsPassPassport() != null)  result.setIsPassPassportLabel(EnumConstants.YesOrNo.of(result.getIsPassPassport()).getRealLabel());
        if(result.getIsPassZhima() != null) result.setIsPassZhimaLabel(EnumConstants.YesOrNo.of(result.getIsPassZhima()).getRealLabel());


        return result;
    }

    /**
     * 置顶
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Object top(SellerTopParam param) {
        UcSeller ucSeller = super.queryById(param.getId());
        if(ucSeller == null){
            return "该买手不存在";
        }
        ucSeller.setIsTop(EnumConstants.YesOrNo.YES.getValue());
        Date now = new Date();

        ucSeller.setTopEndTime(DateUtils.addMinutes(now,param.getTimes()));
        ucSeller.setTopTime(now);
        ucSeller.setUpdateBy(param.getUpdateBy());
        ucSeller.setTopOpId(param.getUpdateBy());

        super.update(ucSeller);
        return ucSeller;

    }

    /**
     * 获取修改记录
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Object getLogList(Long id) {
        Map<String, Object> param = InstanceUtil.newHashMap("memberId",id );
        param.put("memberType", EnumConstants.MemberType.seller.getValue());
        param.put("pageNumber", 1);
        param.put("pageSize", 20);

        param.put("orderBy", "create_time");

        Pagination<UcMemberLog> query = logService.query(param);
        return query!=null && CollectionUtils.isNotEmpty(query.getRecords())?query.getRecords():null;
    }

    /**
     * 取消“过期”的买手置顶
     */
    @Override
    @Transactional
    public void cancelTopOverTime() {
        //封装查询参数
        EntityWrapper<UcSeller> ew = new EntityWrapper();
        ew.where("top_end_time <= NOW()");
        ew.eq("state",EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());

        UcSeller seller = new UcSeller();
        seller.setIsTop(EnumConstants.YesOrNo.NO.getValue());

        mapper.update(seller, ew);

    }

    /**
     * 取消置顶
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Object down(SellerDownParam param) {
        UcSeller ucSeller = super.queryById(param.getId());
        if(ucSeller == null){
            return "该买手不存在";
        }
        ucSeller.setIsTop(EnumConstants.YesOrNo.NO.getValue());

        ucSeller.setUpdateBy(param.getUpdateBy());
        ucSeller.setTopOpId(param.getUpdateBy());

        super.update(ucSeller);
        return ucSeller;
    }
}
