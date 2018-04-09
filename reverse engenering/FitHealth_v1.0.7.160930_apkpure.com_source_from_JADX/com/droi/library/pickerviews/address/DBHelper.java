package com.droi.library.pickerviews.address;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DBHelper {
    private SQLiteDatabase db;

    public DBHelper(Context context) {
        File dbFile = context.getDatabasePath(DataBaseContants.DB_NAME);
        if (!dbFile.exists()) {
            try {
                FileOutputStream out = new FileOutputStream(dbFile);
                InputStream in = context.getAssets().open(DataBaseContants.DB_NAME);
                byte[] buffer = new byte[1024];
                while (true) {
                    int readBytes = in.read(buffer);
                    if (readBytes == -1) {
                        break;
                    }
                    out.write(buffer, 0, readBytes);
                }
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.db = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public ArrayList<String> selectProvince() {
        Cursor cursor = this.db.query(DataBaseContants.TABLE_NAME_PROVINCE, null, null, null, null, null, null);
        ArrayList<String> list = new ArrayList();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.PROVINCE_NAME)));
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> selectCity(String province) {
        Cursor cursor = this.db.query(DataBaseContants.TABLE_NAME_CITY, null, DataBaseContants.CITY_PROVINCE + " = ?", new String[]{province}, null, null, null);
        ArrayList<String> list = new ArrayList();
        while (cursor.moveToNext()) {
            String value = cursor.getString(cursor.getColumnIndex(DataBaseContants.CITY_CITY));
            if (!list.contains(value)) {
                list.add(value);
            }
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> selectArea(String province, String city) {
        Cursor cursor = this.db.query(DataBaseContants.TABLE_NAME_CITY, null, DataBaseContants.CITY_PROVINCE + " = ? AND " + DataBaseContants.CITY_CITY + " = ?", new String[]{province, city}, null, null, null);
        ArrayList<String> list = new ArrayList();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.CITY_AREA)));
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> selectByCode(String code) {
        Cursor cursor = this.db.query(DataBaseContants.TABLE_NAME_CITY, null, DataBaseContants.CITY_CODE + " = ?", new String[]{code}, null, null, null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.CITY_PROVINCE)));
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.CITY_CITY)));
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.CITY_CODE)));
        }
        cursor.close();
        return list;
    }

    public String selectCode(String province, String city, String area) {
        String code = "";
        Cursor cursor = this.db.query(DataBaseContants.TABLE_NAME_CITY, null, DataBaseContants.CITY_PROVINCE + " = ? AND " + DataBaseContants.CITY_CITY + " = ? AND " + DataBaseContants.CITY_AREA + " = ?", new String[]{province, city, area}, null, null, null);
        if (cursor.moveToNext()) {
            code = cursor.getString(cursor.getColumnIndex(DataBaseContants.CITY_CODE));
        }
        cursor.close();
        return code;
    }
}
