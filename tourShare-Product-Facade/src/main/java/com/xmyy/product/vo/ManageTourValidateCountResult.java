package com.xmyy.product.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("行程验证统计结果")
public class ManageTourValidateCountResult implements Serializable {

    @ApiModelProperty("待审核总数")
    private long unAuditCount;

    @ApiModelProperty("已审核总数")
    private long auditCount;

    @ApiModelProperty("审核拒绝总数")
    private long denyCount;

    public long getUnAuditCount() {
        return unAuditCount;
    }

    public void setUnAuditCount(long unAuditCount) {
        this.unAuditCount = unAuditCount;
    }

    public long getAuditCount() {
        return auditCount;
    }

    public void setAuditCount(long auditCount) {
        this.auditCount = auditCount;
    }

    public long getDenyCount() {
        return denyCount;
    }

    public void setDenyCount(long denyCount) {
        this.denyCount = denyCount;
    }
}
