package com.xmyy.manage.web.buyer;

import com.xmyy.circle.service.DgCommentService;
import com.xmyy.circle.vo.CommentAddParam;
import com.xmyy.member.model.UcBuyer;
import com.xmyy.member.model.UcMemberLog;
import com.xmyy.member.service.UcBuyerManageService;
import com.xmyy.member.vo.BuyerManagePageParam;
import com.xmyy.member.vo.BuyerManagePageResult;
import com.xmyy.member.vo.MemberManageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.support.HttpCode;
import top.ibase4j.core.util.ExcelReaderUtil;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 买家后台管理  前端控制器
 *
 * @author zlp
 */
@RestController
@RequestMapping("/manage/buyer")
@Api(value = "买家后台管理", description = "买家后台管理")
public class ManageBuyerController extends BaseController<UcBuyer, UcBuyerManageService> {

    @Resource
    private DgCommentService dgCommentService;

    @PostMapping(value = "/list")
    @ResponseBody
    @ApiOperation(value = "买家/背包客列表", produces = MediaType.APPLICATION_JSON_VALUE, response = BuyerManagePageResult.class)
    public Object list(@RequestBody @Valid BuyerManagePageParam param, BindingResult bindingResult) {
        return exec(() -> service.list(param), bindingResult);
    }


    @ApiOperation(value = "买家/背包客基本信息", response = MemberManageResult.class)
    @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "Long")
    @RequestMapping(value = "getInfo", method = {RequestMethod.GET})
    public Object getInfo(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.getInfo(id), null);
    }


    @ApiOperation(value = "获取修改记录", response = UcMemberLog.class)
    @RequestMapping(value = "getLogList", method = {RequestMethod.GET})
    @ApiImplicitParam(paramType = "query", name = "id", value = "id", dataType = "Long")
    public Object getLogList(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "id不能为空");
        }
        return exec(() -> service.getLogList(id), null);
    }


    @PostMapping("/addVirtual")
    @ApiOperation("批量生产虚拟买家，导入Excel")
    public Object virtual(MultipartFile file) throws IOException {
        if (file == null) {
            return null;
        }
        try {
            List<String[]> data = ExcelReaderUtil.excelToArrayList(file.getOriginalFilename(), file.getInputStream());
            data.remove(0);
            return exec(() -> service.addVirtual(data), null);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BizException("Excel有误，请重新创建Excel");
        }
    }


    @GetMapping("/getRandomVirtual")
    @ApiOperation(value = "获取随机虚拟买家", response = UcBuyer.class)
    public Object getRandomVirtual() {
        return exec(() -> service.getRandomVirtual(), null);
    }


    @PostMapping("/addComment")
    @ApiOperation(value = "评论/回复", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object addComment(@RequestBody CommentAddParam params, BindingResult bindingResult) {
        return exec(() -> dgCommentService.addComment(params), bindingResult);
    }

}