package com.xmyy.product.service;

import com.xmyy.product.model.PtAttr;
import com.xmyy.product.vo.AttrItemAddParam;
import com.xmyy.product.vo.AttrItemListParam;
import com.xmyy.product.vo.AttrModifyParam;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;

/**
 * 模版属性后台管理  服务接口
 * @author simon
 */
public interface ManagePtAttrService extends BaseService<PtAttr> {

    /**
     * 新增属性、属性下的属性值
     * @param params
     * @param currUser
     * @return
     */
    Object addAttrItem(AttrItemAddParam params, Long currUser);

    /**
     * 后台-查询出类目下的属性
     * @param param
     * @return
     */
    Object queryAttrItem(AttrItemListParam param);

    /**
     * 后台-修改类目属性
     * @param param
     * @param currUser
     * @return
     * @throws BizException
     */
    Object saveAttrItemModify(AttrModifyParam param, Long currUser) throws BizException;

}