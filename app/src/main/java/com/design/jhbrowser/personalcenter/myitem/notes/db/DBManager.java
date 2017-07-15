package com.design.jhbrowser.personalcenter.myitem.notes.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.design.jhbrowser.database.DbOpenHelper;
import com.design.jhbrowser.personalcenter.myitem.notes.model.Note;

import java.util.List;

/**
 * Created by AdamL on 2017/5/30.
 */

public class DBManager {
    private Context context;
    private DbOpenHelper helper;
    private SQLiteDatabase dbReader;
    private SQLiteDatabase dbWriter;
    private static DBManager instance;

    public DBManager(Context context) {
        this.context = context;
        helper = new DbOpenHelper(context);
        //创建一个打开或者读取的数据库
        dbReader = helper.getReadableDatabase();
        dbWriter = helper.getWritableDatabase();
    }

    /**
     * 添加单例模式
     *
     * @param context
     * @return
     */
    public static synchronized DBManager getInstance(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
        }
        return instance;
    }

    /**
     * 添加到数据库
     *
     * @param title
     * @param content
     * @param time
     */
    public void addToDB(String title, String content, String time) {
        ContentValues cv = new ContentValues();
        cv.put(DbOpenHelper.TITLE, title);
        cv.put(DbOpenHelper.CONTENT, content);
        cv.put(DbOpenHelper.TIME, time);
        dbWriter.insert(DbOpenHelper.TABLE_NAME, null, cv);
    }


    //  读取数据
    public void readFromDB(List<Note> noteList) {
        Cursor cursor = dbReader.query(DbOpenHelper.TABLE_NAME, null, null, null, null, null, null);
        try {
            while (cursor.moveToNext()) {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.ID)));
                note.setTitle(cursor.getString(cursor.getColumnIndex(DbOpenHelper.TITLE)));
                note.setContent(cursor.getString(cursor.getColumnIndex(DbOpenHelper.CONTENT)));
                note.setTime(cursor.getString(cursor.getColumnIndex(DbOpenHelper.TIME)));
                noteList.add(note);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //  更新数据
    public void updateNote(int noteID, String title, String content, String time) {
        ContentValues cv = new ContentValues();
        cv.put(DbOpenHelper.ID, noteID);
        cv.put(DbOpenHelper.TITLE, title);
        cv.put(DbOpenHelper.CONTENT, content);
        cv.put(DbOpenHelper.TIME, time);
        dbWriter.update(DbOpenHelper.TABLE_NAME, cv, "_id = ?", new String[]{noteID + ""});
    }

    //  删除数据
    public void deleteNote(int noteID) {
        dbWriter.delete(DbOpenHelper.TABLE_NAME, "_id = ?", new String[]{noteID + ""});
    }

    // 根据id查询数据
    public Note readData(int noteID) {
        Cursor cursor = dbReader.rawQuery("SELECT * FROM t_note WHERE _id = ?", new String[]{noteID + ""});
        cursor.moveToFirst();
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndex(DbOpenHelper.ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(DbOpenHelper.TITLE)));
        note.setContent(cursor.getString(cursor.getColumnIndex(DbOpenHelper.CONTENT)));
        return note;
    }


}
