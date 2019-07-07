package com.xmyy.member.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.member.service.UcInviteCodeService;
import com.xmyy.member.vo.InviteCodeGenerateParams;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class InviteCodeServiceImplTest extends BaseJUnitTest {

    @Resource
    private UcInviteCodeService inviteCodeService;

    @Test
    public void batchSendTest() {
        List<InviteCodeGenerateParams.Items> itemsList = new ArrayList<InviteCodeGenerateParams.Items>();

        InviteCodeGenerateParams.Items items = new InviteCodeGenerateParams.Items();
        items.setChannelType(1);
        items.setDays(1);
        items.setMobile("13751843587");
        items.setEmail("1192608977@qq.com");
        itemsList.add(items);
        InviteCodeGenerateParams code = new InviteCodeGenerateParams();
        code.setOccupyMan("afdasf");

        code.setItems(itemsList);
        inviteCodeService.batchSend(code);
    }

}
