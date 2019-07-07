package com.xmyy.member.service;

/**
 * 手机归属地查询
 *
 * @author zlp
 * @since 2018-07-31
 */
public interface PhoneBelongService {

    /**
     * 根据手机号查询归属地
     * @param mobile 手机号
     * @return 归属地
     */
    String getBelongByMobile(String mobile);
	
}