package com.xmyy.search.service;

import com.xmyy.search.vo.ProductSearchParam;

/**
 * Created by Simon on 2018/7/20.
 */
public interface ProductSearchService {

    long getTotalCount();

    Object search(ProductSearchParam query);

}
