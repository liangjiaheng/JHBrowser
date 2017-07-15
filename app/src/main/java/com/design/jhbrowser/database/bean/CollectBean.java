package com.design.jhbrowser.database.bean;

import java.io.Serializable;

/**
 * Created by AdamL on 2017/5/30.
 */

public class CollectBean implements Serializable {

    private String title;
    private String url;
    private String time;

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
}
