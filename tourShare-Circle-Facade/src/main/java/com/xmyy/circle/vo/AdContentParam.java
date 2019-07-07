package com.xmyy.circle.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "变更广告内容入参")
public class AdContentParam implements Serializable {

    @NotNull
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "资源URL")
    private String fileRsurl;

    @ApiModelProperty(value = "点击链接")
    private String clickUrl;
    @JsonIgnore
    private Long createBy;
    @JsonIgnore
    private Long updateBy;

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileRsurl() {
        return fileRsurl;
    }

    public void setFileRsurl(String fileRsurl) {
        this.fileRsurl = fileRsurl;
    }

    public String getClickUrl() {
        return clickUrl;
    }

    public void setClickUrl(String clickUrl) {
        this.clickUrl = clickUrl;
    }
}
