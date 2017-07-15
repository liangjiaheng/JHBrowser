package com.design.jhbrowser.dialogmenu.bean;

import java.io.Serializable;

/**
 * Created by AdamL on 2017/5/13.
 */

public class DialogBean implements Serializable {
    private int imgId;
    private String menuName;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
}
