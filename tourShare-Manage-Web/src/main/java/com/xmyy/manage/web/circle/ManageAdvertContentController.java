package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.WsAdvertContent;
import com.xmyy.circle.service.WsAdvertContentService;
import com.xmyy.circle.vo.AdContentParam;
import com.xmyy.circle.vo.AdManageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;

import javax.validation.Valid;

/**
 * 广告后台管理  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/ad")
@Api(value = "广告后台管理接口", description = "广告后台管理接口")
public class ManageAdvertContentController extends BaseController<WsAdvertContent, WsAdvertContentService> {

    @PostMapping(value = "/list")
    @ResponseBody
    @ApiOperation(value = "后台广告列表", produces = MediaType.APPLICATION_JSON_VALUE, response = WsAdvertContent.class)
    public Object list(@RequestBody @Valid AdManageParam param, BindingResult bindingResult) {
        return exec(() -> service.list(param), bindingResult);
    }


    @ResponseBody
    @RequestMapping(value = "/updateImg", method = RequestMethod.POST)
    @ApiOperation(value = "变更图片与链接", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object updateImg(@Valid @RequestBody AdContentParam param, BindingResult bindingResult) {
        param.setUpdateBy(this.getShiroCurrUser());
        return exec(() -> service.updateImg(param), bindingResult);
    }

}