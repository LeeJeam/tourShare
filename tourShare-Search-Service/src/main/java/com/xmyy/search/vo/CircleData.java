package com.xmyy.search.vo;

import com.xmyy.circle.model.UcDynamicCircle;

import java.io.Serializable;

@SuppressWarnings("serial")
public class CircleData extends UcDynamicCircle implements Serializable {

   private String nickName;
   private String avatarRsurl;
   private Integer isSelf;

   public String getNickName() {
      return nickName;
   }

   public void setNickName(String nickName) {
      this.nickName = nickName;
   }

   public String getAvatarRsurl() {
      return avatarRsurl;
   }

   public void setAvatarRsurl(String avatarRsurl) {
      this.avatarRsurl = avatarRsurl;
   }

   public Integer getIsSelf() {
      return isSelf;
   }

   public void setIsSelf(Integer isSelf) {
      this.isSelf = isSelf;
   }
}
