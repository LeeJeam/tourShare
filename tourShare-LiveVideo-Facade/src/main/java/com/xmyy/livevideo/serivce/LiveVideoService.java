package com.xmyy.livevideo.serivce;

import com.xmyy.livevideo.model.VdLiveVideo;
import com.xmyy.livevideo.vo.LiveVideoCurrentTopParam;
import com.xmyy.livevideo.vo.LiveVideoManagePageParam;
import com.xmyy.livevideo.vo.LiveVideoManageCancelTopParam;
import com.xmyy.livevideo.vo.LiveVideoMangerTopParam;
import top.ibase4j.core.base.BaseService;

/**
 * 直播  服务接口
 *
 * @author AnCheng
 */
public interface LiveVideoService extends BaseService<VdLiveVideo> {

   /**
    * 查询直播列表
    * @param params
    * @return
    */
   Object queryLiveVideoList(LiveVideoManagePageParam params);

   /**
    * 买家端根据国家展示直播列表
    * @param countryCode
    * @return
    */
   Object buyerLiveVideoList(String countryCode,Integer current,Integer size);

   /**
    * 根据行程ID查询直播列表
    * @param tourId
    * @return
    */
   Object buyerByTourId(String tourId);

   /**
    * 查询直播详情统计商品点击数
    * @param roomId
    * @return
    */
   Object queryLiveVideoByRoomId(String roomId);

   /**
    * 查询前五条置顶直播
    * @param params
    * @return
    */
   Object queryCurrentTopList(LiveVideoCurrentTopParam params);

   /**
    * 直播置顶
    * @param params
    * @return
    */
   Object setTop(LiveVideoMangerTopParam params);

   /**
    * 取消置顶
    * @param params
    * @return
    */
   Object  cancelTop(LiveVideoManageCancelTopParam params);

}