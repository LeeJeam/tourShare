package com.xmyy.common.vo;

import java.io.Serializable;

/**
 * 文件上传结果信息<br>
 *
 * @author wangzejun
 * @time 2018年 08月30日 14:38:58
 * @since 1.0.0
 */
public class FileUploadInfo implements Serializable {

    private static final long serialVersionUID = 8705569180247343182L;

    private String imageUrl;

    private String returnCode;

    private String failCause;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }
}