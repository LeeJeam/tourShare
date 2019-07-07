package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.UcMemberRelation;
import com.xmyy.circle.service.UcMemberRelationService;
import com.xmyy.circle.vo.UcMemberRelationPageParam;
import com.xmyy.circle.vo.UcMemberRelationResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 关注后台管理  前端控制器
 *
 * @author yeyu
 */
@RestController
@RequestMapping("/manage/circle/focus")
@Api(value = "关注后台管理接口", description = "关注后台管理接口")
public class ManageMemberRelationController extends BaseController<UcMemberRelation, UcMemberRelationService> {

    @PostMapping(value = "/buyer/list")
    @ApiOperation(value = "获取关注的买家列表", produces = MediaType.APPLICATION_JSON_VALUE, response = UcMemberRelationResult.class)
    public Object getMemberbuyerList(@Valid @RequestBody UcMemberRelationPageParam page, BindingResult bindingResult) {
        return exec(() -> service.getBuyerFocusList(page), bindingResult);
    }


    @PostMapping(value = "/seller/list")
    @ApiOperation(value = "获取关注买手列表", produces = MediaType.APPLICATION_JSON_VALUE, response = UcMemberRelationResult.class)
    public Object getMembersellerList(@Valid @RequestBody UcMemberRelationPageParam page, BindingResult bindingResult) {
        return exec(() -> service.getSellerFocusList(page), bindingResult);
    }
}
