package com.xmyy.search.service.impl;

import com.xmyy.search.dao.CircleDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/***
 * 动态
 *
 * @author zlp
 */
@Service
public class CircleDocServiceImpl {

    @Resource
    private CircleDao circleDao;

    @Transactional(readOnly = true)
    public int countValidCircles(Date lastIndexTime) {
        return this.circleDao.countValidCircles(lastIndexTime);
    }

}
