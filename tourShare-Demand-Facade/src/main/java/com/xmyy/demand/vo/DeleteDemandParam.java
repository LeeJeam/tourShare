package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "删除需求参数")
public class DeleteDemandParam implements Serializable {

    @NotNull(message = "需求ID不能为空")
    @ApiModelProperty( value = "需求ID", required = true)
    private Long id;

    @NotNull(message = "角色不能为空")
    @Range(min = 1, max = 3)
    @ApiModelProperty( value = "角色（1买手，2买家，3背包客）", required = true)
    private Integer role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
