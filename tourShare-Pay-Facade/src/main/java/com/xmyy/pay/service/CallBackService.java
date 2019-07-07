package com.xmyy.pay.service;

/**
 * 通联回调  服务接口
 *
 * @author AnCheng
 */
public interface CallBackService {

    /**
     * 会员电子协议签约回调
     * @param rps
     * @return
     */
    Object signContractCallBack(String rps);

    /**
     * 订单结果异步通知
     * @param rps
     * @return
     */
    Object orderPayCallBack(String rps);

}
