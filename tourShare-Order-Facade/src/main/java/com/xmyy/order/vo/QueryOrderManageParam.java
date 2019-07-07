package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "后台查询订单列表入参")
public class QueryOrderManageParam implements Serializable {

    @NotNull(message = "查询角色不能为空")
    @ApiModelProperty(value = "查询角色（1买手 2买家 3背包客）", required = true)
    private Integer role;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Long userId;

    @ApiModelProperty(value = "物品名称")
    private String productName;

    @ApiModelProperty(value = "订单号")
    private String orderNo;

    @ApiModelProperty(value = "订单状态(1待付款，2待发货，3待收货，4交易成功，5售后处理中，6退款成功，7交易关闭，8已删除)")
    private String orderStatus;

    @ApiModelProperty(value = "生效时间（开始，时间戳）")
    private Date beginTime;

    @ApiModelProperty(value = "生效时间（结束，时间戳）")
    private Date endTime;

    @ApiModelProperty(value = "是否鉴定(0否，1是)")
    private String isAppraisal;

    @ApiModelProperty(value = "是否评价(0否，1是)")
    private String isEvaluate;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "分页页号，默认1")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小，默认10")
    private Integer size = 10;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getIsAppraisal() {
        return isAppraisal;
    }

    public void setIsAppraisal(String isAppraisal) {
        this.isAppraisal = isAppraisal;
    }

    public String getIsEvaluate() {
        return isEvaluate;
    }

    public void setIsEvaluate(String isEvaluate) {
        this.isEvaluate = isEvaluate;
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

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
