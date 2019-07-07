package com.xmyy.manage.web.cert;

import com.xmyy.cert.model.UcMemberCert;
import com.xmyy.cert.service.UcMemberCertService;
import com.xmyy.cert.vo.MemberCertCountParam;
import com.xmyy.cert.vo.MemberCertParam;
import com.xmyy.cert.vo.MemberCertUploadResult;
import com.xmyy.common.shiro.ShiroUtils;
import com.xmyy.common.util.AliyunOSSUtil;
import com.xmyy.common.vo.AdminUserInfo;
import com.xmyy.common.vo.FileUploadInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.ibase4j.core.base.BaseController;
import top.ibase4j.core.support.HttpCode;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

/**
 * 用户认证后台管理  前端控制器
 *
 * @author simon
 */
@RestController
@RequestMapping("/manage/cert")
@Api(value = "用户认证后台管理", description = "用户认证后台管理接口")
public class ManageCertController extends BaseController<UcMemberCert, UcMemberCertService> {

    @GetMapping("/list")
    @ApiOperation(value = "查询认证列表", produces = MediaType.APPLICATION_JSON_VALUE, response = UcMemberCert.class)
    public Object query(MemberCertParam param) {
        return exec(() -> service.queryMemberCertList(param));
    }


    @GetMapping("/userDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "memberId", value = "用户ID", dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "memberType", value = "用户类型（1买手，2背包客）", dataType = "Integer", paramType = "query")})
    @ApiOperation(value = "通过用户ID获取认证详情", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object userDetail(Long memberId, Integer memberType) {
        return exec(() -> service.queryUserCertDetail(memberId, memberType));
    }


    @GetMapping("/detail")
    @ApiOperation(value = "通过认证ID获取认证详情", produces = MediaType.APPLICATION_JSON_VALUE, response = UcMemberCert.class)
    @ApiImplicitParam(paramType = "query", name = "id", value = "认证详情ID", required = true, dataType = "Long")
    public Object queryById(Long id) {
        if (id == null) {
            return setModelMap(HttpCode.BAD_REQUEST.value().toString(), "认证ID不能为空");
        }
        return exec(() -> service.queryById(id));
    }


    @PostMapping("/verifyCertInfo")
    @ApiOperation(value = "审核认证", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object verifyCertInfo(@Valid @RequestBody UcMemberCert memberCert, BindingResult bindingResult) {
        AdminUserInfo userInfo = ShiroUtils.getCurrentUserInfo();
        memberCert.setRealReviewTime(new Date());
        memberCert.setRealReviewUserId(userInfo.getId());
        memberCert.setRealReviewUserName(userInfo.getUserName());
        return exec(() -> service.verifyCertInfo(memberCert), bindingResult);
    }


    @GetMapping("/nextUnaudit")
    @ApiOperation(value = "查询下一个未认证记录", response = UcMemberCert.class)
    public Object queryNextUnaudit() {
        return exec(() -> service.queryNextUnaudit());
    }


    @GetMapping("/resultCount")
    @ApiOperation(value = "获取审核统计结果", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getResultCount(MemberCertCountParam param) {
        return exec(() -> service.getResultCount(param), null);
    }


    @PostMapping(value = "/delete")
    @ApiOperation(value = " 删除用户认证", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiImplicitParam(paramType = "query", name = "id", value = "用户认证详情id", required = false, dataType = "Long")
    public Object del(HttpServletRequest request, @RequestParam(value = "id") Long id) {
        if (id == null) {
            return setModelMap(HttpCode.OK.value().toString(), "id不能为空");
        }
        UcMemberCert memberCert = service.queryById(id);
        return exec(() -> super.del(request, memberCert), null);
    }


    @PostMapping(value = "/uploadImage", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", response = MemberCertUploadResult.class)
    @ApiResponses({
            @ApiResponse(code = 415, message = "415不是服务器中所支持的格式"),
            @ApiResponse(code = 413, message = "413上传的文件超过8M"),
            @ApiResponse(code = 403, message = "403上传的目录，没有权限"),
            @ApiResponse(code = 412, message = "412上传的文件夹没有指定"),
            @ApiResponse(code = 400, message = "400系统异常，联系管理员")
    })
    public Object uploadImage(@ApiParam(value = "上传的文件夹，为了统一管理上传图片的资源", required = true) @RequestParam(required = false, value = "uploadDirName") String uploadDirName,
                              @ApiParam(value = "上传图片", required = true) @RequestParam(required = true, value = "image") MultipartFile image) {
        if (StringUtils.isBlank(uploadDirName)) {
            return setModelMap(HttpCode.PRECONDITION_FAILED.value().toString(), "上传的文件夹没有指定");
        }
        FileUploadInfo resultInfo = AliyunOSSUtil.commonUploadImg(image, uploadDirName, true);
        if (StringUtils.isNotBlank(resultInfo.getFailCause())) {
            return setModelMap(resultInfo.getReturnCode(), resultInfo.getFailCause());
        }
        MemberCertUploadResult urlRet = new MemberCertUploadResult();
        urlRet.setImgUrl(resultInfo.getImageUrl());
        return setSuccessModelMap(urlRet);
    }

}