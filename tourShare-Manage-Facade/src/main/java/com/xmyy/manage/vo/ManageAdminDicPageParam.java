package com.xmyy.manage.vo;

import com.xmyy.manage.model.AdminDic;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

@ApiModel(value = "查询字段入参")
public class ManageAdminDicPageParam extends AdminDic implements Serializable {

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页码（默认1）")
    private Integer current = 1;

    @Range(min = 1, message = "分页参数不正确")
    @ApiModelProperty(value = "页大小（默认10）")
    private Integer size = 10;

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
