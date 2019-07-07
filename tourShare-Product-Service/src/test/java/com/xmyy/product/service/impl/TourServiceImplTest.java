package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.dto.AttrValueDto;
import com.xmyy.product.dto.SeriesAttrDto;
import com.xmyy.product.dto.SeriesColorDto;
import com.xmyy.product.dto.SeriesPriceDto;
import com.xmyy.product.service.PtSeriesService;
import com.xmyy.product.service.TourService;
import com.xmyy.product.service.TourSiteService;
import com.xmyy.product.vo.ApiTourInfoResult;
import com.xmyy.product.vo.ManageSeriesParam;
import com.xmyy.product.vo.SeriesAddParam;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 2018/7/17.
 */
public class TourServiceImplTest extends BaseJUnitTest {

    @Resource
    private TourSiteService tourSiteService;
    @Resource
    private TourService tourService;

    @Test
    public void finishTourTest() {
        tourService.finishTour();
    }

    @Test
    public void queryTourIdsNoCheckPicTest() {
        List<Long> ids = tourService.queryTourIdsNoCheckPic(1322L, 1);
        System.out.println(ids);
    }

    @Test
    public void queryNewTourByMemberIdsTest() {
        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < 1400; i++) {
            ids.add((long) i);
        }
        Map<String, ApiTourInfoResult> resultMap = tourService.queryNewTourByMemberIds(ids);
        for (String key : resultMap.keySet()) {
            System.out.println("key=" + key + " : tourNo=" + resultMap.get(key).getTourNo());
        }
    }

}