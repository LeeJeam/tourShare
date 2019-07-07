package com.xmyy.manage.web.admin;

import com.xmyy.common.EnumConstants;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AbstractController;

import java.util.Map;

/**
 * 基本数据  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/enum")
@Api(value = "基本数据接口", description = "基本数据接口")
public class EnumApiController extends AbstractController {

    @ResponseBody
    //@RequiresPermissions("sys.base.dept.read")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ApiOperation(value = "基本数据", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "query", name = "type", value = "枚举类型：" +
            "1，邀请码渠道；" +
            "2，动态类型；" +
            "3,状态（0发布中，10,审核不通过，50上架，-50下架）；" +
            "4,性别；" +
            "5,0待使用，50已使用，-1已失效;", dataType = "Long")
    public Object get(Integer type) {
        Map<Integer, String> result = null;
        switch (type) {
            case 1:
                result = EnumConstants.ChannelType.getMap();
                break;
            case 2:
                result = EnumConstants.CircleType.getMap();
                break;
            case 3:
                result = EnumConstants.State.toShelvesMap();
                break;
            case 4:
                result = EnumConstants.Gender.toMap();
                break;
            case 5:
                result = EnumConstants.State.toUseMap();
                break;/*
            case default:
                break;*/
        }
        return setSuccessModelMap(new ModelMap(), result);
    }

}