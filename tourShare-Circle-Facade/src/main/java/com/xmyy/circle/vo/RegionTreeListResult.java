package com.xmyy.circle.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "查询区域树的出参")
public class RegionTreeListResult implements Serializable {

    @ApiModelProperty(value="首字母")
    private String idxcode;

    @ApiModelProperty(value="区域编码")
    private String ccode;

    @ApiModelProperty(value="父亲编码")
    private String parentCode;

    @ApiModelProperty(value="区域名称")
    private String cname;

    @ApiModelProperty(value="国旗")
    private String nationalFlag;

    @ApiModelProperty(value="是否拥有子节点")
    private Boolean isHasChild;

    @ApiModelProperty(value="子节点")
    private List<RegionTreeListResult> children;

    @ApiModelProperty(value="国家编码")
    private String prefixPhone;

    public String getCcode() {
        return ccode;
    }

    public void setCcode(String ccode) {
        this.ccode = ccode;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Boolean getHasChild() {
        return isHasChild;
    }

    public void setHasChild(Boolean hasChild) {
        isHasChild = hasChild;
    }

    public List<RegionTreeListResult> getChildren() {
        return children;
    }

    public void setChildren(List<RegionTreeListResult> children) {
        this.children = children;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getIdxcode() {
        return idxcode;
    }

    public void setIdxcode(String idxcode) {
        this.idxcode = idxcode;
    }

    public String getPrefixPhone() {
        return prefixPhone;
    }

    public void setPrefixPhone(String prefixPhone) {
        this.prefixPhone = prefixPhone;
    }

    public String getNationalFlag() {
        return nationalFlag;
    }

    public void setNationalFlag(String nationalFlag) {
        this.nationalFlag = nationalFlag;
    }
}