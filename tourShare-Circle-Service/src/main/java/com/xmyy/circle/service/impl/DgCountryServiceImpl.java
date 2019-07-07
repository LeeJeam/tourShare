package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.xmyy.circle.mapper.DgCountryMapper;
import com.xmyy.circle.model.DgCountry;
import com.xmyy.circle.service.DgCountryService;
import com.xmyy.circle.vo.CountryResult;
import com.xmyy.circle.vo.CountryTreeResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 国家  服务实现类
 *
 * @author wangzejun
 */
@Service(interfaceClass = DgCountryService.class)
@CacheConfig(cacheNames = "DgCountry")
public class DgCountryServiceImpl extends BaseServiceImpl<DgCountry, DgCountryMapper> implements DgCountryService {

    @Resource
    private DgCountryMapper dgCountryMapper;

    @Override
    public List<CountryTreeResult> getZhouCountryTree(Integer isContainHot, String keywords) {
        List<CountryTreeResult> treeList = new ArrayList<CountryTreeResult>();
        String[] zhouNames = new String[]{"亚洲", "欧洲", "非洲", "北美洲", "南美洲", "大洋洲"};
        String[] zhouCodes = new String[]{"ZHOUASC", "ZHOUEUC", "ZHOUAFC", "ZHOUNAC", "ZHOUSAC", "ZHOUOAC"};
        if (isContainHot == EnumConstants.YesOrNo.YES.getValue()) {
            List<CountryResult> hotCountryList = getHotCountryList(keywords);
            if (hotCountryList != null && hotCountryList.size() > 0) {
                CountryTreeResult hotCountryTree = new CountryTreeResult();
                hotCountryTree.setGroupName("热门");
                hotCountryTree.setGroupCode("HTC");
                hotCountryTree.setChildren(hotCountryList);
                treeList.add(hotCountryTree);
            }
        }
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        ew.like("name", keywords);
        List<DgCountry> countryList = dgCountryMapper.selectList(ew);
        int countrySize = countryList != null ? countryList.size() : 0;

        if (countrySize > 0) {
            for (int i = 0; i < zhouNames.length; i++) {
                String zhouChsName = zhouNames[i];
                String zhouCode = zhouCodes[i];

                List<CountryResult> countryTreeList = new ArrayList<CountryResult>();
                CountryResult allCountry = new CountryResult();
                allCountry.setName("全部");
                allCountry.setIsAll(EnumConstants.YesOrNo.YES.getValue());
                allCountry.setContinent(zhouCode);
                countryTreeList.add(allCountry);
                for (int j = 0; j < countrySize; j++) {
                    DgCountry dgCountry = countryList.get(j);
                    if (zhouCode.equals(dgCountry.getContinentCode())) {
                        countryTreeList.add(InstanceUtil.to(dgCountry, CountryResult.class));
                    }
                }

                if (countryTreeList.size() > 1) {
                    CountryTreeResult countryTreeResult = new CountryTreeResult();
                    countryTreeResult.setGroupName(zhouChsName);
                    countryTreeResult.setGroupCode(zhouCode);
                    countryTreeResult.setChildren(countryTreeList);
                    treeList.add(countryTreeResult);
                }
            }
        }

        return treeList;
    }


