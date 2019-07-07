package com.xmyy.livevideo.logic;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xmyy.livevideo.common.Config;
import com.xmyy.livevideo.core.util.LiveVideoUtils;
import com.xmyy.livevideo.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import top.ibase4j.core.util.InstanceUtil;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class IMManger {
    private static Logger log= LoggerFactory.getLogger(IMManger.class);

    private static final String HOST = "https://console.tim.qq.com/"; // IM后台RESTful API的主机地址。
    @Autowired
    RestTemplate restTemplate;

    /**
     * 建群 - 参考@https://cloud.tencent.com/document/product/269/1615
     */
    public boolean createGroup(String groupID,String userSig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/group_open_http_svc/create_group" + getQueryString(userSig));
        LiveVideoGroupParam groupParams = new LiveVideoGroupParam();
        groupParams.setGroupId(groupID);
        groupParams.setName("group_name");
        groupParams.setType("AVChatRoom");
        groupParams.setOwner_Account(Config.IM.ADMINISTRATOR);
        HttpEntity<String> entity = new HttpEntity<String>(LiveVideoUtils.objectToString(groupParams), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("createGroup, groupID: " + groupID + ", body: " + response.toString());
        if (response.getStatusCode().value() == HttpStatus.OK.value()){
            return true;
        } else {
            log.error("createGroup失败, groupID: " + groupID + ", errMsg: " + response.toString());
            return false;
        }
    }

    /**
     * 辅助功能函数 - 心跳超时检查流状态
     * 0: 流状态是推流中
     * 1: 流状态是断流
     * 2: 请求错误
     */
    public int getStreamStatus (String streamID) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 5分钟
        long txTime = System.currentTimeMillis() / 1000 + 300;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.APIKEY + txTime);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://fcgi..com/common_access?appid=" + Config.Live.APP_ID
                + "&interface=Live_Channel_GetStatus&Param.s.channel_id=" + streamID
                + "&t=" + txTime
                + "&sign=" + txSecret);

        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, entity,String.class);
        log.info("getStreamStatus, streamID:" + streamID + ", response :" + response.getBody());
        // 错误
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            log.warn("getStreamStatus出错, streamID: " + streamID + ", HttpCode: " + response.getStatusCode().value());
            return 2;
        }
        ObjectMapper mapper = new ObjectMapper();
        LiveVideoStreamStatusResult rsp = null;
        try {
            rsp = mapper.readValue(response.getBody(), LiveVideoStreamStatusResult.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 推流中
        if (rsp != null && rsp.getRet() == 0 && rsp.getOutput().get(0) != null && rsp.getOutput().get(0).getStatus() == 1) {
            return 0;
        }
        return 1;
    }


    /**
     * 获取录播地址
     */
    public String getLiveVideoUrl (String streamID) {
        String recodeUrl="";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        long txTime =(System.currentTimeMillis() + 60 * 60 * 24 * 1000) / 1000;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.APIKEY + txTime);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://fcgi.video.qcloud.com/common_access?appid=" + Config.Live.APP_ID
                + "&interface=Live_Tape_GetFilelist&Param.s.channel_id=" + streamID
                + "&t=" + txTime
                + "&sign=" + txSecret);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, entity,String.class);
        log.info("Live_Tape_GetFilelist, streamID:" + streamID + ", response :" + response.getBody());
        JSONObject jsonObject = JSONObject.parseObject(response.getBody());//字符串转json对象
        String data = jsonObject.getString("output");//转换字符串
        if (StringUtils.isNotEmpty(data)) {
            JSONObject object = JSONObject.parseObject(data);//字符串转json对象
            String fileList = object.getString("file_list");  //转换字符串
            JSONArray jsonArray = JSONArray.parseArray(fileList);//并将file_list内容取出转为json数组
            for (int i = 0; i < jsonArray.size(); i++) {     //遍历json数组内容
                JSONObject obj = jsonArray.getJSONObject(i);
                recodeUrl = obj.getString("record_file_url");
            }
        }
       return recodeUrl;
    }
    /**
     * 关闭直播
     */
    public boolean closeLiveVideoUrl (String id) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        long txTime = System.currentTimeMillis() / 1000 + 10;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.APIKEY + txTime);
        String code = Config.Live.APP_BIZID + "_" + id;
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://fcgi.video.qcloud.com/common_access?appid=" + Config.Live.APP_ID
                + "&interface=Live_Channel_SetStatus&Param.s.channel_id=" + code+"&Param.n.status=0"
                + "&t=" + txTime
                + "&sign=" + txSecret);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange( builder.build().toUri(), HttpMethod.GET, entity,String.class);
        log.info("Live_Channel_SetStatus, id:" + id + ", response :" + response.getBody());
        if (response.getStatusCode().value() == HttpStatus.OK.value()){
            return true;
        } else {
            log.error("Live_Channel_SetStatusk失败, groupID: " + id + ", errMsg: " + response.toString());
            return false;
        }
    }
    /**
     * 解散群 - 参考@https://cloud.tencent.com/document/product/269/1624
     */
    public boolean destroyGroup(String groupID,String userSig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/group_open_http_svc/destroy_group" + getQueryString(userSig));
        LiveVideoCloseGroupParam groupParams = new LiveVideoCloseGroupParam();
        groupParams.setGroupId(groupID);
        HttpEntity<String> entity = new HttpEntity<String>(LiveVideoUtils.objectToString(groupParams), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("destroyGroup, groupID: " + groupID + ", body: " + response.toString());
        if (response.getStatusCode().value() == HttpStatus.OK.value()){
            log.error("destroyGroup成功, groupID: " + groupID + ", errMsg: " + response.toString());
            return true;
        }else {
            log.error("destroyGroup出错, groupID: " + groupID + ", errMsg: " + response.toString());
            return false;
        }
    }

    public boolean finishRecord(int taskId,String streamID){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        long txTime = (System.currentTimeMillis() + 60 * 60 * 24 * 1000) / 1000;
        String txSecret = LiveVideoUtils.getMD5(Config.Live.APIKEY + txTime);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://fcgi.video.qcloud.com/common_access?appid=" + Config.Live.APP_ID
                + "&interface=Get_LivePushStat&Param.s.channel_id=" + streamID
                +"&Param.n.task_id=" + taskId
                + "&t=" + txTime
                + "&sign=" + txSecret);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange( builder.build().encode().toUri(), HttpMethod.GET, entity,String.class);
        log.info("finishRecord, streamID:" + streamID + ", response :" + response.getBody());
        if (response.getStatusCode().value() == HttpStatus.OK.value()) {
            log.warn("finishRecord成功, streamID: " + streamID + ", HttpCode: " + response.getStatusCode().value());
            return true;
        }else{
            log.warn("finishRecord出错, streamID: " + streamID + ", HttpCode: " + response.getStatusCode().value());
            return false;
        }
    }

    /**
     * 获取群消息 - 参考@https://cloud.tencent.com/document/product/269/1650
     */
    public Object getHistoryMessge(String msgTime,String userSig) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/open_msg_svc/get_history" + getQueryString(userSig));
        LiveVideoImHistoryParam groupParams = new LiveVideoImHistoryParam();
        groupParams.setChatType("Group");
        groupParams.setMsgTime(msgTime);
        HttpEntity<String> entity = new HttpEntity<String>(LiveVideoUtils.objectToString(groupParams), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("消息时间, msgTime: " + msgTime + ", body: " + response.toString());
        if (response.getStatusCode().value() != HttpStatus.OK.value()){
            log.error("消息时间, msgTime: " + msgTime + ", errMsg: " + response.toString());
        }
        return response.toString();
    }

    /**
     * 设置用户资料 - 参考@https://cloud.tencent.com/document/product/269/1640
     */
    public boolean setPortRait(String uuId,String nickName,String image,String userSig) {
        System.out.println("setPortRait: " +uuId+" nickName " + nickName+" image " + image);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(HOST + "v4/profile/portrait_set" + getQueryString(userSig));
        LiveVideoPortRaitParam portRaitParams = InstanceUtil.newInstance(LiveVideoPortRaitParam.class);
        List<LiveVideoProfileItem> list = InstanceUtil.newArrayList();
        LiveVideoProfileItem profileItemNick =  InstanceUtil.newInstance(LiveVideoProfileItem.class);
        profileItemNick.setTag("Tag_Profile_IM_Nick");
        profileItemNick.setValue(nickName);
        list.add(profileItemNick);
        LiveVideoProfileItem profileItemImage= InstanceUtil.newInstance(LiveVideoProfileItem.class);
        profileItemImage.setTag("Tag_Profile_IM_Image");
        profileItemImage.setValue(image);
        list.add(profileItemImage);
        portRaitParams.setFrom_Account(uuId);
        portRaitParams.setProfileItem(list);
        HttpEntity<String> entity = new HttpEntity<String>(LiveVideoUtils.objectToString(JSON.toJSON(portRaitParams)), headers);
        ResponseEntity<String> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                entity,
                String.class);
        log.info("setPortRait, body: " + response.toString());
        if (response.getStatusCode().value() == HttpStatus.OK.value()){
            return true;
        } else {
            log.error("setPortRait失败, errMsg: " + response.toString());
            return false;
        }
    }

    private String getQueryString(String userSig) {
        String query =
                "?sdkappid=" + Config.IM.IM_SDKAPPID +
                        "&identifier=" + Config.IM.ADMINISTRATOR +
                        "&usersig=" + userSig +
                        "&random=" + UUID.randomUUID().toString() +
                        "&contenttype=json";
        return query;
    }

    private String getString(String uuId,String userSig) {
        String query =
                "?sdkappid=" + Config.IM.IM_SDKAPPID +
                        "&identifier=" + uuId+
                        "&usersig=" + userSig +
                        "&random=" + UUID.randomUUID().toString() +
                        "&contenttype=json";
        return query;
    }


}
