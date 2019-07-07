package com.xmyy.order.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ApiModel(value = "申请售后入参")
public class AfterSaleParam implements Serializable {

    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @NotNull(message = "订单商品ID不能为空")
    @ApiModelProperty(value = "订单商品ID（预售、现货有）", required = true)
    private Long productOrderId;

    @NotNull(message = "售后原因不能为空")
    @ApiModelProperty(value = "售后原因（" +
            "待发货售后原因：\n" +
            "1\t\"拍多/拍错/不想买\"\n" +
            "2\t\"协商一致退款\"\n" +
            "3\t\"买手缺货\"\n" +
            "4\t\"未按约定时间发货\"\n" +
            "已发货售后原因：\n" +
            "5\t\"一直未收到货\"\n" +
            "6\t\"物品破损\"\n" +
            "7\t\"物品错发/漏发\"\n" +
            "8\t\"与描述不符\"\n" +
            "9\t\"质量问题\"\n" +
            "公用：\n" +
            "10\t\"其他\"）", required = true)
    private Integer returnReason;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }

    public Integer getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(Integer returnReason) {
        this.returnReason = returnReason;
    }
}
