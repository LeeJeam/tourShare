package com.xmyy.livevideo.service.lmpl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xmyy.livevideo.common.Config;
import com.xmyy.livevideo.logic.IMManger;
import com.xmyy.livevideo.model.VdLiveCallbackLog;
import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.CloudLiveVideoService;
import com.xmyy.livevideo.serivce.LiveVideoRoomService;
import com.xmyy.livevideo.serivce.VdLiveCallbackLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.ui.ModelMap;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;

/**
 * 腾讯云直播事件消息通知处理
 *
 * @author simon
 */
@Service(interfaceClass = CloudLiveVideoService.class)
public class CloudLiveVideoServiceImpl implements CloudLiveVideoService {

    @Reference
    private LiveVideoRoomService roomService;
    @Reference
    private VdLiveCallbackLogService callbackLogService;
    @Resource
    private IMManger mmgr;

    private final static int SERVER_CODE = 0;

    @Override
    public Object cloudLiveCallBack(String jsonBody) {
        ModelMap modelMap = new ModelMap();
        JSONObject jsonObject = JSON.parseObject(jsonBody);
        int eventType = jsonObject.getIntValue("event_type");
        String streamId = jsonObject.getString("stream_id");

        VdLiveCallbackLog callbackLog = new VdLiveCallbackLog();
        callbackLog.setJsonBody(jsonBody);
        callbackLog.setEventType(eventType);
        callbackLog.setStreamId(streamId);

        if (eventType == Config.LiveMsgEventType.CUT_STREAM) {   //断流标识返回
            if (StringUtils.isNotEmpty(streamId)) {
                String[] streamArray = streamId.split("_");
                if (streamArray.length > 0) {
                    boolean destoryFlag = mmgr.destroyGroup(streamArray[1], roomService.getAppUserSig(Config.IM.ADMINISTRATOR).getUserSig());
                    if (destoryFlag) {
                        modelMap.put("code", SERVER_CODE);
                        return modelMap;
                    }
                }
            }
        } else if (eventType == Config.LiveMsgEventType.RECORD_AV_CREATED) {   //生成录像文件标识返回
            String videoId = jsonObject.getString("video_id");
            String videoUrl = jsonObject.getString("video_url");
            callbackLog.setVideoId(videoId);
            callbackLog.setVideoUrl(videoUrl);

            if (StringUtils.isNotEmpty(streamId) && StringUtils.isNotEmpty(videoUrl)) {
                VdLiveVideo liveParam = InstanceUtil.newInstance(VdLiveVideo.class);
                String[] streamArray = streamId.split("_");
                liveParam.setLiveRoomId(streamArray[1]);
                VdLiveVideo liveVideo = roomService.selectOne(liveParam);
                if (liveVideo != null) {
                    liveVideo.setVideoUrl(videoUrl);
                    VdLiveVideo live = roomService.update(liveVideo);
                    if (live != null) {
                        modelMap.put("code", SERVER_CODE);
                        return modelMap;
                    }
                }
            }
        } else if (eventType == Config.LiveMsgEventType.SCREENSHOT_CREATED) {   //生成截图功能

            VdLiveVideo liveParam = InstanceUtil.newInstance(VdLiveVideo.class);
            String picFullUrl = jsonObject.getString("pic_full_url");
            callbackLog.setPicFullUrl(picFullUrl);

            String[] streamArray = streamId.split("_");
            liveParam.setLiveRoomId(streamArray[1]);
            VdLiveVideo liveVideo = roomService.selectOne(liveParam);
            if (liveVideo != null) {
                liveVideo.setPageUrl(picFullUrl);
                VdLiveVideo live = roomService.update(liveVideo);
                if (live != null) {
                    modelMap.put("code", SERVER_CODE);
                    return modelMap;
                }
            }
        }
        callbackLogService.update(callbackLog);
        modelMap.put("code", SERVER_CODE);
        return modelMap;
    }
}
