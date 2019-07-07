package com.xmyy.circle.vo;

import com.xmyy.circle.dto.ApiOrderProductEvaluateDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "添加订单评价参数")
public class OrderEvaluateAddParam implements Serializable {

    @NotNull
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @NotNull
    @Range(min = 1, max = 5, message = "参数不正确")
    @ApiModelProperty(value = "买手服务评分（1-5分）")
    private Integer buyService;

    @NotNull
    @Range(min = 1, max = 5, message = "参数不正确")
    @ApiModelProperty(value = "商品技师评分（1-5分）")
    private Integer productLevel;

    @ApiModelProperty(value = "商品评价列表")
    private List<ApiOrderProductEvaluateDto> productEvaluate;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getBuyService() {
        return buyService;
    }

    public void setBuyService(Integer buyService) {
        this.buyService = buyService;
    }

    public Integer getProductLevel() {
        return productLevel;
    }

    public void setProductLevel(Integer productLevel) {
        this.productLevel = productLevel;
    }

    public List<ApiOrderProductEvaluateDto> getProductEvaluate() {
        return productEvaluate;
    }

    public void setProductEvaluate(List<ApiOrderProductEvaluateDto> productEvaluate) {
        this.productEvaluate = productEvaluate;
    }
}
