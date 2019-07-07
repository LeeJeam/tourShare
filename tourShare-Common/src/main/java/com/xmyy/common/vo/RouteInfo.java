package com.xmyy.common.vo;

import java.io.Serializable;

/**
 * 路线信息<br>
 *
 * @author wangzejun
 * @time 2018年 05月24日 17:17:22
 * @since 1.0.0
 */
public class RouteInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 4897876516427605854L;

    /**
     * 出发城市
     */
    private CityInfo from;

    /**
     * 当前城市
     */
    private CityInfo  cur;

    /**
     * 目的城市
     */
    private CityInfo to;

    public CityInfo getFrom() {
        return from;
    }

    public void setFrom(CityInfo from) {
        this.from = from;
    }

    public CityInfo getCur() {
        return cur;
    }

    public void setCur(CityInfo cur) {
        this.cur = cur;
    }

    public CityInfo getTo() {
        return to;
    }

    public void setTo(CityInfo to) {
        this.to = to;
    }
}