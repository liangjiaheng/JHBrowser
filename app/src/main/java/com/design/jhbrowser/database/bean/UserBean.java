package com.design.jhbrowser.database.bean;

import java.io.Serializable;

/**
 * Created by AdamL on 2017/5/28.
 */

public class UserBean implements Serializable {

    private String username;
    private String password;
    private String telphone;
    private String age;
    private String imghead;
    private String signature;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getImghead() {
        return imghead;
    }

    public void setImghead(String imghead) {
        this.imghead = imghead;
    }
}
