package com.xmyy.livevideo.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.livevideo.serivce.LiveVideoService;
import org.junit.Test;

public class LiveVideoServiceImplTest extends BaseJUnitTest {

    @Reference
    private LiveVideoService liveVideoService;

    @Test
    public void canAccept() {
        liveVideoService.queryLiveVideoByRoomId("5febbfcc8bcb4840b28d84d5d669aaa3");
    }
}
