package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "我的评价列表请求参数")
public class ProductEvaluateListParam implements Serializable {

    @NotNull
    @Range(min=1, max=3, message="用户类型错误")
    @ApiModelProperty(value = "用户类型（1买手，2买家，3背包客）")
    private Integer memberType;

    @NotNull
    @Range(min=1, max=2, message = "评价类型错误")
    @ApiModelProperty(value = "评价类型（1商品评价，2需求评价）")
    private Integer evaluateType;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码（默认为1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认为10）")
    private Integer size = 10;

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public Integer getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(Integer evaluateType) {
        this.evaluateType = evaluateType;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
