package com.xmyy.common.vo;

import java.io.Serializable;

/**
 * 路线点
 *
 * @author wangzejun
 */
public class CityInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8980684201964395899L;

    /**
     *城市编码
     */
    private String number;

    /**
     * 城市名称
     */
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}