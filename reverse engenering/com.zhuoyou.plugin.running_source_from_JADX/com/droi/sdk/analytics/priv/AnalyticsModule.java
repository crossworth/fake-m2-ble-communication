package com.droi.sdk.analytics.priv;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import com.droi.sdk.DroiError;
import com.droi.sdk.core.DroiHttpRequest;
import com.droi.sdk.core.DroiHttpRequest.Request;
import com.droi.sdk.core.DroiHttpRequest.Response;
import com.droi.sdk.core.DroiUser;
import com.droi.sdk.core.NetworkUtils;
import com.droi.sdk.core.TaskDispatcher;
import com.droi.sdk.core.priv.C0944p;
import com.droi.sdk.internal.DroiDeviceInfoCollector;
import com.droi.sdk.internal.DroiLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPOutputStream;
import org.json.JSONObject;

public class AnalyticsModule {
    public static final String KEY_APP_ID = "X-Droi-AppID";
    public static final String SERVICE_DROI_LOGS = "/droislog/droi_client_log";
    public static final int TYPE_SEND_IN_NO_QUEUED = 2;
    public static final int TYPE_SEND_IN_REALTIME = 0;
    public static final int TYPE_SEND_IN_SCHEDULE = 1;
    private static final byte[] f2352a = "--droi-id-2a8e74d731e327eb4f".getBytes();
    private static final String f2353b = "AnalyticsModule";
    private static final String f2354c = "alanwake";
    private static final String f2355d = "ptn_task";
    private static final String f2356e = "no_queued_task";
    private static final int f2357f = 180000;
    private static final byte[] f2358g = "\r\n".getBytes();
    private static final byte[] f2359h = "--".getBytes();
    private static final int f2360i = 720;
    private static final int f2361j = 153600;
    private static final String f2362k = "am_interval";
    private static final String f2363l = "am_wifionly";
    private static final String f2364m = "am_realtime_interval";
    private static final String f2365n = "/droislog";
    private static final String f2366o = "com.droi.sdk.application_id";
    private final PostTask f2367p;
    private LocalDBHelper f2368q;
    private SharedPreferences f2369r;
    private Context f2370s;
    private String f2371t;
    private String f2372u = "dalog";

    private static class Item {
        public String content;
        public long delayDateMillis;
        public String header;
        public String id;
        public int type;

        private Item() {
        }
    }

    private static class ItemList extends ArrayList<Item> {
        public long timestamp;

        private ItemList() {
        }
    }

    private static class LocalDBHelper extends SQLiteOpenHelper {
        private static final String CONTENT = "content";
        private static final String DELAY_DATE = "delay_date";
        private static final String HEADER = "header";
        static final String INTERNAL_TABLE = "int_table";
        private static final String NAME = "dalog";
        private static final String TYPE = "type";
        private static final int VERSION = 1;

        public LocalDBHelper(Context context, String str) {
            super(context, str, null, 1);
        }

        private void createTable(String str, SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format("CREATE TABLE IF NOT EXISTS %s (%s)", new Object[]{str, "type INT,header TEXT,content TEXT,delay_date INT"}));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void dropTable(String str, SQLiteDatabase sQLiteDatabase) {
            try {
                sQLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", new Object[]{str}));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public DroiError deleteRecord(String str, int i, long j) {
            String str2 = String.format("%s=%d", new Object[]{"type", Integer.valueOf(i)}) + String.format(" AND %s<=%d", new Object[]{DELAY_DATE, Long.valueOf(j)});
            DroiError droiError = new DroiError();
            try {
                getWritableDatabase().delete(str, str2, null);
            } catch (Exception e) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("deleteRecord fail. " + e);
            }
            return droiError;
        }

        public ItemList getImmediatelyRecord(String str, long j, DroiError droiError) {
            return getRecord(str, 0, j, droiError);
        }

