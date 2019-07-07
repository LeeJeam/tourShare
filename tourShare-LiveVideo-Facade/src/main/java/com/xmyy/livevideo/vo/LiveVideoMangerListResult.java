package com.xmyy.livevideo.vo;

import com.xmyy.product.vo.ApiProductListByTourIdResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@ApiModel(value = "后台管理，返回直播列表")
public class LiveVideoMangerListResult  implements Serializable {

    @ApiModelProperty(value = "直播ID")
    private String id;

    @ApiModelProperty(value = "标题")
    private String  roomTitle;

    @ApiModelProperty(value = "直播创建时间")
    private Date  beginTime;

    @ApiModelProperty(value = "直播下线时间")
    private Date endTime;

    @ApiModelProperty(value = "直播有效时间")
    private Date  effectTime;

    @ApiModelProperty(value = "直播用户")
    private String  liveVideoUserName;

    @ApiModelProperty(value = "直播状态")
    private String  videoStatusLabel;

    @ApiModelProperty(value = "观看数")
    private Integer lookNumber;

    @ApiModelProperty(value = "行程ID")
    private Long strokeId;

    @ApiModelProperty(value = "聊天室")
    private String liveRoomId;

    @ApiModelProperty(value = "监管人员")
    private String supervise;

    @ApiModelProperty(value = "是否违规(1,违规，0不违规)")
    private Integer isViolate;

    @ApiModelProperty(value = "录播URL地址")
    private String videoUrl;

    @ApiModelProperty(value = "弹幕数")
    private Integer pointNumber;

    @ApiModelProperty(value = "商品列表")
    private List<ApiProductListByTourIdResult> productList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPointNumber() {
        return pointNumber;
    }

    public void setPointNumber(Integer pointNumber) {
        this.pointNumber = pointNumber;
    }

    public List<ApiProductListByTourIdResult> getProductList() {
        return productList;
    }

    public void setProductList(List<ApiProductListByTourIdResult> productList) {
        this.productList = productList;
    }

    public String getSupervise() {
        return supervise;
    }

    public void setSupervise(String supervise) {
        this.supervise = supervise;
    }

    public Integer getIsViolate() {
        return isViolate;
    }

    public void setIsViolate(Integer isViolate) {
        this.isViolate = isViolate;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Integer getLookNumber() {
        return lookNumber;
    }

    public void setLookNumber(Integer lookNumber) {
        this.lookNumber = lookNumber;
    }

    public Long getStrokeId() {
        return strokeId;
    }

    public void setStrokeId(Long strokeId) {
        this.strokeId = strokeId;
    }

    public String getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(String liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEffectTime() {
        return effectTime;
    }

    public void setEffectTime(Date effectTime) {
        this.effectTime = effectTime;
    }

    public String getLiveVideoUserName() {
        return liveVideoUserName;
    }

    public void setLiveVideoUserName(String liveVideoUserName) {
        this.liveVideoUserName = liveVideoUserName;
    }

    public String getVideoStatusLabel() {
        return videoStatusLabel;
    }

    public void setVideoStatusLabel(String videoStatusLabel) {
        this.videoStatusLabel = videoStatusLabel;
    }

}
