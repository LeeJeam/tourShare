package com.xmyy.search.service.impl;

import com.xmyy.search.dao.SellerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/***
 * 买手
 *
 * @author zlp
 */
@Service
public class SellerDocServiceImpl {

    @Resource
    private SellerDao sellerDao;

    @Transactional(readOnly = true)
    public int countValidSellers(Date lastIndexTime) {
        return this.sellerDao.countValidSellers(lastIndexTime);
    }

}
