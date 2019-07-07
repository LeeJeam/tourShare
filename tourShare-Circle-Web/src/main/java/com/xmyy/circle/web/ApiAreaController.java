package com.xmyy.circle.web;

import com.xmyy.circle.model.DgArea;
import com.xmyy.circle.service.DgAreaService;
import com.xmyy.circle.vo.ApiAreaJieDaoParam;
import com.xmyy.circle.vo.AreaTreeResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;

import javax.annotation.Resource;

/**
 * 省市区县  前端控制器
 *
 * @author wangzejun
 */
@RestController
@RequestMapping("/api/circle/area")
@Api(value = "省市区县信息", description = "省市区县信息树")
public class ApiAreaController extends BaseController<DgArea, DgAreaService> {

    @GetMapping(value = "/tree")
    @ApiOperation(value = "省市区县信息树", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, response = AreaTreeResult.class)
    public Object tree() {
        return exec(() -> service.getAreaTree(), null);
    }


    @GetMapping(value = "/getJieDao")
    @ApiOperation(value = "根据区县编码获取街道信息", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE, response = AreaTreeResult.class)
    @ApiImplicitParam(paramType = "query", name = "quxianCode", dataType = "String")
    public Object getJiedaoInfo(String quxianCode) {
        ApiAreaJieDaoParam params = new ApiAreaJieDaoParam();
        params.setQuxianCode(quxianCode);
        return exec(() -> service.getJieDaoInfo(params), null);
    }

}