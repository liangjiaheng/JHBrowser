package com.design.jhbrowser.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design.jhbrowser.database.DbOpenHelper;
import com.design.jhbrowser.database.bean.CollectBean;
import com.design.jhbrowser.utils.KYStringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AdamL on 2017/5/30.
 */

public class CollectDao {

    private Context mContext;
    private SQLiteDatabase db;
    private DbOpenHelper helper;
    private String tableName;

    public CollectDao(Context context) {
        this.mContext = context;
        helper = new DbOpenHelper(mContext);
        tableName = KYStringUtils.getTableName(CollectBean.class);
    }

    /**
     * 得到数据Cursor游标
     *
     * @return
     */
    public Cursor getCursor() {
        db = helper.getWritableDatabase();
        Cursor cursor;
        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
            return cursor;
        } catch (Exception e) {
            cursor = null;
        }
        return cursor;
    }

    public long saveCollect(CollectBean collectBean) {
        db = helper.getWritableDatabase();
        long num = 0;
        try {
            ContentValues cv = new ContentValues();
            cv.put("title", collectBean.getTitle());
            cv.put("url", collectBean.getUrl());
            cv.put("time", collectBean.getTime());

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

    public List<CollectBean> getCollect() {
        db = helper.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<CollectBean> list = null;
        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                CollectBean collectBean = new CollectBean();
                collectBean.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                collectBean.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                collectBean.setTime(cursor.getString(cursor.getColumnIndex("time")));
                list.add(collectBean);
            }
        } catch (Exception e) {
            return list;
        } finally {
            cursor.close();
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

    public int deleteAllCollect() {
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

    public long deleteItemCollect(String time) {
        db = helper.getWritableDatabase();
        long num = 0;
        try {
            String[] times = {time};
            num = db.delete(tableName, "time=?", times);
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
