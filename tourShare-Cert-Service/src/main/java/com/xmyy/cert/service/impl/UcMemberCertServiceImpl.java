package com.xmyy.cert.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.codingapi.tx.annotation.TxTransaction;
import com.google.common.collect.Lists;
import com.xmyy.cert.dao.UcMemberCertDao;
import com.xmyy.cert.mapper.UcMemberCertMapper;
import com.xmyy.cert.model.UcMemberCert;
import com.xmyy.cert.service.UcMemberCertService;
import com.xmyy.cert.vo.MemberCertAddParam;
import com.xmyy.cert.vo.MemberCertCountParam;
import com.xmyy.cert.vo.MemberCertParam;
import com.xmyy.cert.vo.MemberCertResult;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.circle.vo.CountryResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.CacheUtils;
import com.xmyy.common.util.ValidateUtil;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.pay.service.AllinPayMemberService;
import com.xmyy.pay.vo.SetRealNameParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.IDCardUtil;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import static com.xmyy.common.util.ModelMapUtil.setModelMap;

/**
 * 用户认证  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = UcMemberCertService.class)
@CacheConfig(cacheNames = "UcMemberCert")
public class UcMemberCertServiceImpl extends BaseServiceImpl<UcMemberCert, UcMemberCertMapper> implements UcMemberCertService {

    @Reference
    private UcSellerService ucSellerService;
    @Reference
    private UcBuyerService ucBuyerService;
    @Reference
    private DgCountryService dgCountryService;
    @Reference
    private AllinPayMemberService allinPayMemberService;
    @Resource
    private UcMemberCertDao memberCertDao;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private ThreadPoolTaskExecutor executor;

    private final static String LOCK_CERT = "S:iBase4J:CERT:";

    @Override
    @Transactional(readOnly = true)
    public Object queryMemberCertList(MemberCertParam param) {
        //封装查询参数
        EntityWrapper<UcMemberCert> ew = new EntityWrapper<>();
        if (StringUtils.isNotBlank(param.getMemberName())) {
            ew.like("member_name", param.getMemberName());
        }
        if (StringUtils.isNotBlank(param.getMobile())) {
            ew.like("mobile", param.getMobile());
        }
        if (param.getState() != null) {
            ew.eq("state", param.getState());
        }
        if (StringUtils.isNotBlank(param.getRealReviewUserName())) {
            ew.like("real_review_user_name", param.getRealReviewUserName());
        }
        if (param.getRealReviewTime() != null) {
            ew.eq("real_review_time", param.getRealReviewTime());
        }
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));
        int count = mapper.selectCount(ew);

        List<MemberCertResult> list = null;
        if (count > 0) {
            RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
            List<UcMemberCert> mlist = mapper.selectPage(rb, ew);
            list = mlist.stream().map(o -> {
                MemberCertResult r = InstanceUtil.to(o, MemberCertResult.class);
                if (o.getState() != null && EnumConstants.identityAuthStatus.valueOf(o.getState()) != null)
                    r.setStateLabel(EnumConstants.Gender.valueOf(o.getState()).getLabel());
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<MemberCertResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryUserCertDetail(Long memberId, Integer memberType) {
        if (memberId == null) {
            return "用户ID不能为空";
        }
        if (memberType == null) {
            return "用户类型不能为空";
        }

        EntityWrapper<UcMemberCert> ew = new EntityWrapper<>();
        ew.eq("member_id", memberId);
        ew.eq("member_type", memberType);
        ew.orderBy("id_", false);
        ew.last("LIMIT 1");
        List<UcMemberCert> list = mapper.selectList(ew);
        if (CollectionUtils.isEmpty(list)) {
            return "没有认证信息";
        }

        return list.get(0);
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object saveIdentityInfo(Long memberId, MemberCertAddParam memberCertAddParams) {
        if (!IDCardUtil.isIdentity(memberCertAddParams.getIdCardNumber())) {
            return "无效的身份证号码";
        }

        if (!ValidateUtil.check(ValidateUtil.REGEX_PASSPORT, memberCertAddParams.getPassportNumber())) {
            return "护照号码格式错误";
        }

        CountryResult country = dgCountryService.getCountryByShortCode(memberCertAddParams.getLiveCountryShortCode());
        if (country == null) {
            return "居住地国家码错误";
        }

        if (memberCertAddParams.getMemberType() == EnumConstants.MemberType.seller.getValue()) { //买手认证
            UcSeller seller = ucSellerService.queryById(memberId);
            if (seller != null) {
                if (seller.getRealState() != null && seller.getRealState() == EnumConstants.identityAuthStatus.SUBMIT.getValue().intValue()) {
                    return "买手已提交认证信息";
                } else if (seller.getRealState() != null && seller.getRealState() == EnumConstants.identityAuthStatus.PASS.getValue().intValue()) {
                    return "买手信息已认证通过";
                }

                //申请认证后，更新用户认证状态为“提交认证”
                seller.setRealState(EnumConstants.identityAuthStatus.SUBMIT.getValue());
                seller.setUpdateTime(new Date());
                ucSellerService.update(seller);
            } else {
                return "用户不存在";
            }
        } else if (memberCertAddParams.getMemberType() == EnumConstants.MemberType.buyer.getValue()) { //背包客认证
            UcBuyer buyer = ucBuyerService.queryById(memberId);
            if (buyer != null) {
                if (buyer.getRealState() != null && buyer.getRealState() == EnumConstants.identityAuthStatus.SUBMIT.getValue().intValue()) {
                    return "背包客已提交认证信息";
                } else if (buyer.getRealState() != null && buyer.getRealState() == EnumConstants.identityAuthStatus.PASS.getValue().intValue()) {
                    return "背包客信息已认证通过";
                }

                //申请认证后，更新用户认证状态为“提交认证”
                buyer.setRealState(EnumConstants.identityAuthStatus.SUBMIT.getValue());
                buyer.setUpdateTime(new Date());
                ucBuyerService.update(buyer);
            } else {
                return "用户不存在！";
            }
        }

        //用户信息更新，需要清除缓存
        CacheUtils.delSellerInfo(memberId);

        return addMemberCert(memberId, memberCertAddParams, country);
    }

    private Object addMemberCert(Long memberId, MemberCertAddParam memberCertAddParams, CountryResult country) {
        Date now = new Date();
        UcMemberCert uMemberCert = InstanceUtil.newInstance(UcMemberCert.class);
        uMemberCert.setMemberId(memberId);
        uMemberCert.setIdCardNumber(memberCertAddParams.getIdCardNumber());
        uMemberCert.setPassportNumber(memberCertAddParams.getPassportNumber());
        uMemberCert.setPassRsurl(memberCertAddParams.getPassRsurl());
        uMemberCert.setRealCreateTime(now);
        uMemberCert.setIsPassIdentity(EnumConstants.YesOrNo.NO.getValue());
        uMemberCert.setIsPassPassport(EnumConstants.YesOrNo.NO.getValue());
        uMemberCert.setIsPassZhima(EnumConstants.YesOrNo.NO.getValue());
        uMemberCert.setIsOverseas(memberCertAddParams.getIsOverseas());
        uMemberCert.setZhimaScore(memberCertAddParams.getZhimaScore());
        uMemberCert.setState(EnumConstants.identityAuthStatus.SUBMIT.getValue());
        uMemberCert.setMemberType(memberCertAddParams.getMemberType());
        uMemberCert.setMemberName(memberCertAddParams.getMemberName());
        uMemberCert.setMobile(memberCertAddParams.getMobile());
        uMemberCert.setIdentityRsurl(memberCertAddParams.getIdentityRsurl());
        uMemberCert.setLiveCountry(country.getName());
        uMemberCert.setLiveCountryShortCode(country.getShortCode());
        UcMemberCert update = super.update(uMemberCert);

        //调用通联接口认证身份证
        SetRealNameParam p = new SetRealNameParam();
        p.setName(memberCertAddParams.getMemberName());
        //TODO 这里做转换是因为参数不统一，新增认证入参2代表背包客，而身份证认证接口3代表背包客，以后要寻求一致
        p.setMemberType(memberCertAddParams.getMemberType() == 2 ? 3 : memberCertAddParams.getMemberType());
        p.setIdentityNo(memberCertAddParams.getIdCardNumber());
        p.setId(update.getId());
        executor.execute(() -> allinPayMemberService.setRealName(memberId, p));

        return setModelMap(HttpCode.OK.value().toString(), "提交认证成功");
    }


    @Override
    @Transactional
    @TxTransaction(isStart = true)
    public Object verifyCertInfo(UcMemberCert paramCert) throws BizException {
        try {
            //避免不同后台人员同时操作同一条数据
            boolean setnx = CacheUtil.getLockManager().setnx(LOCK_CERT + paramCert.getId(), new Date());
            CacheUtil.getLockManager().expire(LOCK_CERT + paramCert.getId(), 10 * 60); //过期时间10分钟
            if (!setnx) {
                return "该记录正在被其他人操作";
            }

            UcMemberCert memberCert = super.queryById(paramCert.getId());
            if (memberCert == null) {
                return "认证信息不存在";
            }

            if (EnumConstants.identityAuthStatus.PASS.getValue().intValue() == memberCert.getState() ||
                    EnumConstants.identityAuthStatus.NOT_PASS.getValue().intValue() == memberCert.getState()) {
                return "该认证审核过了";
            }

            CountryResult country = dgCountryService.getCountryByShortCode(memberCert.getLiveCountryShortCode());

            if (paramCert.getMemberType() == EnumConstants.MemberType.seller.getValue()) { //审核买手
                UcSeller seller = ucSellerService.queryById(paramCert.getMemberId());
                if (EnumConstants.identityAuthStatus.PASS.getValue().intValue() == paramCert.getState()) { //通过认证申请
                    seller.setRealState(EnumConstants.identityAuthStatus.PASS.getValue());
                    seller.setIsPassIdentity(EnumConstants.YesOrNo.YES.getValue());
                    seller.setIsPassPassport(EnumConstants.YesOrNo.YES.getValue());
                    seller.setIsPassZhima(EnumConstants.YesOrNo.YES.getValue());
                    seller.setRealName(memberCert.getMemberName());
                    seller.setIdCardNumber(memberCert.getIdCardNumber());
                    try {
                        seller.setGender(IDCardUtil.getGenderFromPersonIDCode(memberCert.getIdCardNumber()).getValue());
                        seller.setBirthday(IDCardUtil.getBirthdayFromPersonIDCode(memberCert.getIdCardNumber()));
                    } catch (Throwable t) {
                        throw new BizException("身份证号解析错误");
                    }
                    seller.setLiveCountry(country.getName());
                    seller.setLiveCountryShortCode(country.getShortCode());
                } else if (EnumConstants.identityAuthStatus.NOT_PASS.getValue().intValue() == paramCert.getState()) { //不通过认证申请
                    seller.setRealState(EnumConstants.identityAuthStatus.NOT_PASS.getValue());
                }
                ucSellerService.update(seller);
                CacheUtils.delSellerInfo(seller.getId());

            } else if (paramCert.getMemberType() == EnumConstants.MemberType.buyer.getValue()) {   //审核背包客
                UcBuyer buyer = ucBuyerService.queryById(paramCert.getMemberId());
                if (EnumConstants.identityAuthStatus.PASS.getValue().intValue() == paramCert.getState()) { //通过认证申请
                    buyer.setRealState(EnumConstants.identityAuthStatus.PASS.getValue());
                    buyer.setIsPassIdentity(EnumConstants.YesOrNo.YES.getValue());
                    buyer.setIsPassPassport(EnumConstants.YesOrNo.YES.getValue());
                    buyer.setIsPassZhima(EnumConstants.YesOrNo.YES.getValue());
                    buyer.setRealName(memberCert.getMemberName());
                    buyer.setIdCardNumber(memberCert.getIdCardNumber());
                    try {
                        buyer.setGender(IDCardUtil.getGenderFromPersonIDCode(memberCert.getIdCardNumber()).getValue());
                        buyer.setBirthday(IDCardUtil.getBirthdayFromPersonIDCode(memberCert.getIdCardNumber()));
                    } catch (Throwable t) {
                        throw new BizException("身份证号解析错误");
                    }
                    buyer.setLiveCountry(country.getName());
                    buyer.setLiveCountryShortCode(country.getShortCode());
                    buyer.setIsPack(EnumConstants.YesOrNo.YES.getValue()); //通过认证成为背包客
                } else if (EnumConstants.identityAuthStatus.NOT_PASS.getValue().intValue() == paramCert.getState()) { //不通过认证申请
                    buyer.setRealState(EnumConstants.identityAuthStatus.NOT_PASS.getValue());
                }
                ucBuyerService.update(buyer);
                CacheUtils.delBuyerInfo(buyer.getId());
            }

            //更新认证表状态
            memberCert.setRealRemark(paramCert.getRealRemark());
            if (EnumConstants.identityAuthStatus.PASS.getValue().intValue() == paramCert.getState()) {
                memberCert.setIsPassIdentity(EnumConstants.YesOrNo.YES.getValue());
                memberCert.setIsPassPassport(EnumConstants.YesOrNo.YES.getValue());
                memberCert.setIsPassZhima(EnumConstants.YesOrNo.YES.getValue());
                memberCert.setState(EnumConstants.identityAuthStatus.PASS.getValue());
            } else if (EnumConstants.identityAuthStatus.NOT_PASS.getValue().intValue() == paramCert.getState()) {
                memberCert.setIsPassIdentity(EnumConstants.YesOrNo.NO.getValue());
                memberCert.setIsPassPassport(EnumConstants.YesOrNo.NO.getValue());
                memberCert.setIsPassZhima(EnumConstants.YesOrNo.NO.getValue());
                memberCert.setState(EnumConstants.identityAuthStatus.NOT_PASS.getValue());
            }
            super.update(memberCert);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        } finally {
            CacheUtil.getLockManager().del(LOCK_CERT + paramCert.getId()); //保证被操作的key一定删除，避免死锁
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Object queryNextUnaudit() {
        //获取所有正在被操作的认证ID
        Set<Serializable> keys = redisTemplate.keys(LOCK_CERT + "*");
        Set set = keys.stream().map(s -> (String) s)
                .map(s -> s.split(LOCK_CERT))
                .flatMap(Arrays::stream)
                .filter(s -> !s.equals("")).collect(Collectors.toSet());

        EntityWrapper<UcMemberCert> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("state", EnumConstants.identityAuthStatus.SUBMIT.getValue());
        ew.notIn("id_", set);
        List<UcMemberCert> list = mapper.selectList(ew);
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return "全部都审核完啦";
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, String>> getResultCount(MemberCertCountParam param) {
        return this.memberCertDao.getResultCount(param);
    }

}
