package com.xmyy.circle.dao;

import com.xmyy.circle.vo.ProductEvaluateParam;
import com.xmyy.circle.vo.ProductEvaluateResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by yeyu on 2018/6/13.
 * 描述：
 */
public interface DgProductEvaluateDao {

    List<ProductEvaluateResult> queryProductEvaluate(ProductEvaluateParam param);

    Integer countProductEvaluate(ProductEvaluateParam param);

    Float getFavorable(@Param("productId") Long productId);
}
