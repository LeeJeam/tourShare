package com.xmyy.pay.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "实名认证入参")
public class SetRealNameParam implements Serializable {

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "用户类型(1买手，2买家，3背包客)", required = true)
    private Integer memberType;

    @NotNull(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    @ApiModelProperty(value = "证件类型（默认1）", required = true)
    private Long identityType = 1L;

    @NotNull(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号", required = true)
    private String identityNo;

    @ApiModelProperty(value = "认证ID",hidden = true)
    private Long id;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getIdentityType() {
        return identityType;
    }

    public void setIdentityType(Long identityType) {
        this.identityType = identityType;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
