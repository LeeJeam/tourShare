package com.xmyy.circle.service;

import com.xmyy.circle.model.DgCountry;
import com.xmyy.circle.vo.CountryResult;
import com.xmyy.circle.vo.CountryTreeResult;
import top.ibase4j.core.base.BaseService;

import java.util.List;
import java.util.Map;

/**
 * 国家  服务接口
 *
 * @author wangzejun
 */
public interface DgCountryService extends BaseService<DgCountry> {

    List<CountryTreeResult> getZhouCountryTree(Integer isContainHot,String keywords);

    List<CountryTreeResult> getZimuCountryTree(Integer isContainHot,String keywords);

    List<CountryResult> getHotCountryList(String keywords);

    void loadRedisForCountryList();

    CountryResult getCountryByName(String name);

    CountryResult getCountryByShortCode(String shortCode);

    List<String>  getCountryShortCodesByZhouCode(String zhouCode);

    List<CountryResult>  getCountrysByZhouCode(String zhouCode);

    /**
     * 根据国家码列表，查询 国家码-国旗 的Map集合
     * @param shortCodes
     * @return
     */
    Map<String,String> getNationFlagsByShortCodes(List<String> shortCodes);
}