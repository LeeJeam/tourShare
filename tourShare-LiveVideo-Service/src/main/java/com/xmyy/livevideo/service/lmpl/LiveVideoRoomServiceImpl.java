package com.xmyy.livevideo.service.lmpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.codingapi.tx.annotation.TxTransaction;
import com.google.common.collect.Lists;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.CacheUtils;
import com.xmyy.common.vo.MemberInfo;
import com.xmyy.livevideo.common.Config;
import com.xmyy.livevideo.common.WebRTCSigConfig;
import com.xmyy.livevideo.logic.IMManger;
import com.xmyy.livevideo.logic.LiveUtil;
import com.xmyy.livevideo.mapper.VdLiveVideoMapper;
import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.LiveVideoRoomService;
import com.xmyy.livevideo.vo.*;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcSeller;
import com.xmyy.member.service.UcBuyerService;
import com.xmyy.member.service.UcSellerService;
import com.xmyy.product.service.TourService;
import com.xmyy.product.vo.ApiTourLiveParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.support.Pagination;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.xmyy.common.util.ModelMapUtil.setModelMap;

/**
 * 直播房间  服务实现类
 *
 * @author simon
 */
@Service(interfaceClass = LiveVideoRoomService.class)
public class LiveVideoRoomServiceImpl extends BaseServiceImpl<VdLiveVideo, VdLiveVideoMapper> implements LiveVideoRoomService {
    private static Logger log = LoggerFactory.getLogger(LiveVideoRoomServiceImpl.class);

    @Reference
    private UcSellerService ucSellerService;
    @Reference
    private UcBuyerService ucBuyerService;
    @Reference
    private TourService tourService;
    @Resource
    private VdLiveVideoMapper liveVideoMapper;
    @Resource
    private LiveUtil liveUtil;
    @Resource
    private IMManger mmgr;

    /**
     * 直播创建房间并创建聊天群
     *
     * @return
     */
    public Object createLiveRoom(LiveVideoAddParam liveVideoAddParams) {
        LiveVideoResult liveVideoResult = null;
        if (liveVideoAddParams.getMemberId() == null || liveVideoAddParams.getRoomTitle() == null
                || liveVideoAddParams.getMemberType() == null || liveVideoAddParams.getStrokeId() == null) {
            return "请求失败，缺少参数";
        }
        if (liveVideoAddParams.getRoomDesc().length() > 1024 || liveVideoAddParams.getRoomTitle().length() > 1024) {
            return "直播间信息不能过长";
        }
        String uuId = IdWorker.get32UUID();
        if (liveVideoAddParams.getMemberType() == EnumConstants.MemberType.seller.getValue()) {   //认证FALG为买手
            UcSeller seller = ucSellerService.queryById(Long.valueOf(liveVideoAddParams.getMemberId()));
            if (seller != null) {   //买手不能为空
                if (mmgr.createGroup(uuId, getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig())) {
                    liveVideoResult = saveLiveRoom(uuId, liveVideoAddParams.getStrokeId(), liveVideoAddParams);
                } else {
                    return "买手创建直播间失败";
                }
            } else {
                return "买手用户信息不存在";
            }
        } else if (liveVideoAddParams.getMemberType() == EnumConstants.MemberType.buyer.getValue()) {  //验证FALG背包客
            UcBuyer buyer = ucBuyerService.queryById(Long.valueOf(liveVideoAddParams.getMemberId()));
            if (buyer != null) {     //买家不能为空
                if (mmgr.createGroup(uuId, getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig())) {
                    liveVideoResult = saveLiveRoom(uuId, liveVideoAddParams.getStrokeId(), liveVideoAddParams);
                } else {
                    return "背包客创建直播间失败";
                }
            } else {
                return "背包客用户信息不存在";
            }
        } else {
            return "无效用户信息";
        }
        return liveVideoResult;
    }


