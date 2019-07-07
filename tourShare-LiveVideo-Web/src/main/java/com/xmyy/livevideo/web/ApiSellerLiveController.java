package com.xmyy.livevideo.web;

import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.CloudLiveVideoService;
import com.xmyy.livevideo.serivce.LiveVideoRoomService;
import com.xmyy.livevideo.serivce.LiveVideoService;
import com.xmyy.livevideo.vo.*;
import com.xmyy.member.vo.MemberManageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.AppBaseController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 买手端直播  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/api/live")
@Api(value = "买手端开直播间接口", description = "买手端开直播间接口")
public class ApiSellerLiveController extends AppBaseController<VdLiveVideo, LiveVideoService> {

    @Resource
    private LiveVideoRoomService roomService;
    @Resource
    private CloudLiveVideoService cloudLiveVideoService;

    @PostMapping("/getAppUserSig")
    @ApiOperation(value = "APP登录后台获取userSign", produces = MediaType.APPLICATION_JSON_VALUE, response = LoginInfoResult.class)
    @ApiImplicitParam(paramType = "query", name = "uuid", value = "uuid", required = true, dataType = "String")
    public Object getAppUserSig(String uuid) {
        return exec(() -> roomService.getAppUserSig(uuid));
    }


    @PostMapping("/createLiveRoom")
    @ApiOperation(value = "买手端创建直播间", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登陆sessid", required = true, dataType = "String")
    public Object createLiveRoom(@RequestBody LiveVideoAddParam liveVideoAddParams) {
        return exec(() -> roomService.createLiveRoom(liveVideoAddParams));

    }


    @PostMapping("/updateLiveRoomStatus")
    @ApiOperation(value = "买手端开播后更新直播状态", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "sessid", required = true, dataType = "String")
    public Object updateLiveRoomStatus(@RequestBody LiveVideoStatusParam liveVideoStatusParams) {
        return exec(() -> roomService.updateLiveRoomStatus(liveVideoStatusParams));
    }


    @PostMapping(value = "/noticeCountNum")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "sessid", required = true, dataType = "String")
    @ApiOperation(value = "买手端系统通知进群退群统计人数", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoCountResult.class)
    public Object noticeCountNum(@RequestBody LiveVideoCountParam countParams) {
        return exec(() -> roomService.noticeCountNum(countParams.getRoomId(), countParams.getType()));
    }


    @GetMapping(value = "/myLiveVideo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId", value = "买手ID", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小", required = true, dataType = "Integer")
    })
    @ApiOperation(value = "买手端我的直播", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoResult.class)
    public Object myLiveVideo(Long memberId, Integer current, Integer size) {
        return exec(() -> roomService.myLiveVideo(memberId, current, size));
    }


    @PostMapping("/closeLiveRoom")
    @ApiOperation(value = "买手端关闭直播间", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "sessid", required = true, dataType = "String")
    public Object closeLiveRoom(@RequestBody LiveVideoParam liveVideoParams) {
        return exec(() -> roomService.destroyLiveRoom(liveVideoParams));
    }


    @GetMapping(value = "/getSellerInfo")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "memberId", value = "卖家ID", dataType = "Long"),
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "sessid", required = true, dataType = "String")
    })
    @ApiOperation(value = "买手个人主页", produces = MediaType.APPLICATION_JSON_VALUE, response = MemberManageResult.class)
    public Object getSellerInfo(Long memberId) {
        return exec(() -> roomService.getSellerInfo(memberId));
    }


    @PostMapping("/cloudLiveCallBack")
    @ApiOperation(value = "腾讯云回调参数", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object cloudLiveCallBack(HttpServletRequest request) {
        try {
            String jsonBody = IOUtils.toString(request.getInputStream(), Charset.forName("utf-8"));
            logger.debug("===============Tencent Yun CallBack==============");
            logger.debug(jsonBody);
            return exec(() -> cloudLiveVideoService.cloudLiveCallBack(jsonBody));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";
    }


    @PostMapping("/setPortRait")
    @ApiOperation(value = "设置用户资料", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "uuid", value = "uuid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "nickName", value = "别名", dataType = "String"),
            @ApiImplicitParam(paramType = "header", name = "image", value = "图片路径", required = true, dataType = "String")
    })
    public Object setPortRait(HttpServletRequest request) {
        String uuId = request.getHeader("uuid");
        String nickName = request.getHeader("nickName");
        String image = request.getHeader("image");
        return exec(() -> roomService.setPortRait(uuId, nickName, image));
    }

}
