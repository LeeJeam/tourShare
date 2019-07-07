package com.xmyy.product.service;

import com.xmyy.product.model.PtBrand;
import com.xmyy.product.vo.BrandListParam;
import com.xmyy.product.vo.BrandSaveParam;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;

import java.util.Map;

/**
 * 品牌后台管理  服务接口
 *
 * @author simon
 */
public interface ManagePtBrandService extends BaseService<PtBrand> {

    /**
     * 查询品牌列表
     * @param params
     * @return
     */
    Object query(BrandListParam params);

    /**
     * "新增/保存编辑 品牌
     * @param params
     * @return
     * @throws BizException
     */
    Object save(BrandSaveParam params) throws BizException;

    /**
     * 编辑品牌（打开回显）
     * @param id
     * @return
     */
    Object edit(Long id);

    /**
     * 删除品牌
     * @param id
     * @return
     */
    Object deleteBrand(Long id);

    /**
     * 查询品牌列表
     * @param categoryId
     * @param categoryId2
     * @param initial
     * @return
     */
    Object getList(Long categoryId, Long categoryId2, String initial);
	
}