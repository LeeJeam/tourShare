package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "当前TOP预售商品实体")
public class CurrTopResult implements Serializable {

    @ApiModelProperty(value = "商品ID")
    private Long id;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品封面")
    private String cover;

    @ApiModelProperty(value = "商品价格")
    private BigDecimal price;

    @ApiModelProperty(value = "商品订单数")
    private Integer orderCount;

    @ApiModelProperty(value = "买手名称")
    private String sellerName;

    @ApiModelProperty(value = "操作人名称")
    private String opName;

    @ApiModelProperty(value = "操作时间")
    private Date topOpTime;

    @ApiModelProperty(value = "失效时间")
    private Date topEndTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public Date getTopOpTime() {
        return topOpTime;
    }

    public void setTopOpTime(Date topOpTime) {
        this.topOpTime = topOpTime;
    }

    public Date getTopEndTime() {
        return topEndTime;
    }

    public void setTopEndTime(Date topEndTime) {
        this.topEndTime = topEndTime;
    }
}
