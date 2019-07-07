package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.DgRegion;
import com.xmyy.circle.service.RegionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import top.ibase4j.core.base.BaseController;

/**
 * 国家地区  前端控制器
 *
 * @author wangzejun
 */
public class ManageRegionController extends BaseController<DgRegion, RegionService> {

    @PostMapping("/regionList")
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String")
    @ApiOperation(value = "国家列表", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getTreeList() {
        return exec(() -> service.getRegionList(), null);
    }
}