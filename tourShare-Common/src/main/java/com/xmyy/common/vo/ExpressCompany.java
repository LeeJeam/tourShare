package com.xmyy.common.vo;

import java.io.Serializable;

/**
 * 快递公司信息
 *
 * @author wangzejun
 */
public class ExpressCompany implements Serializable {

    /**
     * 公司ID
     */
    private String id;

    /**
     *公司编码
     */
    private String comCode;

    /**
     *编号记录数
     */
    private String noCount;


    /**
     *编号前戳
     */
    private String noPre;

    /**
     * 开始时间
     */
    private String startTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComCode() {
        return comCode;
    }

    public void setComCode(String comCode) {
        this.comCode = comCode;
    }

    public String getNoCount() {
        return noCount;
    }

    public void setNoCount(String noCount) {
        this.noCount = noCount;
    }

    public String getNoPre() {
        return noPre;
    }

    public void setNoPre(String noPre) {
        this.noPre = noPre;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
}