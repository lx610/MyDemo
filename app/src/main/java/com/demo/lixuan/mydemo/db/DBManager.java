package com.demo.lixuan.mydemo.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 类 名: DBManager
 * 说 明:
 * 修 改 记 录:
 * 版 权 所 有:   Copyright  2016
 * 公       司:   深圳市旅联网络科技有限公司
 * version   0.1
 * date   2018/1/24
 * author lixuan
 */

public class DBManager extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "BOOKS.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "books_table";
    public final static String BOOK_ID = "book_id";
    public final static String BOOK_NAME = "book_name";
    public final static String BOOK_AUTHOR = "book_author";

    public DBManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" + BOOK_ID
                + " INTEGER primary key autoincrement, " + BOOK_NAME + " text, "+ BOOK_AUTHOR +" text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db
                .query(TABLE_NAME, null, null, null, null, null, null);

        return cursor;


    }

    //增加操作
    public long insert(String bookname,String author)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        /* ContentValues */
        ContentValues cv = new ContentValues();
        cv.put(BOOK_NAME, bookname);
        cv.put(BOOK_AUTHOR, author);
        long row = db.insert(TABLE_NAME, null, cv);
        return row;
    }

    //删除操作
    public void delete(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = BOOK_ID + " = ?";
        String[] whereValue ={ Integer.toString(id) };
        db.delete(TABLE_NAME, where, whereValue);
    }

}
