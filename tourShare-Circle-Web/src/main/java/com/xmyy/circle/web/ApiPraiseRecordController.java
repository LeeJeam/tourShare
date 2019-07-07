package com.xmyy.circle.web;

import com.xmyy.circle.model.DgPraiseRecord;
import com.xmyy.circle.service.DgPraiseRecordService;
import com.xmyy.circle.vo.MyPraiseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AppBaseController;

import javax.servlet.http.HttpServletRequest;

/**
 * 点赞  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/praise")
@Api(value = "动态点赞", description = "动态点赞")
public class ApiPraiseRecordController extends AppBaseController<DgPraiseRecord, DgPraiseRecordService> {

    @ResponseBody
    @GetMapping(value = "/myList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", name = "sessid", value = "登录sessid", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "type", value = "1-收到的赞（默认）；2-发出的赞", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "current", value = "页码（默认1）", dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "size", value = "页大小（默认10）", dataType = "Long")
    })
    @ApiOperation(value = "我的赞", produces = MediaType.APPLICATION_JSON_VALUE, response = MyPraiseResult.class)
    public Object mylist(Integer type, Integer current, Integer size, HttpServletRequest request) {
        return exec(() -> service.mylist(type, this.getCurrUser(request), current, size));
    }
}
