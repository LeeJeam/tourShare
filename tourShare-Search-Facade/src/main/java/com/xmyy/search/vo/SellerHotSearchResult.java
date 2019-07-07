package com.xmyy.search.vo;

import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

@ApiModel(value = "热词搜索结果")
public class SellerHotSearchResult implements Serializable {

	private List<String> hostWords;

	public List<String> getHostWords() {
		return hostWords;
	}

	public void setHostWords(List<String> hostWords) {
		this.hostWords = hostWords;
	}
}
