package com.xmyy.livevideo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "直播置顶对象")
public class LiveVideoMangerTopParam implements Serializable {

    @NotBlank
    @ApiModelProperty(value = "直播id", required = true)
    private String id;

    @NotNull
    @ApiModelProperty(value = "有效时长，分钟为单位", required = true)
    private Integer times;

    private Long updateBy;

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }
}


