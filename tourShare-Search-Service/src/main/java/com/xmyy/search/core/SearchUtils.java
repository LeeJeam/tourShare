package com.xmyy.search.core;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import top.ibase4j.core.support.Pagination;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class SearchUtils implements Serializable{
	private static final long serialVersionUID = 6289661499619569646L;
	final private static Logger logger = LoggerFactory.getLogger(SearchUtils.class);

	public static final String TYPE_SELLER = "seller";
	public static final String TYPE_SEARCH_LOG = "searchLog";
	public static final String TYPE_PRODUCT = "product";
	public static final String TYPE_PACKER = "packer";
	public static final String TYPE_CIRCLE = "circle";

	public static class EsNode {
		final private String host;
		final private String port;
		EsNode(String host, String port) {
			super();
			this.host = host;
			this.port = port;
		}
		public String getHost() {
			return host;
		}
		public String getPort() {
			return port;
		}
		@Override
		public String toString() {
			return "EsNode [host=" + host + ", port=" + port + "]";
		}

	}

	public static List<EsNode> parseEsNodes(String nodestr){
		String[] nodes = StringUtils.split(nodestr, ",");
		return Stream.of(nodes).map(str->{
			String[] hostAndPort = StringUtils.split(str, ":");
			return new EsNode(hostAndPort[0], hostAndPort.length>1?hostAndPort[1]:"9200");
		}).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	public static Map<Object, Object> readMapping(String path){
//		System.out.println("read mapping file: " + path);
		ClassPathResource resource = new ClassPathResource(path);
		try {
			Map<Object, Object> data = JsonMapper.ignoreNull().fromJson(resource.getInputStream(), Map.class);
			return data;
		} catch (IOException e) {
			throw new RuntimeException("read mapping error.", e);
		}
	}

	public static boolean isNullOrBlankString(Object value){
		return value==null || (String.class.isInstance(value) && org.apache.commons.lang3.StringUtils.isBlank(value.toString()));
	}

	public static <R, T> Pagination<R> mapToYooyoPage(org.springframework.data.domain.Page<T> page, Function<T, R> mapper){
		Pagination<R> result = new Pagination<>();
		result.setTotal((int)page.getTotalElements());

		if(page.getContent()!=null){
			List<R> resultList = page.getContent().stream().map(mapper).collect(Collectors.toList());
			result.setRecords(resultList);
		}

		return result;
	}

	public static List<String> splitToList(String strs, String op){
		if(StringUtils.isBlank(strs)){
			return ImmutableList.of();
		}
		return Splitter.on(op).omitEmptyStrings().trimResults().splitToList(strs);
	}

	public static Set<String> toSet(String str){
		List<String> list = SearchUtils.splitToList(str, ",");
		return Sets.newHashSet(list);
	}

	private SearchUtils(){}

}
