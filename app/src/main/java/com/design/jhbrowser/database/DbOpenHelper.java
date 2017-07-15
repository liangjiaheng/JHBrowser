package com.design.jhbrowser.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.design.jhbrowser.database.bean.BookmarkBean;
import com.design.jhbrowser.database.bean.CollectBean;
import com.design.jhbrowser.database.bean.HistoryBean;
import com.design.jhbrowser.database.bean.UserBean;
import com.design.jhbrowser.database.dao.CollectDao;
import com.design.jhbrowser.utils.KYStringUtils;

/**
 * Created by AdamL on 2017/5/18.
 */

public class DbOpenHelper extends SQLiteOpenHelper {

    private final static int VERSION = 2;
    private final static String DATABASENAME = "browser.db";
    public static final String TABLE_NAME = "t_note";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String TIME = "time";
    public static final String ID = "_id";

    public DbOpenHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KYStringUtils.getCreateSQL(HistoryBean.class));
        db.execSQL(KYStringUtils.getCreateSQL(BookmarkBean.class));
        db.execSQL("create table " + TABLE_NAME + " ("
                + ID + " integer primary key autoincrement  ,"
                + CONTENT + " TEXT NOT NULL,"
                + TITLE + " TEXT NOT NULL,"
                + TIME + " TEXT NOT NULL)");
        db.execSQL(KYStringUtils.getCreateSQL(UserBean.class));
        db.execSQL(KYStringUtils.getCreateSQL(CollectBean.class));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //表历史纪录
        db.execSQL(KYStringUtils.getDropSQL(HistoryBean.class));
        db.execSQL(KYStringUtils.getCreateSQL(HistoryBean.class));
        //书签记录
        db.execSQL(KYStringUtils.getDropSQL(BookmarkBean.class));
        db.execSQL(KYStringUtils.getCreateSQL(BookmarkBean.class));
        //表用户记录
        db.execSQL(KYStringUtils.getDropSQL(UserBean.class));
        db.execSQL(KYStringUtils.getCreateSQL(UserBean.class));
        //表收藏记录
        db.execSQL(KYStringUtils.getDropSQL(CollectBean.class));
        db.execSQL(KYStringUtils.getCreateSQL(CollectBean.class));
    }
}
