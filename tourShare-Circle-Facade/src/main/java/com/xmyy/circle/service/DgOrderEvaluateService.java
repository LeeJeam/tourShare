package com.xmyy.circle.service;

import com.xmyy.circle.model.DgOrderEvaluate;
import com.xmyy.circle.vo.OrderEvaluateAddParam;
import top.ibase4j.core.base.BaseService;

/**
 * <p>
 * 14订单买手评价  服务类
 * </p>
 *
 * @author simon
 * @since 2018-06-05
 */
public interface DgOrderEvaluateService extends BaseService<DgOrderEvaluate> {

    Object add(OrderEvaluateAddParam params, Long userId);

    Object list(Long orderId,Long userId);

}