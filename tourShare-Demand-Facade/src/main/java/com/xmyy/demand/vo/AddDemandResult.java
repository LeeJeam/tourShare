package com.xmyy.demand.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

@ApiModel(value = "创建需求后返回的数据")
public class AddDemandResult implements Serializable {

    @ApiModelProperty(value = "需求id")
    private Long id;

    @ApiModelProperty(value = "需求编号")
    private String demandNo;

    @ApiModelProperty(value = "预算价格")
    private BigDecimal budgetPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDemandNo() {
        return demandNo;
    }

    public void setDemandNo(String demandNo) {
        this.demandNo = demandNo;
    }

    public BigDecimal getBudgetPrice() {
        return budgetPrice;
    }

    public void setBudgetPrice(BigDecimal budgetPrice) {
        this.budgetPrice = budgetPrice;
    }
}
