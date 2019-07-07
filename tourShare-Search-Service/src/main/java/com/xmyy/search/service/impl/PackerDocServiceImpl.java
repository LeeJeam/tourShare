package com.xmyy.search.service.impl;

import com.xmyy.search.dao.PackerDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/***
 * 背包客
 *
 * @author zlp
 */
@Service
public class PackerDocServiceImpl {

    @Resource
    private PackerDao packerDao;

    @Transactional(readOnly = true)
    public int countValidPackers(Date lastIndexTime) {
        return this.packerDao.countValidPackers(lastIndexTime);
    }

}