    @Transactional
    public LiveVideoResult saveLiveRoom(String userId, Long strokeId, LiveVideoAddParam liveVideoAddParams) {
        Date now = new Date();
        VdLiveVideo liveVideo = InstanceUtil.to(liveVideoAddParams, VdLiveVideo.class);
        liveVideo.setLiveRoomId(userId);
        liveVideo.setMemberId(liveVideoAddParams.getMemberId());
        liveVideo.setPullUrl(liveUtil.genAcceleratePlayUrl(userId));
        liveVideo.setPullRtmpUrl(liveUtil.genAcceleratePlayUrl(userId));
        liveVideo.setPushUrl(liveUtil.genPushUrl(userId));
        liveVideo.setStatus(EnumConstants.VideoStatus.PLAYING.getValue());
        liveVideo.setCreateTime(now);
        liveVideo.setBeginTime(now);
        liveVideo.setStrokeId(strokeId);
        liveVideo.setCountryCode(liveVideoAddParams.getCountryCode());
        liveVideo.setCreateBy(liveVideoAddParams.getMemberId());
        liveVideo.setMemberType(liveVideoAddParams.getMemberType());
        liveVideo.setGroupId(userId);
        super.update(liveVideo);
        LiveVideoResult liveVideoResult = InstanceUtil.to(liveVideo, LiveVideoResult.class);
        MemberInfo sellerInfo = ucSellerService.getMemberInfo(liveVideoAddParams.getMemberId(), EnumConstants.MemberType.valueOf(liveVideoAddParams.getMemberType()).getValue()); //获取用户信息
        if (sellerInfo != null) {
            liveVideoResult.setNickName(sellerInfo.getNickName()); //买手昵称
            liveVideoResult.setImagUrl(sellerInfo.getAvatarRsurl()); //买手头像
        } else {
            UcSeller seller = ucSellerService.queryById(liveVideo.getMemberId()); //redis没有数据，从数据库中查询
            if (seller != null) {
                liveVideoResult.setNickName(seller.getNickName());
                liveVideoResult.setImagUrl(seller.getAvatarRsurl());
                MemberInfo info = InstanceUtil.to(seller, MemberInfo.class);
                CacheUtils.setMemberInfo(seller.getId(), EnumConstants.MemberType.valueOf(EnumConstants.MemberType.seller.getValue()), info);
            }
        }
        return liveVideoResult;
    }


    /**
     * 修改直播状态
     *
     * @param liveVideoStatusParams
     * @return
     */
    @Transactional
    @TxTransaction
    public Object updateLiveRoomStatus(LiveVideoStatusParam liveVideoStatusParams) {
        log.info("update live roomId:{} status:{}", liveVideoStatusParams.getLiveRoomId(), liveVideoStatusParams.getStatus());
        if (liveVideoStatusParams.getLiveRoomId() == null || liveVideoStatusParams.getStatus() == null
                || liveVideoStatusParams.getMemberId() == null || liveVideoStatusParams.getMemberType() == null) {
            return "请求失败，缺少参数";
        }
        VdLiveVideo liveVideo = InstanceUtil.newInstance(VdLiveVideo.class);
        liveVideo.setLiveRoomId(liveVideoStatusParams.getLiveRoomId());
        VdLiveVideo vdLiveVideo = super.selectOne(liveVideo);
        if (vdLiveVideo != null) {

            try {
                Date now = new Date();
                vdLiveVideo.setStatus(liveVideoStatusParams.getStatus());
                vdLiveVideo.setUpdateTime(now);
                vdLiveVideo.setBeginTime(now);
                vdLiveVideo.setMemberType(liveVideoStatusParams.getMemberType());
                vdLiveVideo.setUpdateBy(liveVideoStatusParams.getMemberId());
                super.update(vdLiveVideo);

                EntityWrapper<VdLiveVideo> ew = new EntityWrapper<>();
                ew.eq("live_room_id", liveVideoStatusParams.getLiveRoomId());
                ew.orderBy("id_", false);
                ew.last("LIMIT 1");
                List<VdLiveVideo> liveVideos = liveVideoMapper.selectList(ew);
                if (!CollectionUtils.isEmpty(liveVideos)) {
                    VdLiveVideo video = liveVideos.get(0);
                    ApiTourLiveParam tourLiveParams = new ApiTourLiveParam();
                    tourLiveParams.setCreateBy(liveVideoStatusParams.getMemberId());
                    tourLiveParams.setIsLive(liveVideoStatusParams.getStatus());
                    tourLiveParams.setTourId(video.getStrokeId());
                    log.info("update tour id:{}", video.getStrokeId());
                    tourService.updateLiveStatus(tourLiveParams);
                }
            } catch (Exception e) {
                throw new BizException("业务异常");
            }

            return setModelMap(HttpCode.OK.value().toString(), "修改状态成功");
        } else {
            return "记录不存在";
        }

    }


