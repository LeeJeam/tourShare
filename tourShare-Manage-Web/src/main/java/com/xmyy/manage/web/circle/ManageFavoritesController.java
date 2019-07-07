package com.xmyy.manage.web.circle;

import com.xmyy.circle.model.DgFavorites;
import com.xmyy.circle.service.DgFavoritesService;
import com.xmyy.circle.vo.FavoritesPageParam;
import com.xmyy.circle.vo.MyFavoritesResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;

import javax.validation.Valid;

/**
 * 用户收藏后台管理  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/favorites")
@Api(value = "用户收藏后台管理接口", description = "用户收藏后台管理接口")
public class ManageFavoritesController extends BaseController<DgFavorites, DgFavoritesService> {

    @PostMapping(value = "/list")
    @ApiOperation(value = "用户收藏", produces = MediaType.APPLICATION_JSON_VALUE, response = MyFavoritesResult.class)
    public Object list(@RequestBody @Valid FavoritesPageParam param, BindingResult bindingResult) {
        if (param.getBuyerId() == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "买家id不能为空");
        }
        return exec(() -> service.mylist(param.getType(), param.getBuyerId(), param.getCurrent(), param.getSize(), 2), bindingResult);
    }

}