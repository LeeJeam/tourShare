package com.xmyy.pay.service;

import com.xmyy.pay.vo.*;

/**
 * 通联支付相关  服务接口
 */
public interface AllinPayOrderService {

    /**
     * 托管代收
     * @param params
     * @return
     */
    Object agentCollectApply(AgentCollectApplyParam params);

    /**
     * 获取快捷支付（前台短信方式）请求参数
     * @param bizUserId
     * @param bizOrderNo
     * @param verificationCode
     * @return
     */
//    Object getQuickPayParam(String bizUserId, String bizOrderNo, String verificationCode);

    /**
     * 托管代付
     * @param params
     * @return
     */
    Object signalAgentPay(SignalAgentPayParam params);

    /**
     * 申请退款
     * @param params
     * @return
     */
    Object refund(RefundParam params);

    /**
     * 查询账户余额
     * @param memberId
     * @param memberType
     * @return
     */
    Object queryBalance(Long memberId, Integer memberType);

    /**
     * 查询支付状态，若支付状态改变，做对应业务操作
     * @param bizOrderNo
     * @return
     */
    Object getOrderDetail(String bizOrderNo);

    /**
     * 查询账户收支明细
     * @param memberId
     * @param memberType
     * @param current
     * @param size
     * @return
     */
    Object queryInExpDetail(Long memberId, Integer memberType, Integer current, Integer size);

    /**
     * 我的钱包
     * @param memberId
     * @param memberType
     * @param current
     * @param size
     * @return
     */
    Object wallet(Long memberId, Integer memberType, Integer current, Integer size);

    /**
     * 提现界面，获取余额与绑定卡列表
     * @param memberId
     * @param memberType
     * @return
     */
    Object prepareToDraw(Long memberId, Integer memberType);

    /**
     * 提现申请
     * @param memberId
     * @param params
     * @return
     */
    Object withDrawApply(Long memberId, DrawApplyParam params);

    /**
     * 确认支付（后台+短信验证码确认）
     * @param params
     * @return
     */
    Object pay(PayParam params);

    /**
     * 查询提现结果
     * @param memberId
     * @param memberType
     * @param bizOrderNo
     * @return
     */
    Object withDrawStatus(Long memberId, Integer memberType, String bizOrderNo);

}
