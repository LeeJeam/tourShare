package com.xmyy.circle.web;

import com.xmyy.search.service.CircleSearchService;
import com.xmyy.search.vo.CirclePageParam;
import com.xmyy.search.vo.CirclePageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AbstractController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 动态ES搜索  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/circle/dynamic/search")
@Api(value = "动态ES搜索", description = "动态ES搜索")
public class ApiCricleSearchController extends AbstractController {

    @Resource
    private CircleSearchService circleSearchService;

    @PostMapping(value = "list")
    @ApiOperation(value = "动态ES搜索列表", produces = MediaType.APPLICATION_JSON_VALUE, response = CirclePageResult.class)
    public Object search(@Valid @RequestBody CirclePageParam query, BindingResult br, HttpServletRequest request) {
        return exec(() -> {
            query.setBuyerId(this.getCurrUser(request));
            return circleSearchService.search(query);
        }, br);
    }

}
