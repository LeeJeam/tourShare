package com.xmyy.livevideo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class LiveVideoFileListOutput {

    @JsonProperty(value = "all_count")
    private int all_count;

    private ArrayList<LiveVideoFileListResult> file_list;

    public int getAll_count() {
        return all_count;
    }

    public void setAll_count(int all_count) {
        this.all_count = all_count;
    }
    @JsonIgnore
    public ArrayList<LiveVideoFileListResult> getFile_list() {
        return file_list;
    }

    public void setFile_list(ArrayList<LiveVideoFileListResult> file_list) {
        this.file_list = file_list;
    }
}
