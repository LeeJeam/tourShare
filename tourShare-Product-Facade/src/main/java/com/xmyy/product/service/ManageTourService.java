package com.xmyy.product.service;

import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;

import java.util.List;
import java.util.Map;

/**
 * 行程后台管理  服务接口
 *
 * @author wangzejun
 */
public interface ManageTourService extends BaseService<DgTour> {

    /**
     * 管理端-查询行程信息
     *
     * @param params
     * @return
     */
    ManageTourQueryResult queryForBuyer(ManageTourQueryParam params);

    /**
     * 查询行程目的地统计数
     *
     * @param params
     * @return
     */
    List<ManageTourDestRegionCountResult> queryRegionCount(ManageTourQueryParam params);

    /**
     * 查询行程验证列表数据
     *
     * @param params
     * @return
     */
    Pagination<ManageTourValidateDetailResult> queryForValidate(ManageTourGetValidateParam params);

    /**
     * 统计行程验证情况
     *
     * @param params
     * @return
     */
    ManageTourValidateCountResult countForValidate(ManageTourGetValidateParam params);

    /**
     * 审核行程
     *
     * @param params
     * @return
     */
    Object validateTour(ManageTourValidateParam params, AdminUserInfo userInfo) throws BizException;

    /**
     * 查询下一个未审核行程
     *
     * @return
     */
    ManageTourDetailResult queryNextUnaudit();

    /**
     * 行程置顶
     *
     * @param params
     * @return
     */
    Object setTop(MananageTourTopParam params);

    /**
     * 取消"过期的"行程置顶
     */
    void cancelTopOverTime();

    /**
     * 取消指定的置顶行程
     *
     * @param id
     */
    Object cancleTop(Long id, AdminUserInfo userInfo);

    /**
     * 查询前5个置顶行程
     *
     * @return
     */
    List<ManageTourTopResult> queryForTopList();
}