    /**
     * 进群后通知统计进入退出人数
     */
    public Object noticeCountNum(String roomId, Integer type) {
        if (roomId == null || roomId == "" || type == null) {
            return "请求失败，缺少参数";
        }
        LiveVideoCountResult countResult = InstanceUtil.newInstance(LiveVideoCountResult.class);
        VdLiveVideo liveVideo = InstanceUtil.newInstance(VdLiveVideo.class);
        liveVideo.setLiveRoomId(roomId);
        VdLiveVideo vdLiveVideo = super.selectOne(liveVideo);
        if (vdLiveVideo == null) {
            return "房间不存在";
        }
        if (type == EnumConstants.ActionType.ENTER.getValue()) {
            if (vdLiveVideo.getLookNumber() == null) {
                vdLiveVideo.setLookNumber(0);
            }
            int lookNumber = vdLiveVideo.getLookNumber() + 1;
            vdLiveVideo.setLookNumber(lookNumber);
            if (vdLiveVideo.getSysLookNumber() == null) {
                vdLiveVideo.setSysLookNumber(0);
            }
            int sysLookNumber = vdLiveVideo.getSysLookNumber() + 1;
            vdLiveVideo.setSysLookNumber(sysLookNumber);
            super.update(vdLiveVideo);
            countResult.setLookNumber(vdLiveVideo.getLookNumber());
            return countResult;
        }
        if (type == EnumConstants.ActionType.EXIT.getValue()) {
            if (vdLiveVideo.getSysLookNumber() == 0) {
                vdLiveVideo.setSysLookNumber(0);
            } else {
                int sysLookNumber = vdLiveVideo.getSysLookNumber() - 1;
                vdLiveVideo.setSysLookNumber(sysLookNumber);
            }
            super.update(vdLiveVideo);
            countResult.setLookNumber(vdLiveVideo.getLookNumber());
            return countResult;
        }
        if (type == EnumConstants.ActionType.COUNT.getValue()) {
            countResult.setLookNumber(vdLiveVideo.getLookNumber());
            return countResult;
        }
        return null;
    }


