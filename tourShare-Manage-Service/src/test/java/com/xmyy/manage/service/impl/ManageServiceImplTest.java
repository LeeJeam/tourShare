package com.xmyy.manage.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.manage.service.AdminMenuService;
import com.xmyy.manage.vo.ManageMenuFrontendResult;
import org.junit.Test;

import java.util.List;

public class ManageServiceImplTest extends BaseJUnitTest {

    @Reference
    private AdminMenuService adminMenuService;

    @Test
    public void canAccept() {
        List<ManageMenuFrontendResult> manageMenuFrontendResults = adminMenuService.queryForPermissionTree(1L);
        for (ManageMenuFrontendResult result : manageMenuFrontendResults) {
            System.out.println(result);
        }
    }
}
