package com.xmyy.manage.web.live;

import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.serivce.LiveVideoService;
import com.xmyy.livevideo.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

import javax.validation.Valid;

/**
 * 直播置顶后台管理  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/live")
@Api(value = "直播置顶后台管理接口", description = "直播置顶后台管理接口")
public class ManageLiveTopController extends BaseController<VdLiveVideo, LiveVideoService> {

    @PostMapping(value = "/toplist")
    @ApiOperation(value = "直播置顶管理列表", response = LiveVideoMangerListResult.class)
    public Object queryTopList(@Valid @RequestBody LiveVideoManagePageParam params, BindingResult bindingResult) {
        return exec(() -> service.queryLiveVideoList(params), bindingResult);
    }


    @PostMapping(value = "/setTop")
    @ApiOperation(value = "直播置顶")
    public Object setTop(@Valid @RequestBody LiveVideoMangerTopParam params, BindingResult bindingResult) {
        return exec(() -> service.setTop(params), bindingResult);
    }


    @PostMapping(value = "/currentToplist")
    @ApiOperation(value = "查询当前五条直播置顶数据", response = LiveVideoMangerListResult.class)
    public Object queryCurrentTopList(@Valid @RequestBody LiveVideoCurrentTopParam params, BindingResult bindingResult) {
        return exec(() -> service.queryCurrentTopList(params), bindingResult);
    }


    @PostMapping(value = "/cancelTop")
    @ApiOperation(value = "取消置顶", response = LiveVideoMangerListResult.class)
    public Object cancelTop(@Valid @RequestBody LiveVideoManageCancelTopParam params, BindingResult bindingResult) {
        return exec(() -> service.cancelTop(params), bindingResult);
    }

}