    @Override
    public Object myLiveVideo(Long memberId, Integer current, Integer size) {
        //封装查询参数
        EntityWrapper<VdLiveVideo> ew = new EntityWrapper();
        ew.eq("member_id", memberId);
        ew.eq("member_type", EnumConstants.MemberType.seller.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));
        List<LiveVideoResult> resultList = null;
        int count = liveVideoMapper.selectCount(ew);
        if (count > 0) {
            RowBounds rb = new RowBounds((current - 1) * size, size);
            List<VdLiveVideo> liveList = liveVideoMapper.selectPage(rb, ew);
            resultList = setMemberInfo(liveList);
        }
        // 分页
        Pagination<LiveVideoResult> pageResult = new Pagination<LiveVideoResult>();
        pageResult.setCurrent(current);
        pageResult.setSize(size);
        pageResult.setTotal(count);
        pageResult.setRecords(resultList);
        int pages = (count % size == 0) ? count / size : count / size + 1;
        pageResult.setPages(pages);
        return pageResult;
    }


    public List<LiveVideoResult> setMemberInfo(List<VdLiveVideo> liveList) {
        List<LiveVideoResult> resultList = liveList.stream().map(o -> {
            LiveVideoResult result = InstanceUtil.to(o, LiveVideoResult.class);
            if (o.getStatus() != null && EnumConstants.VideoStatus.valueOf(o.getStatus()) != null) {
                result.setStateLabel(EnumConstants.VideoStatus.valueOf(o.getStatus()).getLabel());
            }
            if (o.getMemberId() != null && o.getMemberType() != null) {
                MemberInfo sellerInfo = ucSellerService.getMemberInfo(o.getMemberId(), EnumConstants.MemberType.valueOf(o.getMemberType()).getValue());//获取用户信息
                if (sellerInfo != null && !"".equals(sellerInfo)) {
                    result.setNickName(sellerInfo.getNickName()); //昵称
                    result.setImagUrl(sellerInfo.getAvatarRsurl()); //头像
                } else {
                    UcSeller seller = ucSellerService.queryById(o.getMemberId());
                    if (seller != null) {
                        result.setNickName(seller.getNickName());
                        result.setImagUrl(seller.getAvatarRsurl());
                        MemberInfo info = InstanceUtil.to(seller, MemberInfo.class);
                        if (info != null) {
                            CacheUtils.setMemberInfo(seller.getId(), EnumConstants.MemberType.valueOf(EnumConstants.MemberType.seller.getValue()), info);
                        }
                    }
                }
            }
            return result;
        }).collect(Collectors.toCollection(ArrayList::new));
        return resultList;
    }


    /**
     * 关闭直播间并解散聊天群组
     *
     * @return
     */
    public Object destroyLiveRoom(LiveVideoParam liveVideoParams) {
        if (liveVideoParams.getLiveRoomId() == null || liveVideoParams.getMemberId() == null
                || liveVideoParams.getMemberType() == null || liveVideoParams.getLiveRoomId() == null) {
            return "请求失败，缺少参数";
        }
        VdLiveVideo liveVideo = InstanceUtil.newInstance(VdLiveVideo.class);
        liveVideo.setLiveRoomId(liveVideoParams.getLiveRoomId());
        liveVideo.setMemberType(liveVideoParams.getMemberType());
        liveVideo.setMemberId(liveVideoParams.getMemberId());
        VdLiveVideo vdLiveVideo = super.selectOne(liveVideo);
        if (vdLiveVideo != null) {
            Date now = new Date();
            vdLiveVideo.setStatus(EnumConstants.VideoStatus.END.getValue());
            vdLiveVideo.setUpdateTime(now);
            vdLiveVideo.setUpdateBy(vdLiveVideo.getMemberId());
            vdLiveVideo.setEndTime(now);
            boolean destroyRoomFlag = mmgr.destroyGroup(liveVideoParams.getLiveRoomId(), getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig());
            if (destroyRoomFlag) {
                mmgr.closeLiveVideoUrl(vdLiveVideo.getLiveRoomId());
                vdLiveVideo.setVideoUrl(mmgr.getLiveVideoUrl(Config.Live.APP_BIZID + "_" + vdLiveVideo.getLiveRoomId()));
                super.update(vdLiveVideo);

                EntityWrapper<VdLiveVideo> ew = new EntityWrapper<>();
                ew.eq("live_room_id", liveVideoParams.getLiveRoomId());
                ew.orderBy("id_", false);
                ew.last("LIMIT 1");
                List<VdLiveVideo> liveVideos = liveVideoMapper.selectList(ew);
                if (!CollectionUtils.isEmpty(liveVideos)) {
                    VdLiveVideo video = liveVideos.get(0);
                    ApiTourLiveParam tourLiveParams = new ApiTourLiveParam();
                    tourLiveParams.setCreateBy(liveVideoParams.getMemberId());
                    tourLiveParams.setIsLive(liveVideoParams.getStatus());
                    tourLiveParams.setTourId(video.getStrokeId());
                    log.info("update tour id:{}", video.getStrokeId());
                    tourService.updateLiveStatus(tourLiveParams);
                }

                return setModelMap(HttpCode.OK.value().toString(), "关闭直播间成功");
            } else {
                return setModelMap(HttpCode.INTERNAL_SERVER_ERROR.value().toString(), "关闭直播间失败");
            }
        } else {
            return "不是房间主人，无法关闭房间";
        }
    }


    /**
     * 定时器检查房间是否意外已经关闭
     */
    public void onTimerCheckHeartBeat() {
        EntityWrapper<VdLiveVideo> ew = new EntityWrapper();
        ew.eq("member_type", EnumConstants.MemberType.seller.getValue());
        ew.orderDesc(Lists.newArrayList("create_time"));
        List<VdLiveVideo> liveList = liveVideoMapper.selectList(ew);
        for (VdLiveVideo live : liveList) {
            String streamId = LiveUtil.getStreamIdFromPushUrl(live.getPushUrl());
            if (StringUtils.isNotEmpty(streamId) && mmgr.getStreamStatus(streamId) == 1) {
                mmgr.destroyGroup(live.getLiveRoomId(), getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig());
            }
        }
    }


    /**
     * @param msgTime
     * @return
     */
    public Object getHistoryMessge(String msgTime) {
        return mmgr.getHistoryMessge(msgTime, getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig());
    }


    /**
     * @param uuId
     * @return
     */
    @Override
    public Object setPortRait(String uuId, String nickName, String image) {
        return mmgr.setPortRait(uuId, nickName, image, getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig());
    }


    @Override
    public Object getRoomList(LiveVideoParam liveVideoParams) {
        if (liveVideoParams.getMemberId() == null || liveVideoParams.getMemberType() == null) {
            return "请求失败，缺少参数";
        }
        //封装查询参数
        EntityWrapper<VdLiveVideo> ew = new EntityWrapper();
        if (StringUtils.isNotBlank(liveVideoParams.getMemberId().toString())) {
            ew.eq("member_id", liveVideoParams.getMemberId());
        }
        if (StringUtils.isNotBlank(liveVideoParams.getMemberType().toString())) {
            ew.eq("memeber_type", liveVideoParams.getMemberId());
        }
        List<VdLiveVideo> liveVideoPagination = liveVideoMapper.selectList(ew);
        return liveVideoPagination;
    }


    /**
     * 获取买手个人主页信息
     */
    public Object getSellerInfo(Long memberId) {
        UcSeller seller = ucSellerService.queryById(memberId); //从数据库中查询
        if (seller != null) {
            MemberInfo info = InstanceUtil.to(seller, MemberInfo.class);
            CacheUtils.setMemberInfo(seller.getId(), EnumConstants.MemberType.valueOf(EnumConstants.MemberType.seller.getValue()), info);
        }
        return seller;
    }


    /**
     * APP登录获取userSig相关参数
     *
     * @param uuId
     * @return
     */
    public LoginInfoResult getAppUserSig(String uuId) {

        LoginInfoResult loginInfoResult = InstanceUtil.newInstance(LoginInfoResult.class);
        WebRTCSigConfig api = new WebRTCSigConfig();
        api.setSdkAppid((int) Config.IM.IM_SDKAPPID);
        api.setPrivateKey(Config.IM.PRIVATEKEY);
        api.setPublicKey(Config.IM.PUBLICKEY);
        if (uuId != null) {
            loginInfoResult.setUserSig(api.genUserSig(uuId, 86400));
            loginInfoResult.setUserID(uuId);
        }
        return loginInfoResult;

    }

}
