package com.design.jhbrowser.database.bean;

import java.io.Serializable;

/**
 * Created by AdamL on 2017/5/18.
 */

public class HistoryBean implements Serializable {

    private String title;
    private String url;
    private String time;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
