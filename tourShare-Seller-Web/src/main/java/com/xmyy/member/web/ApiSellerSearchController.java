package com.xmyy.member.web;

import com.xmyy.member.vo.SellerPageResult;
import com.xmyy.search.service.SellerSearchService;
import com.xmyy.search.vo.HotSearchParam;
import com.xmyy.search.vo.SellerHotSearchResult;
import com.xmyy.search.vo.SellerPageParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.AbstractController;
import top.ibase4j.core.util.WebUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 买手ES搜索  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/api/seller/search")
@Api(value = "买手ES搜索接口", description = "买手ES搜索接口")
public class ApiSellerSearchController extends AbstractController {

    @Resource
    private SellerSearchService sellerSearchService;

    @PostMapping(value = "list")
    @ApiOperation(value = "ES搜索买手+背包客列表", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerPageResult.class)
    public Object search(@Valid @RequestBody SellerPageParam query, BindingResult br, HttpServletRequest request) {
        String clientIp = WebUtil.getHost(request);
        query.setClientIp(clientIp);
        return exec(() -> sellerSearchService.search(query), br);
    }


    @PostMapping(value = "hots")
    @ApiOperation(value = "ES热门搜索关键词", produces = MediaType.APPLICATION_JSON_VALUE, response = SellerHotSearchResult.class)
    public Object host(@RequestBody HotSearchParam query) {
        return exec(() -> sellerSearchService.findHotSearchList(query), null);
    }

}
