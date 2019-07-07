package com.xmyy.search.repository;

import com.xmyy.search.vo.CircleDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CircleRepository extends ElasticsearchRepository<CircleDoc, Long>{

}
