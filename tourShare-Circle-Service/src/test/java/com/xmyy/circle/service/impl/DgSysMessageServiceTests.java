package com.xmyy.circle.service.impl;

import com.xmyy.circle.service.DgSysMessageService;
import com.xmyy.circle.vo.DgSysMessageParam;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.junit.BaseJUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * Created by yeyu on 2018/7/5.
 * 描述：
 */
public class DgSysMessageServiceTests extends BaseJUnitTest {

    @Resource
    private DgSysMessageService service;

    @Test
    @Rollback
    public void addSysMessage(){
        DgSysMessageParam param=new DgSysMessageParam();
        param.setTitle("系统消息");
        param.setContent("系统消息");
        param.setMessageType(1);
        param.setMemberType(EnumConstants.MemberType.seller.getValue());
        param.setCover("http://xmyy.com/1.jpg");
        param.setImages("http://xmyy.com/1.jpg");
        Object obj= service.addSysMessage(param,1L);
        Assert.assertNotNull(obj);
    }
    /*@Test
    public void getSysMessageList(){
        logger.info("单元测试-------------获取系统消息");
        DgSysMessagePageParams param=new DgSysMessagePageParams();
        param.setCurrent(1);
        param.setSize(10);
        param.setMemberId(1L);
        param.setMessageType(1);
        param.setMemberType(1);
        Object obj= service.getSysMessageList(param);
        Assert.assertNotNull(obj);
    }
    @Test
    public  void countNotReadMessage(){
        logger.info("单元测试-------------尚未阅读的系统消息条数");
        DgSysMessageNotReadParams param=new DgSysMessageNotReadParams();
        param.setMemberId(1L);
        param.setMemberType(1);
        param.setMessageType(1);
        Object obj= service.countNotReadMessage(param);
        Assert.assertNotNull(obj);
    }
    @Test
    public void findSysMessageById(){
        logger.info("单元测试-------------读取系统消息详情");
        DgSysMessageReadParams param=new DgSysMessageReadParams();
        param.setMemberType(1);
        param.setMessageId(4L);
        param.setMemberId(1L);
        Object obj= service.findSysMessageById(param);
        Assert.assertNotNull(obj);
    }
    @Test
    @Rollback
    public void addSysMessageRead(){
        logger.info("单元测试-------------读取系统添加阅读记录消息详情");
        DgSysMessageReadParams params=new DgSysMessageReadParams();
        params.setMemberId(23L);
        params.setMessageId(3L);
        params.setMemberType(1);
        idgSysMessageReadService.addSysMessageRead(params,1L);
    }*/
}
