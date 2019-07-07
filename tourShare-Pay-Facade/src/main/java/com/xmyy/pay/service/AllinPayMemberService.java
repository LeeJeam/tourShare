package com.xmyy.pay.service;

import com.xmyy.pay.vo.*;
import com.xmyy.pay.vo.MemberInfoResult;

/**
 * 会员相关接口
 */
public interface AllinPayMemberService {

    /**
     * 创建个人会员
     * @param uuid
     * @return
     */
    Boolean createMember(String uuid);

    /**
     * 获取验证码
     * @param memberId
     * @return
     */
    Object sendVerificationCode(Long memberId, Integer memberType, String phone);

    /**
     * 确认绑定手机
     * @param memberId
     * @param params
     * @return
     */
    Object bindPhone(Long memberId, BindPhoneParam params);

    /**
     * 修改绑定手机
     * @param memberId
     * @param params
     * @return
     */
    Object changeBindPhone(Long memberId, ChangeBindPhoneParam params);

    /**
     * 个人实名认证
     * @param memberId
     * @param params
     * @return
     */
    Object setRealName(Long memberId, SetRealNameParam params);

    /**
     * 获取电子会员签约参数
     * @param memberId
     * @param memberType
     * @return
     */
    Object getSignContract(Long memberId, Integer memberType);

    /**
     * 查询卡BIN信息
     * @param cardNo
     * @return
     */
    Object getBankCardBin(String cardNo);

    /**
     * 我的银行卡列表
     * @param memberId
     * @param memberType
     * @return
     */
    Object queryBankCardList(Long memberId, Integer memberType, String cardNo);

    /**
     * 请求绑定银行卡
     * @param memberId
     * @param params
     * @return
     */
    Object applyBindBankCard(Long memberId, ApplyBindBankCardParam params);

    /**
     * 确认绑定银行卡
     * @param memberId
     * @param params
     * @return
     */
    Object bindBankCard(Long memberId, BindBankCardParam params);

    /**
     * 解除绑定银行卡
     * @param memberId
     * @param params
     * @return
     */
    Object unbindBankCard(Long memberId, UnbindBankCardParam params);

    /**
     * 设置平台交易密码
     * @param memberId
     * @param params
     * @return
     */
    Object setPayPwd(Long memberId, SetPayPwdParam params);

}
