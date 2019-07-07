package com.xmyy.circle.web;

import com.xmyy.circle.model.DgTags;
import com.xmyy.circle.service.DgTagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

/**
 * 标签  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/tags")
@Api(value = "标签接口", description = "标签接口")
public class ApiTagsContentController extends BaseController<DgTags, DgTagsService> {

    @GetMapping(value = "/list")
    @ApiOperation(value = "标签列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object tagList() {
        return exec(() -> service.tagList());
    }

}