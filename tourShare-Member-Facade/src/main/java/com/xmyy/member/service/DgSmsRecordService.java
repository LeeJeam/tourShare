package com.xmyy.member.service;

import com.xmyy.member.model.DgSmsRecord;
import top.ibase4j.core.base.BaseService;

import java.util.Map;

/**
 * 短信发送记录  服务类
 *
 * @author admin
 * @since 2018-05-23
 */
public interface DgSmsRecordService extends BaseService<DgSmsRecord> {

    /**
     * @param redisCodeCount redis 总数key
     * @param smsCodeNo 短信模板code
     * @param mobile    手机号
     * @param redisCode redis key
     * @return
     * */
    public String sendSmsandCache(String redisCodeCount, String smsCodeNo, String mobile, String redisCode);

    /**
     * 短信发送
     * @param mobile 手机号
     * @param tmpCode 短信模板code
     * @param content 发送内容
     * @return
     */
    public boolean sendSms(String mobile, String tmpCode, Map<String,String> content);
	
}