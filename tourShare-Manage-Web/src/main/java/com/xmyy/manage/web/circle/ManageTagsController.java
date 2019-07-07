package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.DgTags;
import com.xmyy.circle.service.DgTagsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

/**
 * 标签  前端控制器
 *
 * @author LinBo
 */
@RestController
@RequestMapping("/manage/tags")
@Api(value = "标签服务", description = "标签服务")
public class ManageTagsController extends BaseController<DgTags, DgTagsService> {

    @GetMapping("/list")
    @ApiOperation(value = "查询所有标签", response = String.class)
    public Object getAll() {
        return exec(() -> service.tagList());
    }
}
