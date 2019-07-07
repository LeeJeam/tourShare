package com.xmyy.circle.mapper;

import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.vo.EvaluateByProductIdParam;
import com.xmyy.circle.vo.EvaluateByProductIdResult;
import com.xmyy.circle.vo.ProductEvaluateListParam;
import com.xmyy.circle.vo.ProductEvaluateListResult;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import top.ibase4j.core.base.BaseMapper;

import java.util.List;

public interface DgProductEvaluateMapper extends BaseMapper<DgProductEvaluate> {

    Integer getProductEvaluatePageCount(@Param("params") ProductEvaluateListParam params, @Param("userId") Long userId);

    List<ProductEvaluateListResult> getProductEvaluatePageList(@Param("params") ProductEvaluateListParam params, @Param("userId") Long userId, RowBounds rb);

    Integer getEvaluateByProductIdPageCount(EvaluateByProductIdParam params);

    List<EvaluateByProductIdResult> getEvaluateByProductIdPageList(EvaluateByProductIdParam params, RowBounds rb);

}