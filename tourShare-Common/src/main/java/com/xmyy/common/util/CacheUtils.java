package com.xmyy.common.util;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.vo.MemberInfo;
import org.springframework.data.redis.core.RedisTemplate;
import top.ibase4j.core.util.CacheUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * 缓存数据工具类
 */
public class CacheUtils {

    private RedisTemplate<Serializable, Serializable> redisTemplate;

    //****************用户密码******************//
    public static void setRedisPassword(String type, String password, String mobile) {
        CacheUtil.getLockManager().hset("S:iBase4J:"+type+":password:", mobile, PasswordUtil.encodePassword(password));
    }

    //****************用户信息******************//
    /**
     * 获取用户信息
     * @param id 用户ID
     * @param memberType 用户类型（买手、买家/背包客）
     * @return
     */
    public static MemberInfo getMemberInfo(Long id, EnumConstants.MemberType memberType) {
        if(memberType == null || id == null) {
            return null;
        }

        String value = null;
        if (memberType.getValue() == EnumConstants.MemberType.seller.getValue()) {
            value = (String)CacheUtil.getLockManager().hget("S:iBase4J:seller:id_key:", id);
        } else {
            value = (String)CacheUtil.getLockManager().hget("S:iBase4J:buyer:id_key:", id);
        }
        if (!StringUtils.isBlank(value)) {
           return JSON.parseObject(value, MemberInfo.class);
        }

        return null;
    }

    /**
     * 设置用户信息
     * @param id 用户ID
     * @param memberType 用户类型（买手、买家/背包客）
     * @param info
     */
    public static void setMemberInfo(Long id, EnumConstants.MemberType memberType, MemberInfo info) {
        if(memberType == null || id == null|| info==null) return;
        if(memberType.getValue() == EnumConstants.MemberType.seller.getValue()){
            setSellerInfo(id, info);
        }else{
            setBuyerInfo(id, info);
        }
    }

    private static void setBuyerInfo(Long id, MemberInfo info) {
        try {
            info.setTime(System.currentTimeMillis());
            CacheUtil.getLockManager().hset("S:iBase4J:buyer:id_key:", id, JSON.toJSONString(info));
        } catch (Exception var3) {
            throw new RuntimeException("保存buyer info 失败，错误信息：", var3);
        }
    }

    private static void setSellerInfo(Long id, MemberInfo info) {
        try {
            info.setTime(System.currentTimeMillis());
            CacheUtil.getLockManager().hset("S:iBase4J:seller:id_key:", id, JSON.toJSONString(info));
        } catch (Exception var3) {
            throw new RuntimeException("保存seller info 失败，错误信息：", var3);
        }
    }

    public static void delBuyerInfo(Long id) {
        try {
            CacheUtil.getLockManager().hdel("S:iBase4J:buyer:id_key:", id);
        } catch (Exception var2) {
            throw new RuntimeException("删除buyer info 失败，错误信息：", var2);
        }
    }

    public static void delSellerInfo(Long id) {
        try {
            CacheUtil.getLockManager().hdel("S:iBase4J:seller:id_key:", id);
        } catch (Exception var2) {
            throw new RuntimeException("删除seller info 失败，错误信息：", var2);
        }
    }


    //****************国家地区数据******************//
    public static void setAreaTreeInfo(String keyName,String treeJson){
        try {
            CacheUtil.getLockManager().hset("S:iBase4J:area:treeInfo_key:", keyName, treeJson);
        } catch (Exception execption) {
            throw new RuntimeException("保存地区信息失败，错误信息：", execption);
        }
    }

    public static String getAreaTreeInfo(String keyName) {
        return (String)CacheUtil.getLockManager().hget("S:iBase4J:area:treeInfo_key:", keyName);
    }

    public static void delAreaTreeInfo(String keyName){
        String value = (String)CacheUtil.getLockManager().hget("S:iBase4J:area:treeInfo_key:", keyName);
        try {
            CacheUtil.getLockManager().hdel("S:iBase4J:area:treeInfo_key:", keyName);
        } catch (Exception var2) {
            throw new RuntimeException("删除地区信息失败，错误信息：", var2);
        }
    }

    public static void setJieDaoInfo(String keyName,String treeJson){
        try {
            CacheUtil.getLockManager().hset("S:iBase4J:area:jiedaoInfo_key:", keyName, treeJson);
        } catch (Exception execption) {
            throw new RuntimeException("保存街道信息失败，错误信息：", execption);
        }
    }

    public static String getJieDaoTreeInfo(String keyName) {
        return (String)CacheUtil.getLockManager().hget("S:iBase4J:area:jiedaoInfo_key:", keyName);
    }

    public static void delJieDaoInfo(String keyName){
        try {
            CacheUtil.getLockManager().hdel("S:iBase4J:area:jiedaoInfo_key:", keyName);
        } catch (Exception var2) {
            throw new RuntimeException("删除街道信息失败，错误信息：", var2);
        }
    }

    /**
     * 保存国家信息
     * @param keyName
     * @param countryJson
     */
    public static void setCountryInfo(String keyName,String countryJson){
        try {
            CacheUtil.getLockManager().hset("S:iBase4J:area:country_key:", keyName, countryJson);
        } catch (Exception e) {
            throw new RuntimeException("保存国家信息失败，错误信息：",e);
        }
    }

    /**
     * 获取国家信息
     * @param keyName
     * @return
     */
    public static String getCountryInfo(String keyName){
        return (String)CacheUtil.getLockManager().hget("S:iBase4J:area:country_key:", keyName);
    }

    /**
     * 删除国家信息
     * @param keyName
     */
    public static void deleteCountryInfo(String keyName){
        try {
            CacheUtil.getLockManager().hdel("S:iBase4J:area:country_key:", keyName);
        } catch (Exception var2) {
            throw new RuntimeException("删除国家信息失败，错误信息：", var2);
        }
    }

    //****************广告位版本号******************//
    public static void setHeadInfoVersion(String value){
        try {
            CacheUtil.getLockManager().set("S:iBase4J:headInfoVersion", value);
        } catch (Exception var) {
            throw new RuntimeException("保存预加载信息失败，错误信息：", var);
        }
    }

    public static String getHeadInfoVersion() {
        String version = (String) CacheUtil.getLockManager().get("S:iBase4J:headInfoVersion");
        if (StringUtils.isEmpty(version)) {
            setHeadInfoVersion("1");
            return "1";
        }
        return version;
    }

    public static void delHeadInfoVersion(){
        try {
            CacheUtil.getLockManager().del("S:iBase4J:headInfoVersion");
        } catch (Exception var2) {
            throw new RuntimeException("删除预加载信息失败，错误信息：", var2);
        }
    }

}
