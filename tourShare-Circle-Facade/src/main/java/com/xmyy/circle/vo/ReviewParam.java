package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "追评参数")
public class ReviewParam implements Serializable{

    @NotNull
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "追评列表")
    private List<Review> reviews;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @ApiModel(value = "追评实体")
    public static class Review implements Serializable {

        @NotNull
        @ApiModelProperty(value = "商品评价ID")
        private Long id;

        @ApiModelProperty(value = "追评内容")
        private String reviewContent;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getReviewContent() {
            return reviewContent;
        }

        public void setReviewContent(String reviewContent) {
            this.reviewContent = reviewContent;
        }
    }

}
