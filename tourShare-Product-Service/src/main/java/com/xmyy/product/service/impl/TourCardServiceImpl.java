package com.xmyy.product.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xmyy.product.dao.DgTourCardDao;
import com.xmyy.product.dto.ApiTourCardDto;
import com.xmyy.product.mapper.DgTourCardMapper;
import com.xmyy.product.model.DgTourCard;
import com.xmyy.product.service.TourCardService;
import com.xmyy.product.vo.ApiTourCardMatchParam;
import com.xmyy.product.vo.ApiTourCardMatchResult;
import com.xmyy.product.vo.ApiTourCardAddParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import top.ibase4j.core.base.BaseServiceImpl;
import top.ibase4j.core.util.InstanceUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行程卡片  服务实现类
 *
 * @author wangzejun
 */
@Service(interfaceClass = TourCardService.class)
@CacheConfig(cacheNames = "DgTourCard")
public class TourCardServiceImpl extends BaseServiceImpl<DgTourCard, DgTourCardMapper> implements TourCardService {

    @Resource
    private DgTourCardMapper dgTourCardMapper;
    @Resource
    private DgTourCardDao dgTourCardDao;
    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    private final String CARD_KEY = "S:iBase4J:tourcard:card_Key:";

    @Override
    @Transactional
    public Object add(ApiTourCardAddParam params, Long createId) {
        DgTourCard dgTourCard = InstanceUtil.to(params, DgTourCard.class);
        return dgTourCardMapper.insert(dgTourCard);
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, String> getTourCardsMapList(ApiTourCardMatchParam params) {
        List<String> cardCodes = params.getCardCodes();
        Map<String, String> cardMap = new HashMap<>();

        //TODO 先从redis中获取？
        StringBuffer key = new StringBuffer();
        key.append("%s").append("_");
        key.append(params.getCardType()).append("_");
        key.append(params.getCardSize()).append("_");
        key.append(params.getDeviceType());
        Serializable defaultCard = redisTemplate.opsForValue().get(String.format(key.toString(), "DEFAULT"));
        if (defaultCard != null) {
            cardMap.put("DEFAULT", (String) defaultCard);
            cardCodes.forEach(t -> {
                Serializable tempCard = redisTemplate.opsForValue().get(String.format(key.toString(), t));
                if (tempCard != null) {
                    cardMap.put(t, (String) tempCard);
                } else {
                    cardMap.put(t, (String) defaultCard);
                }
            });
        }

        //从数据库获取行程卡片
        if (cardMap.size() == 0) {
            cardCodes.add("DEFAULT");
            List<ApiTourCardMatchResult> cardList = dgTourCardDao.getTourCardsMapList(params);
            for (ApiTourCardMatchResult result : cardList) {
                //目前相同参数下卡片有多张的情况下，暂时只取第一张
                if (!cardMap.containsKey(result.getCardCode()) && StringUtils.isNotBlank(result.getImgUrl())) {
                    cardMap.put(result.getCardCode(), result.getImgUrl());
                }
            }
            //如果cardCode没有查询到卡片，则使用默认卡片
            cardCodes.forEach(t -> {
                if (!cardMap.containsKey(t)) {
                    cardMap.put(t, cardMap.get("DEFAULT"));
                }
            });
        }
        return cardMap;
    }


    @Override
    public void loadRedisForTourCard() {
        List<ApiTourCardDto> cardList = dgTourCardDao.getAllList();
        if (cardList != null && cardList.size() > 0) {
            cardList.forEach(t -> {
                String key = t.getCardCode() + "_" +
                        t.getSkinType() + "_" +
                        t.getCardType() + "_" +
                        t.getCardSize() + "_" +
                        t.getDeviceType();
                redisTemplate.opsForValue().set(CARD_KEY + key, t.getImgUrl());
            });
        }
    }
}
