package com.xmyy.manage.web.live;

import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.LiveVideoService;
import com.xmyy.livevideo.vo.LiveVideoManagePageParam;
import com.xmyy.livevideo.vo.LiveVideoMangerListResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

/**
 * 直播后台管理  前端控制器
 *
 * @author james
 */
@RestController
@RequestMapping("/manage/live")
@Api(value = "直播后台管理接口", description = "直播后台管理接口")
public class ManageLiveController extends BaseController<VdLiveVideo, LiveVideoService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "直播查询列表", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoMangerListResult.class)
    public Object query(@RequestBody LiveVideoManagePageParam params) {
        return setSuccessModelMap(new ModelMap(), service.queryLiveVideoList(params));
    }


    @PostMapping(value = "/detail")
    @ApiOperation(value = "直播详情", produces = MediaType.APPLICATION_JSON_VALUE, response = LiveVideoMangerListResult.class)
    @ApiImplicitParam(paramType = "query", name = "roomId", value = "直播房间ID", required = false, dataType = "String")
    public Object get(@RequestParam(value = "roomId") String roomId) {
        return exec(() -> service.queryLiveVideoByRoomId(roomId), null);

    }
}