package com.xmyy.cert.web;

import com.xmyy.cert.vo.MemberCertUploadResult;
import com.xmyy.common.util.AliyunOSSUtil;
import com.xmyy.common.vo.FileUploadInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.ibase4j.core.base.AbstractController;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 文件上传  前端控制器
 *
 * @author wangzejun
 */
@RestController("/api/fileupload")
@Api(value = "文件上传接口", description = "APP文件上传接口")
public class ApiFileUploadController extends AbstractController {

    @ResponseBody
    @PostMapping(value = "/uploadImage", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", response = MemberCertUploadResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "sessid", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 415, message = "415不是服务器中所支持的格式"),
            @ApiResponse(code = 413, message = "413上传的文件超过8M"),
            @ApiResponse(code = 403, message = "403上传的目录，没有权限"),
            @ApiResponse(code = 412, message = "412上传的文件夹没有指定"),
            @ApiResponse(code = 400, message = "400系统异常，联系管理员")
    })
    public Object uploadImage(@ApiParam(value = "上传的文件夹，为了统一管理上传图片的资源", required = true) @RequestParam(required = true, value = "uploadDirName") String uploadDirName,
                              @ApiParam(value = "上传图片", required = true) @RequestParam(required = true, value = "image") MultipartFile image) {
        Map<String, Object> returnMap = new LinkedHashMap<>();
        if (StringUtils.isBlank(uploadDirName)) {
            returnMap.put("code", 412);
            returnMap.put("msg", "412上传的文件夹没有指定");
            returnMap.put("timestmap", System.currentTimeMillis());
            return returnMap;
        }

        try {
            FileUploadInfo fileUploadInfo = AliyunOSSUtil.commonUploadImg(image, uploadDirName, true);
            if (StringUtils.isNotBlank(fileUploadInfo.getFailCause())) {
                returnMap.put("code", fileUploadInfo.getReturnCode());
                returnMap.put("msg", fileUploadInfo.getFailCause());
                returnMap.put("timestamp", System.currentTimeMillis());
                return returnMap;
            } else {
                returnMap.put("code", 200);
                returnMap.put("msg", "请求成功");
                returnMap.put("data", fileUploadInfo.getImageUrl());
                returnMap.put("timestamp", System.currentTimeMillis());
                return returnMap;
            }
        } catch (Exception e) {
            returnMap.put("code", 400);
            returnMap.put("msg", "系统异常，清联系管理员");
            returnMap.put("timestmap", System.currentTimeMillis());
            e.printStackTrace();
            logger.error("上传服务，上传失败的原因：{}", e.getCause());
            return returnMap;
        }

    }


    @ResponseBody
    @PostMapping(value = "/uploadFile", consumes = "multipart/*", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "上传图片", response = MemberCertUploadResult.class)
    @ApiImplicitParam(paramType = "header", name = "sessid", value = "sessid", required = true, dataType = "String")
    @ApiResponses({
            @ApiResponse(code = 415, message = "415不是服务器中所支持的格式"),
            @ApiResponse(code = 413, message = "413上传的文件超过8M"),
            @ApiResponse(code = 403, message = "403上传的目录，没有权限"),
            @ApiResponse(code = 412, message = "412上传的文件夹没有指定"),
            @ApiResponse(code = 400, message = "400系统异常，联系管理员")
    })
    public Object uploadFile(@ApiParam(value = "上传的文件夹，为了统一管理上传图片的资源", required = true) @RequestParam(required = true, value = "uploadDirName") String uploadDirName,
                             @ApiParam(value = "上传文件", required = true) @RequestParam(required = true, value = "file") MultipartFile image) {
        Map<String, Object> returnMap = new LinkedHashMap<>();
        if (StringUtils.isBlank(uploadDirName)) {
            returnMap.put("code", 400);
            returnMap.put("msg", "上传的文件夹没有指定");
            returnMap.put("timestmap", System.currentTimeMillis());
            return returnMap;
        }

        try {
            FileUploadInfo fileUploadInfo = AliyunOSSUtil.commonUploadFile(image, uploadDirName, true);
            if (StringUtils.isNotBlank(fileUploadInfo.getFailCause())) {
                returnMap.put("code", fileUploadInfo.getReturnCode());
                returnMap.put("msg", fileUploadInfo.getFailCause());
                returnMap.put("timestamp", System.currentTimeMillis());
                return returnMap;
            } else {
                returnMap.put("code", 200);
                returnMap.put("msg", "请求成功");
                returnMap.put("data", fileUploadInfo.getImageUrl());
                returnMap.put("timestamp", System.currentTimeMillis());
                return returnMap;
            }
        } catch (Exception e) {
            returnMap.put("code", 400);
            returnMap.put("msg", "系统异常，清联系管理员");
            returnMap.put("timestmap", System.currentTimeMillis());
            e.printStackTrace();
            logger.error("上传服务，上传失败的原因：{}", e.getCause());
            return returnMap;
        }
    }
}