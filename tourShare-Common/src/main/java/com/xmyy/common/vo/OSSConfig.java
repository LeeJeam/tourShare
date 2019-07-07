package com.xmyy.common.vo;

import com.xmyy.common.util.PropertiesUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName: OSSConfig
 * @Description: OSS配置类
 * @author AggerChen
 * @date 2016年11月4日 下午3:58:36
 */
public class OSSConfig {
	private String endpoint;               //连接区域地址
	private String accessKeyId;            //连接keyId
	private String accessKeySecret;        //连接秘钥
	private String bucketName;             //需要存储的bucketName
	private String imagesSaveLocation;     //图片保存路径
	private String filesSaveLocation;		//文件保存路径
	private String pemessionImageLocation;   //图片有权限上传的目录
	private String pemessionFileLocation;    //文件有权限上传的目录

	public OSSConfig() {
		try {
			Map<String, String> configMap = PropertiesUtils.getMapByPath("config/ali-oss.properties");
			this.endpoint = configMap.get("endpoint");
			this.bucketName = configMap.get("bucketName");
			this.accessKeyId = configMap.get("accessKeyId");
			this.accessKeySecret = configMap.get("accessKeySecret");
			this.imagesSaveLocation=configMap.get("imagesSaveLocation");
			this.filesSaveLocation=configMap.get("filesSaveLocation");
			this.pemessionFileLocation=configMap.get("pemessionFileLocation");
			this.pemessionImageLocation=configMap.get("pemessionImageLocation");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getAccessKeyId() {
		return accessKeyId;
	}

	public void setAccessKeyId(String accessKeyId) {
		this.accessKeyId = accessKeyId;
	}

	public String getAccessKeySecret() {
		return accessKeySecret;
	}

	public void setAccessKeySecret(String accessKeySecret) {
		this.accessKeySecret = accessKeySecret;
	}

	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getImagesSaveLocation() {
		return imagesSaveLocation;
	}

	public void setImagesSaveLocation(String imagesSaveLocation) {
		this.imagesSaveLocation = imagesSaveLocation;
	}

	public String getFilesSaveLocation() {
		return filesSaveLocation;
	}

	public void setFilesSaveLocation(String filesSaveLocation) {
		this.filesSaveLocation = filesSaveLocation;
	}

	public static void main(String[] args) {
		new OSSConfig();
	}

	public String getPemessionImageLocation() {
		return pemessionImageLocation;
	}

	public void setPemessionImageLocation(String pemessionImageLocation) {
		this.pemessionImageLocation = pemessionImageLocation;
	}

	public String getPemessionFileLocation() {
		return pemessionFileLocation;
	}

	public void setPemessionFileLocation(String pemessionFileLocation) {
		this.pemessionFileLocation = pemessionFileLocation;
	}
}
