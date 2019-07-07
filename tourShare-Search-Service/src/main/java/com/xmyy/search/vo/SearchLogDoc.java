package com.xmyy.search.vo;

import com.xmyy.search.core.SearchUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.Date;

@Document(indexName = "#{esConfig.logdataIndexName}",type = SearchUtils.TYPE_SEARCH_LOG)
@Setting(settingPath = "/mapping/yy_mapping.json")
public class SearchLogDoc {
	
	@Id
	private String id;

	//	@Field(type = FieldType.keyword,fielddata = true)
	//@CompletionField(analyzer="fulltextAnalyzerMax", searchAnalyzer="fulltextAnalyzerMax")
	//https://stackoverflow.com/questions/36238978/spring-data-elasticsearch-registering-custom-analyser
	@Field(type = FieldType.keyword,fielddata = true,analyzer="fulltextAnalyzerMax", searchAnalyzer="fulltextAnalyzerMax")
	@CompletionField(analyzer="fulltextAnalyzerMax", searchAnalyzer="fulltextAnalyzerMax")
	private String keyword;
	private String searchword;
	private String city;
	private Date createAt;
	private String type;//member,product,cricle

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getSearchword() {
		return searchword;
	}
	public void setSearchword(String searchword) {
		this.searchword = searchword;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
