package com.xmyy.product.service;

import com.xmyy.product.model.PtCategory;
import com.xmyy.product.vo.CategoryListResult;
import top.ibase4j.core.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 商品类目  服务接口
 * @author simon
 */
public interface PtCategoryService extends BaseService<PtCategory> {

    /**
     * APP查询类目列表(关联有品牌的类目)
     * @param categoryId
     * @return
     */
    List<CategoryListResult> getList(Long categoryId);

    /**
     * 查询<类目ID，类目名称>对应的MAP
     * @return
     */
    Map<Long, String> getCategoryNameMap();

    /**
     * 二级类目ID与父ID对应MAP（一级类目ID）
     * @return
     */
    Map<Long, Long> getParentIdMap();

}