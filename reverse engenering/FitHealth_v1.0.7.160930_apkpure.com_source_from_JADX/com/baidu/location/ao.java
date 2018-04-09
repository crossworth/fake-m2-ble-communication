package com.baidu.location;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.p000a.C0495a;
import java.io.File;
import java.util.Locale;
import org.json.JSONObject;

class ao implements an, C1619j {
    private static ao gW = null;
    private String g0 = null;
    private double g1 = 0.0d;
    private boolean g2 = false;
    private double g3 = 0.0d;
    private String g4 = "bdcltb09";
    private int g5 = 0;
    private boolean g6 = false;
    private double gU = 0.0d;
    private String gV = null;
    private SQLiteDatabase gX = null;
    private long gY = 0;
    private boolean gZ = false;

    private ao() {
        try {
            bA();
        } catch (Exception e) {
        }
    }

    private void bA() {
        File file = new File(L);
        File file2 = new File(L + "/ls.db");
        if (!file.exists()) {
            file.mkdirs();
        }
        if (!file2.exists()) {
            try {
                file2.createNewFile();
            } catch (Exception e) {
            }
        }
        if (file2.exists()) {
            this.gX = SQLiteDatabase.openOrCreateDatabase(file2, null);
            this.gX.execSQL("CREATE TABLE IF NOT EXISTS " + this.g4 + "(id CHAR(40) PRIMARY KEY,time DOUBLE,tag DOUBLE, type DOUBLE , ac INT);");
        }
    }

    public static ao bz() {
        if (gW == null) {
            gW = new ao();
        }
        return gW;
    }

    private void m5884g(Message message) {
        C1977g.m5942g().m5954if(m5889new(true), message);
    }

    private String m5885int(boolean z) {
        if (!this.g6) {
            return z ? "{\"result\":{\"time\":\"" + C1974b.m5918if() + "\",\"error\":\"67\"}}" : "{\"result\":{\"time\":\"" + C1974b.m5918if() + "\",\"error\":\"63\"}}";
        } else {
            if (z) {
                return String.format(Locale.CHINA, "{\"result\":{\"time\":\"" + C1974b.m5918if() + "\",\"error\":\"66\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%f\",\"isCellChanged\":\"%b\"}}", new Object[]{Double.valueOf(this.g3), Double.valueOf(this.g1), Double.valueOf(this.gU), Boolean.valueOf(true)});
            }
            return String.format(Locale.CHINA, "{\"result\":{\"time\":\"" + C1974b.m5918if() + "\",\"error\":\"68\"},\"content\":{\"point\":{\"x\":" + "\"%f\",\"y\":\"%f\"},\"radius\":\"%f\",\"isCellChanged\":\"%b\"}}", new Object[]{Double.valueOf(this.g3), Double.valueOf(this.g1), Double.valueOf(this.gU), Boolean.valueOf(C2065y.ag().ai())});
        }
    }

    private void m5886l(String str) {
        if (this.gX == null || str == null) {
            this.g6 = false;
        } else if (System.currentTimeMillis() - this.gY >= 1500 && !str.equals(this.gV)) {
            this.g6 = false;
            try {
                Cursor rawQuery = this.gX.rawQuery("select * from " + this.g4 + " where id = \"" + str + "\";", null);
                this.gV = str;
                this.gY = System.currentTimeMillis();
                if (rawQuery != null) {
                    if (rawQuery.moveToFirst()) {
                        this.g3 = rawQuery.getDouble(1) - 1235.4323d;
                        this.gU = rawQuery.getDouble(2) - 4326.0d;
                        this.g1 = rawQuery.getDouble(3) - 2367.3217d;
                        this.g6 = true;
                    }
                    rawQuery.close();
                }
            } catch (Exception e) {
                this.gY = System.currentTimeMillis();
            }
        }
    }

    public void bB() {
    }

    public void by() {
        if (this.gX != null) {
            this.gX.close();
            this.gX = null;
        }
    }

    public void m5887h(Message message) {
        m5884g(message);
    }

    public void m5888if(String str, C0529a c0529a) {
        Object obj = null;
        double d = 0.0d;
        if (this.gX != null && C2065y.ag().ai() && c0529a.m2202for()) {
            String a = c0529a.m2199a();
            try {
                double parseDouble;
                float parseFloat;
                JSONObject jSONObject = new JSONObject(str);
                int parseInt = Integer.parseInt(jSONObject.getJSONObject("result").getString("error"));
                int i;
                if (parseInt == BDLocation.TypeNetWorkLocation) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("content");
                    if (jSONObject2.has("clf")) {
                        String string = jSONObject2.getString("clf");
                        if (string.equals("0")) {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject("point");
                            d = Double.parseDouble(jSONObject3.getString("x"));
                            parseDouble = Double.parseDouble(jSONObject3.getString("y"));
                            parseFloat = Float.parseFloat(jSONObject2.getString(C0495a.f2122char));
                        } else {
                            String[] split = string.split("\\|");
                            d = Double.parseDouble(split[0]);
                            parseDouble = Double.parseDouble(split[1]);
                            parseFloat = Float.parseFloat(split[2]);
                        }
                    }
                    i = 1;
                    parseFloat = 0.0f;
                    parseDouble = 0.0d;
                } else {
                    if (parseInt == BDLocation.TypeServerError) {
                        this.gX.delete(this.g4, "id = \"" + a + "\"", null);
                        return;
                    }
                    i = 1;
                    parseFloat = 0.0f;
                    parseDouble = 0.0d;
                }
                if (obj == null) {
                    d += 1235.4323d;
                    parseDouble += 2367.3217d;
                    float f = 4326.0f + parseFloat;
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(LogColumns.TIME, Double.valueOf(d));
                    contentValues.put("tag", Float.valueOf(f));
                    contentValues.put("type", Double.valueOf(parseDouble));
                    try {
                        if (this.gX.update(this.g4, contentValues, "id = \"" + a + "\"", null) <= 0) {
                            contentValues.put("id", a);
                            this.gX.insert(this.g4, null, contentValues);
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public BDLocation m5889new(boolean z) {
        m5886l(C1981n.m6008K().m6018H().m2199a());
        return new BDLocation(m5885int(z));
    }
}