        public ItemList getRecord(String str, int i, long j, DroiError droiError) {
            Cursor rawQuery;
            Object e;
            Throwable th;
            try {
                rawQuery = getReadableDatabase().rawQuery((String.format("SELECT rowid, * FROM %s WHERE %s=%d", new Object[]{str, "type", Integer.valueOf(i)}) + String.format(" AND %s<=%d", new Object[]{DELAY_DATE, Long.valueOf(j)})) + String.format(" ORDER BY %s", new Object[]{"type"}), null);
                try {
                    ItemList itemList = new ItemList();
                    itemList.timestamp = j;
                    if (rawQuery != null) {
                        while (rawQuery.moveToNext()) {
                            Item item = new Item();
                            item.id = rawQuery.getString(0);
                            item.type = rawQuery.getInt(1);
                            item.header = rawQuery.getString(2);
                            item.content = rawQuery.getString(3);
                            item.delayDateMillis = rawQuery.getLong(4);
                            itemList.add(item);
                        }
                    }
                    if (droiError != null) {
                        droiError.setCode(0);
                        droiError.setAppendedMessage(null);
                    }
                    if (rawQuery == null) {
                        return itemList;
                    }
                    rawQuery.close();
                    return itemList;
                } catch (Exception e2) {
                    e = e2;
                    if (droiError != null) {
                        try {
                            droiError.setCode(DroiError.ERROR);
                            droiError.setAppendedMessage("getImmediatelyRecord fail. " + e);
                        } catch (Throwable th2) {
                            th = th2;
                            if (rawQuery != null) {
                                rawQuery.close();
                            }
                            throw th;
                        }
                    }
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                    return null;
                }
            } catch (Exception e3) {
                e = e3;
                rawQuery = null;
                if (droiError != null) {
                    droiError.setCode(DroiError.ERROR);
                    droiError.setAppendedMessage("getImmediatelyRecord fail. " + e);
                }
                if (rawQuery != null) {
                    rawQuery.close();
                }
                return null;
            } catch (Throwable th3) {
                th = th3;
                rawQuery = null;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            createTable(INTERNAL_TABLE, sQLiteDatabase);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            dropTable(INTERNAL_TABLE, sQLiteDatabase);
            createTable(INTERNAL_TABLE, sQLiteDatabase);
        }

        public DroiError putRecord(String str, int i, String str2, String str3, long j) {
            String format = String.format("INSERT INTO %s (%s) VALUES(%s)", new Object[]{str, "type,header,content,delay_date", i + ",'" + str2 + "','" + str3 + "'," + j});
            DroiError droiError = new DroiError();
            try {
                getWritableDatabase().execSQL(format);
            } catch (Exception e) {
                droiError.setCode(DroiError.ERROR);
                droiError.setAppendedMessage("PutRecord fail. " + e);
            }
            return droiError;
        }
    }

    private static class PostTask implements Runnable {
        private String appId;
        private Context context;
        private LocalDBHelper dbHelper;
        private SharedPreferences sharedPref;

        public PostTask(Context context, LocalDBHelper localDBHelper, SharedPreferences sharedPreferences, String str) {
            this.dbHelper = localDBHelper;
            this.sharedPref = sharedPreferences;
            this.context = context;
            this.appId = str;
        }

        private byte[] genMultiPartData(Map<String, StringBuilder> map) throws IOException {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            for (String str : map.keySet()) {
                byteArrayOutputStream.write(AnalyticsModule.f2352a);
                byteArrayOutputStream.write(AnalyticsModule.f2358g);
                byteArrayOutputStream.write(String.format("Content-Type: application/x-gzip\r\nContent-Disposition: form-data; filename=\"%s\"\r\n\r\n", new Object[]{str}).getBytes());
                OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream2);
                gZIPOutputStream.write(((StringBuilder) map.get(str)).toString().getBytes());
                gZIPOutputStream.close();
                byteArrayOutputStream.write(byteArrayOutputStream2.toByteArray());
                byteArrayOutputStream.write(AnalyticsModule.f2358g);
            }
            byteArrayOutputStream.write(AnalyticsModule.f2352a);
            byteArrayOutputStream.write(AnalyticsModule.f2359h);
            byteArrayOutputStream.write(AnalyticsModule.f2358g);
            return byteArrayOutputStream.toByteArray();
        }

