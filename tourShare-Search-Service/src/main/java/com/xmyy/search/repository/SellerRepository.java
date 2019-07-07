package com.xmyy.search.repository;

import com.xmyy.search.vo.SellerDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 买手ES数据操作层
 */
public interface SellerRepository extends ElasticsearchRepository<SellerDoc, Long>{

}
