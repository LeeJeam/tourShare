package com.xmyy.livevideo.logic;

import com.alibaba.fastjson.JSONObject;
import com.xmyy.livevideo.common.Config;
import com.xmyy.livevideo.core.util.LiveVideoUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class LiveUtil {

    @Resource
    RestTemplate restTemplate;
    private static Logger log = LoggerFactory.getLogger(LiveUtil.class);

    /**
     * 生成推流地址
     */
    public String genPushUrl(String userID) {
        return genPushUrl(userID, System.currentTimeMillis() / 1000  + Config.Live.validTime);
    }

    private String genPushUrl(String userID, long txTime) {
        String liveCode = Config.Live.APP_BIZID + "_" + userID;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.PUSH_SECRET_KEY + liveCode + Long.toHexString(txTime).toUpperCase());
        String ext = "?bizid=" + Config.Live.APP_BIZID + "&txSecret=" + txSecret + "&txTime=" + Long.toHexString(txTime).toUpperCase();
        String pushUrl = "rtmp://" + Config.Live.APP_BIZID + ".livepush.myqcloud.com/live/" + liveCode + ext;
        log.info("genPushUrl , url: " + pushUrl);
        return pushUrl;
    }

    /**
     * 生成混流地址
     */
    public String genMixedPlayUrl(String subID, String suffix) {
        String liveCode = Config.Live.APP_BIZID + "_" + subID;
        return "https://" + Config.Live.APP_BIZID + ".liveplay.myqcloud.com/live/" + liveCode + "." + suffix;
    }

    /**
     * 生成加速拉流播放地址
     */
    public String genAcceleratePlayUrl(String subID) {
        return genAcceleratePlayUrl(subID, System.currentTimeMillis() / 1000  + Config.Live.validTime);
    }

    /**
     * 生成加速拉流播放地址
     */
    private String genAcceleratePlayUrl(String subID, long txTime) {
        String liveCode = Config.Live.APP_BIZID + "_" + subID;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.PUSH_SECRET_KEY + liveCode + Long.toHexString(txTime).toUpperCase());
        String ext = "?bizid=" + Config.Live.APP_BIZID + "&txSecret=" + txSecret + "&txTime=" + Long.toHexString(txTime).toUpperCase();
        String accPlayUrl = "rtmp://" + Config.Live.APP_BIZID + ".liveplay.myqcloud.com/live/" + liveCode + ext;
        return accPlayUrl;
    }

    public static String getCloseLiveUrl(String id) {
        // 此请求的有效时间
        Long current = System.currentTimeMillis() / 1000 + 10;
        // 生成sign签名
        String sign = LiveVideoUtils.getMD5(new StringBuffer().append(Config.Live.APIKEY).append(current).toString());
        // 生成需要关闭的直播码
        String code = Config.Live.APP_BIZID + "_" + id;
        // 生成关闭的参数列表
        String params = new StringBuffer().append("&interface=Live_Channel_SetStatus").append("&Param.s.channel_id=").append(code).append("&Param.n.status=0").append("&t=").append(current).append("&sign=").append(sign).toString();
        // 拼接关闭URL
        String url ="http://fcgi.video.qcloud.com/common_access" + "?appid=" + Config.Live.APP_ID + params;
        return url;
    }

    /**
     * 录播视频
     * @param userID
     * @return
     */
    public  String getRecordUrl(String userID) {
        Long current = (System.currentTimeMillis() + 60 * 60 * 24 * 1000) / 1000;
        String sign = LiveVideoUtils.getMD5(new StringBuffer().append(Config.Live.APIKEY).append(current).toString());
        String code = Config.Live.APP_BIZID + "_" + userID;
        String params = new StringBuffer().append("&interface=Live_Tape_GetFilelist").append("&Param.s.channel_id=").append(code).append("&t=").append(current).append("&sign=").append(sign).toString();
        String url = Config.Live.API_ADDRESS + "?appid=" +  Config.Live.APP_ID  + params;  // 拼接URL
        return url;
    }

    /**
     * 向云直播后台请求混流操作
     */
    public String mergeStream(Map map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://fcgi.video.qcloud.com/common_access" + getQueryString());

        HttpEntity<String> entity;
        Object param = map.get("mergeParams");
        if (param instanceof String) {
            entity = new HttpEntity<String>((String) param, headers);
        } else {
            entity = new HttpEntity<String>(JSONObject.toJSONString(param), headers);
        }

        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);

        log.info("mergeStream response: " + response.toString());

        return response.getBody();
    }

    /**
     * 从推流地址中提取流ID，完整的(推流状态检查) + 去掉bizid前缀的(生成对应的播放地址)
     */
    public static String getStreamIdFromPushUrl(String pushUrl) {
        int index = pushUrl.indexOf("?");
        if (index == -1)
            return null;
        String substr = pushUrl.substring(0, index);
        int index_2 = substr.lastIndexOf("/");
        String streamID = substr.substring(index_2 + 1, index);
        return streamID;
    }

    private String getQueryString() {
        long txTime = System.currentTimeMillis() / 1000 + 60;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.APIKEY + txTime);
        String query = "?appid=" + Config.Live.APP_ID +
                "&interface=mix_streamv2.start_mix_stream_advanced&t=" + txTime + "&sign=" + txSecret;
        return query;
    }

}
