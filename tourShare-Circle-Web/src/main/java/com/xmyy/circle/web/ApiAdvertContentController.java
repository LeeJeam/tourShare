package com.xmyy.circle.web;

import com.xmyy.circle.model.WsAdvertContent;
import com.xmyy.circle.service.WsAdvertContentService;
import com.xmyy.circle.vo.AdApiParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ibase4j.core.base.BaseController;

import javax.validation.Valid;

/**
 * 广告位  前端控制器
 *
 * @author zlp
 */
@Controller
@RequestMapping("/api/circle/ad")
@Api(value = "广告位接口", description = "广告位接口")
public class ApiAdvertContentController extends BaseController<WsAdvertContent, WsAdvertContentService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "广告位列表", produces = MediaType.APPLICATION_JSON_VALUE, response = WsAdvertContent.class)
    public Object listContent(@RequestBody @Valid AdApiParam param, BindingResult bindingResult) {
        return exec(() -> service.listContent(param), bindingResult);
    }

}