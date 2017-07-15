package com.design.jhbrowser.fragment.bean;

import java.io.Serializable;

/**
 * Created by AdamL on 2017/5/8.
 */

public class RecyclerBean implements Serializable {

    private int imgId;
    private String tvtitle;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getTvtitle() {
        return tvtitle;
    }

    public void setTvtitle(String tvtitle) {
        this.tvtitle = tvtitle;
    }
}
