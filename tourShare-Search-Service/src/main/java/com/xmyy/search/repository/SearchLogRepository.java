package com.xmyy.search.repository;

import com.xmyy.search.vo.SearchLogDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SearchLogRepository extends ElasticsearchRepository<SearchLogDoc, String>{

}
