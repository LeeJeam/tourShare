package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.util.RedisLock;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.product.dao.DgTourDao;
import com.xmyy.product.mapper.DgTourMapper;
import com.xmyy.product.model.DgTour;
import com.xmyy.product.service.ManageTourService;
import com.xmyy.product.vo.*;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.Pagination;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 行程后台管理  服务实现类
 *
 * @author wangzejun
 */
@Service(interfaceClass = ManageTourService.class)
public class ManageTourServiceImpl extends BaseServiceImpl<DgTour, DgTourMapper> implements ManageTourService {

    @Resource
    private DgTourMapper tourMapper;
    @Resource
    private DgTourDao dgTourDao;
    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    // redis key 验证行程
    private final static String REDISKEY_VALIDATE_TOUR = "manage_validate_tour";


    @Override
    @Transactional
    public Object setTop(MananageTourTopParam params) {
        DgTour dgTour = tourMapper.selectById(params.getId());
        if (dgTour == null || dgTour.getEnable() == 0) {
            return "该行程不存在";
        }

        dgTour.setIsTop(EnumConstants.YesOrNo.YES.getValue());
        Date now = new Date();
        dgTour.setTopBeginTime(now);
        dgTour.setTopEndTime(DateUtils.addMinutes(now, params.getTimes()));
        return tourMapper.updateById(dgTour);
    }


    @Override
    @Transactional
    public void cancelTopOverTime() {
        //封装查询参数
        EntityWrapper<DgTour> ew = new EntityWrapper<>();
        ew.lt("top_end_time", new Date());
        ew.eq("is_top", EnumConstants.YesOrNo.YES.getValue());

        DgTour dgTour = new DgTour();
        dgTour.setIsTop(EnumConstants.YesOrNo.NO.getValue());
        mapper.update(dgTour, ew);
    }


    @Override
    @Transactional
    public Object cancleTop(Long id, AdminUserInfo userInfo) {
        Objects.requireNonNull(id, "无效行程ID");
        DgTour tour = new DgTour();
        tour.setId(id);
        tour.setIsTop(EnumConstants.YesOrNo.NO.getValue());
        mapper.updateById(tour);
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ManageTourTopResult> queryForTopList() {
        List<ManageTourTopResult> results = dgTourDao.queryForTop();
        return results;
    }


    @Override
    @Transactional(readOnly = true)
    public ManageTourQueryResult queryForBuyer(ManageTourQueryParam params) {
        // 条件查询行程
        List<ManageTourDetailResult> data = dgTourDao.queryForBuyer(params);
        int total = dgTourDao.countForBuyer(params);
        Pagination<ManageTourDetailResult> pageination = new Pagination<>();
        pageination.setTotal(total);
        pageination.setSize(params.getSize());
        pageination.setCurrent(params.getCurrent());
        pageination.setRecords(data);

        // 当前分页查询的交易金额统计
        long curAmount = 0L;
        for (ManageTourDetailResult mtd : data) {
            curAmount += (mtd.getAmount() == null) ? 0L : mtd.getAmount();
        }

        ManageTourQueryResult result = new ManageTourQueryResult();
        result.setAmountCount(curAmount);
        result.setPageination(pageination);
        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public List<ManageTourDestRegionCountResult> queryRegionCount(ManageTourQueryParam params) {
        // 目的地统计
        return dgTourDao.countForDestRegion(params);
    }


    @Override
    @Transactional(readOnly = true)
    public Pagination<ManageTourValidateDetailResult> queryForValidate(ManageTourGetValidateParam params) {
        List<ManageTourValidateDetailResult> data = dgTourDao.queryForValidate(params);
        int total = dgTourDao.countForValidate(params);
        Pagination<ManageTourValidateDetailResult> pageData = new Pagination<>();
        pageData.setCurrent(params.getCurrent());
        pageData.setSize(params.getSize());
        pageData.setRecords(data);
        pageData.setTotal(total);
        return pageData;
    }


    @Override
    @Transactional(readOnly = true)
    public ManageTourValidateCountResult countForValidate(ManageTourGetValidateParam params) {
        List<Map<String, Object>> dbCountResult = dgTourDao.queryValidateCount(params);
        ManageTourValidateCountResult countResult = new ManageTourValidateCountResult();
        long auditCount = 0L;
        long denyCount = 0L;
        long unAuditCount = 0L;
        for (Map<String, Object> map : dbCountResult) {
            Integer key = (Integer) map.get("auditStatus");
            if (key == null) {
                continue;
            }
            Long count = (Long) map.get("count");
            switch (key.toString()) {
                case "1":
                    unAuditCount = count;
                    break;
                case "2":
                    auditCount = count;
                    break;
                case "3":
                    denyCount = count;
                    break;
                default:
                    logger.warn("行程状态没有定义{},状态码错误", key);
                    break;
            }
        }
        countResult.setAuditCount(auditCount);
        countResult.setDenyCount(denyCount);
        countResult.setUnAuditCount(unAuditCount);
        return countResult;
    }


    @Override
    @Transactional
    public Object validateTour(ManageTourValidateParam params, AdminUserInfo userInfo) throws BizException {
        RedisLock redisLock = new RedisLock(redisTemplate);
        try {
            if (!redisLock.lock(REDISKEY_VALIDATE_TOUR + params.getId())) {
                throw new BizException("系统繁忙，请稍后重试");
            }
            Long tourId = params.getId();
            Integer updateStatus = params.getStatus();
            Assert.isTrue((updateStatus == EnumConstants.TourAuditStatus.PASS_AUDIT.getValue() ||
                            updateStatus == EnumConstants.TourAuditStatus.UNPASS_AUDIT.getValue()),
                    "非法审核状态值:" + updateStatus);
            DgTour dgTour = tourMapper.selectById(tourId);
            Assert.notNull(dgTour, "无效行程ID：" + tourId);
            Integer dbAuditStatus = dgTour.getAuditStatus();
            Assert.isTrue(dbAuditStatus <= EnumConstants.TourAuditStatus.WAIT_AUDIT.getValue(),
                    "行程已审核");
            Assert.isTrue(dbAuditStatus >= EnumConstants.TourAuditStatus.WAIT_AUDIT.getValue(),
                    "未提交证件，不能审核");

            String userName = userInfo.getUserName();
            Long userId = userInfo.getId();
            DgTour tour = new DgTour();
            tour.setId(tourId);
            tour.setAuditStatus(updateStatus);
            // 行程验证后，设置行程为“进行中”状态
            tour.setStatus(EnumConstants.TourStatus.RUNNING.getValue());
            tour.setAuditTime(new Date());
            tour.setAuditUserId(userId);
            tour.setAuditUserName(userName);
            tourMapper.updateById(tour);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException(e.getMessage());
        } finally {
            redisLock.unlock(REDISKEY_VALIDATE_TOUR + params.getId());
        }
        return null;
    }


    @Override
    @Transactional(readOnly = true)
    public ManageTourDetailResult queryNextUnaudit() {
        ManageTourQueryParam params = new ManageTourQueryParam();
        params.setCurrent(1);
        params.setSize(1);
        params.setAuditStatus(EnumConstants.TourAuditStatus.WAIT_AUDIT.getValue());
        List<ManageTourDetailResult> results = dgTourDao.queryForBuyer(params);
        return CollectionUtils.isEmpty(results) ? null : results.get(0);
    }

}
