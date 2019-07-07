package com.xmyy.product.service;

import com.xmyy.product.model.PtCategory;
import com.xmyy.product.vo.CategoryAddParam;
import com.xmyy.product.vo.CategoryListResult;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * 商品类目后台管理  服务接口
 * @author simon
 */
public interface ManagePtCategoryService extends BaseService<PtCategory> {

    /**
     * 新增类目
     * @param params
     * @return
     */
    Object add(CategoryAddParam params);

    /**
     * 后台管理查询类目列表
     * @param id
     * @return
     */
    List<CategoryListResult> getCategoryList(Long id);

    /**
     * 查询所有二级类目列表
     * @return
     */
    List<CategoryListResult> getCategoryList2();

}