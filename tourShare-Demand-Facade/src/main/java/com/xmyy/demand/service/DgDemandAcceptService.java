package com.xmyy.demand.service;

import com.xmyy.demand.model.DgDemandAccept;
import com.xmyy.demand.vo.CancelAcceptParam;
import com.xmyy.demand.vo.AddAcceptParam;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * 需求接单  服务接口
 *
 * @author AnCheng
 */
public interface DgDemandAcceptService extends BaseService<DgDemandAccept> {

    /**
     * 买手/背包客接单
     *
     * @param acceptParams
     * @param memberId
     * @return
     */
    Object accept(AddAcceptParam acceptParams, Long memberId);

    /**
     * 买手/背包客取消接单
     *
     * @param params
     * @param memberId
     * @return
     */
    Object cancelAccept(CancelAcceptParam params, Long memberId);

    /**
     * 订单支付成功，更新需求接单表
     *
     * @param demandId
     * @param sellerId
     * @return
     */
    Object paySuccess(Long demandId, Long sellerId);

    /**
     * 判断能否接单，匹配行程
     *
     * @param id
     * @param isPacker
     * @param memberId
     * @return
     */
    Object canAccept(Long id, Integer isPacker, Long memberId);

}