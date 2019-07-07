package com.xmyy.order.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.order.mapper.DgProductOrderMapper;
import com.xmyy.order.model.DgProductOrder;
import com.xmyy.order.service.DgProductOrderService;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * 预售商品快照表 服务实现类
 *
 * @author AnCheng
 */
@Service(interfaceClass = DgProductOrderService.class)
@CacheConfig(cacheNames = "DgProductOrder")
public class DgProductOrderServiceImpl extends BaseServiceImpl<DgProductOrder, DgProductOrderMapper> implements DgProductOrderService {

    @Resource
    private DgProductOrderMapper productOrderMapper;

    @Override
    public List<DgProductOrder> queryByOrderId(Long orderId) {
        EntityWrapper<DgProductOrder> ew = new EntityWrapper<>();
        ew.eq("order_id_", orderId);
        return productOrderMapper.selectList(ew);
    }

    @Override
    public List<DgProductOrder> queryByTourId(Long id) {
        EntityWrapper<DgProductOrder> ew = new EntityWrapper<>();
        ew.eq("tour_id", id);
        return productOrderMapper.selectList(ew);
    }
}
