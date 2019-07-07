package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.circle.dao.ManageCircleDao;
import com.xmyy.circle.mapper.UcDynamicCircleMapper;
import com.xmyy.circle.model.UcDynamicCircle;
import com.xmyy.circle.service.ManageCircleService;
import com.xmyy.circle.vo.*;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 动态后台管理  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = ManageCircleService.class)
@CacheConfig(cacheNames = "UcDynamicCircle")
public class ManageCircleServiceImpl extends BaseServiceImpl<UcDynamicCircle, UcDynamicCircleMapper> implements ManageCircleService {

    @Resource
    private ManageCircleDao circleDao;

    /**
     * 动态列表&买手动态列表
     *
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public Pagination<CircleTopPageResult> list(CircleTopPageParam param) {
        int totalCount = this.circleDao.countCircleTopPage(param);
        List<CircleTopPageResult> page = circleDao.listCircleTopPage(param, new RowBounds((param.getCurrent() - 1) * param.getSize(), param.getSize()));

        page.parallelStream().forEach(p -> {
            if (p.getState() != null) p.setStateLabel(EnumConstants.State.getState(p.getState()).getShelvesLabel());
            if (p.getTypeId() != null) p.setTypeLabel(EnumConstants.CircleType.valueOf(p.getTypeId()).getName());
        });

        Pagination<CircleTopPageResult> resultPagination = new Pagination<>();
        resultPagination.setTotal(totalCount);
        resultPagination.setRecords(page);
        resultPagination.setCurrent(param.getCurrent());
        resultPagination.setSize(param.getSize());
        return resultPagination;
    }

    /**
     * 置顶
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Object top(CircleTopParam param) {
        UcDynamicCircle circle = super.queryById(param.getId());
        if (circle == null) {
            return "该动态不存在";
        }
        circle.setIsTop(EnumConstants.YesOrNo.YES.getValue());
        Date now = new Date();

        circle.setTopEndTime(DateUtils.addMinutes(now, param.getTimes()));
        circle.setTopTime(now);
        circle.setUpdateBy(param.getUpdateBy());
        circle.setTopOpId(param.getUpdateBy());

        super.update(circle);
        return circle;
    }


    /**
     * 上架
     *
     * @param id
     * @param currUser
     * @return
     */
    @Override
    @Transactional
    public Object setUp(Long id, Long currUser) {
        UcDynamicCircle circle = super.queryById(id);
        if (circle == null) {
            return "该动态不存在";
        }
        circle.setState(EnumConstants.State.NORMAL.value());
        circle.setUpdateBy(currUser);

        super.update(circle);
        return circle;
    }

    /**
     * 下架
     *
     * @param id
     * @param currUser
     * @return
     */
    @Override
    @Transactional
    public Object setDown(Long id, Long currUser) {
        UcDynamicCircle circle = super.queryById(id);
        if (circle == null) {
            return "该动态不存在";
        }
        circle.setState(EnumConstants.State.DELETED.value());
        circle.setUpdateBy(currUser);

        super.update(circle);
        return circle;
    }

    /**
     * 获取统计结果
     *
     * @param param
     * @return
     */
    @Override
    public CircleCountResult getResultCount(CircleTopPageParam param) {
        CircleCountResult result = new CircleCountResult();
        if (param.getTypeId() != null && param.getTypeId().intValue() == EnumConstants.CircleType.video.getValue()) {
            result.setNoteConut(0);
            result.setVideoCount(this.circleDao.countCircleTopPage(param));
        }

        if (param.getTypeId() != null && param.getTypeId().intValue() == EnumConstants.CircleType.note.getValue()) {
            result.setNoteConut(this.circleDao.countCircleTopPage(param));
            result.setVideoCount(0);
        }

        if (param.getTypeId() == null) {
            param.setTypeId(EnumConstants.CircleType.note.getValue());
            result.setNoteConut(this.circleDao.countCircleTopPage(param));
            param.setTypeId(EnumConstants.CircleType.video.getValue());
            result.setVideoCount(this.circleDao.countCircleTopPage(param));
        }
        return result;
    }

    /**
     * 取消置顶
     *
     * @param param
     * @return
     */
    @Override
    @Transactional
    public Object down(CircleDownParam param) {
        UcDynamicCircle circle = super.queryById(param.getId());
        if (circle == null) {
            return "该动态不存在";
        }
        circle.setIsTop(EnumConstants.YesOrNo.NO.getValue());
        circle.setUpdateBy(param.getUpdateBy());
        circle.setTopOpId(param.getUpdateBy());

        super.update(circle);
        return circle;
    }
}
