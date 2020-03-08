package com.demo.lixuan.mydemo.baseElement;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {
    public static final int BOOK_DIR = 0;
    public static final int BOKK_ITEM = 1;
    public static final int CATEGORY_DIR = 2;
    public static final int CATEGORY_ITEM = 3;

    public static final String AUTHORITY =  "com.demo.lixuan.mydemo.baseElement.provider";
    private static UriMatcher uriMatcher;
    private MyDatabase dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"book/#",BOOK_DIR);
        uriMatcher.addURI(AUTHORITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHORITY,"categroy/#",CATEGORY_ITEM);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabase(getContext(),"BookStore.db",null,1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                cursor = db.query("Book",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case BOKK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                cursor = db.query("Book",projection,"id = ?" , new String[] {bookId},null,null,sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Categroy",projection,selection,selectionArgs,null,null,sortOrder);
                break;
            case CATEGORY_ITEM:
                String categroyId = uri.getPathSegments().get(1);
                cursor = db.query("Categroy",projection,"id = ?" , new String[] {categroyId},null,null,sortOrder);
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                return "vnd.android.cursor.dir/com.demo.lixuan.mydemo.baseElement.provider.book";
            case BOKK_ITEM:
                return "vnd.android.cursor.item/com.demo.lixuan.mydemo.baseElement.provider.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/com.demo.lixuan.mydemo.baseElement.provider.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/com.demo.lixuan.mydemo.baseElement.provider.category";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri uriReturn = null;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR :
            case BOKK_ITEM :
                long newBookid = db.insert("Book", null, contentValues);
                uriReturn = Uri.parse("content://"  + AUTHORITY + "/book/" + newBookid);
                break;

            case CATEGORY_DIR :
                break;
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, contentValues);
                uriReturn = Uri.parse("content://"  + AUTHORITY + "/book/" + newCategoryId);
                break;
            default:
                break;
        }
        return uriReturn;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){

        }
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)){
            case BOOK_DIR:
                deleteRows = db.update("Book",contentValues,selection,selectionArgs);
                break;
            case BOKK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows =db.delete("Book","id = ?",new String[]{bookId});
        }
        return deleteRows;
    }
}
