package com.xmyy.search.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.DateUtils;
import com.xmyy.manage.model.AdminDic;
import com.xmyy.manage.service.AdminDicService;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.util.InstanceUtil;

import java.util.Date;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.xmyy.common.Constants.SYS_USER_ID;

@Transactional
@Service
public class SearchCommonServiceImpl {

    @Autowired
    private AdminDicService adminDicService;

    public int doWithLastOperationTime(String timeKey, Function<Date, Integer> consumer) {
        DateTime now = new DateTime().minusMinutes(1);
        Date lastIndexTime = null;

        List<AdminDic> dicts = adminDicService.queryList(InstanceUtil.newHashMap("code", timeKey));
        AdminDic dict = null;
        if (!CollectionUtils.isEmpty(dicts)) {
            dict = dicts.get(0);
        }

        if (dict == null) {
            lastIndexTime = new Date();
            dict = new AdminDic();
            dict.setType(EnumConstants.AdminDicType.SELLER_LAST_INDEX_TIME.getValue());
            dict.setCode(timeKey);
            dict.setUpdateTime(lastIndexTime);
            dict.setCreateTime(lastIndexTime);
            dict.setCodeText(now.toString(DateUtils.Pattern_DATE_SHORT_TIME));
            dict.setCreateBy(SYS_USER_ID);
            dict.setUpdateBy(SYS_USER_ID);
            dict.setEditable(EnumConstants.YesOrNo.NO.getValue());
            adminDicService.insert(dict);
            return consumer.apply(null);

        } else {

            lastIndexTime = DateTimeFormat.forPattern(DateUtils.Pattern_DATE_SHORT_TIME).parseDateTime(dict.getCodeText()).toDate();
            dict.setUpdateTime(now.toDate());
            dict.setUpdateBy(SYS_USER_ID);
//			dict.setValue(DateUtils.formatDate(now, DateUtils.Pattern_DATE_TIME_MILLS));
            dict.setCodeText(now.toString(DateUtils.Pattern_DATE_SHORT_TIME));
            adminDicService.update(dict);
            return consumer.apply(lastIndexTime);
        }
    }

    public Date getLastOperationTime(String timeKey) {
        DateTime now = new DateTime().minusMinutes(1);
        Date lastIndexTime = null;

        List<AdminDic> dicts = adminDicService.queryList(InstanceUtil.newHashMap("code", timeKey));
        AdminDic dict = null;
        if (!CollectionUtils.isEmpty(dicts)) {
            dict = dicts.get(0);
        }

        if (dict == null) {
            lastIndexTime = new Date();
            dict = new AdminDic();
            dict.setType(timeKey);
            dict.setCode(timeKey);
            dict.setUpdateTime(lastIndexTime);
            dict.setCreateTime(lastIndexTime);
            dict.setCodeText(now.toString(DateUtils.Pattern_Date_Time));
            dict.setCreateBy(SYS_USER_ID);
            dict.setUpdateBy(SYS_USER_ID);
            dict.setEditable(EnumConstants.YesOrNo.NO.getValue());
            adminDicService.insert(dict);
            return null;
        } else {
            lastIndexTime = DateTimeFormat.forPattern(DateUtils.Pattern_Date_Time).parseDateTime(dict.getCodeText()).toDate();
            dict.setUpdateTime(now.toDate());
            dict.setUpdateBy(SYS_USER_ID);
            dict.setCodeText(now.toString(DateUtils.Pattern_Date_Time));
            adminDicService.update(dict);
            return lastIndexTime;
        }
    }

    public int doInBatchIfNecessary(final int size, int total, BiFunction<Integer, Integer, Integer> func) {
        int batchCount = 1;
        int batchSize = size;
        if (size > 0) {
            batchCount = total % batchSize;
            batchCount = batchCount == 0 ? total / batchSize : total / batchSize + 1;
        } else {
            batchSize = total;
        }

        int importCount = 0;
        for (int i = 0; i < batchCount; i++) {
            if (batchSize > 0)
                importCount += func.apply(batchSize, i);
        }
        return importCount;
    }


}
