package com.xmyy.demand.service;

import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.vo.*;
import top.ibase4j.core.base.BaseService;

import java.util.List;

/**
 * 需求  服务接口
 */
public interface DgDemandOrderService extends BaseService<DgDemandOrder> {

    /**
     * 获取需求详情，若需求单状态为"待指定"，则查询接单表
     * @param id
     * @param role
     * @param memberId
     * @return
     */
    Object queryDemandDetail(Long id, Integer role, Long memberId);

    /**
     * 我的需求列表
     * @param role
     * @param memberId
     * @param pageIndex
     * @param size
     * @return
     */
    Object queryDemandList(Integer role, Long memberId, Integer pageIndex, Integer size);

    /**
     * 取消需求
     * @param params
     * @param memberId
     * @return
     */
    Object cancelDemandOrder(CancleDemandParam params, Long memberId);

    /**
     * 添加辛苦费
     * @param params
     * @param memberId
     * @return
     */
    Object addToilsome(AddToilsomeParam params, Long memberId);

    /**
     * 订单ID查询需求
     * @param orderId
     * @return
     */
    DgDemandOrder queryByOrderId(Long orderId);

    /**
     * 获取接单人列表
     * @param id
     * @param memberId
     * @return
     */
    Object listAcceptor(Long id, Long memberId);

    /**
     * 指定接单人，生成待支付订单，获取支付所需数据
     * @param params
     * @param memberId
     * @return
     */
    Object appoint(DemandToOrderParam params, Long memberId);

    /**
     * 买家查询下架需求
     * @param id
     * @param current
     * @param size
     * @return
     */
    Object queryCancelDemandList(Long id, Integer current, Integer size);

    /**
     * 删除需求
     * @param params
     * @param memberId
     * @return
     */
    Object deleteDemand(DeleteDemandParam params, Long memberId);

    /**
     * 创建"待支付"需求单
     * @param params
     * @param memberId
     * @return
     */
    Object createDemand(AddDemandParam params, Long memberId);

    /**
     * 查询待接单需求列表
     * @param tag
     * @param pageSize
     * @param pageIndex
     * @param memberId
     * @param isPacker
     * @return
     */
    Object queryWaitingAcceptList(String tag, Integer pageSize, Integer pageIndex, Long memberId, Integer isPacker);

    /**
     * 未登录查询需求列表
     * @param pageIndex
     * @param tag
     * @return
     */
    Object queryListNoLogin(Integer pageIndex, Integer pageSize, String tag);

    /**
     * 未登录需求详情
     * @param id
     * @return
     */
    Object queryDetailNoLogin(Long id);

    /**
     * 重新发布需求
     * @param id
     * @param memberId
     * @return
     */
    Object againRelease(Long id, Long memberId);

    /**
     * 支付需求
     * @param params
     * @param memberId
     * @return
     */
    Object payForDemand(PayDemandParam params, Long memberId);

    /**
     * 搜索需求
     * @param keyword
     * @param pageIndex
     * @param memberId
     * @param isPacker
     * @param sortType
     * @param sortName
     * @param tag
     * @return
     */
    Object searchDemand(String keyword,Integer pageIndex, Long memberId,Integer isPacker, Integer sortType,String sortName,String tag);

    /**
     * 根据行程id查询需求单
     * @param id 行程id
     * @return 需求单列表
     */
    List<DgDemandOrder> queryByTourId(Long id);

    /**
     * 下架行程取消需求
     * @param tourId
     * @return
     */
    boolean cancelDemandByTourId(Long tourId);

    /**
     * 超过收货时间，且没有形成订单的需求，更新为“已失效”
     */
    void closeDeliveryTimeOutDemand();

    /**
     * 获取买家指定过的买手ID列表
     * @param buyerId
     * @param current
     * @param size
     * @return
     */
    List<Long> getSellerIds(Long buyerId, Integer current, Integer size);

}