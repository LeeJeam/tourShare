package com.xmyy.product.vo;

import com.xmyy.product.dto.ApiTourSiteDto;
import com.xmyy.product.dto.UserAuthInfoDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "行程详情结果")
public class ApiTourDetailResult implements Serializable {

    @ApiModelProperty(value = "行程ID")
    private Long id;

    @ApiModelProperty(value = "行程编号")
    private String tourNo;

    @ApiModelProperty(value="标签，多个标签以英文逗号隔开")
    private String tags;

    @ApiModelProperty(value="登机牌图片url")
    private String checkPic;

    @ApiModelProperty(value="登机牌类型（0-登机牌，默认；1-港澳台出境小票")
    private Integer checkType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value="上传登机牌剩余时间，时间戳")
    private Long remainTime;

    @ApiModelProperty(value = "行程的状态（0-已下架/已失效，1-未进行，2-进行中，3-已完成）")
    private Integer status;

    @ApiModelProperty(value = "审核状态（0-无证件，默认；1-未审核；2-审核通过；3-审核不通过）")
    private Integer auditStatus;

    @ApiModelProperty(value = "行程站点信息")
    private List<ApiTourSiteDto> tourSites;

    @ApiModelProperty(value="是否正在直播（0-未直播；1-直播中）")
    private Integer isLive;

    @ApiModelProperty(value = "是否背包客（0-否，1-是）")
    private Integer isPacker;

    @ApiModelProperty(value="用户认证信息")
    private UserAuthInfoDto userAuthInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTourNo() {
        return tourNo;
    }

    public void setTourNo(String tourNo) {
        this.tourNo = tourNo;
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

    public List<ApiTourSiteDto> getTourSites() {
        return tourSites;
    }

    public void setTourSites(List<ApiTourSiteDto> tourSites) {
        this.tourSites = tourSites;
    }

    public Integer getIsLive() {
        return isLive;
    }

    public void setIsLive(Integer isLive) {
        this.isLive = isLive;
    }

    public String getCheckPic() {
        return checkPic;
    }

    public void setCheckPic(String checkPic) {
        this.checkPic = checkPic;
    }

    public Integer getCheckType() {
        return checkType;
    }

    public void setCheckType(Integer checkType) {
        this.checkType = checkType;
    }

    public Long getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(Long remainTime) {
        this.remainTime = remainTime;
    }

    public Integer getIsPacker() {
        return isPacker;
    }

    public void setIsPacker(Integer isPacker) {
        this.isPacker = isPacker;
    }

    public UserAuthInfoDto getUserAuthInfo() {
        return userAuthInfo;
    }

    public void setUserAuthInfo(UserAuthInfoDto userAuthInfo) {
        this.userAuthInfo = userAuthInfo;
    }
}