package com.xmyy.livevideo.service.lmpl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Lists;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.CacheUtils;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.livevideo.mapper.VdLiveVideoMapper;
import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.LiveVideoRoomService;
import com.xmyy.livevideo.serivce.LiveVideoService;
import com.xmyy.livevideo.vo.*;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.product.service.PtProductService;
import com.xmyy.product.vo.ApiProductListByTourIdParam;
import com.xmyy.product.vo.ApiProductListByTourIdResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 直播  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = LiveVideoService.class)
@CacheConfig(cacheNames = "VdLiveVideo")
public class LiveVideoServiceImpl extends BaseServiceImpl<VdLiveVideo, VdLiveVideoMapper> implements LiveVideoService {

    @Reference
    private LiveVideoRoomService roomService;
    @Reference
    private UcSellerService ucSellerService;
    @Reference
    private PtProductService ptProductService;
    @Resource
    private VdLiveVideoMapper liveVideoMapper;


    @Override
    @Transactional(readOnly = true)
    public Object queryLiveVideoList(LiveVideoManagePageParam params) {
        EntityWrapper<VdLiveVideo> ew = new EntityWrapper<>();
        if (params.getState() != null) {
            ew.eq("status", params.getState());
        }
        if (params.getBeginTime() != null) {
            ew.eq("begin_time", params.getBeginTime());
        }
        if (params.getSellerId() != null && params.getSellerId() != null) {
            ew.eq("member_id", params.getSellerId());
        }
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));
        List<LiveVideoMangerListResult> list = null;
        int count = mapper.selectCount(ew);
        if (count > 0) {
            RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
            List<VdLiveVideo> mlist = mapper.selectPage(rb, ew);
            list = mlist.stream().map(o -> {
                LiveVideoMangerListResult r = InstanceUtil.to(o, LiveVideoMangerListResult.class);
                if (o.getMemberId() != null && o.getMemberType() != null) {
                    MemberInfo memberInfo = ucSellerService.getMemberInfo(o.getMemberId(), EnumConstants.MemberType.valueOf(o.getMemberType()).getValue());//获取用户信息
                    r.setLiveVideoUserName(memberInfo.getRealName());
                }
                if (o.getStatus() != null && EnumConstants.VideoStatus.valueOf(o.getStatus()) != null)
                    r.setVideoStatusLabel(EnumConstants.VideoStatus.valueOf(o.getStatus()).getLabel());
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }
        Pagination<LiveVideoMangerListResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(params.getCurrent());
        page.setSize(params.getSize());
        page.setTotal(count);
        return page;
    }


    @Override
    @Transactional(readOnly = true)
    public Object buyerLiveVideoList(String countryCode, Integer current, Integer size) {
        if (current == null || current < 1) {
            current = 1;
        }
        if (size == null || size < 1) {
            size = 1;
        }

        EntityWrapper<VdLiveVideo> ew = new EntityWrapper<>();
        if (StringUtils.isNotBlank(countryCode)) {
            ew.eq("country_code", countryCode);
        }
        ew.eq("status", EnumConstants.VideoStatus.PLAYING.getValue());
        ew.eq("member_type", EnumConstants.MemberType.seller.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));
        int count = liveVideoMapper.selectCount(ew);

        List<LiveVideoResult> resultList = null;
        if (count > 0) {
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<VdLiveVideo> mlist = liveVideoMapper.selectPage(rb, ew);
            if (CollectionUtils.isNotEmpty(mlist)) {
                resultList = roomService.setMemberInfo(mlist);
            }
        }

        Pagination<LiveVideoResult> pageResult = new Pagination<>();
        pageResult.setCurrent(current);
        pageResult.setSize(size);
        pageResult.setTotal(count);
        pageResult.setRecords(resultList);
        int pages = (count % size == 0) ? count / size : count / size + 1;
        pageResult.setPages(pages);

        return pageResult;
    }


    @Override
    @Transactional(readOnly = true)
    public Object buyerByTourId(String tourId) {
        VdLiveVideo liveVideo = InstanceUtil.newInstance(VdLiveVideo.class);
        if (tourId != null) {
            liveVideo.setStrokeId(Long.valueOf(tourId));
            liveVideo.setMemberType(EnumConstants.MemberType.seller.getValue());
            liveVideo.setStatus(EnumConstants.VideoStatus.PLAYING.getValue());
        }
        VdLiveVideo vliveVideo = liveVideoMapper.selectOne(liveVideo);

        if (vliveVideo != null) {
            LiveVideoResult result = InstanceUtil.to(vliveVideo, LiveVideoResult.class);
            if (vliveVideo.getStatus() != null && EnumConstants.VideoStatus.valueOf(vliveVideo.getStatus()) != null) {  //直播状态
                result.setStateLabel(EnumConstants.VideoStatus.valueOf(vliveVideo.getStatus()).getLabel());
            }
            MemberInfo sellerInfo = ucSellerService.getMemberInfo(vliveVideo.getMemberId(), EnumConstants.MemberType.valueOf(vliveVideo.getMemberType()).getValue());//获取用户信息
            if (sellerInfo != null) {
                result.setNickName(sellerInfo.getNickName()); //买手昵称
                result.setImagUrl(sellerInfo.getAvatarRsurl()); //买手头像
                return result;
            } else {
                UcSeller seller = ucSellerService.queryById(vliveVideo.getMemberId()); //redis没有数据，从数据库中查询
                if (seller != null) {
                    result.setNickName(seller.getNickName());
                    result.setImagUrl(seller.getAvatarRsurl());
                    MemberInfo memberInfo = InstanceUtil.to(seller, MemberInfo.class);
                    CacheUtils.setMemberInfo(seller.getId(), EnumConstants.MemberType.valueOf(EnumConstants.MemberType.seller.getValue()), memberInfo);
                }
                return result;
            }
        } else {
            return "获取直播不存在";
        }
    }


    @Override
    @Transactional
    public Object setTop(LiveVideoMangerTopParam params) {
        VdLiveVideo topParams = InstanceUtil.newInstance(VdLiveVideo.class);
        topParams.setLiveRoomId(params.getId());
        VdLiveVideo liveVideo = liveVideoMapper.selectOne(topParams);
        if (liveVideo == null || liveVideo.getEnable() == 0) {
            return "该直播不存在";
        }
        liveVideo.setIsTop(EnumConstants.YesOrNo.YES.getValue());
        Date now = new Date();
        liveVideo.setTopBeginTime(now);
        liveVideo.setTopEndTime(DateUtils.addMinutes(now, params.getTimes()));
        liveVideo.setUpdateBy(params.getUpdateBy());
        return liveVideoMapper.updateById(liveVideo);
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryLiveVideoByRoomId(String roomId) {
        VdLiveVideo liveVideo = InstanceUtil.newInstance(VdLiveVideo.class);
        liveVideo.setLiveRoomId(roomId);
        liveVideo.setEnable(EnumConstants.YesOrNo.YES.getValue());
        VdLiveVideo liveVideos = liveVideoMapper.selectOne(liveVideo);
        LiveVideoMangerListResult result = InstanceUtil.to(liveVideos, LiveVideoMangerListResult.class);

        if (result != null) {
            ApiProductListByTourIdParam tourIdParams = InstanceUtil.newInstance(ApiProductListByTourIdParam.class);
            tourIdParams.setTourId(liveVideos.getStrokeId());
            Pagination<ApiProductListByTourIdResult> page = ptProductService.getListByTourId(tourIdParams);
            result.setProductList(page.getRecords());
        }

        return result;
    }


    @Override
    @Transactional(readOnly = true)
    public Object queryCurrentTopList(LiveVideoCurrentTopParam params) {
        //封装查询参数
        EntityWrapper<VdLiveVideo> ew = new EntityWrapper<>();
        if (params.getIsTop() != null) {
            ew.eq("is_top", params.getIsTop());
        }
        ew.eq("enable_", EnumConstants.YesOrNo.YES.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));
        List<LiveVideoMangerListResult> list = null;
        int count = mapper.selectCount(ew);
        if (count > 0) {
            RowBounds rb = new RowBounds((params.getCurrent() - 1) * params.getSize(), params.getSize());
            List<VdLiveVideo> mlist = mapper.selectPage(rb, ew);
            list = mlist.stream().map(o -> {
                LiveVideoMangerListResult r = InstanceUtil.to(o, LiveVideoMangerListResult.class);
                if (o.getMemberId() != null && o.getMemberType() != null) {
                    MemberInfo memberInfo = ucSellerService.getMemberInfo(o.getMemberId(), EnumConstants.MemberType.valueOf(o.getMemberType()).getValue());//获取用户信息
                    r.setLiveVideoUserName(memberInfo.getRealName());
                }
                if (o.getStatus() != null && EnumConstants.VideoStatus.valueOf(o.getStatus()) != null)
                    r.setVideoStatusLabel(EnumConstants.VideoStatus.valueOf(o.getStatus()).getLabel());
                return r;
            }).collect(Collectors.toCollection(ArrayList::new));
        }

        Pagination<LiveVideoMangerListResult> page = new Pagination<>();
        page.setRecords(list);
        page.setCurrent(params.getCurrent());
        page.setSize(params.getSize());
        page.setTotal(count);
        return page;
    }


    @Override
    @Transactional
    public Object cancelTop(LiveVideoManageCancelTopParam params) {
        VdLiveVideo topParams = InstanceUtil.newInstance(VdLiveVideo.class);
        topParams.setLiveRoomId(params.getId());
        VdLiveVideo liveVideo = liveVideoMapper.selectOne(topParams);
        if (liveVideo == null || liveVideo.getEnable() == 0) {
            return "该直播不存在";
        }
        liveVideo.setIsTop(EnumConstants.YesOrNo.NO.getValue());
        liveVideo.setUpdateBy(params.getUpdateBy());
        return liveVideoMapper.updateById(liveVideo);
    }

}
