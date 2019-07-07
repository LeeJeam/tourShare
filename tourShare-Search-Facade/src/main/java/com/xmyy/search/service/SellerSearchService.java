package com.xmyy.search.service;

import com.xmyy.search.vo.HotSearchParam;
import com.xmyy.search.vo.SellerHotSearchResult;
import com.xmyy.search.vo.SellerPageParam;


public interface SellerSearchService {
    public Object search(SellerPageParam query);
    public SellerHotSearchResult findHotSearchList(HotSearchParam query);
	
}