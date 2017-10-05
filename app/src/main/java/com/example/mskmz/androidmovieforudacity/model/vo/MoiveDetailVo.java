package com.example.mskmz.androidmovieforudacity.model.vo;

import java.util.List;

/**
 * Created by wangzekang on 2017/10/4.
 */

public class MoiveDetailVo {
    private String overview;
    private String id;
    private String backdrop_path;
    private String original_title;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }
}
