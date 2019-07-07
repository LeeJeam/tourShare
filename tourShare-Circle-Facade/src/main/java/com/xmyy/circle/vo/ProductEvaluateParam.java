package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "评价管理接口参数")
public class ProductEvaluateParam implements Serializable{

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码（默认1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    @ApiModelProperty(value = "商品名称")
    private String productName;

    @ApiModelProperty(value = "订单号")
    private String waybillNo;

    @ApiModelProperty(value = "是否含有图片（0全部，1图片评论）;默认0")
    private Integer isPicture = 0;

    @ApiModelProperty(value = "评价分数,默认0")
    private Integer productLevel = 0;

    @ApiModelProperty(value = "评价状态（-1:全部,0未评价 1已评价 2已追评）,默认-1")
    private Integer commentStatus = -1;

    @NotNull
    @ApiModelProperty(value = "用户类型（1买手，2买家，3背包客)")
    private Integer userType = 1;

    @NotNull
    @ApiModelProperty(value = "用户Id")
    private Long userId;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public Integer getIsPicture() {
        return isPicture;
    }

    public void setIsPicture(Integer isPicture) {
        this.isPicture = isPicture;
    }

    public Integer getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(Integer productLevel) {
        this.productLevel = productLevel;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

}