        public static boolean isNetworkInWifi(Context context) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo == null) {
                return false;
            }
            int type = activeNetworkInfo.getType();
            boolean z = type == 1 || type == 6;
            return z;
        }

        private void postInternalData(long j) {
            Object obj = null;
            DroiError droiError = new DroiError();
            try {
                ItemList record = this.dbHelper.getRecord("int_table", 2, j, droiError);
                if (!droiError.isOk()) {
                    Log.e(AnalyticsModule.f2353b, "Query records fail. " + droiError.toString());
                }
                if (sendData(record)) {
                    droiError = this.dbHelper.deleteRecord("int_table", 2, record.timestamp);
                    if (!droiError.isOk()) {
                        Log.e(AnalyticsModule.f2353b, "Delete records fail. " + droiError.toString());
                    }
                }
                record = this.dbHelper.getImmediatelyRecord("int_table", j, droiError);
                if (!droiError.isOk()) {
                    Log.e(AnalyticsModule.f2353b, "Query records fail. " + droiError.toString());
                }
                if (sendData(record)) {
                    droiError = this.dbHelper.deleteRecord("int_table", 0, record.timestamp);
                    if (!droiError.isOk()) {
                        Log.e(AnalyticsModule.f2353b, "Delete records fail. " + droiError.toString());
                    }
                }
                if (!this.sharedPref.getBoolean("am_wifionly", true) || isNetworkInWifi(this.context)) {
                    obj = 1;
                }
                if (obj != null) {
                    ItemList record2 = this.dbHelper.getRecord("int_table", 1, j, droiError);
                    if (!droiError.isOk()) {
                        Log.e(AnalyticsModule.f2353b, "Query records fail. " + droiError.toString());
                    }
                    if (sendData(record2)) {
                        DroiError deleteRecord = this.dbHelper.deleteRecord("int_table", 1, record2.timestamp);
                        if (!deleteRecord.isOk()) {
                            Log.e(AnalyticsModule.f2353b, "Delete records fail. " + deleteRecord.toString());
                        }
                    }
                }
            } catch (Throwable e) {
                Log.e(AnalyticsModule.f2353b, "Error: ", e);
            }
        }

        private void runWithCatch() {
            if (NetworkUtils.isWifiOrMobileAvailable(NetworkUtils.getNetworkState(this.context))) {
                postInternalData(new Date().getTime());
            }
        }

        private boolean sendData(ItemList itemList) throws RemoteException, IOException {
            if (itemList == null || itemList.size() == 0) {
                return false;
            }
            Map hashMap = new HashMap();
            int size = itemList.size();
            Iterator it = itemList.iterator();
            int i = 1;
            int i2 = 0;
            while (it.hasNext()) {
                Item item = (Item) it.next();
                String str = item.header;
                String str2 = item.content;
                StringBuilder stringBuilder = (StringBuilder) hashMap.get(str);
                if (stringBuilder == null) {
                    stringBuilder = new StringBuilder();
                    hashMap.put(str, stringBuilder);
                }
                stringBuilder.append(str2);
                if (size != i) {
                    stringBuilder.append("\r\n");
                }
                int i3 = i + 1;
                i = (str2.length() + 2) + i2;
                if (i > AnalyticsModule.f2361j) {
                    if (!sendPartData(hashMap).isOk()) {
                        return false;
                    }
                    hashMap.clear();
                    i = 0;
                }
                i2 = i;
                i = i3;
            }
            return hashMap.size() == 0 || sendPartData(hashMap).isOk();
        }

        private DroiError sendPartData(Map<String, StringBuilder> map) throws IOException, RemoteException {
            String str = null;
            DroiError droiError = new DroiError();
            byte[] genMultiPartData = genMultiPartData(map);
            if (genMultiPartData == null) {
                Log.w(AnalyticsModule.f2353b, "no analytics data need to sent");
                return droiError;
            }
            DroiUser currentUser = DroiUser.getCurrentUser();
            if (currentUser != null && currentUser.isAnonymous() && !currentUser.isAuthorized() && DroiUser.isAutoAnonymousUserEnabled()) {
                DroiUser.loginWithAnonymous(null);
            } else if (currentUser == null || !currentUser.isAuthorized()) {
                return new DroiError(DroiError.USER_NOT_AUTHORIZED, null);
            }
            Request make = Request.make("/droislog/droi_client_log", genMultiPartData);
            make.addHeader("X-Droi-AppID", this.appId);
            make.setEnableGZip(false);
            Response request = DroiHttpRequest.instance().request(make);
            if (request == null) {
                Log.e(AnalyticsModule.f2353b, "No response");
                return new DroiError(DroiError.ERROR, "no response");
            }
            int statusCode = request.getStatusCode();
            int errorCode = request.getErrorCode();
            int droiStatusCode = request.getDroiStatusCode();
            droiError = new DroiError();
            if (statusCode != 200 || errorCode != 0 || droiStatusCode < 0) {
                return new DroiError(DroiError.ERROR, "Send fail. status: " + statusCode + ", code: " + errorCode + ", droiStatus: " + droiStatusCode);
            }
            if (request.getData() != null) {
                str = new String(request.getData());
            }
            if (str == null) {
                droiError.setCode(DroiError.ERROR);
                return droiError;
            }
            try {
                int i = new JSONObject(str).getInt("Code");
                if (i == 0) {
                    return droiError;
                }
                droiError.setCode(i);
                return droiError;
            } catch (Throwable e) {
                Log.e(AnalyticsModule.f2353b, "Error: ", e);
                return droiError;
            }
        }

        public void run() {
            try {
                runWithCatch();
            } catch (Throwable e) {
                Log.e(AnalyticsModule.f2353b, "error: ", e);
            }
        }
    }

    public AnalyticsModule(Context context) {
        this.f2370s = context;
        String d = C0944p.m2798d(context);
        String packageName = DroiDeviceInfoCollector.getPackageName(context);
        if (!(d == null || d.isEmpty() || d.equals(packageName))) {
            this.f2372u = "dalog" + Math.abs(d.hashCode());
        }
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                this.f2371t = bundle.getString(f2366o);
            }
        } catch (NameNotFoundException e) {
            Log.e(f2353b, "Metadata not found");
        }
        this.f2369r = context.getSharedPreferences("droicore", 0);
        this.f2368q = new LocalDBHelper(context, this.f2372u);
        this.f2367p = new PostTask(context, this.f2368q, this.f2369r, this.f2371t);
        TaskDispatcher.getDispatcher(f2354c).enqueueTask(this.f2367p, f2355d);
    }

    public DroiError send(int i, String str, String str2) {
        TaskDispatcher dispatcher = TaskDispatcher.getDispatcher(f2354c);
        long time = new Date().getTime();
        if (i == 1) {
            time += (long) (this.f2369r.getInt("am_interval", f2360i) * 60000);
        }
        final int i2 = i;
        final String str3 = str;
        final String str4 = str2;
        Runnable c07811 = new Runnable() {
            public void run() {
                DroiError putRecord = AnalyticsModule.this.f2368q.putRecord("int_table", i2, str3, str4, time);
                if (!putRecord.isOk()) {
                    DroiLog.m2870e(AnalyticsModule.f2353b, "putRecord fail. " + putRecord.toString());
                }
            }
        };
        DroiError droiError = new DroiError();
        if (!dispatcher.enqueueTask(c07811)) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("PutRecord fail.");
        }
        if (i == 2) {
            dispatcher.killTask(f2355d);
            dispatcher.enqueueTask(this.f2367p, f2356e);
        }
        if (dispatcher.isTaskCancelled(f2355d) && !dispatcher.enqueueTimerTask(this.f2367p, f2357f, f2355d)) {
            droiError.setCode(DroiError.ERROR);
            droiError.setAppendedMessage("Enqueue fail.");
        }
        return droiError;
    }

    public void setScheduleConfig(int i, boolean z) {
        Editor edit = this.f2369r.edit();
        edit.putInt("am_interval", i);
        edit.putBoolean("am_wifionly", z);
        edit.apply();
    }
}
