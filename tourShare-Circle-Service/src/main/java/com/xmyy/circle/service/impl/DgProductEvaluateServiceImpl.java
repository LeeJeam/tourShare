package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.dao.DgProductEvaluateDao;
import com.xmyy.circle.mapper.DgPraiseRecordMapper;
import com.xmyy.circle.mapper.DgProductEvaluateMapper;
import com.xmyy.circle.model.DgPraiseRecord;
import com.xmyy.circle.model.DgProductEvaluate;
import com.xmyy.circle.service.DgPraiseRecordService;
import com.xmyy.circle.service.DgProductEvaluateService;
import com.xmyy.circle.vo.*;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.ServiceCode;
import com.xmyy.common.util.ModelMapUtil;
import com.xmyy.demand.model.DgDemandOrder;
import com.xmyy.demand.service.DgDemandOrderService;
import com.xmyy.order.model.DgOrder;
import com.xmyy.order.model.DgProductOrder;
import com.xmyy.order.service.DgOrderService;
import com.xmyy.order.service.DgProductOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.Constants;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单商品评价  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = DgProductEvaluateService.class)
@CacheConfig(cacheNames = "DgProductEvaluate")
public class DgProductEvaluateServiceImpl extends BaseServiceImpl<DgProductEvaluate, DgProductEvaluateMapper> implements DgProductEvaluateService {

    @Resource
    DgProductEvaluateMapper dgProductEvaluateMapper;
    @Resource
    DgPraiseRecordMapper dgPraiseRecordMapper;
    @Resource
    DgProductOrderService dgProductOrderService;
    @Resource
    private DgPraiseRecordService praiseRecordService;
    @Resource
    private DgProductEvaluateDao dgProductEvaluateDao;
    @Resource
    DgOrderService dgOrderService;
    @Resource
    DgDemandOrderService dgDemandOrderService;

    @Override
    public Object reviewOpen(Long orderId, Long userId) {
        EntityWrapper<DgProductEvaluate> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("order_id", orderId);
        ew.andNew("review_content is null");
        ew.or("review_content = ''");
        List<DgProductEvaluate> evaluateList = dgProductEvaluateMapper.selectList(ew);

        List<ReviewOpenResult> resultList = InstanceUtil.newArrayList();
        for (DgProductEvaluate evaluate : evaluateList) {
            ReviewOpenResult result = new ReviewOpenResult();
            result.setId(evaluate.getId());
            result.setProductId(evaluate.getProductId());
            result.setProductOrderId(evaluate.getProductOrderId());
            result.setOrderId(evaluate.getOrderId());
            result.setProductType(evaluate.getOrderType());

            Integer orderType = evaluate.getOrderType();
            if (orderType == 3) { //需求单
                DgDemandOrder dgDemandOrder = dgDemandOrderService.queryById(evaluate.getProductId());
                if (dgDemandOrder != null) {
                    result.setTitle(dgDemandOrder.getDemandDesc());
                    result.setProductPrice(dgDemandOrder.getBudgetPrice());

                    String demandImages = dgDemandOrder.getDemandImages();
                    if (StringUtils.isNotBlank(demandImages)) {
                        if (demandImages.contains(",")) {
                            result.setProductCover(demandImages.substring(0, demandImages.indexOf(',')));
                        } else {
                            result.setProductCover(demandImages);
                        }
                    }
                }
            } else { //商品订单
                Long productOrderId = evaluate.getProductOrderId();
                DgProductOrder dgProductOrder = dgProductOrderService.queryById(productOrderId);
                if (dgProductOrder != null) {
                    result.setTitle(dgProductOrder.getProductName());
                    result.setSpecParams(dgProductOrder.getSpecParams());
                    result.setProductNum(dgProductOrder.getProductNum());
                    result.setProductCover(dgProductOrder.getProductCover());
                    result.setProductPrice(dgProductOrder.getProductPrice());
                }
            }
            resultList.add(result);
        }

        return resultList;
    }

    @Override
    public Object reviewSave(ReviewParam params, Long userId) {
        Long orderId = params.getOrderId();
        List<ReviewParam.Review> reviews = params.getReviews();
        for (ReviewParam.Review review : reviews) {
            DgProductEvaluate productEvaluate = new DgProductEvaluate();
            productEvaluate.setId(review.getId());
            productEvaluate.setReviewContent(review.getReviewContent());
            productEvaluate.setReviewTime(new Date());
            productEvaluate.setUpdateTime(new Date());
            productEvaluate.setUpdateBy(userId);
            dgProductEvaluateMapper.updateById(productEvaluate);
        }

        EntityWrapper<DgProductEvaluate> ew = new EntityWrapper<>();
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.eq("order_id", orderId);
        ew.andNew("review_content is null");
        ew.or("review_content = ''");
        Integer count = dgProductEvaluateMapper.selectCount(ew);

        //全部已追评，更新订单评价状态
        if (count == 0) {
            DgOrder dgOrder = new DgOrder();
            dgOrder.setId(orderId);
            dgOrder.setEvaluateStatus(EnumConstants.EvaluateStatus.FINISH_EVALUATE.getValue());
            dgOrderService.update(dgOrder);
        }

        return null;
    }

