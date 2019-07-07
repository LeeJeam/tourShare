package com.xmyy.common.push;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.tencent.xinge.*;
import com.xmyy.common.EnumConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 信鸽推送
 *
 * @author yeyu
 */
public class PushMessage {

    private static final Logger logger = LogManager.getLogger(PushMessage.class);

    //android买家端secretKey
    private static String androidBuyerSecretKey = "5fc06a94139395fec6b21bd0acd31e4f";
    //android买家端accessId
    private static long androidBuyerAccessId = 2100299404;

    //IOS买家端secretKey
    private static String isoBuyerSecretKey = "a4a51d59b59fa364006b87ebe6a73d9e";
    //IOS买家端accessId
    private static long isoBuyerAccessId = 2200299286L;

    //android买手端secretKey
    private static String androidSellerSecretKey = "c95487f9b412a02aed09c6c0a2413c68";
    //android买手端secretKey
    private static long androidSellerAccessId = 2100299412;

    //IOS买手端secretKey
    private static String isoSellerSecretKey = "b0a8dc888c1371d3a0f62962e0da9078";
    //IOS买手端accessId
    private static long isoSellerAccessId = 2200299324L;

    //安卓信鸽实例
    private XingeApp xinge;
    //IOS信鸽实例
    private XingeApp xingeIOS;
    //安卓配置信息
    private Message message;
    //IOS配置信息
    private MessageIOS messageIOS;
    //买家端实例
    private static PushMessage pushBuyerMessage;
    //买手端实例
    private static PushMessage pushSellerMessage;


    public static void  main(String args[]) {
        Map<String,Object> customMap=new HashMap<>();
        customMap.put("operationType", EnumConstants.PushType.EVERYDAY_RADIO.getValue());
        customMap.put("memberType",EnumConstants.MemberType.seller.getValue());
        PushMessage.getSellerInstance().pushSingleMessage("标题","内容----------------------",customMap,new PushUser("0a0fb99409134782ae30c1410700a788", EnumConstants.DeviceType.IOS.getValue()),null);
    }

    /**
     * 构造函数
     * @param memberType 买家或者买手
     */
    private PushMessage(String memberType) {
        if (!StringUtils.isEmpty(memberType) && "seller".equals(memberType)) {
            logger.info("初始化买手信鸽推送消息对象");
            xinge = new XingeApp(androidSellerAccessId, androidSellerSecretKey);
            xingeIOS = new XingeApp(isoSellerAccessId, isoSellerSecretKey);
        } else {
            logger.info("初始化买家信鸽推送消息对象");
            xinge = new XingeApp(androidBuyerAccessId, androidBuyerSecretKey);
            xingeIOS = new XingeApp(isoBuyerAccessId, isoBuyerSecretKey);
        }
        //初始化Message
        logger.info("初始化安卓Message消息配置");
        initConfigAndroid();
        //初始化IOSMessage
        logger.info("初始化IOSMessage消息配置");
        initConfigIOS();
    }

    /**
     * 安卓初始化配置
     */
    private void initConfigAndroid() {
        message = new Message();

        //设置推送消息时间区间
        TimeInterval acceptTime = new TimeInterval(0, 0, 23, 59);
        message.addAcceptTime(acceptTime);

        //消息展示样式：响铃，震动，通知可删除，通知不累加
        Style style = new Style(3, 1, 1, 1, 0);
        message.setStyle(style);

        message.setType(Message.TYPE_NOTIFICATION);

        /*
         * 消息离线存储时间（单位：秒），默认3天
         * 选填，最长存储时间3天，设置0等同于使用默认值
         */
        message.setExpireTime(24*60*60);
    }

    /**
     * IOS初始化配置
     */
    private void initConfigIOS() {
        messageIOS = new MessageIOS();
        messageIOS.setType(MessageIOS.TYPE_APNS_NOTIFICATION);
        messageIOS.setExpireTime(24*60*60);
        messageIOS.setBadge(1);
        messageIOS.setSound("beep.wav");
        messageIOS.setCategory("NEW_MESSAGE_CATEGORY");
    }

    /**
     * 获取买手端实例
     */
    public static PushMessage getSellerInstance() {
        if (pushSellerMessage == null) {
            pushSellerMessage = new PushMessage("seller");
        }
        return pushSellerMessage;
    }

    /**
     * 获取买家端实例
     */
    public static PushMessage getBuyerInstance() {
        if (pushBuyerMessage == null) {
            pushBuyerMessage = new PushMessage("buyer");
        }
        return pushBuyerMessage;
    }


