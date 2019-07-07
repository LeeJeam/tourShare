package com.xmyy.circle.service;

import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.vo.EvaluateByProductIdParam;
import com.xmyy.circle.vo.ProductEvaluateListParam;
import com.xmyy.circle.vo.ProductEvaluateListResult;
import com.xmyy.circle.vo.ProductEvaluateParam;
import com.xmyy.circle.vo.ReviewParam;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.support.Pagination;

import java.util.List;

/**
 * <p>
 * 14订单商品评价  服务类
 * </p>
 *
 * @author simon
 * @since 2018-06-05
 */
public interface DgProductEvaluateService extends BaseService<DgProductEvaluate> {

    Object reviewOpen(Long id,Long userId);

    Object reviewSave(ReviewParam params, Long userId);

    Pagination<ProductEvaluateListResult> evaluateList(ProductEvaluateListParam params, Long userId);

    Object evaluateByProductIdList(EvaluateByProductIdParam params, Long userId);

    Object praise(Long id, Long currUser,Integer memberType);

    Object queryProductEvaluate(ProductEvaluateParam param);

    List<DgProductEvaluate> queryByOrderId(Long orderId);
}