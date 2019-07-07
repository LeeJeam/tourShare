package com.xmyy.common.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 地区树<br>
 *
 * @author wangzejun
 * @time 2018年 06月27日 14:12:48
 * @since 1.0.0
 */
public class AreaTreeInfo implements Serializable {

    private static final long serialVersionUID = 9028349715923527906L;

    /**
     * 区域名称
     */
    private String areaName;

    /**
     * 区域编码
     */
    private String areaCode;

    /**
     * 子节点
     */
    List<AreaTreeInfo> children;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public List<AreaTreeInfo> getChildren() {
        return children;
    }

    public void setChildren(List<AreaTreeInfo> children) {
        this.children = children;
    }
}