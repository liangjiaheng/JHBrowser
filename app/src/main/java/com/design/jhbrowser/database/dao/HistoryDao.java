package com.design.jhbrowser.database.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design.jhbrowser.database.DbOpenHelper;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.utils.KYStringUtils;

import java.util.ArrayList;

/**
 * Created by AdamL on 2017/5/18.
 */

public class HistoryDao {

    private Context mContext;
    private SQLiteDatabase db;
    private DbOpenHelper helper;
    private final String tableName;

    public HistoryDao(Context context) {
        this.mContext = context;
        helper = new DbOpenHelper(mContext);
        tableName = KYStringUtils.getTableName(HistoryBean.class);
    }

    public void saveHistory(HistoryBean historyBean) {
        db = helper.getWritableDatabase();
        StringBuffer buffer = new StringBuffer();
        String sql = buffer.append("insert into ")
                .append(tableName)
                .append("(title,url,time,imgUrl) values('")
                .append(historyBean.getTitle())
                .append("','")
                .append(historyBean.getUrl())
                .append("','")
                .append(historyBean.getTime())
                .append("','")
                .append(historyBean.getImgUrl())
                .append("')")
                .toString();
        db.execSQL(sql);
    }


    public ArrayList<HistoryBean> getHistory() {
        ArrayList<HistoryBean> list = null;
        db = helper.getWritableDatabase();
        Cursor cursor = null;
        try {
            list = new ArrayList<>();
            cursor = db.query(tableName, null, null, null, null, null,
                    null);
            while (cursor.moveToNext()) {
                HistoryBean histroy = new HistoryBean();
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String imgUrl = cursor.getString(cursor.getColumnIndex("imgUrl"));
                histroy.setTitle(title);
                histroy.setUrl(url);
                histroy.setTime(time);
                histroy.setImgUrl(imgUrl);
                list.add(histroy);
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

    public int wipeTable() {
        db = helper.getWritableDatabase();
        int wipe = 0;
        try {
            wipe = db.delete(tableName, null, null);
            return wipe;
        } catch (Exception e) {
            wipe = 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return wipe;
    }


    public long deleteHistory(String time) {
        db = helper.getWritableDatabase();
        long delete = 0;
        try {
            String[] times = {time};
            delete = db.delete(tableName, "time=?", times);
        } catch (Exception e) {
            delete = 0;
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return delete;
    }


}