    @Override
    public Pagination<ProductEvaluateListResult> evaluateList(ProductEvaluateListParam params, Long userId) {

        Pagination<ProductEvaluateListResult> resultPagination = new Pagination<>();

        int totalCount = dgProductEvaluateMapper.getProductEvaluatePageCount(params, userId);
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
        List<ProductEvaluateListResult> pageList = dgProductEvaluateMapper.getProductEvaluatePageList(params, userId, rb);

        for (ProductEvaluateListResult result : pageList) {

            if (params.getEvaluateType() == 1) { //商品评价
                DgProductOrder dgProductOrder = dgProductOrderService.queryById(result.getProductOrderId());
                if (dgProductOrder != null) {
                    result.setProductId(dgProductOrder.getProductId());
                    result.setTitle(dgProductOrder.getProductName());
                    result.setProductPrice(dgProductOrder.getProductPrice());
                    result.setProductType(dgProductOrder.getProductType());
                    result.setProductCover(dgProductOrder.getProductCover());
                }
            } else {
                DgDemandOrder dgDemandOrder = dgDemandOrderService.queryById(result.getProductId());
                if (dgDemandOrder != null) {
                    result.setTitle(dgDemandOrder.getDemandDesc());
                    result.setProductPrice(dgDemandOrder.getBudgetPrice());

                    String demandImages = dgDemandOrder.getDemandImages();
                    if (StringUtils.isNotBlank(demandImages)) {
                        if (demandImages.contains(",")) {
                            result.setProductCover(demandImages.substring(0, demandImages.indexOf(',')));
                        } else {
                            result.setProductCover(demandImages);
                        }
                    }
                }
                result.setProductType(EnumConstants.OrderType.DEMAND_SALE.getValue());
            }

            String picRsurl = result.getPicRsurl();
            if (StringUtils.isNotBlank(picRsurl)) {
                String[] picArr = picRsurl.split(",");
                List<String> pics = InstanceUtil.newArrayList();
                for (String pic : picArr) {
                    pics.add(pic);
                }
                result.setPics(pics);
            }
        }


        resultPagination.setTotal(totalCount);
        resultPagination.setRecords(pageList);
        resultPagination.setCurrent(params.getCurrent());
        resultPagination.setSize(params.getSize());

        return resultPagination;
    }

    @Override
    public Object evaluateByProductIdList(EvaluateByProductIdParam params, Long userId) {

        Pagination<EvaluateByProductIdResult> resultPagination = new Pagination<>();
        RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());

        Integer total = dgProductEvaluateMapper.getEvaluateByProductIdPageCount(params);

        List<EvaluateByProductIdResult> resultList = dgProductEvaluateMapper.getEvaluateByProductIdPageList(params, rb);

        for (EvaluateByProductIdResult result : resultList) {
            Long evaluateId = result.getId();

            if (userId == null || userId == 0) {
                result.setIsPraise(0);
            } else {
                EntityWrapper<DgPraiseRecord> ew = new EntityWrapper<>();
                ew.eq("evaluate_id_", evaluateId);
                ew.eq("member_id", userId);
                Integer count = dgPraiseRecordMapper.selectCount(ew);
                if (count > 0) {
                    result.setIsPraise(1);
                } else {
                    result.setIsPraise(0);
                }
            }
            String picRsurl = result.getPicRsurl();
            if (StringUtils.isNotBlank(picRsurl)) {
                String[] picArr = picRsurl.split(",");
                List<String> pics = InstanceUtil.newArrayList();
                for (String pic : picArr) {
                    pics.add(pic);
                }
                result.setPics(pics);
            }
        }

        resultPagination.setTotal(total);
        resultPagination.setRecords(resultList);
        resultPagination.setCurrent(params.getCurrent());
        resultPagination.setSize(params.getSize());
        return resultPagination;
    }


    @Override
    @Transactional
    public Object praise(Long id, Long memberId, Integer memberType) {
        DgProductEvaluate evaluate = super.queryById(id);
        if (evaluate != null) {

            DgPraiseRecord record = new DgPraiseRecord();
            record.setEvaluateId(evaluate.getId());
            record.setMemberId(memberId);
            record.setMemberType(memberType);

            String code = ServiceCode.ServeCode_600.code();
            String msg = null;
            boolean isAdd = praiseRecordService.addPraise(record);

            if (isAdd) {
                evaluate.setPraiseCount(evaluate.getPraiseCount() == null ? 1 : evaluate.getPraiseCount() + 1);
                msg = "点赞成功";
            } else {
                evaluate.setPraiseCount(evaluate.getPraiseCount() > 0 ? evaluate.getPraiseCount() - 1 : 0);
                code = ServiceCode.ServeCode_601.code();
                msg = "取消点赞成功";
            }

            super.update(evaluate);

            return ModelMapUtil.setModelMap(code, msg);
        }
        return null;
    }

    /**
     * 获取评价管理列表
     *
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable(value = Constants.CACHE_NAMESPACE + "queryProductEvaluate")
    @Override
    public Object queryProductEvaluate(ProductEvaluateParam param) {
        Integer pageNum = 1;
        if (param.getCurrent() != null && param.getCurrent() != 0) {
            pageNum = param.getCurrent();
        }
        Integer pageNextSize = 0;
        if (param.getSize() != null && param.getSize() != 0) {
            pageNextSize = (pageNum - 1) * param.getSize();
        }
        param.setCurrent(pageNextSize);
        List<ProductEvaluateResult> resultList = dgProductEvaluateDao.queryProductEvaluate(param);
        Integer total = dgProductEvaluateDao.countProductEvaluate(param);
        //以分页方式返回
        Pagination<ProductEvaluateResult> page = new Pagination<>();
        page.setCurrent(pageNum);
        page.setSize(param.getSize());
        //获取关注总条数
        page.setTotal(total);
        page.setRecords(resultList);
        return page;
    }

    @Override
    public List<DgProductEvaluate> queryByOrderId(Long orderId) {
        EntityWrapper<DgProductEvaluate> ew = new EntityWrapper<>();
        ew.eq("order_id", orderId);
        return dgProductEvaluateMapper.selectList(ew);
    }
}
