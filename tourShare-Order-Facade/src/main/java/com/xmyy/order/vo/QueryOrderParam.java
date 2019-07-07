package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@ApiModel(value = "APP端查询订单列表参数")
public class QueryOrderParam implements Serializable {

    @NotNull(message = "用户角色不能为空")
    @Range(min = 1, max = 3)
    @ApiModelProperty(value = "用户角色（1买手，2买家，3背包客）", required = true)
    private Integer role;

    @ApiModelProperty(value = "订单分类（买家端：0全部，1待付款, 2待发货，3待收货，4待评价，7交易关闭）（买手/背包客端：0全部，2待发货，3已发货）/ 查询售后列表不需要此参数")
    private Integer orderStatus;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "分页页号（默认1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
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
