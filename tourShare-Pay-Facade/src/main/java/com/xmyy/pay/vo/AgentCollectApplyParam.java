package com.xmyy.pay.vo;

import com.alibaba.fastjson.JSONArray;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@ApiModel(value = "托管代收入参")
public class AgentCollectApplyParam implements Serializable {

    @NotEmpty(message = "订单ID列表不能为空")
    @ApiModelProperty(value = "订单ID列表")
    private List<Long> orderIdList;

    @NotNull(message = "付款用户ID不能为空")
    @ApiModelProperty(value = "付款用户ID", hidden = true)
    private Long memberId;

    @NotNull(message = "用户类型不能为空")
    @ApiModelProperty(value = "付款用户类型(1买手，2背包客/买家)", required = true)
    private Integer memberType;

    @NotEmpty(message = "收款列表不能为空")
    @ApiModelProperty(value = "收款列表", required = true)
    private JSONArray recieverList;

    @NotNull(message = "金额不能为空")
    @ApiModelProperty(value = "金额：分", required = true)
    private Long amount;

    @NotNull(message = "手续费不能为空")
    @ApiModelProperty(value = "手续费：分", required = true)
    private Long fee;

    @NotNull(message = "支付方式不能为空")
    @Range(min = 1, max = 3, message = "不支持的支付方式")
    @ApiModelProperty(value = "支付方式（1支付宝；2微信；3快捷支付）", required = true)
    private Integer payMethod;

    @ApiModelProperty(value = "银行卡号（快捷支付需要此参数）")
    private String bankCardNo;

    @ApiModelProperty(value = "小背包交易密码（快捷支付需要）")
    private String PayPwd;

    @ApiModelProperty(value = "IP地址", hidden = true)
    private String ip;

    public List<Long> getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(List<Long> orderIdList) {
        this.orderIdList = orderIdList;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Integer getMemberType() {
        return memberType;
    }

    public void setMemberType(Integer memberType) {
        this.memberType = memberType;
    }

    public JSONArray getRecieverList() {
        return recieverList;
    }

    public void setRecieverList(JSONArray recieverList) {
        this.recieverList = recieverList;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getPayPwd() {
        return PayPwd;
    }

    public void setPayPwd(String payPwd) {
        PayPwd = payPwd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
