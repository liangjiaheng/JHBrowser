package com.design.jhbrowser.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design.jhbrowser.database.DbOpenHelper;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.database.bean.UserBean;
import com.design.jhbrowser.utils.KYStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/30.
 */

public class UserDao {

    private Context mContext;
    private SQLiteDatabase db;
    private DbOpenHelper helper;
    private String tableName;

    public UserDao(Context context) {
        this.mContext = context;
        helper = new DbOpenHelper(mContext);
        tableName = KYStringUtils.getTableName(UserBean.class);
    }

    /**
     * 保存用户信息
     *
     * @param userBean
     * @return
     */
    public long saveUserData(UserBean userBean) {
        db = helper.getWritableDatabase();
        long num = 0;
        try {
            ContentValues cv = new ContentValues();
            cv.put("username", userBean.getUsername());
            cv.put("password", userBean.getPassword());
            cv.put("telphone", userBean.getTelphone());
            cv.put("age", userBean.getAge());
            cv.put("imghead", userBean.getImghead());
            cv.put("signature", userBean.getSignature());
            num = db.insert(tableName, null, cv);
        } catch (Exception e) {
            num = 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return num;
    }


    public List<UserBean> getUserInfo() {
        db = helper.getWritableDatabase();
        List<UserBean> user = null;
        Cursor cursor = null;
        try {
            user = new ArrayList<>();
            cursor = db.query(tableName, null, null, null, null, null,
                    null);
            while (cursor.moveToNext()) {
                UserBean userBean = new UserBean();
                userBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userBean.setTelphone(cursor.getString(cursor.getColumnIndex("telphone")));
                userBean.setImghead(cursor.getString(cursor.getColumnIndex("imghead")));
                userBean.setAge(cursor.getString(cursor.getColumnIndex("age")));
                userBean.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
                user.add(userBean);
            }
        } catch (Exception e) {
            return user;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return user;
    }


    public List<UserBean> getUserLoginState(String username) {
        db = helper.getWritableDatabase();
        List<UserBean> user = null;
        Cursor cursor = null;
        try {
            user = new ArrayList<>();
            String[] usernames = {username};
            cursor = db.query(tableName, null, "username=?", usernames, null, null,
                    null);
            while (cursor.moveToNext()) {
                UserBean userBean = new UserBean();
                userBean.setUsername(cursor.getString(cursor.getColumnIndex("username")));
                userBean.setPassword(cursor.getString(cursor.getColumnIndex("password")));
                userBean.setTelphone(cursor.getString(cursor.getColumnIndex("telphone")));
                userBean.setImghead(cursor.getString(cursor.getColumnIndex("imghead")));
                userBean.setAge(cursor.getString(cursor.getColumnIndex("age")));
                userBean.setSignature(cursor.getString(cursor.getColumnIndex("signature")));
                user.add(userBean);
            }
        } catch (Exception e) {
            return user;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return user;
    }

    public int updateUserData(UserBean userBean) {
        db = helper.getWritableDatabase();
        int num = 0;
        try {
            ContentValues cv = new ContentValues();
            cv.put("username", userBean.getUsername());
            cv.put("password", userBean.getPassword());
            cv.put("telphone", userBean.getTelphone());
            cv.put("age", userBean.getAge());
            cv.put("imghead", userBean.getImghead());
            cv.put("signature", userBean.getSignature());
            num = db.update(tableName, cv, null, null);
        } catch (Exception e) {
            num = 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return num;
    }

    public int deleteUser() {
        db = helper.getWritableDatabase();
        int num = 0;
        try {
            num = db.delete(tableName, null, null);
        } catch (Exception e) {
            num = 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return num;
    }

}
