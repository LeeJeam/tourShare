package com.xmyy.product.vo;

import com.xmyy.product.dto.PtSeriesAttrDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "系列商品管理出参对象",description = "系列商品管理出参对象")
public class ManagerPtSeriesListResult implements Serializable {

    @ApiModelProperty(value = "系列ID")
    private Long seriesId;

    @ApiModelProperty(value = "系列名称")
    private String seriesName;

    @ApiModelProperty(value = "系列编号")
    private String seriesNo;

    @ApiModelProperty(value = "系列类目1")
    private String cateGory1 ;

    @ApiModelProperty(value = "系列种类")
    private String cateGory2;

    @ApiModelProperty(value = "品牌ID")
    private Long ptBrandId;

    private List<PtSeriesAttrDto> ptSeriesAttrDtoList;

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getCateGory1() {
        return cateGory1;
    }

    public void setCateGory1(String cateGory1) {
        this.cateGory1 = cateGory1;
    }

    public String getCateGory2() {
        return cateGory2;
    }

    public void setCateGory2(String cateGory2) {
        this.cateGory2 = cateGory2;
    }

    public Long getPtBrandId() {
        return ptBrandId;
    }

    public void setPtBrandId(Long ptBrandId) {
        this.ptBrandId = ptBrandId;
    }

    public List<PtSeriesAttrDto> getPtSeriesAttrDtoList() {
        return ptSeriesAttrDtoList;
    }

    public void setPtSeriesAttrDtoList(List<PtSeriesAttrDto> ptSeriesAttrDtoList) {
        this.ptSeriesAttrDtoList = ptSeriesAttrDtoList;
    }

}
