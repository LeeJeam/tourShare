package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.circle.mapper.WsAdvertContentMapper;
import com.xmyy.circle.mapper.WsAdvertPositionMapper;
import com.xmyy.circle.model.WsAdvertContent;
import com.xmyy.circle.model.WsAdvertPosition;
import com.xmyy.circle.service.WsAdvertContentService;
import com.xmyy.circle.vo.AdApiParam;
import com.xmyy.circle.vo.AdContentParam;
import com.xmyy.circle.vo.AdManageListResult;
import com.xmyy.circle.vo.AdManageParam;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.CacheUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 广告内容  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = WsAdvertContentService.class)
@CacheConfig(cacheNames = "WsAdvertContent")
public class WsAdvertContentServiceImpl extends BaseServiceImpl<WsAdvertContent, WsAdvertContentMapper> implements WsAdvertContentService {

    @Resource
    private WsAdvertPositionMapper positionMapper;

    /**
     * 后台广告列表
     *
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<AdManageListResult> list(AdManageParam param) {
        //封装查询参数
        EntityWrapper<WsAdvertPosition> ew1 = new EntityWrapper();
        ew1.like("code", StringUtils.isBlank(param.getType()) ? "Buyer" : param.getType());

        List<WsAdvertPosition> list = positionMapper.selectList(ew1);

        List<AdManageListResult> results = list.parallelStream().map(g -> {
            AdManageListResult adManageResult = new AdManageListResult();

            //封装查询参数
            EntityWrapper<WsAdvertContent> ew = new EntityWrapper();

            ew.eq("position_id_", g.getId());
            ew.and("state", EnumConstants.State.NORMAL.value());
            ew.and("enable_", EnumConstants.YesOrNo.YES.getValue());
            ew.orderDesc(Lists.newArrayList("sort"));

            List<WsAdvertContent> lists = mapper.selectList(ew);

            adManageResult.setPosition(g.getName());
            adManageResult.setContents(lists);
            return adManageResult;

        }).collect(Collectors.toCollection(ArrayList::new));

        return results;
    }

    /**
     * 变更图片
     *
     * @param param
     * @return
     */
    @Transactional
    @Override
    public Object updateImg(AdContentParam param) {
        WsAdvertContent content = super.mapper.selectById(param.getId());
        if (content == null) {
            return "该广告内容不存在";
        }

        content = InstanceUtil.to(param, WsAdvertContent.class);
        //广告位、Banner内容变更后，更新预加载信息版本号
        CacheUtils.setHeadInfoVersion(Integer.toString(Integer.parseInt(CacheUtils.getHeadInfoVersion()) + 1));

        super.update(content);

        return null;
    }

    /**
     * api 广告位列表
     *
     * @param param
     * @return
     */
    @Transactional(readOnly = true)
    @Override
    public List<WsAdvertContent> listContent(AdApiParam param) {
        //封装查询参数
        EntityWrapper<WsAdvertPosition> ew1 = new EntityWrapper();
        ew1.eq("code", param.getCode());

        List<WsAdvertPosition> list = positionMapper.selectList(ew1);

        List<Long> ids = list.parallelStream().map(g -> {
            return g.getId();
        }).collect(Collectors.toCollection(ArrayList::new));


        //封装查询参数
        EntityWrapper<WsAdvertContent> ew = new EntityWrapper();

        ew.in("position_id_", ids);
        ew.and("state", EnumConstants.State.NORMAL.value());
        ew.and("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("sort"));

        List<WsAdvertContent> lists = mapper.selectList(ew);

        return lists;
    }


}
