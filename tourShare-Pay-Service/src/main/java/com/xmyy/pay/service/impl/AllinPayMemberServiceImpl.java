package com.xmyy.pay.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.codingapi.tx.annotation.TxTransaction;
import com.xmyy.cert.model.UcMemberCert;
import com.xmyy.cert.service.UcMemberCertService;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.CacheUtils;
import com.xmyy.common.util.PasswordUtil;
import com.xmyy.common.util.ToolsUtil;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcPayPassword;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.DgSmsRecordService;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcPayPasswordService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.pay.allinpay.ServerFactory;
import com.xmyy.pay.allinpay.YunResponse;
import com.xmyy.pay.allinpay.constants.AllinPayEnum;
import com.xmyy.pay.allinpay.constants.Server;
import com.xmyy.pay.allinpay.constants.YunConfig;
import com.xmyy.pay.allinpay.request.*;
import com.xmyy.pay.allinpay.response.*;
import com.xmyy.pay.core.YunClient;
import com.xmyy.pay.core.util.RSAUtil;
import com.xmyy.pay.service.AllinPayMemberService;
import com.xmyy.pay.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.util.CacheUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.xmyy.common.Constants.*;
import static com.xmyy.common.util.ModelMapUtil.setModelMap;

/**
 * 通联会员接口 服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = AllinPayMemberService.class)
//@CacheConfig(cacheNames = "AllinPayMember")
public class AllinPayMemberServiceImpl implements AllinPayMemberService {
    private static Logger logger = LoggerFactory.getLogger(AllinPayMemberServiceImpl.class);
    private static YunClient yunClient = YunClient.getInstance();

    @Resource
    private UcSellerService sellerService;
    @Resource
    private UcBuyerService buyerService;
    @Resource
    private UcMemberCertService certService;
    @Resource
    private UcPayPasswordService payPasswordService;
    @Resource
    private DgSmsRecordService dgSmsRecordService;

    @Override
    public Boolean createMember(String uuid) {
        ServerHandler handler = ServerFactory.memberHandler(Server.Member.CREATEMEMBER);
        CreateMemberParams params = new CreateMemberParams();
        params.setBizUserId(uuid);
        params.setMemberType(AllinPayEnum.MemberType.PERSON.getValue());
        params.setSource(AllinPayEnum.Source.MOBILE.getValue());
        YunResponse response = yunClient.doRequest(handler, params, CreateMemberResult.class);
        if (!response.isOK()) {
            logger.info(response.getMessage());
            return false;
        }
        return response.isOK();
    }


    @Override
    @Transactional(readOnly = true)
    public Object sendVerificationCode(Long memberId, Integer memberType, String phone) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, memberType);
        if (memberInfo == null) {
            return "获取用户信息失败";
        }
        if (memberInfo.getMobile() == null || !ToolsUtil.isMobile(memberInfo.getMobile())) {
            return "手机号格式不正确";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.SENDVERIFICATIONCODE);
        SendVerificationCodeParams params = new SendVerificationCodeParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setPhone(phone);
        params.setVerificationCodeType(AllinPayEnum.VerfificationCodeType.BINDPHONE.getValue());
        YunResponse response = yunClient.doRequest(handler, params, SendVerificationCodeResult.class);
        if (!response.isOK()) {
            logger.info("通联绑定手机获取验证码失败，phone：{}，失败原因：{}", phone, response.getMessage());
            return response.getMessage();
        }

        ArrayList<String> result = new ArrayList<>();
        result.add(phone);
//        return ModelMapUtil.setModelMap(ServiceCode.ServeCode_602, phone);
        return result;
    }


    @Override
    @Transactional
    public Object bindPhone(Long memberId, BindPhoneParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, p.getMemberType());
        if (memberInfo == null) {
            return "获取用户信息失败";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.BINDPHONE);
        BindPhoneParams params = new BindPhoneParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setPhone(p.getPhone());
        params.setVerificationCode(p.getVerificationCode());
        YunResponse response = yunClient.doRequest(handler, params, BindPhoneResult.class);
        if (!response.isOK()) {
            logger.info("通联绑定手机失败，phone：{}，失败原因：{}", p.getPhone(), response.getMessage());
            return response.getMessage();
        }

        //更新用户绑定手机数据，删除对应缓存数据
        if (p.getMemberType() == EnumConstants.MemberType.seller.getValue()) {
            UcSeller seller = sellerService.queryById(memberId);
            seller.setBindPhone(p.getPhone());
            sellerService.update(seller);
            CacheUtils.delSellerInfo(memberId);
        } else {
            UcBuyer buyer = buyerService.queryById(memberId);
            buyer.setBindPhone(p.getPhone());
            buyerService.update(buyer);
            CacheUtils.delBuyerInfo(memberId);
        }

        return null;
    }


    @Override
    @Transactional
    public Object changeBindPhone(Long memberId, ChangeBindPhoneParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, p.getMemberType());
        if (memberInfo == null) {
            return "获取用户信息失败";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.CHANGEBINDPHONE);
        ChangeBindPhoneParams params = new ChangeBindPhoneParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setNewPhone(p.getNewPhone());
        params.setOldPhone(p.getOldPhone());
        params.setNewVerificationCode(p.getNewVerificationCode());
        YunResponse response = yunClient.doRequest(handler, params, ChangeBindPhoneResult.class);
        if (!response.isOK()) {
            logger.info("通联更改绑定手机失败，oldPhone：{}，newPhone：{}，失败原因：{}", p.getOldPhone(), p.getNewPhone(), response.getMessage());
            return response.getMessage();
        }

        //更新用户绑定手机数据，删除对应缓存数据
        if (p.getMemberType() == EnumConstants.MemberType.seller.getValue()) {
            UcSeller seller = sellerService.queryById(memberId);
            seller.setBindPhone(p.getNewPhone());
            sellerService.update(seller);
            CacheUtils.delSellerInfo(memberId);
        } else {
            UcBuyer buyer = buyerService.queryById(memberId);
            buyer.setBindPhone(p.getNewPhone());
            buyerService.update(buyer);
            CacheUtils.delBuyerInfo(memberId);
        }
        return null;
    }


    @Override
    @Transactional
    @TxTransaction
    public Object setRealName(Long memberId, SetRealNameParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, p.getMemberType());
        if (memberInfo == null) {
            return "获取用户信息失败";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.SETREALNAME);
        SetRealNameParams params = new SetRealNameParams();
        params.setBizUserId(memberInfo.getUuid());
        params.setName(p.getName());
        params.setIdentityType(AllinPayEnum.IdentityType.IDENTITY.getValue());
        try {
            params.setIdentityNo(RSAUtil.encrypt(p.getIdentityNo()));
        } catch (Exception e) {
            e.printStackTrace();
            return "实名认证信息加密失败";
        }
        YunResponse response = yunClient.doRequest(handler, params, SetRealNameResult.class);

        if (!response.isOK()) {
            logger.info("通联实名认证失败：{}", response.getMessage());
            //身份证认证失败，更新认证信息为“实名认证失败”
            UcMemberCert memberCert = new UcMemberCert();
            memberCert.setId(p.getId());
            memberCert.setState(EnumConstants.identityAuthStatus.NOT_PASS.getValue());
            memberCert.setRealRemark("实名认证失败：" + response.getMessage());
            certService.update(memberCert);

            return response.getMessage();
        }

        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public Object getSignContract(Long memberId, Integer memberType) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, memberType);
        if (memberInfo == null) {
            return "获取用户信息失败";
        }

        //封装数据
        HashMap<String, Object> param = new HashMap<>();
        param.put("bizUserId", memberInfo.getUuid());
        param.put("memberType", AllinPayEnum.MemberType.PERSON.getValue());
        param.put("jumpUrl", YunConfig.SIGN_CONTRACT_SUCCESS_FRONT_URL);
        param.put("backUrl", YunConfig.SIGN_CONTRACT_SUCCESS_BACK_URL);
        param.put("source", AllinPayEnum.Source.MOBILE.getValue());

        try {
            return yunClient.getRequest("MemberService", "signContract", param);
        } catch (Exception e) {
            e.printStackTrace();
            return "获取参数失败";
        }
    }


    @Override
    public Object getBankCardBin(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            return "银行卡号不能为空";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.GETBANKCARDBIN);
        GetBankCardBinParams params = new GetBankCardBinParams();
        try {
            params.setCardNo(RSAUtil.encrypt(cardNo));
        } catch (Exception e) {
            e.printStackTrace();
            return "银行卡号加密失败";
        }

        YunResponse response = yunClient.doRequest(handler, params, GetBankCardBinResult.class);
        if (response.isOK()) {
            String cardBinInfo = ((GetBankCardBinResult) response.getResult()).getCardBinInfo();
            return JSON.parseObject(cardBinInfo, BankCardBinResult.class);
        } else {
            logger.info(response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryBankCardList(Long memberId, Integer memberType, String cardNo) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, memberType);
        if (memberInfo == null) {
            return "获取用户信息失败";
        }

        //调用查询绑定银行卡接口
        ServerHandler handler = ServerFactory.memberHandler(Server.Member.QUERYBANKCARD);
        QueryBankCardParams params = new QueryBankCardParams();
        params.setBizUserId(memberInfo.getUuid());
        if (!StringUtils.isEmpty(cardNo)) {
            try {
                params.setCardNo(RSAUtil.encrypt(cardNo));
            } catch (Exception e) {
                e.printStackTrace();
                return "银行卡号加密失败";
            }
        }
        YunResponse response = yunClient.doRequest(handler, params, QueryBankCardResult.class);

        if (response.isOK()) {
            String bindCardList = ((QueryBankCardResult) response.getResult()).getBindCardList();
            List<BankCardResult> resultList = JSON.parseArray(bindCardList, BankCardResult.class);
            for (BankCardResult result : resultList) {
                try {
                    result.setBankCardNo(RSAUtil.dencrypt(result.getBankCardNo()));
                } catch (Exception e) {
                    e.printStackTrace();
                    return "银行卡号解密失败";
                }
                result.setIsQuickPayCard(result.getIsQUICKPAYCard() ? 1 : 0);
                //TODO 补充银行卡其他信息
//                result.setBankCode();
//                result.setLogo();
//                result.setHotline();
            }
            return resultList;
        } else {
            logger.info("查询绑定银行卡失败，bizUserId：{}，失败原因：{}", memberInfo.getUuid(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object applyBindBankCard(Long memberId, ApplyBindBankCardParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, p.getMemberType());
        if (StringUtils.isEmpty(memberInfo.getUuid())) {
            return "获取用户UUID失败";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.APPLYBINDBANKCARD);
        ApplyBindBankCardParams params = new ApplyBindBankCardParams();
        params.setBizUserId(memberInfo.getUuid());
        try {
            params.setCardNo(RSAUtil.encrypt(p.getCardNo()));
        } catch (Exception e) {
            e.printStackTrace();
            return "身份证号加密失败";
        }
        params.setPhone(p.getPhone());
        params.setName(p.getName());
        params.setCardCheck(p.getCardCheck());
        params.setIdentityType(AllinPayEnum.IdentityType.IDENTITY.getValue());
        try {
            params.setIdentityNo(RSAUtil.encrypt(p.getIdentityNo()));
        } catch (Exception e) {
            e.printStackTrace();
            return "银行卡加密失败";
        }
        YunResponse response = yunClient.doRequest(handler, params, ApplyBindBankCardResult.class);

        if (response.isOK()) {
            ApplyBindBankCardMyResult mResult = new ApplyBindBankCardMyResult();
            ApplyBindBankCardResult result = (ApplyBindBankCardResult) response.getResult();
            mResult.setBizUserId(result.getBizUserId());
            mResult.setTranceNum(result.getTranceNum());
            mResult.setTransDate(result.getTransDate());
            mResult.setBankName(result.getBankName());
            mResult.setCardType(result.getCardType());
            mResult.setPhone(p.getPhone());
            return mResult;
        } else {
            logger.info(response.getMessage());
            logger.info("申请绑卡失败，bizUserId：{}，失败原因：{}", memberInfo.getUuid(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    public Object bindBankCard(Long memberId, BindBankCardParam p) {
        ServerHandler handler = ServerFactory.memberHandler(Server.Member.BINDBANKCARD);
        BindBankCardParams params = new BindBankCardParams();
        params.setBizUserId(p.getBizUserId());
        params.setTranceNum(p.getTranceNum());
        params.setPhone(p.getPhone());
        params.setVerificationCode(p.getVerificationCode());
        YunResponse response = yunClient.doRequest(handler, params, BindBankCardResult.class);

        if (response.isOK()) {
            return null;
        } else {
            logger.info(response.getMessage());
            logger.info("确认绑卡失败，bizUserId：{}，失败原因：{}", p.getBizUserId(), response.getMessage());
            return response.getMessage();
        }
    }


    @Override
    @Transactional(readOnly = true)
    public Object unbindBankCard(Long memberId, UnbindBankCardParam p) {
        MemberInfo memberInfo = sellerService.getMemberInfo(memberId, p.getMemberType());
        if (StringUtils.isEmpty(memberInfo.getUuid())) {
            return "获取用户UUID失败";
        }

        ServerHandler handler = ServerFactory.memberHandler(Server.Member.UNBINDBANKCARD);
        UnbindBankCardParams params = new UnbindBankCardParams();
        params.setBizUserId(memberInfo.getUuid());
        try {
            params.setCardNo(RSAUtil.encrypt(p.getCardNo()));
        } catch (Exception e) {
            e.printStackTrace();
            return "银行卡加密失败";
        }
        YunResponse response = yunClient.doRequest(handler, params, BindBankCardResult.class);

        if (response.isOK()) {
            return null;
        } else {
            logger.info("解绑银行卡失败，bizUserId：{}，失败原因：{}", memberInfo.getUuid(), response.getMessage());
            return response.getMessage();
        }
    }


    /**
     * 设置支付密码三个步骤：
     * 1、获取验证码：2个参数，手机号、用户类型
     * 2、验证输入的验证码：3个参数，手机号、用户类型、验证码
     * 3、设置支付密码：4个参数
     */
    @Override
    @Transactional
    @TxTransaction
    public Object setPayPwd(Long memberId, SetPayPwdParam params) {
        if (!ToolsUtil.isMobile(params.getMobile())) {
            return "请输入有效的手机号码";
        }

        //1、发送验证码
        if (StringUtils.isEmpty(params.getSmsCode())) {
            String smsInRedis = null;
            if (params.getMemberType() == EnumConstants.MemberType.seller.getValue()) {
                smsInRedis = dgSmsRecordService.sendSmsandCache(REDIS_SELLER_PAYPW_SMS_CODE_COUNT, TEMPLATE_CODE_SETPAYPWD, params.getMobile(), REDIS_SELLER_PAYPW_SMS_CODE);
            } else if (params.getMemberType() == EnumConstants.MemberType.buyer.getValue()) {
                smsInRedis = dgSmsRecordService.sendSmsandCache(REDIS_BUYER_PAYPW_SMS_CODE_COUNT, TEMPLATE_CODE_SETPAYPWD, params.getMobile(), REDIS_BUYER_PAYPW_SMS_CODE);
            }

            if (!com.alibaba.dubbo.common.utils.StringUtils.isBlank(smsInRedis)) {
                return smsInRedis;
            }

            return setModelMap(ServiceCode.ServeCode_600, "短信验证码已发送，请您注意查收");
        }

        //2、验证码有传入，校验验证码
        Object redisSmsCode = null;
        if (params.getMemberType() == EnumConstants.MemberType.seller.getValue()) {
            redisSmsCode = CacheUtil.getCache().get(REDIS_SELLER_PAYPW_SMS_CODE + params.getMobile());
        } else if (params.getMemberType() == EnumConstants.MemberType.buyer.getValue()) {
            redisSmsCode = CacheUtil.getCache().get(REDIS_BUYER_PAYPW_SMS_CODE + params.getMobile());
        }
        if (redisSmsCode == null || !redisSmsCode.toString().equals(params.getSmsCode())) {
            return "短信验证码不正确";
        } else if (StringUtils.isBlank(params.getPayPwd())) {
            return setModelMap(ServiceCode.ServeCode_601, "较验验证码成功");
        }

        //3、设置/修改 支付密码
        if (params.getMemberType() == EnumConstants.MemberType.seller.getValue()) {
            UcSeller seller = sellerService.queryById(memberId);
            if (seller != null) {
                Long payPwdId = getPayPwdId(params.getPayPwd());
                seller.setPayPasswordId(payPwdId);
                sellerService.update(seller);
                CacheUtils.delSellerInfo(memberId);
            } else {
                return "用户不存在";
            }
        } else if (params.getMemberType() == EnumConstants.MemberType.buyer.getValue()) {
            UcBuyer buyer = buyerService.queryById(memberId);
            if (buyer != null) {
                Long payPwdId = getPayPwdId(params.getPayPwd());
                buyer.setPayPasswordId(payPwdId);
                buyerService.update(buyer);
                CacheUtils.delBuyerInfo(memberId);
            } else {
                return "用户不存在";
            }
        }
        return null;
    }


    /**
     * 创建支付密码（加密），返回ID
     */
    private Long getPayPwdId(String payPwd) {
        UcPayPassword password = new UcPayPassword();
        password.setPassword(PasswordUtil.encodePassword(payPwd));
        password.setCreateTime(new Date());
        return payPasswordService.update(password).getId();
    }

}
