package com.xmyy.livevideo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LiveVideoFileListResult {

    @JsonProperty(value = "vid")
    private String vid;

    @JsonProperty(value = "start_time")
    private String start_time;

    @JsonProperty(value = "end_time")
    private String end_time;

    @JsonProperty(value = "file_id")
    private String file_id;

    @JsonProperty(value = "record_file_url")
    private String record_file_url;

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getFile_id() {
        return file_id;
    }

    public void setFile_id(String file_id) {
        this.file_id = file_id;
    }

    public String getRecord_file_url() {
        return record_file_url;
    }

    public void setRecord_file_url(String record_file_url) {
        this.record_file_url = record_file_url;
    }
}
