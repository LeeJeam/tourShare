package com.xmyy.common.vo;

import java.io.Serializable;

/**
 * 物流信息节点数据
 *
 * @author wangzejun
 */
public class ExpressItem implements Serializable {

    /**
     * 原始时间
     */
    private String time;

    /**
     * 信息
     */
    private String context;

    /**
     * 格式化后的时间
     */
//    private String ftime;

    /**
     * 本数据元对应的行政区域的编码<br>
     * 只有实时查询接口中提交resultv2标记后才会出，见3.1.1<br>
     */
//    private String areaCode;

    /**
     * 本数据元对应的行政区域的名称,<br>
     * 只有实时查询接口中提交resultv2标记后才会出，见3.1.1<br>
     */
//    private String areaName;

    /**
     *本数据元对应的签收状态<br>
     * 只有实时查询接口中提交resultv2标记后才会出，见3.1.1<br>
     */
//    private String status;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

/*    public String getFtime() {
        return ftime;
    }

    public void setFtime(String ftime) {
        this.ftime = ftime;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ExpressItem{" +
                "time=" + time +
                ", context='" + context + '\'' +
                ", ftime='" + ftime + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", areaName='" + areaName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }*/
}