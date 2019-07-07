package com.xmyy.search.repository;

import com.xmyy.search.vo.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by Simon on 2018/7/20.
 */
public interface ProductRepository extends ElasticsearchRepository<ProductDoc,Long> {
}
