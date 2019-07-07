package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "行程信息入参")
public class ApiTourAddParam implements Serializable {

    @NotBlank(message = "标签不能为空")
    @ApiModelProperty(value = "标签（多个用逗号分隔）", required = true)
    private String tags;

    @NotNull(message = "登机牌类型不能为空")
    @ApiModelProperty(value = "登机牌类型（0-登机牌；1-港澳台出境小票）")
    private Integer checkType;

    @NotNull(message = "用户类型不能为空")
    @Range(min = 0, max = 1)
    @ApiModelProperty(value = "是否背包客（0否，1是）", required = true)
    private Integer isPacker;

    @NotNull(message = "起点站不能为空")
    @ApiModelProperty(value = "起点站，目前是中国", required = true)
    private TourSiteDto startSite;

    @NotEmpty(message = "中途站不能为空")
    @ApiModelProperty(value = "中途站，除首站和返程站外的站点")
    private List<TourSiteDto> middleSites;

    @NotNull(message = "返程站不能为空")
    @ApiModelProperty(value = "返程站，目前是中国", required = true)
    private TourSiteDto returnSite;

    @ApiModel(value = "行程站点入参")
    public static class TourSiteDto implements Serializable {
        @NotNull
        @ApiModelProperty(value = "地区编码")
        private String regionCode;

        @NotNull
        @ApiModelProperty(value = "地区名称")
        private String regionName;

        @NotNull
        @ApiModelProperty(value = "站点类型（0-起点站，1-中途站，2-返程站）")
        private Integer siteType;

        @ApiModelProperty(value = "出发时间（起点站不需要填此参数）")
        private Date planBeginTime;

        public String getRegionCode() {
            return regionCode;
        }

        public void setRegionCode(String regionCode) {
            this.regionCode = regionCode;
        }

        public String getRegionName() {
            return regionName;
        }

        public void setRegionName(String regionName) {
            this.regionName = regionName;
        }

        public Integer getSiteType() {
            return siteType;
        }

        public void setSiteType(Integer siteType) {
            this.siteType = siteType;
        }

        public Date getPlanBeginTime() {
            return planBeginTime;
        }

        public void setPlanBeginTime(Date planBeginTime) {
            this.planBeginTime = planBeginTime;
        }
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public TourSiteDto getStartSite() {
        return startSite;
    }

    public void setStartSite(TourSiteDto startSite) {
        this.startSite = startSite;
    }

    public List<TourSiteDto> getMiddleSites() {
        return middleSites;
    }

    public void setMiddleSites(List<TourSiteDto> middleSites) {
        this.middleSites = middleSites;
    }

    public TourSiteDto getReturnSite() {
        return returnSite;
    }

    public void setReturnSite(TourSiteDto returnSite) {
        this.returnSite = returnSite;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }
}