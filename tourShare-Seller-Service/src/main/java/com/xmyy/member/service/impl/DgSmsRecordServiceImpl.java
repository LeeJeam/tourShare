package com.xmyy.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.toolkit.IdWorker;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.common.util.SmsUtil;
import com.xmyy.common.vo.SendSmsResult;
import com.xmyy.member.mapper.DgSmsRecordMapper;
import com.xmyy.member.model.DgSmsRecord;
import com.xmyy.member.service.DgSmsRecordService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.exception.BizException;
import top.ibase4j.core.util.CacheUtil;
import top.ibase4j.core.util.InstanceUtil;
import top.ibase4j.core.util.MathUtil;

import java.util.Date;
import java.util.Map;

/**
 * 阿里大于短信发送  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = DgSmsRecordService.class)
@CacheConfig(cacheNames = "DgSmsRecord")
public class DgSmsRecordServiceImpl extends BaseServiceImpl<DgSmsRecord, DgSmsRecordMapper> implements DgSmsRecordService {

    @Transactional
    public String sendSmsandCache(String redisCodeCount, String smsCodeNo, String mobile, String redisCode) {
        /*
         * 短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，支持1条/分钟，5条/小时，10条/天。
         * 一个手机号码通过阿里云短信服务平台只能收到40条/天。（如您是在发送验证码时提示业务限流，建议根据以上业务调整接口调用时间）
         */
        Object smsSeconds = CacheUtil.getCache().get("sms:seconds" + mobile); //该号码上次发送的时间
        Object redisSmsCodeCount = CacheUtil.getCache().get("sms:counts" + mobile); //该号码当天发送的次数

        int redisSmsCodeCountIn = 0;
        if (redisSmsCodeCount != null) redisSmsCodeCountIn = Integer.parseInt(redisSmsCodeCount.toString());

        if (smsSeconds != null) {
            long sxiat = System.currentTimeMillis() - Long.parseLong(smsSeconds.toString());
            if (sxiat <= 60000) {
                return "您的操作过于频繁，请稍后再试";
            }

            if (redisSmsCodeCountIn > 10) {
                return "您当天获取验证码次数已用完，请明天再来";
            }

            if (sxiat <= 3600000 && redisSmsCodeCountIn > 5) {
                return "您的操作过于频繁，请稍后再试";
            }
        }

        //生成验证码并发送
        String smsCode = RandomStringUtils.randomNumeric(6);
        logger.info("smsCode->" + smsCode);
        this.sendSms(mobile, smsCodeNo, InstanceUtil.newHashMap("code", smsCode));

        //将验证码存入redis，有效时间15分钟
        CacheUtil.getCache().set(redisCode + mobile, smsCode, 900);

        //更新该号码发送验证码的时间，失效时间为当天24:00
        CacheUtil.getCache().set("sms:seconds" + mobile, System.currentTimeMillis());
        CacheUtil.getCache().expireAt("sms:seconds" + mobile, DateUtils.getDateEnd(new Date()).getTime());

        //发送次数+1，失效时间为当天24:00
        CacheUtil.getCache().set("sms:counts" + mobile, redisSmsCodeCount == null ? 1 : MathUtil.add(redisSmsCodeCount, 1).intValue());
        CacheUtil.getCache().expireAt("sms:counts" + mobile, DateUtils.getDateEnd(new Date()).getTime());

        return null;
    }

    @Override
    public boolean sendSms(String mobile, String tmpCode, Map<String, String> content) {
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(tmpCode) || content == null) {
            throw new BizException("短信发送缺少参数");
        }

        DgSmsRecord record = new DgSmsRecord();
        SendSmsResult response = SmsUtil.sendSms(mobile, tmpCode, content);
        logger.info(JSON.toJSONString(response));
        if (response.getCode() != null) {
            record.setBizId(response.getRequestId());
            if (response.getCode().equals("OK")) {
                // 请求成功
                record.setState(EnumConstants.State.NORMAL.value());
                record.setRemark(response.getMessage());
            } else {
                record.setState(EnumConstants.State.DELETED.value());
                record.setRemark(response.getMessage());
            }
        } else {
            record.setBizId(IdWorker.get32UUID());
            record.setState(EnumConstants.State.DELETED.value());
        }
        record.setMobile(mobile);
        record.setContent(JSON.toJSONString(content));
        super.update(record);

        return true;
    }
}
