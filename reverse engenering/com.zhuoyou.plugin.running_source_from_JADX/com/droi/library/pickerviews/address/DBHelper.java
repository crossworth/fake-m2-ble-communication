package com.droi.library.pickerviews.address;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
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
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_PROVINCE, null, null, null, null, null, null);
        ArrayList<String> list = new ArrayList();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_PROVINCE_NAME)));
        }
        cursor.close();
        return list;
    }

    public String getProvinceCode(String provinceName) {
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_PROVINCE, null, DataBaseContants.HAT_PROVINCE_NAME + " = ?", new String[]{provinceName}, null, null, null);
        cursor.moveToFirst();
        String values = cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_PROVINCE_CODE));
        cursor.close();
        return values;
    }

    public ArrayList<String> selectCity(String province) {
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_CITY, null, DataBaseContants.HAT_FATHER_CODE + " = ?", new String[]{province}, null, null, null);
        ArrayList<String> list = new ArrayList();
        while (cursor.moveToNext()) {
            String value = cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_CITY_NAME));
            if (!list.contains(value)) {
                list.add(value);
            }
        }
        cursor.close();
        return list;
    }

    public String getCityCodeByName(String provinceCode, String cityName) {
        if (TextUtils.isEmpty(provinceCode) || TextUtils.isEmpty(cityName)) {
            return "";
        }
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_CITY, null, DataBaseContants.HAT_CITY_NAME + " = ? AND " + DataBaseContants.HAT_FATHER_CODE + " = ?", new String[]{cityName, provinceCode}, null, null, null);
        cursor.moveToFirst();
        String code = cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_SON_CODE));
        cursor.close();
        return code;
    }

    public String getProvinceName(String code) {
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_CITY, null, DataBaseContants.HAT_SON_CODE + " = ?", new String[]{code}, null, null, null);
        cursor.moveToFirst();
        String fatherCode = cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_FATHER_CODE));
        Cursor cursor1 = this.db.query(DataBaseContants.HAT_TABLE_PROVINCE, null, DataBaseContants.HAT_PROVINCE_CODE + " = ?", new String[]{fatherCode}, null, null, null);
        cursor1.moveToFirst();
        String provinceName = cursor1.getString(cursor1.getColumnIndex(DataBaseContants.HAT_PROVINCE_NAME));
        cursor.close();
        cursor1.close();
        return provinceName;
    }

    public String getCityName(String code) {
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_CITY, null, DataBaseContants.HAT_SON_CODE + " = ?", new String[]{code}, null, null, null);
        cursor.moveToFirst();
        String name = cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_CITY_NAME));
        cursor.close();
        return name;
    }

    public ArrayList<String> selectArea(String cityCode) {
        if (TextUtils.isEmpty(cityCode)) {
            return null;
        }
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_AREA, null, DataBaseContants.HAT_FATHER_CODE + " = ?", new String[]{cityCode}, null, null, null);
        ArrayList<String> list = new ArrayList();
        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_CITY_NAME)));
        }
        cursor.close();
        return list;
    }

    public ArrayList<String> selectByCode(String code) {
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_AREA, null, DataBaseContants.HAT_CITY_CODE + " = ?", new String[]{code}, null, null, null);
        ArrayList<String> list = new ArrayList();
        if (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_FATHER_CODE)));
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_CITY_NAME)));
            list.add(cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_CITY_CODE)));
        }
        cursor.close();
        return list;
    }

    public String selectCode(String province, String city, String area) {
        String codeCity = "";
        String codeArea = "";
        Cursor cursor = this.db.query(DataBaseContants.HAT_TABLE_CITY, null, DataBaseContants.HAT_FATHER_CODE + " = ? AND " + DataBaseContants.HAT_CITY_NAME + " = ?  ", new String[]{getProvinceCode(province), city}, null, null, null);
        if (cursor.moveToNext()) {
            codeCity = cursor.getString(cursor.getColumnIndex(DataBaseContants.HAT_SON_CODE));
        }
        cursor.close();
        Cursor cursorCode = this.db.query(DataBaseContants.HAT_TABLE_AREA, null, DataBaseContants.HAT_FATHER_CODE + " = ? AND " + DataBaseContants.HAT_CITY_NAME + " = ? ", new String[]{codeCity, area}, null, null, null);
        if (cursorCode.moveToNext()) {
            codeArea = cursorCode.getString(cursorCode.getColumnIndex(DataBaseContants.HAT_CITY_CODE));
        }
        cursorCode.close();
        return TextUtils.isEmpty(codeArea) ? provinceCode : codeArea;
    }
}
