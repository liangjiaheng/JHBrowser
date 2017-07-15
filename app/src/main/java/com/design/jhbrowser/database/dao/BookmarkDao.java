package com.design.jhbrowser.database.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design.jhbrowser.database.DbOpenHelper;
import com.design.jhbrowser.database.bean.BookmarkBean;
import com.design.jhbrowser.utils.KYStringUtils;
import com.design.jhbrowser.utils.LogUtils;

import java.util.ArrayList;

/**
 * Created by AdamL on 2017/5/18.
 */

public class BookmarkDao {
    private Context mContext;
    private SQLiteDatabase db;
    private DbOpenHelper helper;
    private final String tableName;

    public BookmarkDao(Context context) {
        this.mContext = context;
        helper = new DbOpenHelper(mContext);
        tableName = KYStringUtils.getTableName(BookmarkBean.class);
    }

    /**
     * 保存历史记录
     *
     * @param bookmarkBean
     * @return
     */
    public long saveBookmark(BookmarkBean bookmarkBean) {
        db = helper.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put("title", bookmarkBean.getTitle());
            cv.put("url", bookmarkBean.getUrl());
            cv.put("time", bookmarkBean.getTime());

            long a = db.insert(tableName, null, cv);
            return a;
        } catch (Exception e) {
            LogUtils.e("error", "error");
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return 0;
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

    /**
     * 得到数据集合
     *
     * @return
     */
    public ArrayList<BookmarkBean> getBookmark() {
        db = helper.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<BookmarkBean> list = null;
        try {
            cursor = db.query(tableName, null, null, null, null, null, null);
            list = new ArrayList<>();
            while (cursor.moveToNext()) {
                BookmarkBean bookmark = new BookmarkBean();
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String url = cursor.getString(cursor.getColumnIndex("url"));
                String time = cursor.getString(cursor.getColumnIndex("time"));

                bookmark.setTitle(title);
                bookmark.setUrl(url);
                bookmark.setTime(time);
                list.add(bookmark);
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

    /**
     * 清空表数据
     *
     * @return
     */
    public int wipeData() {
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

    /**
     * 删除单条数据
     *
     * @param time
     * @return
     */
    public long deleteBookmark(String time) {
        db = helper.getWritableDatabase();
        long delete = 0;
        try {
            String[] times = {time};
            delete = db.delete(tableName, "time=?", times);
            return delete;
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
