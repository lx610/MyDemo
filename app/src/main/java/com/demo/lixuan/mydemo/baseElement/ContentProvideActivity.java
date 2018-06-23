package com.demo.lixuan.mydemo.baseElement;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.lixuan.mydemo.R;
import com.demo.lixuan.mydemo.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类名： ContentProvideActivity
 * 说明：
 * <p>
 * 修改记录：
 * <p>
 * 版 权 所 有:   Copyright  2018
 * 公       司:   深圳市旅联网络科技有限公司
 * version   2.0
 * date   2018/4/20
 * author lixuan
 * Created by elk-lx on 2018/4/20.
 */

public class ContentProvideActivity extends BaseActivity {
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    private MyDatabase mDbHelper;

    @Override
    public int getLayoutResId() {
        return R.layout.activity_only_linearlayout;
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        mLlContainer.addView(generateTextButton("create db", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDbHelper.getWritableDatabase();
            }
        }));

        mLlContainer.addView(generateTextButton("add data", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","The Da Vinic Code");
                values.put("author","Dan down");
                values.put("pages","454");
                values.put("price","16.96");

                db.insert("Book",null,values);

                values.clear();
                //装载第二条数据
                values.put("name","The Lost Symbol");
                values.put("author","Dan down");
                values.put("pages","510");
                values.put("price","19.6");
                db.insert("Book",null,values);
            }
        }));

        mLlContainer.addView(generateTextButton("updata", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price",10.99);
                db.update("Book",values,"name = ?" , new String[]{ "The Da vinci " });
            }
        }));

        mLlContainer.addView(generateTextButton("query", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price =cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("SQLiteDatabase query", "onClick: name=============== " + name);
                        Log.d("SQLiteDatabase query", "onClick: author=============== " + author);
                        Log.d("SQLiteDatabase query", "onClick: pages=============== " + pages);
                        Log.d("SQLiteDatabase query", "onClick: price=============== " + price);
                    }while (cursor.moveToNext());
                    cursor.close();
                }
            }
        }));

        mLlContainer.addView(generateTextButton("delete", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.delete("Book","pages > ?", new String[]{"500"});//删除页码超过500页的书籍
            }
        }));

        mLlContainer.addView(generateTextButton("ContentResolver", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri=Uri.parse("content://com.demo.lixuan.mydemo//Book");
               Cursor cursor= getContentResolver().query(uri,null,null,null,null);
                if (cursor !=null){
                 while (cursor.moveToNext()){
                     String name = cursor.getString(cursor.getColumnIndex("name"));
                     String author=cursor.getString(cursor.getColumnIndex("author"));
                     int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                     double price =cursor.getDouble(cursor.getColumnIndex("price"));

                     Log.d("ContentResolver query", "onClick: name=============== " + name);
                     Log.d("ContentResolver query", "onClick: author=============== " + author);
                     Log.d("ContentResolver query", "onClick: pages=============== " + pages);
                     Log.d("ContentResolver query", "onClick: price=============== " + price);
                 }
                    cursor.close();
                }
            }
        }));

        mLlContainer.addView(generateTextButton("queryData by contentResoler", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("content://com.demo.lixuan.mydemo.baseElement.provider/book");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                if (cursor != null){
                    while (cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author  = cursor.getString(cursor.getColumnIndex("author"));
                        String pages  = cursor.getString(cursor.getColumnIndex("pages"));
                        String price  = cursor.getString(cursor.getColumnIndex("price"));

                        Log.d("contentResoler query", "onClick: name=============== " + name);
                        Log.d("contentResoler query", "onClick: author=============== " + author);
                        Log.d("contentResoler query", "onClick: pages=============== " + pages);
                        Log.d("contentResoler query", "onClick: price=============== " + price);
                    }

                    cursor.close();
                }
            }
        }));
    }

    @Override
    public void initData() {
        mDbHelper = new MyDatabase(this, "BookStore.db", null, 1);
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
