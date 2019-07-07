package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "一级类目商品统计结果")
public class ProductStatisticResult implements Serializable {

    @ApiModelProperty(value = "总数量")
    private Integer totalCount;

    @ApiModelProperty(value = "一级类目列表")
    private List<CategoryStatistic> statistics;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<CategoryStatistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<CategoryStatistic> statistics) {
        this.statistics = statistics;
    }

    @ApiModel(value = "一级类目统计")
    public static class CategoryStatistic implements Serializable{

        @ApiModelProperty(value = "一级类目ID")
        private Long categoryId;

        @ApiModelProperty(value = "一级类目名称")
        private String categoryName;

        @ApiModelProperty(value = "数量")
        private Integer count;

        public Long getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Long categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

}
