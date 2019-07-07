package com.xmyy.product.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by yeyu on 2018/6/15.
 * 描述：
 */
@ApiModel(value = "商品管理列表附加属性",description = "商品管理列表附加属性")
public class PtAttrDto implements Serializable {
    @ApiModelProperty(value = "属性值_id")
    private Long ptAttrValueId;
    @ApiModelProperty(value = "属性类型_id")
    private Long ptAttrTypeId;
    @ApiModelProperty(value = "属性类型名称")
    private String ptAttrTypeName;
    @ApiModelProperty(value = "属性值名称")
    private String ptAttrValueName;

    public Long getPtAttrValueId() {
        return ptAttrValueId;
    }

    public void setPtAttrValueId(Long ptAttrValueId) {
        this.ptAttrValueId = ptAttrValueId;
    }

    public Long getPtAttrTypeId() {
        return ptAttrTypeId;
    }

    public void setPtAttrTypeId(Long ptAttrTypeId) {
        this.ptAttrTypeId = ptAttrTypeId;
    }

    public String getPtAttrTypeName() {
        return ptAttrTypeName;
    }

    public void setPtAttrTypeName(String ptAttrTypeName) {
        this.ptAttrTypeName = ptAttrTypeName;
    }

    public String getPtAttrValueName() {
        return ptAttrValueName;
    }

    public void setPtAttrValueName(String ptAttrValueName) {
        this.ptAttrValueName = ptAttrValueName;
    }

    @Override
    public String toString() {
        return "PtAttrDto{" +
                "ptAttrValueId=" + ptAttrValueId +
                ", ptAttrTypeId=" + ptAttrTypeId +
                ", ptAttrTypeName='" + ptAttrTypeName + '\'' +
                ", ptAttrValueName='" + ptAttrValueName + '\'' +
                '}';
    }
}
