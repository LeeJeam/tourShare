package com.xmyy.search.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.Serializable;

@Component
public class EsConfig implements Serializable{

	private static final long serialVersionUID = -6737586213402111829L;
	@Value("${es.indexName.sellerdata}")
	private String sellerdataIndexName;

	@Value("${es.indexName.logdata}")
	private String logdataIndexName;

	@Value("${es.indexName.productdata}")
	private String productIndexName;

	@Value("${es.indexName.packerdata}")
	private String packerdataIndexName;

	@Value("${es.indexName.circledata}")
	private String circledataIndexName;

	@PostConstruct
	public void checkConfig(){
		Assert.hasText(sellerdataIndexName,"sellerdataIndexName");
		Assert.hasText(logdataIndexName,"logdataIndexName");
		Assert.hasText(productIndexName,"yy_productdata");
		Assert.hasText(packerdataIndexName,"yy_packerdata");
		Assert.hasText(circledataIndexName,"yy_circledata");
	}

	public String getLogdataIndexName() {
		return logdataIndexName;
	}

	public void setLogdataIndexName(String logdataIndexName) {
		this.logdataIndexName = logdataIndexName;
	}

	public String getSellerdataIndexName() {
		return sellerdataIndexName;
	}

	public void setSellerdataIndexName(String sellerdataIndexName) {
		this.sellerdataIndexName = sellerdataIndexName;
	}

	public String getProductIndexName() {
		return productIndexName;
	}

	public void setProductIndexName(String productIndexName) {
		this.productIndexName = productIndexName;
	}

	public String getPackerdataIndexName() {
		return packerdataIndexName;
	}

	public void setPackerdataIndexName(String packerdataIndexName) {
		this.packerdataIndexName = packerdataIndexName;
	}

	public String getCircledataIndexName() {
		return circledataIndexName;
	}

	public void setCircledataIndexName(String circledataIndexName) {
		this.circledataIndexName = circledataIndexName;
	}
}
