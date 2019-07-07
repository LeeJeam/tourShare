package com.xmyy.circle.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Simon on 2018/6/5.
 */
@ApiModel(value = "商品评价实体")
public class ApiOrderProductEvaluateDto implements Serializable {

    @NotNull
    @ApiModelProperty(value = "商品ID")
    private Long productId;

    @ApiModelProperty(value = "商品快照ID")
    private Long productOrderId;

    @ApiModelProperty(value = "评价内容")
    private String content;

    @ApiModelProperty(value = "图片列表")
    private List<String> images;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getProductOrderId() {
        return productOrderId;
    }

    public void setProductOrderId(Long productOrderId) {
        this.productOrderId = productOrderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
