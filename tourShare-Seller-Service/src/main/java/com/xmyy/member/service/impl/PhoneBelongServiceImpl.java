package com.xmyy.member.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.common.util.ToolsUtil;
import com.xmyy.member.service.PhoneBelongService;
import com.xmyy.member.vo.PhoneBelongResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * 手机归属地查询  服务实现类
 *
 * @author zlp
 */
@Service(interfaceClass = PhoneBelongService.class)
public class PhoneBelongServiceImpl implements PhoneBelongService {

    @Resource
    private RestTemplate restTemplate;

    private final String url = "http://api.ip138.com/mobile/?token=ddd53483cc98b55f7a6ecf3878247d67&mobile=";

    @Override
    public String getBelongByMobile(String mobile) {
        if (StringUtils.isBlank(mobile)) {
            return null;
        }

        if (!ToolsUtil.isMobiles(mobile)) {
            return null;
        }

        PhoneBelongResult phoneBelongResult = restTemplate.getForObject(url + mobile, PhoneBelongResult.class);

        if (phoneBelongResult.getRet().equals("ok")) {
            //{"ret":"ok","mobile":"13751843587","data":["广东","广州","移动","020",""]}
            return phoneBelongResult.getData().get(1);
        }

        return null;
    }
}
