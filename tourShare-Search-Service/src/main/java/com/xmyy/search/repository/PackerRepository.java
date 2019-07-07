package com.xmyy.search.repository;

import com.xmyy.search.vo.PackerDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PackerRepository extends ElasticsearchRepository<PackerDoc, Long>{

}
