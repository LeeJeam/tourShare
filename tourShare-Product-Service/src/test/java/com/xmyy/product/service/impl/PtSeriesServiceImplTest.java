package com.xmyy.product.service.impl;

import com.xmyy.common.junit.BaseJUnitTest;
import com.xmyy.product.dto.AttrValueDto;
import com.xmyy.product.dto.SeriesAttrDto;
import com.xmyy.product.dto.SeriesColorDto;
import com.xmyy.product.dto.SeriesPriceDto;
import com.xmyy.product.service.PtSeriesService;
import com.xmyy.product.vo.ManageSeriesParam;
import com.xmyy.product.vo.SeriesAddParam;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2018/7/17.
 */
public class PtSeriesServiceImplTest extends BaseJUnitTest {

    @Resource
    private PtSeriesService ptSeriesService;

    @Test
    public void add() {
        SeriesAddParam params = new SeriesAddParam();
        params.setCategoryId(55L);
        params.setCategoryId2(56L);
        params.setBrandId(17L);
        params.setName("测试商品");
        params.setAlias("测试商品别名");

        SeriesAttrDto attrDto = new SeriesAttrDto();
        attrDto.setId(3L);
        attrDto.setName("属性名称");
        AttrValueDto valueDto = new AttrValueDto();
        valueDto.setId(4L);
        valueDto.setName("属性值名称");
        List<AttrValueDto> attrValueDtos = new ArrayList<>();
        List<SeriesAttrDto> seriesAttrDtos = new ArrayList<>();
        attrValueDtos.add(valueDto);
        seriesAttrDtos.add(attrDto);
        params.setAttrItem(seriesAttrDtos);

        SeriesColorDto colorDto = new SeriesColorDto();
        colorDto.setName("红色");
        colorDto.setImage("http://test.jpg");
        List<SeriesColorDto> seriesColorDtos = new ArrayList<>();
        seriesColorDtos.add(colorDto);
        params.setColorItem(seriesColorDtos);

        SeriesPriceDto priceDto = new SeriesPriceDto();
        priceDto.setRegion("美国");
        SeriesPriceDto pd = new SeriesPriceDto();
        SeriesPriceDto.Shop shop = pd.new Shop();
        shop.setShop("纽约免税店");
        shop.setPrice(new BigDecimal(110));
        List<SeriesPriceDto.Shop> shops = new ArrayList<>();
        shops.add(shop);
        priceDto.setShops(shops);
        List<SeriesPriceDto> priceDtos = new ArrayList<>();
        priceDtos.add(priceDto);
        params.setPriceItem(priceDtos);

        Object add = ptSeriesService.add(params);
        Assert.assertEquals(1,add);
    }

    @Test
    public void getList() {
        Long categoryId=55L;
        Long categoryId2=56L;
        Long brandId=17L;
        Object list = ptSeriesService.getList(categoryId, categoryId2, brandId);
        System.out.println(list);
    }

    @Test
    public void queryManagerPtSeriesList() {
        ManageSeriesParam param=new ManageSeriesParam();
        Object object= ptSeriesService.queryManagerPtSeriesList(param);
        Assert.assertNotNull(object);
    }

    @Test
    public void statistic() {
        Object statistic = ptSeriesService.statistic();
        System.out.println(statistic);
    }

}