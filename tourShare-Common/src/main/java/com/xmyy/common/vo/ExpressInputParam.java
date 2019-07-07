package com.xmyy.common.vo;

import java.io.Serializable;

/**
 * 快递100调用入参
 *
 * @author wangzejun
 */
public class ExpressInputParam implements Serializable {

    /**
     *要查询的快递公司代码，不支持中文，对应的公司代码见 
     */
    private String com;

    /**
     * 公司名字
     */
    private String comName;

    /**
     *要查询的快递单号，请勿带特殊符号，不支持中文（大小写不敏感）
     */
    private String nu;

    /**
     *返回类型：<br>
     *0：返回json字符串，<br> 
     *1：返回xml对象，<br> 
     *2：返回html对象，<br> 
     *3：返回text文本<br>
     *如果不填，默认返回json字符串。<br>
     */
    private String show="0";

    /**
     *返回信息数量：
     *1:返回多行完整的信息，
     *0:只返回一行信息。
     *不填默认返回多行。
     */
    private String muti="1";

    /**
     *排序：
     *desc：按时间由新到旧排列，
     *asc：按时间由旧到新排列。
     *不填默认返回倒序（大小写不敏感）
     */
    private String order="desc";

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

    public String getShow() {
        return show;
    }

    public void setShow(String show) {
        this.show = show;
    }

    public String getMuti() {
        return muti;
    }

    public void setMuti(String muti) {
        this.muti = muti;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }
}