package com.xmyy.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel("有卡片的行程")
public class ApiTourImgDto implements Serializable {

    @ApiModelProperty(value = "行程ID")
    private Long id;

    @ApiModelProperty(value = "标签，多个标签以英文逗号隔开")
    private String tags;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "行程的状态（0-已下架，1-未进行，2-进行中，3-已完成）")
    private Integer status;

    @ApiModelProperty(value = "审核状态（0-未上传，默认；1-未审核；2-审核通过；3-审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty(value = "首站国家码")
    private String firstRegionCode;

    @ApiModelProperty(value = "首站名称")
    private String firstRegionName;

    @ApiModelProperty(value = "目的地国家码")
    private String destRegionCode;

    @ApiModelProperty(value = "目的地名称")
    private String destRegionName;

    @ApiModelProperty(value = "首站时间")
    private Date firstTime;

    @ApiModelProperty(value = "返程时间")
    private Date returnTime;

    @ApiModelProperty(value = "行程站点数")
    private Integer siteCount;

    @ApiModelProperty(value = "当前站点名称")
    private String curRegionName;

    @ApiModelProperty(value = "当前站点国家码", hidden = true)
    @JsonIgnore
    private String curRegionCode;

    @ApiModelProperty(value = "当前站点卡片")
    private String curImgUrl;

    @ApiModelProperty(value = "是否正在直播")
    private Integer isLive;

    @ApiModelProperty(value = "是否背包客（0-否；1-是）")
    private Integer isPacker;

    @ApiModelProperty(value = "行程创建者ID")
    private Long createBy;

    @ApiModelProperty(value = "行程用户UUID")
    private String userUuid;

    @ApiModelProperty(value = "行程用户头像")
    private String avatarRsurl;

    @ApiModelProperty(value = "行程用户的呢称")
    private String nickName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getFirstRegionCode() {
        return firstRegionCode;
    }

    public void setFirstRegionCode(String firstRegionCode) {
        this.firstRegionCode = firstRegionCode;
    }

    public String getFirstRegionName() {
        return firstRegionName;
    }

    public void setFirstRegionName(String firstRegionName) {
        this.firstRegionName = firstRegionName;
    }

    public String getDestRegionCode() {
        return destRegionCode;
    }

    public void setDestRegionCode(String destRegionCode) {
        this.destRegionCode = destRegionCode;
    }

    public String getDestRegionName() {
        return destRegionName;
    }

    public void setDestRegionName(String destRegionName) {
        this.destRegionName = destRegionName;
    }

    public Date getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(Date firstTime) {
        this.firstTime = firstTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getSiteCount() {
        return siteCount;
    }

    public void setSiteCount(Integer siteCount) {
        this.siteCount = siteCount;
    }

    public String getCurRegionName() {
        return curRegionName;
    }

    public void setCurRegionName(String curRegionName) {
        this.curRegionName = curRegionName;
    }

    public String getCurRegionCode() {
        return curRegionCode;
    }

    public void setCurRegionCode(String curRegionCode) {
        this.curRegionCode = curRegionCode;
    }

    public String getCurImgUrl() {
        return curImgUrl;
    }

    public void setCurImgUrl(String curImgUrl) {
        this.curImgUrl = curImgUrl;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public String getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public String getAvatarRsurl() {
        return avatarRsurl;
    }

    public void setAvatarRsurl(String avatarRsurl) {
        this.avatarRsurl = avatarRsurl;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
}