package com.xmyy.circle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.xmyy.circle.dao.DgAreaDao;
import com.xmyy.circle.dto.AreaDto;
import com.xmyy.circle.mapper.DgAreaMapper;
import com.xmyy.circle.model.DgArea;
import com.xmyy.circle.service.DgAreaService;
import com.xmyy.circle.vo.ApiAreaJieDaoParam;
import com.xmyy.circle.vo.AreaJiedaoResult;
import com.xmyy.circle.vo.AreaTreeResult;
import com.xmyy.common.EnumConstants;
import com.xmyy.common.util.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.DependsOn;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 地区  服务实现类
 *
 * @author wangzejun
 */
@Service(interfaceClass = DgAreaService.class)
@CacheConfig(cacheNames = "DgArea")
@DependsOn({"dgAreaDao"})
public class DgAreaServiceImpl extends BaseServiceImpl<DgArea, DgAreaMapper> implements DgAreaService {

    @Resource
    private DgAreaDao dgAreaDao;

    @Value("${circle.isInitAreaInfo}")
    private String isInitArea="false";

    @PostConstruct
    public void loadAreaInfo(){
        if(Boolean.parseBoolean(isInitArea)){
            try {
                CacheUtils.delAreaTreeInfo(EnumConstants.AreaOrCountryRedisKeys.AREA_TREE_KEY.getKey());
            } catch (Exception e) {
                e.printStackTrace();
            }
            getAreaTree();
            loadJieDaoInfo();
        }
    }

    @Override
    public List<AreaTreeResult> getAreaTree() {
        List<AreaTreeResult> areaTreeResults=null;

        String areaTreeInfo = null;
        try {
            areaTreeInfo = CacheUtils.getAreaTreeInfo(EnumConstants.AreaOrCountryRedisKeys.AREA_TREE_KEY.getKey());
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("省市区县redis缓存中获取失败，redis服务器网络有问题或者redis没有启动");
        }

        if(StringUtils.isNotBlank(areaTreeInfo)){
            areaTreeResults= JSON.parseArray(areaTreeInfo, AreaTreeResult.class);
        }else{
            areaTreeResults= getTreeList();
            //把区域信息放到redis中
            try {
                CacheUtils.setAreaTreeInfo(EnumConstants.AreaOrCountryRedisKeys.AREA_TREE_KEY.getKey(),
                        JSON.toJSONString(areaTreeResults));
            } catch (Exception e) {
                logger.info("省市区县数据存入redis失败，redis服务器网络有问题或者redis没有启动");
            }
        }
        return areaTreeResults;
    }

    @Override
    public List<AreaJiedaoResult> getJieDaoInfo(ApiAreaJieDaoParam params) {
        String keyName=String.format("%s:%s",EnumConstants.AreaOrCountryRedisKeys.AREA_JIEDAO_KEY.getKey(),params.getLevel());
        String jieDaoInfo = CacheUtils.getAreaTreeInfo(keyName);
        if(StringUtils.isNotBlank(jieDaoInfo)){
          return JSON.parseArray(jieDaoInfo,AreaJiedaoResult.class);
        }else{
            List<AreaDto> jieDaoList = dgAreaDao.queryArea(params.getQuxianCode(), params.getLevel());
            if(jieDaoList!=null&&jieDaoList.size()>0){
                return jieDaoList.stream().map(t -> {
                    return InstanceUtil.to(t, AreaJiedaoResult.class);
                }).collect(Collectors.toCollection(ArrayList::new));
            }
        }
        return new ArrayList<AreaJiedaoResult>();
    }

    /**
     * 获取街道信息
      * @return
     */
   private Map<String, List<AreaTreeResult>> loadJieDaoInfo(){
       //查询所有的街道信息
       List<AreaTreeResult> jiedaoList = getListByCondition(null,4);

       //组装数据
       //街道按区县分组到区县数据
        Map<String, List<AreaTreeResult>> jiedaoMap = getTreeMapByList(jiedaoList);
        for(Entry<String,List<AreaTreeResult>> jiedaoEntry:jiedaoMap.entrySet()){
            String keyName=String.format("%s:%s",EnumConstants.AreaOrCountryRedisKeys.AREA_JIEDAO_KEY.getKey(),jiedaoEntry.getKey());
            CacheUtils.setJieDaoInfo(keyName,JSON.toJSONString(jiedaoEntry.getValue()));
        }
        return jiedaoMap;
   }

    /**
     * 从数据库获取省市区县，并放到redis里面
     * @return
     */
    private List<AreaTreeResult> getTreeList(){
        //一、查询数据
        //1.查询所有的省
        List<AreaTreeResult> provinceList = getListByCondition(null,1);
        //2.查询所有的市
        List<AreaTreeResult> cityList = getListByCondition(null,2);
        //3.查询所有的区
        List<AreaTreeResult> quList = getListByCondition(null,3);
        //4.查询所有的街道信息
        //List<AreaTreeResult> jiedaoList = getListByCondition(null,4);

        //二、组装数据
        //1.街道按区县分组到区县数据
        /*Map<String, List<AreaTreeResult>> jiedaoMap = getTreeMapByList(jiedaoList);
        for(AreaTreeResult quArea:quList){
            quArea.setChildren(jiedaoMap.get(quArea.getAreaCode()));
        }*/

        //2.区县按城市分组到城市数据
        Map<String, List<AreaTreeResult>> quMap=getTreeMapByList(quList);
        for (AreaTreeResult cityArea:cityList){
            cityArea.setChildren(quMap.get(cityArea.getAreaCode()));
        }

        //3.城市按省份分组到省份数据
        Map<String, List<AreaTreeResult>> cityMap = getTreeMapByList(cityList);
        for(AreaTreeResult provinceArea:provinceList){
            provinceArea.setChildren(cityMap.get(provinceArea.getAreaCode()));
        }

        return provinceList;
    }

    /**
     *根据treeList得到分组
     * @param treeList
     * @return
     */
    public Map<String,List<AreaTreeResult>> getTreeMapByList(List<AreaTreeResult> treeList){
        Map<String,List<AreaTreeResult>> groupMap=new LinkedHashMap<String,List<AreaTreeResult>>();
        for(AreaTreeResult areaTreeResult:treeList){
            if(groupMap.containsKey(areaTreeResult.getParentCode())){
                List<AreaTreeResult> areaTreeResults = groupMap.get(areaTreeResult.getParentCode());
                areaTreeResults.add(areaTreeResult);
                groupMap.put(areaTreeResult.getParentCode(),areaTreeResults);
            }else{
                List<AreaTreeResult> areaList = new ArrayList<>();
                areaList.add(areaTreeResult);
                groupMap.put(areaTreeResult.getParentCode(),areaList);
            }
        }
        return groupMap;
    }

    /**
     * 获取区域信息列表
     * @param parentCode
     * @param level
     * @return
     */
    private List<AreaTreeResult> getListByCondition(String parentCode,Integer level){
        List<AreaDto> dgAreas = dgAreaDao.queryArea(parentCode,level);

        List<AreaTreeResult> areaList=dgAreas.stream().map(t->{
            AreaTreeResult areaTreeResult=InstanceUtil.to(t, AreaTreeResult.class);
            return areaTreeResult;
        }).collect(Collectors.toCollection(ArrayList::new));

        return areaList;
    }

}
