package com.xmyy.product.model;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import top.ibase4j.core.base.BaseModel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 行程
 *
 * @author wzj
 */
@ApiModel("行程")
@TableName("dg_tour")
@SuppressWarnings("serial")
public class DgTour extends BaseModel {

    @ApiModelProperty(value = "行程编号（T+9位数字）")
	@TableField("tour_no")
	private String tourNo;
    @ApiModelProperty(value = "标签，多个逗号分隔")
	private String tags;
    @ApiModelProperty(value = "登机牌图片")
	@TableField("check_pic")
	private String checkPic;
    @ApiModelProperty(value = "登机牌类型（0-登机牌，默认；1-港澳台出境小票）")
	@TableField("check_type")
	private Integer checkType;
    @ApiModelProperty(value = "登机牌上传时间")
	@TableField("upload_time")
	private Date uploadTime;
    @ApiModelProperty(value = "审核状态（0-未上传，默认；1-未审核；2-审核通过；3-审核不通过）")
	@TableField("audit_status")
	private Integer auditStatus;
    @ApiModelProperty(value = "审核时间")
	@TableField("audit_time")
	private Date auditTime;
    @ApiModelProperty(value = "审核人ID")
	@TableField("audit_user_id")
	private Long auditUserId;
    @ApiModelProperty(value = "审核人姓名")
	@TableField("audit_user_name")
	private String auditUserName;
    @ApiModelProperty(value = "行程状态（0-已下架，1-未进行，2-进行中，3-已完成）")
	private Integer status;
    @ApiModelProperty(value = "站点数")
	@TableField("site_count")
	private Integer siteCount;
    @ApiModelProperty(value = "首站地区编码")
	@TableField("first_region_code")
	private String firstRegionCode;
    @ApiModelProperty(value = "首站名称")
	@TableField("first_region_name")
	private String firstRegionName;
    @ApiModelProperty(value = "首站时间")
	@TableField("first_time")
	private Date firstTime;
    @ApiModelProperty(value = "目的地地区编码")
	@TableField("dest_region_code")
	private String destRegionCode;
    @ApiModelProperty(value = "目的地名称")
	@TableField("dest_region_name")
	private String destRegionName;
    @ApiModelProperty(value = "返程时间")
	@TableField("return_time")
	private Date returnTime;
    @ApiModelProperty(value = "当前所在地区编码")
	@TableField("cur_region_code")
	private String curRegionCode;
    @ApiModelProperty(value = "当前所在地区名称")
	@TableField("cur_region_name")
	private String curRegionName;
    @ApiModelProperty(value = "是否直播（0-未直播，1-直播中）")
	@TableField("is_live")
	private Integer isLive;
    @ApiModelProperty(value = "取消方式（0-系统超时，1-手动取消）")
	@TableField("cancel_way")
	private Integer cancelWay;
    @ApiModelProperty(value = "取消时间")
	@TableField("cancel_time")
	private Date cancelTime;
    @ApiModelProperty(value = "信用值")
	@TableField("trust_value")
	private Integer trustValue;
    @ApiModelProperty(value = "交易量")
	@TableField("trade_count")
	private Long tradeCount;
    @ApiModelProperty(value = "真实名字")
	@TableField("real_name")
	private String realName;
    @ApiModelProperty(value = "手机号码")
	private String mobile;
    @ApiModelProperty(value = "是否背包客（0-否，1-是）")
	@TableField("is_packer")
	private Integer isPacker;
	@ApiModelProperty(value = "是否自营买手（0-大众买手；1-自营买手）")
	@TableField("is_self")
	private Integer isSelf;
    @ApiModelProperty(value = "是否置顶（0-否，1-是）")
	@TableField("is_top")
	private Integer isTop;
    @ApiModelProperty(value = "置顶开始时间")
	@TableField("top_begin_time")
	private Date topBeginTime;
    @ApiModelProperty(value = "置顶结束时间")
	@TableField("top_end_time")
	private Date topEndTime;
    @ApiModelProperty(value = "置顶人ID")
	@TableField("toper_id")
	private Long toperId;
    @ApiModelProperty(value = "置顶人姓名")
	@TableField("toper_name")
	private String toperName;
    @ApiModelProperty(value = "需求成交数")
	@TableField("demand_count")
	private Long demandCount;
    @ApiModelProperty(value = "订单成交数（通过预售方式成交的订单）")
	@TableField("order_count")
	private Long orderCount;
    @ApiModelProperty(value = "成交金额")
	private BigDecimal amount;


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

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}

	public Long getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSiteCount() {
		return siteCount;
	}

	public void setSiteCount(Integer siteCount) {
		this.siteCount = siteCount;
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

	public Date getFirstTime() {
		return firstTime;
	}

	public void setFirstTime(Date firstTime) {
		this.firstTime = firstTime;
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

	public Date getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Date returnTime) {
		this.returnTime = returnTime;
	}

	public String getCurRegionCode() {
		return curRegionCode;
	}

	public void setCurRegionCode(String curRegionCode) {
		this.curRegionCode = curRegionCode;
	}

	public String getCurRegionName() {
		return curRegionName;
	}

	public void setCurRegionName(String curRegionName) {
		this.curRegionName = curRegionName;
	}

	public Integer getIsLive() {
		return isLive;
	}

	public void setIsLive(Integer isLive) {
		this.isLive = isLive;
	}

	public Integer getCancelWay() {
		return cancelWay;
	}

	public void setCancelWay(Integer cancelWay) {
		this.cancelWay = cancelWay;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public Integer getTrustValue() {
		return trustValue;
	}

	public void setTrustValue(Integer trustValue) {
		this.trustValue = trustValue;
	}

	public Long getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Long tradeCount) {
		this.tradeCount = tradeCount;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getIsPacker() {
		return isPacker;
	}

	public Integer getIsSelf() {
		return isSelf;
	}

	public void setIsSelf(Integer isSelf) {
		this.isSelf = isSelf;
	}

	public void setIsPacker(Integer isPacker) {
		this.isPacker = isPacker;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public Date getTopBeginTime() {
		return topBeginTime;
	}

	public void setTopBeginTime(Date topBeginTime) {
		this.topBeginTime = topBeginTime;
	}

	public Date getTopEndTime() {
		return topEndTime;
	}

	public void setTopEndTime(Date topEndTime) {
		this.topEndTime = topEndTime;
	}

	public Long getToperId() {
		return toperId;
	}

	public void setToperId(Long toperId) {
		this.toperId = toperId;
	}

	public String getToperName() {
		return toperName;
	}

	public void setToperName(String toperName) {
		this.toperName = toperName;
	}

	public Long getDemandCount() {
		return demandCount;
	}

	public void setDemandCount(Long demandCount) {
		this.demandCount = demandCount;
	}

	public Long getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Long orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}