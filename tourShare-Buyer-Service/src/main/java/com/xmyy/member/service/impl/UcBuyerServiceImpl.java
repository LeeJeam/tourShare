package com.xmyy.member.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.google.common.collect.Lists;
import com.xmyy.circle.model.DgTags;
import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.service.DgTagsService;
import com.xmyy.circle.vo.ProductEvaluateListParam;
import com.xmyy.circle.vo.ProductEvaluateListResult;
import com.xmyy.common.Constants;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.*;
import com.xmyy.common.vo.BuyerOrderInfo;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.common.vo.QrCodeInfo;
import com.xmyy.member.mapper.UcBuyerMapper;
import com.xmyy.member.mapper.UcPasswordMapper;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcMemberLogin;
import com.xmyy.member.model.UcPassword;
import com.xmyy.member.service.*;
import com.xmyy.member.vo.*;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.pay.service.AllinPayMemberService;
import com.xmyy.product.service.TourService;
import com.xmyy.product.vo.ApiTourInfoInPackerIndexResult;
import com.xmyy.product.vo.ApiTourInfoResult;
import com.xmyy.product.vo.ApiTourSiteInfoResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.support.login.ThirdPartyLoginHelper;
import top.ibase4j.core.support.login.ThirdPartyUser;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.JsonUtil;
import top.ibase4j.core.util.TokenUtil;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.xmyy.common.Constants.*;
import static com.xmyy.common.util.ModelMapUtil.setModelMap;

