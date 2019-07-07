package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.dao.DgProductEvaluateDao;
import com.xmyy.circle.dto.ApiOrderProductEvaluateDto;
import com.xmyy.circle.mapper.DgOrderEvaluateMapper;
import com.xmyy.circle.mapper.DgProductEvaluateMapper;
import com.xmyy.circle.model.DgOrderEvaluate;
import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.service.DgOrderEvaluateService;
import com.xmyy.circle.vo.OrderEvaluateAddParam;
import com.xmyy.circle.vo.OrderEvaluateListResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.product.service.PtProductService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单买手评价  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = DgOrderEvaluateService.class)
@CacheConfig(cacheNames = "DgOrderEvaluate")
public class DgOrderEvaluateServiceImpl extends BaseServiceImpl<DgOrderEvaluate, DgOrderEvaluateMapper> implements DgOrderEvaluateService {

    @Resource
    DgOrderEvaluateMapper dgOrderEvaluateMapper;
    @Resource
    DgProductEvaluateMapper dgProductEvaluateMapper;
    @Resource
    DgProductEvaluateDao dgProductEvaluateDao;
    @Resource
    DgOrderService dgOrderService;
    @Resource
    UcBuyerService ucBuyerService;
    @Resource
    PtProductService ptProductService;

    @Transactional
    @Override
    public Object add(OrderEvaluateAddParam params, Long userId) {
        Long orderId = params.getOrderId();

        DgOrderEvaluate dgOrderEvaluate = new DgOrderEvaluate();
        dgOrderEvaluate.setOrderId(orderId);
        dgOrderEvaluate.setBuyService(params.getBuyService());
        dgOrderEvaluate.setProductLevel(params.getProductLevel());

        dgOrderEvaluate.setBuyerId(userId);
        dgOrderEvaluate.setCreateBy(userId);

        DgOrder dgOrder = dgOrderService.queryById(orderId);

        if (dgOrder == null) {
            return "不存在该订单";
        }

        Long sellerId = dgOrder.getSellerId();
        Integer isPacker = dgOrder.getIsPacker();
        Integer orderType = dgOrder.getOrderType();

        dgOrderEvaluate.setSellerId(sellerId);
        dgOrderEvaluate.setIsPacker(isPacker);

        EntityWrapper<DgOrderEvaluate> orderEvaluateEw = new EntityWrapper<>();
        orderEvaluateEw.eq("order_id_", orderId);
        List<DgOrderEvaluate> dgOrderEvaluates = dgOrderEvaluateMapper.selectList(orderEvaluateEw);
        if (!CollectionUtils.isEmpty(dgOrderEvaluates)) {
            return "该订单已评价";
        }

        Integer result = dgOrderEvaluateMapper.insert(dgOrderEvaluate);

        //更新订单评价状态
        dgOrder.setEvaluateStatus(EnumConstants.EvaluateStatus.HAS_EVALUATE.getValue());
        dgOrderService.update(dgOrder);

        List<ApiOrderProductEvaluateDto> productEvaluateList = params.getProductEvaluate();
        for (ApiOrderProductEvaluateDto evaluateDto : productEvaluateList) {
            DgProductEvaluate productEvaluate = new DgProductEvaluate();

            productEvaluate.setOrderId(orderId);
            productEvaluate.setProductId(evaluateDto.getProductId());
            productEvaluate.setProductOrderId(evaluateDto.getProductOrderId());

            String content = evaluateDto.getContent();
            if (content == null || content.trim().equals("")) {
                content = "该用户未填写评价~";
            }
            productEvaluate.setContent(content);
            List<String> images = evaluateDto.getImages();
            String picRsurl = StringUtils.join(images, ",");
            productEvaluate.setPicRsurl(picRsurl);

            productEvaluate.setBuyerId(userId);
            productEvaluate.setCreateBy(userId);
            productEvaluate.setSellerId(sellerId);
            productEvaluate.setIsPacker(isPacker);
            productEvaluate.setOrderType(orderType);
            dgProductEvaluateMapper.insert(productEvaluate);

            //计算商品好评度
            Float favorable = dgProductEvaluateDao.getFavorable(evaluateDto.getProductId());
            ptProductService.updateFavorable(evaluateDto.getProductId(), favorable);
        }

        return result;
    }

    @Override
    public Object list(Long orderId, Long userId) {

        if (orderId == null) {
            return "订单ID不能为空";
        }

        EntityWrapper<DgProductEvaluate> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("order_id", orderId);
        ew.eq("seller_id_", userId);
        ew.orderBy("create_time", false);

        List<DgProductEvaluate> evaluateList = dgProductEvaluateMapper.selectList(ew);

        if (CollectionUtils.isEmpty(evaluateList)) {
            return "订单评价为空";
        }

        Long buyerId = evaluateList.get(0).getBuyerId();
        UcBuyer ucBuyer = ucBuyerService.queryById(buyerId);

        if (ucBuyer == null) {
            return "该订单的购买者不存在";
        }

        String nickName = ucBuyer.getNickName();
        String avatarRsurl = ucBuyer.getAvatarRsurl();

        List<OrderEvaluateListResult> productList = evaluateList.stream().map(o -> {
            OrderEvaluateListResult r = InstanceUtil.to(o, OrderEvaluateListResult.class);
            String picRsurl = o.getPicRsurl();
            if (StringUtils.isNotBlank(picRsurl)) {
                String[] picArr = picRsurl.split(",");
                List<String> pics = InstanceUtil.newArrayList();
                for (String pic : picArr) {
                    pics.add(pic);
                }
                r.setPics(pics);
            }
            r.setNickName(nickName);
            r.setAvatarRsurl(avatarRsurl);
            r.setProductType(o.getOrderType());

            Date createTime = o.getCreateTime();
            Date reviewTime = o.getReviewTime();
            if (reviewTime != null) {
                int diffDays = DateUtils.diffDays(createTime, reviewTime);
                r.setDayDiff(diffDays);
            }

            return r;
        }).collect(Collectors.toCollection(ArrayList::new));

        return productList;
    }

}
