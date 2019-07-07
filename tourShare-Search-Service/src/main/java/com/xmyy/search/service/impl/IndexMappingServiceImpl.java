package com.xmyy.search.service.impl;

import com.google.common.collect.Maps;
import com.xmyy.search.core.SearchUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import top.ibase4j.core.util.JsonUtil;
import top.ibase4j.core.util.PropertiesUtil;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

@Service
public class IndexMappingServiceImpl implements Serializable {
    private static final long serialVersionUID = 695894632134170900L;
    private static Logger logger = LoggerFactory.getLogger(IndexMappingServiceImpl.class);

    @Resource
    private RestTemplate restTemplate;

    //@Value("${es.cluster.nodes}")
    private String esNodes = PropertiesUtil.getString("es.cluster.nodes");

    private Map<String, String> indexFileMapping = Maps.newHashMap();


    public IndexMappingServiceImpl map(String indexName, String mappingFilePath) {
        this.indexFileMapping.put(indexName, mappingFilePath);
        return this;
    }

    public boolean deleteType(String indexName, String typeName) {
        return doWithEsNode(node -> {
            String url = buildTypeUrl(node, indexName, typeName);
            deleteByUrl(url);
            return true;
        });
    }

    public boolean deleteIndex(String indexName) {
        return doWithEsNode(node -> {
            String url = buildIndexUrl(node, indexName);
            deleteByUrl(url);
            return true;
        });
    }

    public boolean rebuildIndex(String indexName) {
        return doWithEsNode(node -> {
            return rebuildIndex(node, indexName);
        });
    }

    public boolean doWithEsNode(Function<SearchUtils.EsNode, Boolean> action) {
        Assert.hasText(esNodes, "esNodes不能为空");
        List<SearchUtils.EsNode> nodes = SearchUtils.parseEsNodes(esNodes);
        return nodes.stream().anyMatch(node -> {
            try {
                return action.apply(node);
            } catch (Exception e) {
                logger.error("execute by[" + node.getHost() + "] error:", e);
                return false;
            }
        });
    }

    private String buildIndexUrl(SearchUtils.EsNode node, String indexName) {
        String url = "http://%s:9200/%s";
        url = String.format(url, node.getHost(), indexName);
        return url;
    }

    private String buildTypeUrl(SearchUtils.EsNode node, String indexName, String typeName) {
        String url = "http://%s:9200/%s/%s";
        url = String.format(url, node.getHost(), indexName, typeName);
        return url;
    }

    private boolean deleteByUrl(String indexUrl) {
        try {
            logger.info("delete : {}", indexUrl);
            this.restTemplate.delete(indexUrl);
            return true;
        } catch (Exception e) {
            logger.error("delete error, indexUrl: " + indexUrl, e);
            return false;
        }
    }


    public boolean rebuildIndex(SearchUtils.EsNode node, String indexName) {
        Assert.notNull(node, "node 不能为空");

        String url = buildIndexUrl(node, indexName);
        this.deleteByUrl(url);

        Map<Object, Object> setttings = null;//readIndexMapping(indexName);

        logger.info("create index with mappting: {}", JsonUtil.toJson(setttings));

        try {
            this.restTemplate.put(url, setttings);
        } catch (RestClientException e) {
            logger.error("rebuildIndex:" + e.getMessage());
            return false;
        }
        return true;
    }

    protected Map<Object, Object> readIndexMapping(String indexName) {
        String path = this.indexFileMapping.get(indexName);
        if (StringUtils.isBlank(path)) {
            path = indexName;
        }
        return SearchUtils.readMapping("mapping/" + path + ".mapping.json");
    }


}
