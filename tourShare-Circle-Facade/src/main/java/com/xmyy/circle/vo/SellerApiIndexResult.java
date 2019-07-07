package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@ApiModel(value = "买手首页对象")
public class SellerApiIndexResult implements Serializable {
    @ApiModelProperty(value = "总收入")
    private BigDecimal totalPrice;

    @ApiModelProperty(value = "累计订单")
    private Integer tradeCount;

    @ApiModelProperty(value = "头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "认证状态（0提交认证，50认证审核通过，-50认证审核不通过）")
    private Integer realState;

    @ApiModelProperty(value = "广告图")
    private List<Adverts> ads;

    @ApiModelProperty(value = "今日提醒")
    private List<EveryDayRadioResult>  everyDayradios;

    @ApiModelProperty(value = "热门标签")
    private List<String> tags;

    @ApiModel(value = "广告内容")
    public static class Adverts implements Serializable {
        @ApiModelProperty(value = "资源URL")
        private String fileRsurl;

        @ApiModelProperty(value = "点击链接")
        private String clickUrl;

        public String getFileRsurl() {
            return fileRsurl;
        }

        public void setFileRsurl(String fileRsurl) {
            this.fileRsurl = fileRsurl;
        }

        public String getClickUrl() {
            return clickUrl;
        }

        public void setClickUrl(String clickUrl) {
            this.clickUrl = clickUrl;
        }
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTradeCount() {
        return tradeCount;
    }

    public void setTradeCount(Integer tradeCount) {
        this.tradeCount = tradeCount;
    }

    public List<Adverts> getAds() {
        return ads;
    }

    public void setAds(List<Adverts> ads) {
        this.ads = ads;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public List<EveryDayRadioResult> getEveryDayradios() {
        return everyDayradios;
    }

    public void setEveryDayradios(List<EveryDayRadioResult> everyDayradios) {
        this.everyDayradios = everyDayradios;
    }

    public Integer getRealState() {
        return realState;
    }

    public void setRealState(Integer realState) {
        this.realState = realState;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

}