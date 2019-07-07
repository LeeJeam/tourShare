package com.xmyy.search.vo;

import com.xmyy.member.model.UcSeller;

import java.io.Serializable;

@SuppressWarnings("serial")
public class SellerData extends UcSeller  implements Serializable {

    private String productRsurl;

    public String getProductRsurl() {
        return productRsurl;
    }

    public void setProductRsurl(String productRsurl) {
        this.productRsurl = productRsurl;
    }
}
