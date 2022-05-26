package com.suzuha.baithiquanlythietbi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    private static final String DB_NAME = "danhsach";
    private static final int DB_VERSION = 1;
    private static final String TABLE_NAME = "mydanhsach";
    private static final String ID_COL = "id";
    private static final String URL_COL = "url";
    private static final String NAME_COL = "name";
    private static final String BRAND_COL = "brand";
    private static final String YEAR_COL = "year";
    private static final String DETAIL_COL = "detail";

    public DataBase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = " CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + URL_COL + " TEXT,"
                + NAME_COL + " TEXT,"
                + BRAND_COL + " TEXT,"
                + YEAR_COL + " TEXT,"
                + DETAIL_COL + "TEXT)";
        db.execSQL(query);
    }

    public void addDevice(item A)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(URL_COL, A.url);
        values.put(NAME_COL, A.title);
        values.put(BRAND_COL, A.Brand);
        values.put(YEAR_COL, A.Year);
        values.put(DETAIL_COL, A.Detail);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void deleteDevice(item A)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name=?", new String[]{A.title});
        db.close();
    }

    public void updateDevice(item A, item B) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put(URL_COL, B.url);
        values.put(NAME_COL, B.title);
        values.put(BRAND_COL,B.Brand);
        values.put(YEAR_COL,B.Year);
        values.put(DETAIL_COL,B.Detail);

        db.update(TABLE_NAME,values,"name=?",new String[]{A.title});
        db.close();

    }

    public ArrayList<item> readDanhSach() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        ArrayList<item> arrayList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                arrayList.add(new item
                        (cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                        ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
