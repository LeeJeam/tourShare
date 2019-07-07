package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.circle.dao.DgRegionDao;
import com.xmyy.circle.mapper.DgRegionMapper;
import com.xmyy.circle.model.DgRegion;
import com.xmyy.circle.service.RegionService;
import com.xmyy.circle.vo.ApiRegionFlagListParam;
import com.xmyy.circle.vo.ApiRegionFlagResult;
import com.xmyy.circle.vo.RegionTreeListParam;
import com.xmyy.circle.vo.RegionTreeListResult;
import com.xmyy.common.EnumConstants;
import org.springframework.cache.annotation.CacheConfig;
import top.ibase4j.core.base.BaseServiceImpl;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 地区表  服务实现类
 *
 * @author wangzejun
 */
@CacheConfig(cacheNames = "DgRegion")
@Service(interfaceClass = RegionService.class)
public class RegionServiceImpl extends BaseServiceImpl<DgRegion, DgRegionMapper> implements RegionService {

    @Resource
    private DgRegionDao dgRegionDao;

    @Override
    public Object getTreeList(RegionTreeListParam params) {
        if (params.getShowStyle() == EnumConstants.ShowStyle.LIST_STYLE.getValue()) {
            return dgRegionDao.getTreeList(params);
        } else if (params.getShowStyle() == EnumConstants.ShowStyle.ZGS_STYLE.getValue()) {
            return queryTreeListFisrtStyle(params);
        } else if (params.getShowStyle() == EnumConstants.ShowStyle.ZIMU_STYLE.getValue()) {
            params.setIsHot(null);
            return queryTreeListSecondStyle(params);
        } else if (params.getShowStyle() == EnumConstants.ShowStyle.SSQ_STYLE.getValue()) {
            params.setIsHot(null);
            return queryTreeListThirdStyle(params);
        } else {
            return "没有指定显示的风格";
        }
    }

    /**
     * 洲-国风格
     *
     * @param params
     * @return
     */
    private List<RegionTreeListResult> queryTreeListFisrtStyle(RegionTreeListParam params) {
        List<RegionTreeListResult> treeList = new ArrayList<>();

        //热门
        RegionTreeListResult hotRegion = null;
        params.setGrade(params.getMinGrade() + 1);
        if (params.getIsHot() != null && EnumConstants.RegionHot.YES.getValue().equals(params.getIsHot())) {
            List<RegionTreeListResult> hotGuoList = dgRegionDao.getTreeList(params);
            hotRegion = new RegionTreeListResult();
            hotRegion.setCname("热门");
            hotRegion.setCcode("hotCountry");
            hotRegion.setChildren(hotGuoList);
        }

        params.setGrade(params.getMinGrade());
        params.setIsHot(null);
        //六大洲
        List<RegionTreeListResult> zhouRegions = dgRegionDao.getTreeList(params);
        params.setGrade(params.getGrade() + 1);
        for (RegionTreeListResult treeItem : zhouRegions) {
            params.setParentcode(treeItem.getCcode());
            List<RegionTreeListResult> guoList = dgRegionDao.getTreeList(params);
            if (guoList != null && guoList.size() > 0) {
                treeItem.setHasChild(true);
                treeItem.setChildren(guoList);
            }
        }

        if (hotRegion != null) {
            treeList.add(hotRegion);
        }

        treeList.addAll(zhouRegions);
        return treeList;
    }

    /**
     * 字母风格
     *
     * @param params
     * @return
     */
    private List<RegionTreeListResult> queryTreeListSecondStyle(RegionTreeListParam params) {
        String[] idCodes = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        List<RegionTreeListResult> regionTreeListDtos = dgRegionDao.getTreeList(params);
        List<RegionTreeListResult> regions = new ArrayList<RegionTreeListResult>();

        int size = regionTreeListDtos.size();
        for (int i = 0; i < idCodes.length; i++) {
            String curSecond = idCodes[i];
            List<RegionTreeListResult> regionList = new ArrayList<RegionTreeListResult>();
            for (int j = 0; j < size; j++) {
                RegionTreeListResult regionTreeListResult = regionTreeListDtos.get(j);
                if (curSecond.equalsIgnoreCase(regionTreeListResult.getIdxcode())) {
                    regionList.add(regionTreeListResult);
                }
            }

            if (regionList.size() > 0) {
                RegionTreeListResult regionTree = new RegionTreeListResult();
                regionTree.setChildren(regionList);
                regionTree.setCcode(curSecond);
                regionTree.setCname(curSecond);
                regionTree.setParentCode("0");
                regionTree.setIdxcode(curSecond);
                regions.add(regionTree);
            }
        }
        return regions;
    }

    /**
     * 省市区县
     *
     * @param params
     * @return
     */
    private List<RegionTreeListResult> queryTreeListThirdStyle(RegionTreeListParam params) {
        RegionTreeListResult parentTree = null;
        //找出最高父亲节点
        List<RegionTreeListResult> parentTreeList = dgRegionDao.getTreeList(params);
        if (parentTreeList == null || parentTreeList.size() == 0 || parentTreeList.size() > 1) {
            return null;
        } else if (parentTreeList.size() == 1) {
            parentTree = parentTreeList.get(0);
        }
        params.setGrade(null);
        params.setCname(null);
        params.setParentcode(parentTree.getCcode());
        List<RegionTreeListResult> regionTreeList = dgRegionDao.getTreeList(params);
        if (regionTreeList != null && regionTreeList.size() > 0) {
            for (RegionTreeListResult regionTree : regionTreeList) {
                params.setParentcode(regionTree.getCcode());
                //params.setGrade(grade);
                List<RegionTreeListResult> childTreeList = dgRegionDao.getTreeList(params);
                regionTree.setChildren(childTreeList);
                if (childTreeList != null && childTreeList.size() > 0) {
                    regionTree.setHasChild(true);
                    for (RegionTreeListResult childRegionTree : childTreeList) {
                        params.setParentcode(childRegionTree.getCcode());
                        //params.setGrade(grade+1);
                        List<RegionTreeListResult> childList = dgRegionDao.getTreeList(params);
                        childRegionTree.setChildren(childList);
                        regionTree.setHasChild(childList != null && childList.size() > 0 ? true : false);
                    }
                }
            }
        }
        return regionTreeList;
    }

    @Override
    public Map<String, String> getFlagMap() {
        ApiRegionFlagListParam apiRegionFlagListParams = new ApiRegionFlagListParam();
        List<ApiRegionFlagResult> flagList = dgRegionDao.getFlagList(apiRegionFlagListParams);
        LinkedHashMap<String, String> flagMap = new LinkedHashMap<>();

        if (flagList != null && flagList.size() > 0) {
            for (ApiRegionFlagResult flagResult : flagList) {
                flagMap.put(flagResult.getCname(), flagResult.getNationalFlag());
            }
        }
        return flagMap;
    }

    @Override
    public List<ApiRegionFlagResult> getRegionList() {
        ApiRegionFlagListParam apiRegionFlagListParams = new ApiRegionFlagListParam();
        List<ApiRegionFlagResult> regionList = dgRegionDao.getFlagList(apiRegionFlagListParams);
        return regionList;
    }
}
