package com.baidu.location.p007b;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.p006h.C0335e;
import com.baidu.location.p006h.C0468j;
import com.umeng.facebook.share.internal.ShareConstants;
import java.io.File;
import java.util.HashMap;
import java.util.Locale;
import org.andengine.util.level.constants.LevelConstants;
import org.json.JSONObject;

public class C0365a {
    private static Object f339b = new Object();
    private static C0365a f340c = null;
    private static final String f341d = (C0468j.m1029g() + "/gal.db");
    C0364a f342a = null;
    private SQLiteDatabase f343e = null;
    private boolean f344f = false;

    class C0364a extends C0335e {
        int f334a;
        int f335b;
        int f336c;
        int f337d;
        final /* synthetic */ C0365a f338e;

        C0364a(C0365a c0365a) {
            this.f338e = c0365a;
            this.k = new HashMap();
        }

        public void mo1741a() {
            this.h = "http://loc.map.baidu.com/gpsz";
            this.k.put("gpsz", Jni.encode(String.format(Locale.CHINESE, "&x=%d&y=%d", new Object[]{Integer.valueOf(this.f334a), Integer.valueOf(this.f335b)})));
        }

        public void m370a(double d, double d2) {
            double[] coorEncrypt = Jni.coorEncrypt(d, d2, "gcj2wgs");
            this.f334a = (int) Math.floor(coorEncrypt[0] * 100.0d);
            this.f335b = (int) Math.floor(coorEncrypt[1] * 100.0d);
            this.f336c = (int) Math.floor(d * 100.0d);
            this.f337d = (int) Math.floor(d2 * 100.0d);
            this.f338e.f344f = true;
            m204e();
        }

        public void mo1742a(boolean z) {
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    if (jSONObject != null && jSONObject.has(LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT)) {
                        String string = jSONObject.getString(LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);
                        if (string.contains(",")) {
                            String[] split = string.split(",");
                            int length = split.length;
                            int sqrt = (int) Math.sqrt((double) length);
                            if (sqrt * sqrt == length) {
                                int i = this.f336c - ((sqrt - 1) / 2);
                                int i2 = this.f337d - ((sqrt - 1) / 2);
                                for (int i3 = 0; i3 < sqrt; i3++) {
                                    for (length = 0; length < sqrt; length++) {
                                        ContentValues contentValues = new ContentValues();
                                        if (split[(i3 * sqrt) + length].equals("E")) {
                                            contentValues.put("aldata", Double.valueOf(-1000.0d));
                                        } else {
                                            contentValues.put("aldata", Double.valueOf(split[(i3 * sqrt) + length]));
                                        }
                                        contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                                        int i4 = i + length;
                                        int i5 = i2 + i3;
                                        String format = String.format(Locale.CHINESE, "%d,%d", new Object[]{Integer.valueOf(i4), Integer.valueOf(i5)});
                                        try {
                                            if (this.f338e.f343e.update("galdata", contentValues, "id = \"" + format + "\"", null) <= 0) {
                                                contentValues.put(ShareConstants.WEB_DIALOG_PARAM_ID, format);
                                                this.f338e.f343e.insert("galdata", null, contentValues);
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e2) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.f338e.f344f = false;
        }
    }

    public static C0365a m373a() {
        C0365a c0365a;
        synchronized (f339b) {
            if (f340c == null) {
                f340c = new C0365a();
            }
            c0365a = f340c;
        }
        return c0365a;
    }

    private void m375b(double d, double d2) {
        if (this.f342a == null) {
            this.f342a = new C0364a(this);
        }
        this.f342a.m370a(d, d2);
    }

    public double m376a(double d, double d2) {
        double d3 = Double.MAX_VALUE;
        if (this.f343e != null) {
            Cursor cursor = null;
            try {
                cursor = this.f343e.rawQuery("select * from galdata where id = \"" + String.format(Locale.CHINESE, "%d,%d", new Object[]{Integer.valueOf((int) Math.floor(100.0d * d)), Integer.valueOf((int) Math.floor(100.0d * d2))}) + "\";", null);
                if (cursor != null && cursor.moveToFirst()) {
                    d3 = cursor.getDouble(1);
                    int i = cursor.getInt(2);
                    if (d3 == -1000.0d) {
                        d3 = Double.MAX_VALUE;
                    }
                    long currentTimeMillis = (System.currentTimeMillis() / 1000) - ((long) i);
                    if (!this.f344f && currentTimeMillis > 604800) {
                        m375b(d, d2);
                    }
                } else if (!this.f344f) {
                    m375b(d, d2);
                }
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e3) {
                    }
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e4) {
                    }
                }
            }
        }
        return d3;
    }

    public int m377a(BDLocation bDLocation) {
        float radius;
        double altitude;
        if (bDLocation != null) {
            radius = bDLocation.getRadius();
            altitude = bDLocation.getAltitude();
        } else {
            altitude = 0.0d;
            radius = 0.0f;
        }
        if (this.f343e != null && radius > 0.0f && altitude > 0.0d) {
            double a = m376a(bDLocation.getLongitude(), bDLocation.getLatitude());
            if (a != Double.MAX_VALUE) {
                altitude = Jni.getGpsSwiftRadius(radius, altitude, a);
                return altitude > 50.0d ? 3 : altitude > 20.0d ? 2 : 1;
            }
        }
        return 0;
    }

    public void m378b() {
        try {
            File file = new File(f341d);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                this.f343e = SQLiteDatabase.openOrCreateDatabase(file, null);
                this.f343e.execSQL("CREATE TABLE IF NOT EXISTS galdata(id CHAR(40) PRIMARY KEY,aldata DOUBLE,tt INT);");
                this.f343e.setVersion(1);
            }
        } catch (Exception e) {
            this.f343e = null;
        }
    }

    public void m379c() {
        if (this.f343e != null) {
            try {
                this.f343e.close();
            } catch (Exception e) {
            } finally {
                this.f343e = null;
            }
        }
    }
}
