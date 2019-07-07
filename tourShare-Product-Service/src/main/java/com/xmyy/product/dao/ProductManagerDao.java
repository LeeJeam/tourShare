package com.xmyy.product.dao;

import com.xmyy.product.model.PtAttr;
import com.xmyy.product.vo.ManageProductParams;
import com.xmyy.product.vo.ProductManagerResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 商品后台DAO
 *
 * @author simon
 */
public interface ProductManagerDao {

    List<ProductManagerResult> queryProductManageList(ManageProductParams params);

    Integer countProductManage(ManageProductParams param);

    List<PtAttr> queryPtAttrList(Map<String,String> map);
}