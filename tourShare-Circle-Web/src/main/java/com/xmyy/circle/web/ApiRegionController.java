package com.xmyy.circle.web;

import com.xmyy.circle.model.DgRegion;
import com.xmyy.circle.service.RegionService;
import com.xmyy.circle.vo.RegionTreeListParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import top.ibase4j.core.base.BaseController;

/**
 * 地区服务  前端控制器
 *
 * @author wangzejun
 */
@Api(value = "地区接口", description = "地区接口")
@Controller
@RequestMapping("/api/circle/region")
public class ApiRegionController extends BaseController<DgRegion, RegionService> {

    @PostMapping("/treeList")
    @ApiOperation(value = "地区树列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getTreeList(@RequestBody RegionTreeListParam params) {
        return exec(() -> service.getTreeList(params), null);
    }
}