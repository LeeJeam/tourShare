package com.xmyy.member.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "生成单个邀请码入参")
public class InviteCodeGenerateParams implements Serializable {

    @ApiModelProperty(value = "责任人")
    private String occupyMan;

    @Valid
    @NotEmpty
    @ApiModelProperty(value = "items集合")
    private List<Items> items  = new ArrayList<>();

    @ApiModel(value = "items集合")
    public static class Items implements Serializable{
        @ApiModelProperty(value = "手机号码", required = true)
        @NotBlank(message = "手机号不能为空")
        @Pattern(regexp = "^(1\\d{10})$", message = "无效的手机号码")
        private String mobile;

        @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$", message = "无效的email帐号")
        @ApiModelProperty(value = "接受人email")
        private String email;

        @NotNull
        @ApiModelProperty(value = "渠道(1运营推广，2线下推广，3用户邀请)", required = true)
        private Integer channelType;

        @NotNull
        @ApiModelProperty(value = "时效", required = true)
        private Integer days;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getChannelType() {
            return channelType;
        }

        public void setChannelType(Integer channelType) {
            this.channelType = channelType;
        }

        public Integer getDays() {
            return days;
        }

        public void setDays(Integer days) {
            this.days = days;
        }
    }
    public String getOccupyMan() {
        return occupyMan;
    }

    public void setOccupyMan(String occupyMan) {
        this.occupyMan = occupyMan;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