    /************************* 从这里开始是推送方法 ***************************/
    /**
     * 推送到指定账号
     * @param title 标题
     * @param content 内容
     * @param custom 参数，如果没有，可为空
     * @param pushUser 发送账户，uuid,deviceType设备类型
     * @param timeStamp timeStamp 定时发送时间串 如：2013-12-20 18:31:00,如果不定时，可以为null
     */
    public void pushSingleMessage(String title, String content, Map<String, Object> custom, PushUser pushUser, String timeStamp){
        //构造推送内容
        this.createBasicPush(title, content, custom, timeStamp);

        //点击推送消息，打开应用
//        actionTypeActivity(null);

        if (pushUser.getDeviceType() == EnumConstants.DeviceType.Android.getValue().intValue()) {
            JSONObject ret = xinge.pushSingleAccount(0, pushUser.getUuid(), message);
            if (ret.getInt("ret_code") != 0) {
                logger.info("ERROR----------信鸽推送：安卓设备推送今日提醒失败," + ret.getString("err_msg"));
            } else {
                logger.info("SUCCESS--------信鸽推送：安卓设备推送今日提醒成功");
            }
        }
        if (pushUser.getDeviceType() == EnumConstants.DeviceType.IOS.getValue().intValue()) {
            JSONObject retIso = xingeIOS.pushSingleAccount(0, pushUser.getUuid(), messageIOS, XingeApp.IOSENV_PROD);
            if (retIso.getInt("ret_code") != 0) {
                logger.info("ERROR----------信鸽推送：IOS设备推送今日提醒失败," + retIso.getString("err_msg"));
            } else {
                logger.info("SUCCESS--------信鸽推送：IOS设备推送今日提醒成功");
            }
        }
    }


    /**
     * 批量推送
     * @param title 标题
     * @param content 内容
     * @param custom 自定义参数,如果没有自定义参数，可以为null
     * @param timeStamp 定时发送时间串 如：2013-12-20 18:31:00,如果不定时，可以为null
     * @param  pushUsers 账户集合
     */
    public void pushSysMessageToAccountList(String title, String content, Map<String, Object> custom, String timeStamp, List<PushUser> pushUsers){
        if(CollectionUtils.isNotEmpty(pushUsers)) {
            List<String> androidUuidlist = pushUsers.stream().filter(s->s.getDeviceType() == EnumConstants.DeviceType.Android.getValue()).map(PushUser::getUuid).collect(Collectors.toList());
            List<String> iosUuidlist = pushUsers.stream().filter(s->s.getDeviceType() == EnumConstants.DeviceType.IOS.getValue()).map(PushUser::getUuid).collect(Collectors.toList());

            //构造推送内容
            this.createBasicPush(title, content, custom, timeStamp);

            if (CollectionUtils.isNotEmpty(androidUuidlist)) {
                JSONObject androidJson = xinge.createMultipush(message);
                long pushId = androidJson.getJSONObject("result").getLong("push_id");
                if (androidJson.getInt("ret_code") == 0) {
                    JSONObject ret = xinge.pushAccountListMultiple(pushId, androidUuidlist);
                    if (ret.getInt("ret_code") != 0) {
                       logger.info("ERROR----------信鸽推送：安卓设备批量推送消息失败," + ret.getString("err_msg"));
                   } else {
                       logger.info("SUCCESS--------信鸽推送：安卓设备批量推送消息成功");
                   }
                }
            }

            if (CollectionUtils.isNotEmpty(iosUuidlist)) {
                JSONObject iosJson = xingeIOS.createMultipush(messageIOS, XingeApp.IOSENV_PROD);
                long pushId = iosJson.getJSONObject("result").getLong("push_id");
                if (iosJson.getInt("ret_code") == 0) {
                    JSONObject retIso = xingeIOS.pushAccountListMultiple(pushId, iosUuidlist);
                    if (retIso.getInt("ret_code") != 0) {
                        logger.info("ERROR----------信鸽推送：IOS设备批量推送消息失败," + retIso.getString("err_msg"));
                    } else {
                        logger.info("SUCCESS--------信鸽推送：IOS设备批量推送消息成功");
                    }
                }
            }
        }
    }


    /**
     * 构造推送内容
     * @param title 标题
     * @param content 内容
     * @param custom 自定义参数
     * @param timeStamp 定时发送时间串 如：2013-12-20 18:31:00
     */
    private void createBasicPush(String title, String content, Map<String, Object> custom, String timeStamp){
        //标题
        message.setTitle(title);
        //内容
        message.setContent(content);
        JSONObject json = new JSONObject();
        //标题
        json.put("title",title);
        //内容
        json.put("body",content);
        //副标题
        // json.put("subtitle",title);
        messageIOS.setAlert(json);
        //用户自定义的 key-value。注意每一个 key 和 value 的字符都计入消息字符数
        if(custom != null && custom.size() > 0) {
            Map<String,Object> data = new HashMap<>();
            data.put("data",custom);
            message.setCustom(data);
            messageIOS.setCustom(data);
        }
        //设置推送时间
        if(StringUtils.isNotEmpty(timeStamp)) {
            message.setSendTime(timeStamp);
            messageIOS.setSendTime(timeStamp);
        }
    }

    /**
     * 打开网址
     * @param url 点击执行url
     */
    private void actionTypeUrl(String url){
        ClickAction action = new ClickAction();
        action.setActionType(ClickAction.TYPE_URL);
        action.setUrl(url);
        message.setAction(action);
    }

    /**
     * 打开应用内页面
     * @param activity
     */
    private void actionTypeActivity(String activity){
        ClickAction action = new ClickAction();
        action.setActionType(ClickAction.TYPE_ACTIVITY);
        action.setActivity(activity);
//        action.setAtyAttrIntentFlag();
//        action.setAtyAttrPendingIntentFlag();
        message.setAction(action);
    }

}
