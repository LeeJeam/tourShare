package com.xmyy.common.util;

import com.xmyy.common.EnumConstants;
import org.apache.commons.lang3.RandomStringUtils;
import top.ibase4j.core.util.CacheUtil;

import java.math.BigInteger;
import java.util.Random;
import java.util.zip.CRC32;

/**
 * 序列号生成
 * zlp
 */
public class BizSequenceUtils {
    private static final CRC32 crc = new CRC32();
    private static final Random random = new Random();
    private static final int[] map = new int[]{7, 4, 1, 0, 8, 5, 2, 9, 6, 3};//小键盘从上到下，从左到右


    /**
     * 买手或者买家编号 初始值100000
     * @param type
     * @return
     */
    public static String generateMemberNo(String type) {
        Boolean exists = CacheUtil.getLockManager().exists("S:iBase4J:BizNo:" + type);
        if(exists){
           return CacheUtil.getLockManager().incr("S:iBase4J:BizNo:"+type).toString();
        }
        CacheUtil.getLockManager().set("S:iBase4J:BizNo:" + type, 100000);

        return  "100000";
    }

    /**
     * @param bizCode
     * @return 数字
     */
    public static String generateBizNo(EnumConstants.BizCode bizCode) {
        //IdWorker.getId()
        return  generateBizNo(CacheUtil.getLockManager().incr("S:iBase4J:BizNo:"+bizCode.name()), bizCode);
    }

    /**
     * @param wordId
     * @param bizCode
     * @return 数字
     */
    public static String generateBizNo(Long wordId, EnumConstants.BizCode bizCode) {

        StringBuilder sb = new StringBuilder();
        String seq = String.valueOf(wordId  % (long)Math.pow(10, bizCode.length()-4));//9位序列。用于维一性保证

        for(int i=0; i<seq.length(); i++) {
            sb.append(map[seq.charAt(i) - 48]);//将九位序列转成映射值，用于迷惑敌人
        }

        sb.insert(0, sb.length());//序列长度位，避免序列与随机数的冲突。用于维一性保证
        sb.insert(0, bizCode.ordinal());//前缀，业务标识

        for(; sb.length() < bizCode.length()-1; ) {
            sb.append(random.nextInt(10));//不足11位的用随机数补齐，保证编号的固定长度
        }

        crc.update(("xmyooyo.com" + sb).getBytes());//计算效验位时，用xmyooyo.com作为盐

        sb.append(crc.getValue() % 10);//1位CRC32效验码

        if(bizCode.idPrefix()!=null&&bizCode.idPrefix()!=""){
            sb.insert(0,bizCode.idPrefix());
        }

        return sb.toString();
    }

    /**
     * @param bizCode
     * @return 字母+数字
     */
    public static String generateBizStrNo(Long wordId, EnumConstants.BizCode bizCode) {
        String code = null;
        if(wordId ==null){
            code = generateBizNo(bizCode);
        }else{
            code = generateBizNo(wordId,bizCode);
        }

        BigInteger big = new BigInteger(code);
        StringBuilder sb = new StringBuilder(big.toString(36));

        sb.append(RandomStringUtils.random(bizCode.length() - sb.length(), true, true));// 不足用随机数补齐，保证编号的固定长度

        return sb.toString().toUpperCase();

    }

    /**
     * @param bizCode
     * @return 字母+数字
     */
    public static String generateBizStrNo(EnumConstants.BizCode bizCode) {
        String code = generateBizNo(bizCode);

        BigInteger big = new BigInteger(code);
        StringBuilder sb = new StringBuilder(big.toString(36));

        sb.append(RandomStringUtils.random(bizCode.length() - sb.length(), true, true));// 不足用随机数补齐，保证编号的固定长度

        return sb.toString().toUpperCase();

    }




    /**
     *
     * 随机生成邀请码（数字+字母）
     *
     * @param len 邀请码长度
     */
    public static String generateRandom(int len) {
        //字符源，可以根据需要删减
        String generateSource = "23456789abcdefghgklmnpqrstuvwxyz";//去掉1和i ，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }

    /**
     * 随机生成字符串
     * @param len 生成长度
     */
    public static String generateRandomStr(int len) {
        //字符源，可以根据需要删减
        String generateSource = "abcdefghgklmnpqrstuvwxyz"; //去掉1和i，0和o
        String rtnStr = "";
        for (int i = 0; i < len; i++) {
            //循环随机获得当次字符，并移走选出的字符
            String nowStr = String.valueOf(generateSource.charAt((int) Math.floor(Math.random() * generateSource.length())));
            rtnStr += nowStr;
            generateSource = generateSource.replaceAll(nowStr, "");
        }
        return rtnStr;
    }


    public static void main(String[] args) {
		/*System.out.println(IdWorker.getId());
        System.out.println(IdWorker.getId());
        System.out.println(IdWorker.getId());*/
       // String no = generateBizNo(EnumConstants.BizCode.TourNo);
       // System.out.println(no);
       /* BigInteger big = new BigInteger("1");
        StringBuilder sb = new StringBuilder(big.toString(36));
        sb.append(RandomStringUtils.random(5 - sb.length(), true, true));// 不足用随机数补齐，保证编号的固定长度
        System.out.println(sb.toString());*/
       // generateBizNo(EnumConstants.BizCode.InviteCodeNO);

    }
}
