package com.xmyy.livevideo.serivce;


import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.vo.*;
import top.ibase4j.core.base.BaseService;
import top.ibase4j.core.exception.BizException;

import java.util.List;

public interface LiveVideoRoomService extends BaseService<VdLiveVideo> {


    Object getRoomList(LiveVideoParam liveVideoParams);

    Object createLiveRoom(LiveVideoAddParam liveVideoParams);

    Object updateLiveRoomStatus(LiveVideoStatusParam liveVideoStatusParams) throws BizException;

    Object destroyLiveRoom(LiveVideoParam liveVideoParams);

    public Object getHistoryMessge(String msgTime);

    public LoginInfoResult getAppUserSig(String uuId);

    public Object  noticeCountNum(String roomId,Integer type);

    public Object  myLiveVideo(Long memberId,Integer current,Integer size);

    public Object getSellerInfo(Long memberId);

    public List<LiveVideoResult> setMemberInfo(List<VdLiveVideo> liveList);

    public Object setPortRait(String uuId,String nickName,String image);

    public  void onTimerCheckHeartBeat();

}
