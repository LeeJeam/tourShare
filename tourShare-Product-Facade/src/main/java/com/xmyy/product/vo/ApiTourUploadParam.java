package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel("行程修改登机牌信息入参")
public class ApiTourUploadParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "行程ID", required = true)
    private Long id;

    @NotBlank
    @ApiModelProperty(value = "登机牌图片url", required = true)
    private String checkPic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCheckPic() {
        return checkPic;
    }

    public void setCheckPic(String checkPic) {
        this.checkPic = checkPic;
    }

}