    @Override
    public List<CountryTreeResult> getZimuCountryTree(Integer isContainHot, String keywords) {
        List<CountryTreeResult> treeList = new ArrayList<CountryTreeResult>();
        String[] idCodes = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

        if (isContainHot != null && EnumConstants.YesOrNo.YES.getValue() == isContainHot) {

            List<CountryResult> hotCountryList = getHotCountryList(keywords);
            if (hotCountryList != null && hotCountryList.size() > 0) {
                CountryTreeResult hotCountryTree = new CountryTreeResult();
                hotCountryTree.setGroupName("热门国家");
                hotCountryTree.setChildren(hotCountryList);
                treeList.add(hotCountryTree);
            }
        }

        EntityWrapper<DgCountry> ew = new EntityWrapper<DgCountry>();
        ew.like("name", keywords);
        ew.orderAsc(InstanceUtil.newArrayList("initial"));
        List<DgCountry> countryList = dgCountryMapper.selectList(ew);
        int countrySize = countryList.size();
        for (int i = 0; i < idCodes.length; i++) {
            String groupName = idCodes[i];
            List<CountryResult> countryTreeList = new ArrayList<CountryResult>();
            for (int j = 0; j < countrySize; j++) {
                DgCountry dgCountry = countryList.get(j);
                if (groupName.equals(dgCountry.getInitial())) {
                    countryTreeList.add(InstanceUtil.to(dgCountry, CountryResult.class));
                }
            }
            if (countryTreeList.size() > 0) {
                CountryTreeResult countryTreeResult = new CountryTreeResult();
                countryTreeResult.setGroupName(groupName);
                countryTreeResult.setChildren(countryTreeList);
                treeList.add(countryTreeResult);
            }
        }
        return treeList;
    }

    @Override
    public List<CountryResult> getHotCountryList(String keywords) {
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        ew.eq("is_hot", EnumConstants.YesOrNo.YES.getValue());
        if (StringUtils.isNotBlank(keywords)) {
            ew.like("name", keywords);
        }

        ew.orderAsc(InstanceUtil.newArrayList("sort_no"));
        ew.orderDesc(InstanceUtil.newArrayList("is_hot"));
        List<DgCountry> dgCountries = dgCountryMapper.selectList(ew);
        List<CountryResult> list = dgCountries.stream().map(t -> {
            CountryResult countryResult = InstanceUtil.to(t, CountryResult.class);
            return countryResult;
        }).collect(Collectors.toCollection(ArrayList::new));
        return list;
    }

    @Override
    public void loadRedisForCountryList() {
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        List<DgCountry> countries = dgCountryMapper.selectList(ew);
        if (countries != null && countries.size() > 0) {
            for (DgCountry country : countries) {
                String keyName = String.format("%s_%s", EnumConstants.AreaOrCountryRedisKeys.COUNTRY_KEY, country.getShortCode());
                CacheUtils.setCountryInfo(keyName, JSON.toJSONString(country));
            }
        }
    }

    @Override
    public CountryResult getCountryByName(String name) {
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        ew.eq("name", name);
        List<DgCountry> dgCountries = dgCountryMapper.selectList(ew);
        if (dgCountries.size() > 0) {
            DgCountry dgCountry = dgCountries.get(0);
            CountryResult countryResult = InstanceUtil.to(dgCountry, CountryResult.class);
            return countryResult;
        }
        return null;
    }

    @Override
    public CountryResult getCountryByShortCode(String shortCode) {
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        ew.eq("short_code", shortCode);
        List<DgCountry> dgCountries = dgCountryMapper.selectList(ew);
        if (dgCountries != null && dgCountries.size() > 0) {
            return InstanceUtil.to(dgCountries.get(0), CountryResult.class);
        }
        return null;
    }

    @Override
    public Map<String, String> getNationFlagsByShortCodes(List<String> shortCodes) {
        Map<String, String> flagMap = new HashMap<>();
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        ew.in("short_code", shortCodes);
        List<DgCountry> dgCountries = dgCountryMapper.selectList(ew);
        dgCountries.forEach(t -> flagMap.put(t.getShortCode(), t.getLogo()));
        return flagMap;
    }

    @Override
    public List<String> getCountryShortCodesByZhouCode(String zhouCode) {
        EntityWrapper ew = new EntityWrapper();
        ew.eq("continent_code", zhouCode);
        ew.setSqlSelect("short_code");
        return dgCountryMapper.selectObjs(ew);
    }

    @Override
    public List<CountryResult> getCountrysByZhouCode(String zhouCode) {
        EntityWrapper<DgCountry> ew = new EntityWrapper<>();
        ew.eq("continent_code", zhouCode);
        List<DgCountry> dgCountries = dgCountryMapper.selectList(ew);
        return dgCountries.stream().map(t -> {
            return InstanceUtil.to(t, CountryResult.class);
        }).collect(Collectors.toCollection(ArrayList::new));
    }
}
