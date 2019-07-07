/**
 * 
 */
package top.ibase4j.model;

import java.io.Serializable;

/**
 * 
 * @author ShenHuaJie
 * @version 2017年12月5日 下午3:38:42
 */
@SuppressWarnings("serial")
public class FileInfo implements Serializable {
	private String orgName;
	private String fileType;
	private String fileName;
	private Long fileSize;

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}
