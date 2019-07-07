package com.xmyy.search.vo;

import com.xmyy.product.model.PtProduct;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Simon on 2018/7/20.
 */
public class ProductData extends PtProduct implements Serializable {

    private String categoryName;

    private String categoryName2;

    private String brandName;

    private Long sellerId;

    private String nickName;

    private String avatarRsurl;

    List<String> attrValues;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName2() {
        return categoryName2;
    }

    public void setCategoryName2(String categoryName2) {
        this.categoryName2 = categoryName2;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public List<String> getAttrValues() {
        return attrValues;
    }

    public void setAttrValues(List<String> attrValues) {
        this.attrValues = attrValues;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryName2='" + categoryName2 + '\'' +
                ", brandName='" + brandName + '\'' +
                ", attrValues=" + attrValues +
                '}';
    }
}
