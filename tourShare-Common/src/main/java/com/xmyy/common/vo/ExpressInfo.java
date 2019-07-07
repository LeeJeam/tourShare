package com.xmyy.common.vo;

import org.apache.http.conn.routing.RouteInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 物流信息
 *
 * @author wangzejun
 */
public class ExpressInfo implements Serializable {

    /**
     * 物流公司编号
     */
    private String com;

    /**
     * 物流单号
     */
    private String nu;

    /**
     * 查询结果状态： <br>
     * --0：物流单暂无结果<br>
     * --1：查询成功， <br>
     * --2：接口出现异常，<br>
     */
    private String status;

    /**
     * 快递单当前的状态 ：<br>
     * --0：在途，即货物处于运输过程中；<br>
     * --1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；<br>
     * --2：疑难，货物寄送过程出了问题；<br>
     * --3：签收，收件人已签收；<br>
     * --4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；<br>
     * --5：派件，即快递正在进行同城派件；<br>
     * --6：退回，货物正处于退回发件人的途中；<br>
     */
    private String state;

    /**
     * 返回结果信息
     */
    private String message;

    /**
     * 物流节点信息
     */
    private List<ExpressItem> data;

    /**
     * 路线信息
     */
    private RouteInfo routeInfo;

    /**
     * 查询结果
     */
    private Boolean result;

    /**
     * 失败的代号<br>
     * 400: 提交的数据不完整，或者贵公司没授权<br>
     * 500: 表示查询失败，或没有POST提交<br>
     * 501: 服务器错误，快递100服务器压力过大或需要升级，暂停服务<br>
     * 502: 服务器繁忙，详细说明见2.2《查询接口并发协议》<br>
     * 503: 验证签名失败。<br>
     */
    private String returnCode;

    /**
     * 请忽略
     */
    private String condition;

    /**
     * 请忽略
     */
    private String  ischeck;

    /**
     * 请忽略
     */
    private String num;

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getNu() {
        return nu;
    }

    public void setNu(String nu) {
        this.nu = nu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ExpressItem> getData() {
        return data;
    }

    public void setData(List<ExpressItem> data) {
        this.data = data;
    }

    public RouteInfo getRouteInfo() {
        return routeInfo;
    }

    public void setRouteInfo(RouteInfo routeInfo) {
        this.routeInfo = routeInfo;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getIscheck() {
        return ischeck;
    }

    public void setIscheck(String ischeck) {
        this.ischeck = ischeck;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}