/**
 * 买家  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = UcBuyerService.class)
@CacheConfig(cacheNames = "UcBuyer")
public class UcBuyerServiceImpl extends BaseServiceImpl<UcBuyer, UcBuyerMapper> implements UcBuyerService {

    @Resource
    private UcPasswordMapper ucPasswordMapper;
    @Resource
    private UcMemberLoginService ucMemberLoginService;
    @Resource
    private DgSmsRecordService dgSmsRecordService;
    @Resource
    private UcMemberLogService ucMemberLogService;
    @Resource
    private TourService tourService;
    @Resource
    private DgProductEvaluateService productEvaluateService;
    @Resource
    private DgTagsService tagsService;
    @Resource
    private PhoneBelongService phoneBelongService;
    @Resource
    private DgOrderService orderService;
    @Resource
    private UcSellerService sellerService;
    @Resource
    private AllinPayMemberService allinPayMemberService;


    @Override
    @Transactional
    public Object add(BuyerAddParam param) {
        //如果传入参数没有登陆地，根据手机号属地获取
        if (StringUtils.isBlank(param.getLoginCountry())) {
            String area = phoneBelongService.getBelongByMobile(param.getMobile());
            if (!StringUtils.isBlank(area)) {
                param.setLoginCountry(area);
            }
        }

        //如果不能获取手机号属地，登陆地为“未知”
        param.setLoginCountry(StringUtils.isBlank(param.getLoginCountry()) ? "未知" : param.getLoginCountry());

        if (!ToolsUtil.isMobile(param.getMobile())) {
            return "请输入有效的手机号码";
        }

        UcBuyer cBuyer = new UcBuyer();
        cBuyer.setMobile(param.getMobile());

        UcBuyer buyer = super.selectOne(cBuyer);
        if (buyer != null) {
            return "该手机号码已注册";
        }

        if (StringUtils.isBlank(param.getSmsCode())) {
            String smsInRedis = dgSmsRecordService.sendSmsandCache(REDIS_BUYER_ADD_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_ADD, param.getMobile(), REDIS_BUYER_ADD_DYNAMIC_CODE);
            if (!StringUtils.isBlank(smsInRedis)) {
                return smsInRedis;
            }
            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请注意查收");
        }

        //从redis获取发送的验证码并验证
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_BUYER_ADD_DYNAMIC_CODE + param.getMobile());
        if (redisSmsCode == null || !redisSmsCode.toString().equals(param.getSmsCode())) {
            return "请输入有效的短信验证码";
        }

        cBuyer.setSource(param.getSource());
        Object obj = createBuyer(param.getPassword(), param.getTwoPassword(), cBuyer);
        if (obj != null) { //没有错误信息继续，有错误信息直接return
            return obj;
        }

        //密码登陆，记录登录日志
        String sessId = saveLogin(cBuyer, param.getLoginCountry(), EnumConstants.LoginType.pwd, param.getSource());

        //保存token信息
        TokenUtil.setTokenInfo(cBuyer.getUuid(), cBuyer.getId().toString());

        //用户操作日志
        ucMemberLogService.saveLog(EnumConstants.MemberEvent.注册.name(), cBuyer.getId(),
                EnumConstants.MemberType.buyer.getValue(), "创建用户", null);

        //买家信息存入redis
        CacheUtils.setMemberInfo(cBuyer.getId(), EnumConstants.MemberType.buyer, InstanceUtil.to(cBuyer, MemberInfo.class));

        MemberInfoResult result = toBuyerInfoResult(cBuyer);
        result.setSessid(sessId);

        //通联创建会员
        if (!allinPayMemberService.createMember(cBuyer.getUuid())) {
            return "用户注册失败";
        }

        return result;
    }


    @Override
    @Transactional
    public Object resetpwd(ModifyPwdParam param) {
        if (!ToolsUtil.isMobile(param.getMobile())) {
            return "请输入有效的手机号码";
        }

        UcBuyer cBuyer = new UcBuyer();
        cBuyer.setMobile(param.getMobile());
        cBuyer.setState(EnumConstants.State.NORMAL.value());
        cBuyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
        UcBuyer buyer = super.selectOne(cBuyer);
        if (buyer == null) {
            return "不存在该用户";
        }

        cBuyer = super.queryById(buyer.getId());

        if (StringUtils.isBlank(param.getSmsCode())) {
            String smsInRedis = dgSmsRecordService.sendSmsandCache(REDIS_BUYER_RESETPWD_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_RESTPWD, param.getMobile(), REDIS_BUYER_RESETPWD_DYNAMIC_CODE);

            if (!StringUtils.isBlank(smsInRedis)) {
                return smsInRedis;
            }

            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //get sms code from redis
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_BUYER_RESETPWD_DYNAMIC_CODE + param.getMobile());
        if (redisSmsCode == null || !redisSmsCode.toString().equals(param.getSmsCode())) {
            return "请输入有效的短信验证码";
        }

        if (StringUtils.isBlank(param.getPassword())) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (StringUtils.isBlank(param.getTwoPassword())) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (!param.getPassword().equals(param.getTwoPassword())) {
            return "密码输入不一致";
        }

        if (!ToolsUtil.isYooyopwd(param.getPassword())) {
            return "密码必须为6-16位，数字与字母组合";
        }

        //set 密码 to redis
        CacheUtils.setRedisPassword("buyer", param.getPassword(), cBuyer.getMobile());

        UcPassword password = ucPasswordMapper.selectById(cBuyer.getPasswordId());
        password.setPassword(PasswordUtil.encodePassword(param.getPassword()));
        password.setCreateTime(new Date());
        this.ucPasswordMapper.updateById(password);

        ucMemberLogService.saveLog(EnumConstants.MemberEvent.密码重置.name(), cBuyer.getId(),
                EnumConstants.MemberType.buyer.getValue(), "密码重置", null);

        return null;
    }


    @Override
    @Transactional
    public Object login(LoginParam param) {
        if (StringUtils.isBlank(param.getLoginCountry())) {
            String area = phoneBelongService.getBelongByMobile(param.getMobile());
            if (!StringUtils.isBlank(area)) {
                param.setLoginCountry(area);
            }
        }

        param.setLoginCountry(StringUtils.isBlank(param.getLoginCountry()) ? "未知" : param.getLoginCountry());

        UcBuyer cBuyer = new UcBuyer();
        cBuyer.setMobile(param.getMobile());
        cBuyer.setState(EnumConstants.State.NORMAL.value());
        cBuyer.setEnable(EnumConstants.YesOrNo.YES.getValue());

        UcBuyer resultBuyer = super.selectOne(cBuyer);

        if (resultBuyer == null) {
            return ModelMapUtil.setModelMap(ServiceCode.ServeCode_600, "该手机号还未注册");
        }

        // String password = (String)CacheUtil.getCache().get("S:"+top.ibase4j.core.Constants.MSGCHKTYPE.LOGIN+"buyer-"+ param.getMobile());
        String password = (String) CacheUtil.getLockManager().hget("S:iBase4J:buyer:password:", param.getMobile());
        if (PasswordUtil.verifyPassword(param.getPassword(), password)) { //匹配redis中保存的密码
            String sessId = saveLogin(resultBuyer, param.getLoginCountry(), EnumConstants.LoginType.pwd, param.getLoginSource());

            MemberInfoResult result = toBuyerInfoResult(resultBuyer);
            result.setLoginCountry(param.getLoginCountry());
            result.setSessid(sessId);
            return result;
        } else {
            UcPassword cpassword = ucPasswordMapper.selectById(resultBuyer.getPasswordId());
            if (cpassword != null && PasswordUtil.verifyPassword(param.getPassword(), cpassword.getPassword())) {
                //匹配MySQL中的密码
                String sessId = saveLogin(resultBuyer, param.getLoginCountry(), EnumConstants.LoginType.pwd, param.getLoginSource());

                MemberInfoResult result = toBuyerInfoResult(resultBuyer);
                result.setLoginCountry(param.getLoginCountry());
                result.setSessid(sessId);

                //set 密码 to redis
                CacheUtils.setRedisPassword("buyer", param.getPassword(), param.getMobile());

                return result;
            }
            return "用户名或密码无效";
        }
    }


    @Override
    @Transactional
    public Object quicklogin(BuyerQuickLoginParam param) {
        if (StringUtils.isBlank(param.getLoginCountry())) {
            String area = phoneBelongService.getBelongByMobile(param.getMobile());
            if (!StringUtils.isBlank(area)) {
                param.setLoginCountry(area);
            }
        }
        param.setLoginCountry(StringUtils.isBlank(param.getLoginCountry()) ? "未知" : param.getLoginCountry());

        UcBuyer buyer = new UcBuyer();
        buyer.setMobile(param.getMobile());
        buyer.setState(EnumConstants.State.NORMAL.value());
        buyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
        UcBuyer cBuyer = super.selectOne(buyer);

        if (StringUtils.isBlank(param.getSmsCode())) {
            String smsCache = dgSmsRecordService.sendSmsandCache(REDIS_BUYER_QUICKLOGIN_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_LOGIN, param.getMobile(), REDIS_BUYER_QUICKLOGIN_DYNAMIC_CODE);
            if (!StringUtils.isBlank(smsCache)) {
                return smsCache;
            }
            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //get sms code from redis
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_BUYER_QUICKLOGIN_DYNAMIC_CODE + param.getMobile());
        if (redisSmsCode == null || !redisSmsCode.toString().equals(param.getSmsCode())) {
            return "请输入有效的短信验证码";
        }

        if (cBuyer == null) {
            cBuyer = new UcBuyer();
            cBuyer.setMobile(param.getMobile());
            Object obj = createBuyer(param.getPassword(), param.getTwoPassword(), cBuyer);
            if (obj != null) {
                return obj;
            }

            ucMemberLogService.saveLog(EnumConstants.MemberEvent.注册.name(), cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(), "创建用户", null);

            //通联创建会员
            if (!allinPayMemberService.createMember(cBuyer.getUuid())) {
                return "用户注册失败";
            }
        }

        String sessId = saveLogin(cBuyer, param.getLoginCountry(), EnumConstants.LoginType.code, param.getLoginSource());

        MemberInfoResult result = toBuyerInfoResult(cBuyer);
        result.setLoginCountry(param.getLoginCountry());
        result.setSessid(sessId);

        return result;
    }


    @Override
    @Transactional
    public Object wxlogin(WxLoginParam param) {
        if (StringUtils.isBlank(param.getLoginCountry())) {
            String area = phoneBelongService.getBelongByMobile(param.getMobile());
            if (!StringUtils.isBlank(area)) {
                param.setLoginCountry(area);
            }
        }

        param.setLoginCountry(StringUtils.isBlank(param.getLoginCountry()) ? "未知" : param.getLoginCountry());

        //根据openid获取用户
        UcBuyer pBuyer = new UcBuyer();
        pBuyer.setWeixinOpenid(param.getOpenId());
        pBuyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
        pBuyer.setState(EnumConstants.State.NORMAL.value());
        UcBuyer buyerQueryByOpenId = super.selectOne(pBuyer);
        if (buyerQueryByOpenId == null && StringUtils.isBlank(param.getMobile())) {
            return setModelMap(ServiceCode.ServeCode_602, "需绑定手机");
        }

        if (buyerQueryByOpenId == null && !StringUtils.isBlank(param.getMobile())) {
            //根据openid找不到用户，且输入了手机号，根据手机号查找
            pBuyer = new UcBuyer();
            pBuyer.setMobile(param.getMobile());
            pBuyer.setState(EnumConstants.State.NORMAL.value());
            pBuyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
            buyerQueryByOpenId = super.selectOne(pBuyer);

            //没有输入验证码，则发送一条
            if (StringUtils.isBlank(param.getSmsCode())) {
                String smsCache = dgSmsRecordService.sendSmsandCache(REDIS_BUYER_WXLOGIN_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_LOGIN, param.getMobile(), REDIS_BUYER_WXLOGIN_DYNAMIC_CODE);
                if (!StringUtils.isBlank(smsCache)) {
                    return smsCache;
                }
                return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
            }

            //get sms code from redis
            Object redisSmsCode = CacheUtil.getCache().get(REDIS_BUYER_WXLOGIN_DYNAMIC_CODE + param.getMobile());
            if (redisSmsCode == null || !redisSmsCode.toString().equals(param.getSmsCode())) {
                return "请输入有效的短信验证码";
            }

            //验证码正确，且根据手机号也找不到用户，注册新用户
            if (buyerQueryByOpenId == null) {
                buyerQueryByOpenId = new UcBuyer();
                buyerQueryByOpenId.setMobile(param.getMobile());
                buyerQueryByOpenId.setSource(param.getLoginSource());

                if (param.getLoginSource().equals(EnumConstants.DeviceType.TXapplet.getValue())) { //小程序注册不需要密码
                    createRoutineBuyer(buyerQueryByOpenId);
                } else {
                    Object obj = createBuyer(param.getPassword(), param.getTwoPassword(), buyerQueryByOpenId);
                    if (obj != null) {
                        return obj;
                    }
                }

                ucMemberLogService.saveLog(EnumConstants.MemberEvent.注册.name(), buyerQueryByOpenId.getId(),
                        EnumConstants.MemberType.buyer.getValue(), "创建用户", null);

                //通联创建会员
                if (!allinPayMemberService.createMember(buyerQueryByOpenId.getUuid())) {
                    return "用户注册失败";
                }

            }

            buyerQueryByOpenId.setWeixinOpenid(param.getOpenId());
        }

        if (StringUtils.isBlank(param.getHeadimgurl())
                && !StringUtils.isBlank(param.getAccessToken())
                && StringUtils.isBlank(buyerQueryByOpenId.getAvatarRsurl())) {
            //
            ThirdPartyUser thirdUser = null;
            try {
                thirdUser = ThirdPartyLoginHelper.getWxUserinfo(param.getAccessToken(), param.getOpenId());
            } catch (Exception e) {
                logger.info("获取微信用户信息失败" + e.getMessage());
            }
            if (thirdUser != null) {
                buyerQueryByOpenId.setAvatarRsurl(thirdUser.getAvatarUrl());
                buyerQueryByOpenId.setNickName(thirdUser.getUserName());
                buyerQueryByOpenId.setGender(!StringUtils.isBlank(thirdUser.getGender()) ? Integer.parseInt(thirdUser.getGender() + 1) : null);
            }
        }

        if (!StringUtils.isBlank(param.getHeadimgurl()) && StringUtils.isBlank(buyerQueryByOpenId.getAvatarRsurl()))
            buyerQueryByOpenId.setAvatarRsurl(param.getHeadimgurl());
        if (!StringUtils.isBlank(param.getNickname()) && StringUtils.isBlank(buyerQueryByOpenId.getNickName()))
            buyerQueryByOpenId.setNickName(param.getNickname());
        if (param.getGender() != null && buyerQueryByOpenId.getGender() == null)
            buyerQueryByOpenId.setGender(param.getGender());

        if (StringUtils.isBlank(buyerQueryByOpenId.getAvatarRsurl())) {
            buyerQueryByOpenId.setAvatarRsurl(Constants.AVATAR_DEFAULT);
        }

        super.update(buyerQueryByOpenId);
        //登录
        String sessId = saveLogin(buyerQueryByOpenId, param.getLoginCountry(), EnumConstants.LoginType.third, param.getLoginSource());

        //set info to redis
        CacheUtils.setMemberInfo(buyerQueryByOpenId.getId(), EnumConstants.MemberType.buyer, InstanceUtil.to(buyerQueryByOpenId, MemberInfo.class));

        MemberInfoResult result = toBuyerInfoResult(buyerQueryByOpenId);
        result.setLoginCountry(param.getLoginCountry());
        result.setSessid(sessId);
        return result;

    }


    @Override
    @Transactional
    public Object updateInfo(MemberUpdateParam params) {
        UcBuyer cBuyer = super.queryById(params.getId());
        if (cBuyer == null) {
            return "不存在该会员";
        }

        if (!StringUtils.isBlank(params.getPersonalizedSignature())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(),
                    "个性签名修改为：" + params.getPersonalizedSignature(), null);
        }

        if (!StringUtils.isBlank(params.getClassifyTags())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(),
                    "用户标签修改为：" + params.getClassifyTags(), null);
        }

        if (!StringUtils.isBlank(params.getLiveCountry())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(),
                    "居住地修改为：" + params.getLiveCountry(), null);
        }

        if (!StringUtils.isBlank(params.getNickName())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(),
                    "昵称修改为：" + params.getNickName(), null);
        }

        if (!StringUtils.isBlank(params.getOftenPlace())) {
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(),
                    "常去地修改为：" + params.getOftenPlace(), null);
        }

        if (!StringUtils.isBlank(params.getAvatarRsurl())) {
            cBuyer.setAvatarRsurl(params.getAvatarRsurl());
            ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                    cBuyer.getId(),
                    EnumConstants.MemberType.buyer.getValue(),
                    "头像变更", null);
        }

        BeanUtils.copyProperties(params, cBuyer);
        if (StringUtils.isBlank(cBuyer.getBuyerNo())) {
            cBuyer.setBuyerNo(BizSequenceUtils.generateMemberNo("BuyerNo"));
        }

        super.update(cBuyer);
        cBuyer = super.queryById(params.getId());

        //清除缓存
        CacheUtils.delBuyerInfo(cBuyer.getId());
        return null;
    }


    @Override
    @Transactional
    public Object updateMobile(Long buyerId, String newMobile, String smsCode) {
        if (buyerId == null) return "请重新登录";

        if (StringUtils.isBlank(newMobile)) {
            return "请输入手机号码";
        }

        if (!ToolsUtil.isMobile(newMobile)) {
            return "请输入有效的手机号码";
        }

        UcBuyer cBuyer = super.queryById(buyerId);

        if (cBuyer == null) {
            return "不存在该会员";
        }

        UcBuyer cb = new UcBuyer();
        cb.setMobile(newMobile);
        cb.setState(EnumConstants.State.NORMAL.value());
        cb.setEnable(EnumConstants.YesOrNo.YES.getValue());

        UcBuyer buyer = super.selectOne(cb);
        if (buyer != null) {
            return "该手机号码已被绑定";
        }

        if (StringUtils.isBlank(smsCode)) {

            String smsCache = dgSmsRecordService.sendSmsandCache(REDIS_BUYER_UPDATEMOBILE_DYNAMIC_CODE_COUNT, TEMPLATE_CODE_RESTPWD, newMobile, REDIS_BUYER_UPDATEMOBILE_DYNAMIC_CODE);
            if (!StringUtils.isBlank(smsCache)) {
                return smsCache;
            }

            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //get redis sms code
        Object redisSmsCode = CacheUtil.getCache().get(REDIS_BUYER_UPDATEMOBILE_DYNAMIC_CODE + newMobile);
        if (redisSmsCode == null) {
            return "请输入有效的短信验证码";
        }

        if (!redisSmsCode.toString().equals(smsCode)) {
            return "请输入有效的短信验证码";
        }

        ucMemberLogService.saveLog(EnumConstants.MemberEvent.修改信息.name(),
                cBuyer.getId(),
                EnumConstants.MemberType.seller.getValue(),
                "手机号码变更为：" + newMobile, null);

        cBuyer.setMobile(newMobile);

        //set info to redis
        CacheUtils.setMemberInfo(cBuyer.getId(), EnumConstants.MemberType.buyer, InstanceUtil.to(cBuyer, MemberInfo.class));
        super.update(cBuyer);
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public MemberInfoResult getInfo(Long id, Integer type) {
        UcBuyer cBuyer = super.queryById(id);
        if (cBuyer == null) {
            return null;
        }

        MemberInfoResult result = InstanceUtil.to(cBuyer, MemberInfoResult.class);
        if (result.getGender() != null) {
            result.setGenderLabel(EnumConstants.Gender.valueOf(result.getGender()).getLabel());
        }
        if (result.getIsPassIdentity() != null) {
            result.setIsPassIdentityLabel(EnumConstants.YesOrNo.of(result.getIsPassIdentity()).getRealLabel());
        }
        if (result.getIsPassPassport() != null) {
            result.setIsPassPassportLabel(EnumConstants.YesOrNo.of(result.getIsPassPassport()).getRealLabel());
        }
        if (result.getIsPassZhima() != null) {
            result.setIsPassZhimaLabel(EnumConstants.YesOrNo.of(result.getIsPassZhima()).getRealLabel());
        }

        if (type == 1) {
            //买家，获取订单统计
            BuyerOrderInfo orderInfo = getBuyerOrderInfo(id);
            result.setOrderInfo(InstanceUtil.to(orderInfo, OrderInfo.class));
        }

        if (type == 2) {
            //背包客
            //生成用户二维码
            QrCodeInfo info = new QrCodeInfo();
            info.setType(2);
            info.setContent(id.toString());
            result.setQrCode(QRCodeUtils.createQrcode(JsonUtil.toJson(info)));

            //进行中的行程
            try {
                ApiTourInfoInPackerIndexResult tourInfo = tourService.getTourInfoForPackerIndex(id);
                result.setTourTitle(String.format("行程（%s次）", tourInfo.getCount()));
                ApiTourInfoResult runningTour = tourInfo.getTour();
                if (runningTour != null) { //有“进行中”的行程
                    //封装行程信息
                    MemberInfoResult.TourDto tourDto = new MemberInfoResult.TourDto();
                    tourDto.setTourId(runningTour.getId());
                    //如果当前年份超过了行程创建的年份，行程创建时间要显示年份
                    StringBuilder sb = new StringBuilder();
                    if (DateUtils.compareYear(new Date(), runningTour.getCreateTime()) == 1) {
                        sb.append(DateUtils.formatDate(runningTour.getCreateTime(), "yyyy年MM月dd日"));
                    } else {
                        sb.append(DateUtils.formatDate(runningTour.getCreateTime(), "MM月dd日"));
                    }
                    sb.append("创建");
                    tourDto.setCreateTimeStr(sb.toString());
                    tourDto.setStatusStr(EnumConstants.TourStatus.RUNNING.getType());

                    //封装行程站点信息
                    List<ApiTourSiteInfoResult> tourSites = runningTour.getTourSites();
                    ArrayList<MemberInfoResult.TourDto.TourSiteDto> tourSiteInfos = tourSites.stream().map(t -> {
                        MemberInfoResult.TourDto.TourSiteDto tourSiteDto = InstanceUtil.to(t, MemberInfoResult.TourDto.TourSiteDto.class);
                        tourSiteDto.setDepartureTimeStr(DateUtils.formatDate(t.getPlanBeginTime(), "MM月dd日"));
                        //如果站点的出发时间年份大于当前查看时间年份，标记为跨年站点
                        if (DateUtils.compareYear(t.getPlanBeginTime(), new Date()) == 1) {
                            sb.append(DateUtils.formatDate(runningTour.getCreateTime(), "yyyy年MM月dd日"));
                            tourSiteDto.setAcrossYear(1);
                        }
                        return tourSiteDto;
                    }).collect(Collectors.toCollection(ArrayList::new));

                    tourDto.setTourSites(tourSiteInfos);
                    result.setTour(tourDto);
                }
            } catch (Exception e) {
                logger.error("获取行程信息报错：{}", e.getMessage());
            }

            //收到的评价
            ProductEvaluateListParam params = new ProductEvaluateListParam();
            params.setCurrent(1);
            params.setSize(2);
            params.setMemberType(EnumConstants.MemberType.packer.getValue());
            params.setEvaluateType(2);
            try {
                Pagination<ProductEvaluateListResult> evaluatePage = productEvaluateService.evaluateList(params, id);
                if (evaluatePage != null && evaluatePage.getTotal() > 0) {
                    List<MemberInfoResult.EvaluateList> evaluateLists = evaluatePage.getRecords().stream().map(o -> {
                        MemberInfoResult.EvaluateList r = InstanceUtil.to(o, MemberInfoResult.EvaluateList.class);
                        List<String> pics = o.getPics();
                        r.setPics(pics);
                        return r;
                    }).collect(Collectors.toCollection(ArrayList::new));
                    result.setEvaluates(evaluateLists);
                }
            } catch (Exception e) {
                logger.error("订单评价获取报错：{}", e.getMessage());
            }

            //个人信息设置提醒与未上传登机牌的行程提醒
            result.setIsFinishSetInfo(sellerService.isFinishSetInfo(id, EnumConstants.MemberType.packer.getValue()));
            result.setNoCheckPicTourIds(tourService.queryTourIdsNoCheckPic(id, EnumConstants.YesOrNo.YES.getValue()));
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public List<UcBuyer> queryBuyerList(List<Long> ids) {
        if (ids != null && ids.size() > 0) {
            EntityWrapper<UcBuyer> ew = new EntityWrapper<>();
            ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ew.in("id_", ids);
            ew.eq("is_virtual", EnumConstants.YesOrNo.NO.getValue());
            return mapper.selectList(ew);
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryBuyerList(Integer memberType) {
        EntityWrapper<UcBuyer> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("is_pack", memberType);
        ew.eq("is_virtual", EnumConstants.YesOrNo.NO.getValue());
        return mapper.selectList(ew);
    }


    /**
     * 先查询指定的背包客，如果没有在按个人标签匹配查询，再没有按照系统默认热门标签查询
     */
    @Override
    @Transactional(readOnly = true)
    public Pagination<SellerPageResult> topList(SellerPageParam param) {
        List<SellerPageResult> list = null;
        EntityWrapper<UcBuyer> ew = new EntityWrapper<>();
        ew.eq("is_pack", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("state", EnumConstants.State.NORMAL.value());
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("is_virtual", EnumConstants.YesOrNo.NO.getValue());
        ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("top_time"));

        int count = mapper.selectCount(ew);
        if (count == 0) {
            ew = new EntityWrapper<>();
            ew.eq("is_pack", EnumConstants.YesOrNo.YES.getValue());
            ew.eq("state", EnumConstants.State.NORMAL.value());
            ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
            ew.eq("is_virtual", EnumConstants.YesOrNo.NO.getValue());

            if (param.getBuyerId() != null) {
                //个人标签
                UcBuyer buyer = super.queryById(param.getBuyerId());
                if (buyer != null && StringUtils.isNotEmpty(buyer.getClassifyTags())) {
                    ew.where("classify_tags REGEXP replace({0}, ',', '|')", buyer.getClassifyTags());
                    count = mapper.selectCount(ew);
                }
            }

            if (count == 0) {
                //系统默认热门标签
                Map<String, Object> map = InstanceUtil.newHashMap("isRecommend", EnumConstants.YesOrNo.YES.getValue());
                ew.eq("is_pack", EnumConstants.YesOrNo.YES.getValue());
                map.put("enable", EnumConstants.YesOrNo.YES.getValue());
                List<DgTags> tags = tagsService.queryList(map);
                if (!CollectionUtils.isEmpty(tags)) {
                    List<String> tagStr = tags.parallelStream().map(DgTags::getTagName).collect(Collectors.toList());
                    ew.where("classify_tags REGEXP replace({0}, ',', '|')", StringUtils.join(tagStr, ","));
                    count = mapper.selectCount(ew);
                }
            }
            ew.orderDesc(Lists.newArrayList("trade_count", "trust_value"));
        }

        if (count > 0) {
            //分页查询，封装订单信息
            RowBounds rb = new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize());
            List<UcBuyer> mlist = mapper.selectPage(rb, ew);
            list = mlist.stream().map(o -> {
                SellerPageResult r = InstanceUtil.to(o, SellerPageResult.class);
                r.setIsSelf(2);
                r.setIsSelfStr("背包客");
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));

            //获取背包客的行程
            List<Long> memberIds = list.parallelStream().map(SellerPageResult::getId).collect(Collectors.toList());
            Map<String, ApiTourInfoResult> tourMap = null;
            try {
                tourMap = tourService.queryNewTourByMemberIds(memberIds);
            } catch (Exception e) {
                logger.error("获取行程出错：" + e.getMessage());
            }

            if (tourMap != null) {
                Map<String, ApiTourInfoResult> finalTourMap = tourMap;
                list.parallelStream().forEach(l -> {
                    if (finalTourMap.get(l.getId().toString()) != null) {
                        List<SellerPageResult.TourSiteDto> tours = new ArrayList<>();
                        InstanceUtil.copyList(finalTourMap.get(l.getId().toString()).getTourSites(), tours, SellerPageResult.TourSiteDto.class);
                        l.setTours(tours);
                    }
                });
            }
        }

        Pagination<SellerPageResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(param.getCurrent());
        page.setSize(param.getSize());
        page.setTotal(count);

        return page;
    }


    @Transactional
    public String saveLogin(UcBuyer cBuyer, String loginCountry, EnumConstants.LoginType code, Integer loginSource) {
        String sessid = UUID.randomUUID().toString();

        //踢出其他登录用户
        ucMemberLoginService.kickOut(cBuyer.getId(), EnumConstants.MemberType.buyer.getValue(), sessid);

        TokenUtil.setTokenInfo(sessid, cBuyer.getId().toString());

        UcMemberLogin login = new UcMemberLogin();
        login.setLoginCode(cBuyer.getRealName());
        login.setLoginCountry(loginCountry);
        login.setLoginType(code.getValue());
        login.setMemberId(cBuyer.getId());
        login.setMemberType(EnumConstants.MemberType.buyer.getValue());
        login.setMobile(cBuyer.getMobile());
        login.setToken(sessid);
        login.setLoginSource(loginSource);
        this.ucMemberLoginService.saveLogin(login);

        cBuyer.setLoginSource(loginSource);
        super.update(cBuyer);

        return sessid;
    }

    @Transactional
    public Object createBuyer(String password, String twoPassword, UcBuyer cBuyer) {
        if (StringUtils.isBlank(password)) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (StringUtils.isBlank(twoPassword)) {
            return setModelMap(ServiceCode.ServeCode_601, "密码不能为空");
        }

        if (!password.equals(twoPassword)) {
            return "密码输入不一致";
        }

        if (!ToolsUtil.isYooyopwd(password)) {
            return "密码必须为6-16位，数字与字母组合";
        }

        cBuyer.setIsPassIdentity(EnumConstants.YesOrNo.NO.getValue());
        cBuyer.setIsPassZhima(EnumConstants.YesOrNo.NO.getValue());
        cBuyer.setIsPassPassport(EnumConstants.YesOrNo.NO.getValue());
        cBuyer.setState(EnumConstants.State.NORMAL.value());
        cBuyer.setUuid(IdWorker.get32UUID().toLowerCase());

        cBuyer.setTrustValue(100);//默认100分
        cBuyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
        cBuyer.setIsPack(EnumConstants.YesOrNo.NO.getValue());
        if (StringUtils.isBlank(cBuyer.getNickName())) {
            cBuyer.setNickName("XBB"
                    + BizSequenceUtils.generateRandomStr(2) +
                    RandomStringUtils.randomNumeric(3) +
                    BizSequenceUtils.generateRandomStr(2));
        }

        if (StringUtils.isBlank(cBuyer.getAvatarRsurl())) {
            cBuyer.setAvatarRsurl(Constants.AVATAR_DEFAULT);
        }

        cBuyer.setBuyerNo(BizSequenceUtils.generateMemberNo("BuyerNo"));

        //密码加密后存入redis
        CacheUtils.setRedisPassword("buyer", password, cBuyer.getMobile());

        super.update(cBuyer);
        UcPassword passw = new UcPassword();
        passw.setPassword(PasswordUtil.encodePassword(password));
        passw.setCreateTime(new Date());
        this.ucPasswordMapper.insert(passw);

        cBuyer.setPasswordId(passw.getId());
        super.update(cBuyer);
        return null;
    }

    private void createRoutineBuyer(UcBuyer cBuyer) {
        cBuyer.setIsPassIdentity(EnumConstants.YesOrNo.NO.getValue());
        cBuyer.setIsPassZhima(EnumConstants.YesOrNo.NO.getValue());
        cBuyer.setIsPassPassport(EnumConstants.YesOrNo.NO.getValue());
        cBuyer.setState(EnumConstants.State.NORMAL.value());
        cBuyer.setUuid(IdWorker.get32UUID().toLowerCase());
        cBuyer.setTrustValue(100); //信用值默认100分
        cBuyer.setEnable(EnumConstants.YesOrNo.YES.getValue());
        cBuyer.setIsPack(EnumConstants.YesOrNo.NO.getValue());
        if (StringUtils.isBlank(cBuyer.getAvatarRsurl())) {
            cBuyer.setAvatarRsurl(Constants.AVATAR_DEFAULT);
        }
        cBuyer.setBuyerNo(BizSequenceUtils.generateMemberNo("BuyerNo"));
        super.update(cBuyer);
    }

    @Override
    @Transactional(readOnly = true)
    public UcBuyer findByUuid(String uuid) {
        EntityWrapper<UcBuyer> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.in("uuid", uuid);
        ew.eq("state", EnumConstants.State.NORMAL.value());
        List<UcBuyer> ucBuyers = mapper.selectList(ew);

        return CollectionUtils.isNotEmpty(ucBuyers) ? ucBuyers.get(0) : null;
    }

    private MemberInfoResult toBuyerInfoResult(UcBuyer cBuyer) {
        MemberInfoResult result = InstanceUtil.to(cBuyer, MemberInfoResult.class);
        if (result.getGender() != null) result.setGenderLabel(EnumConstants.Gender.valueOf(result.getGender()).getLabel());
        if (result.getIsPassIdentity() != null) result.setIsPassIdentityLabel(EnumConstants.YesOrNo.of(result.getIsPassIdentity()).getRealLabel());
        if (result.getIsPassPassport() != null) result.setIsPassPassportLabel(EnumConstants.YesOrNo.of(result.getIsPassPassport()).getRealLabel());
        if (result.getIsPassZhima() != null) result.setIsPassZhimaLabel(EnumConstants.YesOrNo.of(result.getIsPassZhima()).getRealLabel());
        return result;
    }

    /**
     * 获取买家订单数统计
     */
    private BuyerOrderInfo getBuyerOrderInfo(Long id) {
        BuyerOrderInfo info = null;
        String redisBuyerOrderInfo = (String) CacheUtil.getLockManager().hget("S:iBase4J:buyer:orderInfo_key:", id);
        if (StringUtils.isEmpty(redisBuyerOrderInfo)) {
            info = orderService.queryBuyerOrderInfo(id);
            info.setTime(System.currentTimeMillis());
            CacheUtil.getLockManager().hset("S:iBase4J:buyer:orderInfo_key:", id, JSON.toJSONString(info));
        } else {
            info = JSON.parseObject(redisBuyerOrderInfo, BuyerOrderInfo.class);
        }
        return info;
    }
}
