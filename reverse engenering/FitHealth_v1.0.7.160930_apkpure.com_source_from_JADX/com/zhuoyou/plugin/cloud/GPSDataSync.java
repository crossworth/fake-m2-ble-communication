package com.zhuoyou.plugin.cloud;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.os.StatFs;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import com.zhuoyou.plugin.database.DBOpenHelper;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.Tools;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GPSDataSync {
    public static final String GPS_OPERATION_ADD = "operation_time_info_add";
    public static final String GPS_OPERATION_DELETE = "operation_time_info_delete";
    public static final String GPS_OPERATION_UPDATE = "operation_time_info_update";
    public static final String GPS_POINT_ADD = "point_message_info_add";
    public static final String GPS_POINT_DELETE = "point_message_info_delete";
    public static final String GPS_POINT_UPDATE = "point_message_info_update";
    public static final String GPS_SPORT_ADD = "gps_sport_info_add";
    public static final String GPS_SPORT_DELETE = "gps_sport_info_delete";
    public static final String GPS_SPORT_UPDATE = "gps_sport_info_update";
    private static final String TAG = "GPSDataSync";
    public final String GPS_DOWNLOAD_OPERATION = "operation_info_down";
    public final String GPS_DOWNLOAD_POINT = "point_info_down";
    public final String GPS_DOWNLOAD_SPORT = "gps_info_down";
    private Runnable dowmRunnable = new C12212();
    private Context mContext;
    private Handler mHandler;
    private String openid;
    private long recordId;
    private int result = 0;
    private String rootPath = CvsUtils.GetDir();
    private String txtPath;
    private Runnable upRunnable = new C12201();

    class C12201 implements Runnable {
        C12201() {
        }

        public void run() {
            Log.i("lsj", "run");
            GPSDataSync.this.txtPath = GPSDataSync.this.rootPath + "/" + "zipfile/";
            if (GPSDataSync.this.isSpaceEnough(GPSDataSync.this.rootPath)) {
                CvsUtils.deleteFolder(new File(GPSDataSync.this.rootPath));
                GPSDataSync.this.createGSPFile(GPSDataSync.this.txtPath);
                String up_zip = "upload.zip";
                try {
                    CvsUtils.doZip(GPSDataSync.this.txtPath, GPSDataSync.this.rootPath + "/" + up_zip);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GPSDataSync.this.result = GPSDataSync.this.upload_file(GPSDataSync.this.rootPath, up_zip);
                Log.i("lsj", "GPS result" + GPSDataSync.this.result);
                if (GPSDataSync.this.result == 1) {
                    Log.i("lsj", "GPS update failed");
                    GPSDataSync.this.UpdataLocalDate();
                    Message msg = GPSDataSync.this.mHandler.obtainMessage();
                    msg.what = 200;
                    msg.arg1 = NetMsgCode.postGPSInfo;
                    GPSDataSync.this.mHandler.sendMessage(msg);
                    return;
                }
                Log.d(GPSDataSync.TAG, "GPS update failed");
                GPSDataSync.this.mHandler.sendEmptyMessage(110011);
            }
        }
    }

    class C12212 implements Runnable {
        C12212() {
        }

        public void run() {
            String down_zip = "Running_down.zip";
            if (GPSDataSync.this.isSpaceEnough(GPSDataSync.this.rootPath)) {
                File mfile = new File(GPSDataSync.this.rootPath);
                if (mfile.exists()) {
                    CvsUtils.deleteFolder(mfile);
                }
                mfile.mkdirs();
                GPSDataSync.this.result = GPSDataSync.this.download_file(GPSDataSync.this.rootPath, down_zip);
                Log.d(GPSDataSync.TAG, "result:" + GPSDataSync.this.result);
                if (GPSDataSync.this.result == 3) {
                    Log.d(GPSDataSync.TAG, "download  success");
                    String zip_path = GPSDataSync.this.rootPath + "/" + down_zip;
                    String out_path = GPSDataSync.this.rootPath + "/";
                    try {
                        CvsUtils.unZip(zip_path, out_path);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d(GPSDataSync.TAG, "download failed");
                    }
                    Log.d(GPSDataSync.TAG, "CVSUnzipFile  success");
                    int i = -1;
                    try {
                        i = GPSDataSync.this.ImportCSVFile(out_path);
                        Log.i(GPSDataSync.TAG, "" + i);
                    } catch (RemoteException e2) {
                        e2.printStackTrace();
                        Log.d(GPSDataSync.TAG, "ImportCSVFile failed");
                    } catch (OperationApplicationException e3) {
                        e3.printStackTrace();
                    }
                    if (i == -1) {
                        Log.d(GPSDataSync.TAG, "ImportCSVFile failed");
                        GPSDataSync.this.mHandler.sendEmptyMessage(110011);
                        return;
                    }
                    Log.d(GPSDataSync.TAG, "ImportCSVFile  success");
                    Message msg = GPSDataSync.this.mHandler.obtainMessage();
                    msg.what = NetMsgCode.getGPSInfo;
                    msg.arg1 = 200;
                    GPSDataSync.this.mHandler.sendMessage(msg);
                    return;
                }
                Log.d(GPSDataSync.TAG, "download failed");
                GPSDataSync.this.mHandler.sendEmptyMessage(110011);
            }
        }
    }

    public GPSDataSync(Context context, int type) {
        this.mContext = context;
        this.openid = Tools.getOpenId(this.mContext);
    }

    public void postSportData(Handler handler) {
        this.mHandler = handler;
        new Thread(this.upRunnable).start();
    }

    public void createGSPFile(String filePath) {
        try {
            String str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_SPORT_ADD, DataBaseContants.CONTENT_URI_GPSSPORT, new String[]{"_id", DataBaseContants.GPS_STARTTIME, DataBaseContants.GPS_ENDTIME, DataBaseContants.GPS_SYSSTARTTIME, DataBaseContants.GPS_SYSENDTIME, DataBaseContants.GPS_DURATIONTIME, DataBaseContants.AVESPEED, DataBaseContants.TOTAL_DISTANCE, "steps", DataBaseContants.GPS_CALORIE}, "sync_state  = ? ", new String[]{Integer.toString(0)}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_OPERATION_ADD, DataBaseContants.CONTENT_URI_OPERATION, new String[]{"_id", DataBaseContants.OPERATION_TIME, DataBaseContants.OPERATION_SYSTIME, DataBaseContants.OPERATION_STATE}, "sync_state  = ? ", new String[]{Integer.toString(0)}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_POINT_ADD, DataBaseContants.CONTENT_URI_POINT, new String[]{"_id", "latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_NUMBER}, "sync_state  = ? ", new String[]{Integer.toString(0)}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_SPORT_DELETE, DataBaseContants.CONTENT_URI_GPSSYNC, new String[]{DataBaseContants.GPS_DELETE}, "table_name  = ? ", new String[]{DataBaseContants.TABLE_GPSSPORT_NAME}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_OPERATION_DELETE, DataBaseContants.CONTENT_URI_GPSSYNC, new String[]{DataBaseContants.GPS_DELETE}, "table_name  = ? ", new String[]{DataBaseContants.TABLE_OPERATION_NAME}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_POINT_DELETE, DataBaseContants.CONTENT_URI_GPSSYNC, new String[]{DataBaseContants.GPS_DELETE}, "table_name  = ? ", new String[]{DataBaseContants.TABLE_POINT_NAME}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_SPORT_UPDATE, DataBaseContants.CONTENT_URI_GPSSPORT, new String[]{"_id", DataBaseContants.GPS_STARTTIME, DataBaseContants.GPS_ENDTIME, DataBaseContants.GPS_SYSSTARTTIME, DataBaseContants.GPS_SYSENDTIME, DataBaseContants.GPS_DURATIONTIME, DataBaseContants.AVESPEED, DataBaseContants.TOTAL_DISTANCE, "steps", DataBaseContants.GPS_CALORIE}, "sync_state  = ? ", new String[]{Integer.toString(2)}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_OPERATION_UPDATE, DataBaseContants.CONTENT_URI_OPERATION, new String[]{"_id", DataBaseContants.OPERATION_TIME, DataBaseContants.OPERATION_SYSTIME, DataBaseContants.OPERATION_STATE}, "sync_state  = ? ", new String[]{Integer.toString(2)}, null);
            str = filePath;
            CvsUtils.DBTableToFile(this.mContext, this.openid, str, GPS_POINT_UPDATE, DataBaseContants.CONTENT_URI_POINT, new String[]{"_id", "latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_NUMBER}, "sync_state  = ? ", new String[]{Integer.toString(2)}, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int upload_file(String path, String filename) {
        HttpConnect httpCon = new HttpConnect();
        String filePath = path + "/" + filename;
        HashMap<String, String> params = new HashMap();
        params.put("account", this.openid);
        int result = httpCon.uploadFile(NetMsgCode.UP_URL, params, filePath);
        Log.i("lsj", "GPS result =" + result);
        return result;
    }

    private int download_file(String path, String filename) {
        HttpConnect httpCon = new HttpConnect();
        String filePath = path + "/" + filename;
        HashMap<String, String> params = new HashMap();
        params.put("account", this.openid);
        Log.i("txhlog", "account:" + this.openid);
        params.put("id", Long.toString(this.recordId));
        Log.i("txhlog", "recordId:" + this.recordId);
        int result = httpCon.downloadFile(NetMsgCode.GPS_DOWN_URL, new HashMap(), params, filePath);
        Log.i("txhlog", "result" + result);
        return result;
    }

    public void getGPSFromCloud(Handler handler, long recordId) {
        this.recordId = recordId;
        this.mHandler = handler;
        new Thread(this.dowmRunnable).start();
    }

    private boolean isSpaceEnough(String dir) {
        StatFs sf = new StatFs(this.rootPath);
        if (((((long) sf.getAvailableBlocks()) * ((long) sf.getBlockSize())) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) / PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID < 10) {
            return false;
        }
        return true;
    }

    public void UpdataLocalDate() {
        emove_db_update();
        emove_db_delete();
    }

    private void emove_db_update() {
        ContentResolver cr = this.mContext.getContentResolver();
        ContentValues updateValues = new ContentValues();
        updateValues.put(DataBaseContants.GPS_SYNC, Integer.valueOf(1));
        cr.update(DataBaseContants.CONTENT_URI_POINT, updateValues, null, null);
        cr.update(DataBaseContants.CONTENT_URI_OPERATION, updateValues, null, null);
        cr.update(DataBaseContants.CONTENT_URI_GPSSPORT, updateValues, null, null);
    }

    private void emove_db_delete() {
        DBOpenHelper mDBOpenHelper = new DBOpenHelper(this.mContext);
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM gps_sync");
        db.close();
        mDBOpenHelper.close();
    }

    private int ImportCSVFile(String filePath) throws RemoteException, OperationApplicationException {
        File downfile = new File(filePath);
        if (downfile == null || !downfile.exists()) {
            return 0;
        }
        String[] filename = new String[]{"gps_info_down", "operation_info_down", "point_info_down"};
        ArrayList<ContentProviderOperation> operations = new ArrayList();
        for (String mFile : filename) {
            ArrayList<ArrayList<String>> resLists = CvsUtils.parseFile(filePath, mFile);
            if (resLists != null && resLists.size() > 0) {
                int i;
                ArrayList<String> subList;
                if (mFile.equals("point_info_down")) {
                    for (i = 0; i < resLists.size(); i++) {
                        subList = (ArrayList) resLists.get(i);
                        Log.i("hello", "subList1:" + subList.size());
                        operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI_POINT).withValue("_id", V_NULL(subList, 0)).withValue("latitude", V_NULL(subList, 2)).withValue(DataBaseContants.LONGTITUDE, V_NULL(subList, 3)).withValue("address", V_NULL(subList, 4)).withValue(DataBaseContants.ACCURACY, V_NULL(subList, 5)).withValue("provider", V_NULL(subList, 6)).withValue(DataBaseContants.LOCATION_TIME, V_NULL(subList, 7)).withValue(DataBaseContants.LOCATION_SYS_TIME, V_NULL(subList, 8)).withValue(DataBaseContants.SPEED, V_NULL(subList, 9)).withValue(DataBaseContants.ALTITUDE, V_NULL(subList, 10)).withValue(DataBaseContants.LOCATION_POINT_STATE, V_NULL(subList, 11)).withValue(DataBaseContants.GPS_NUMBER, V_NULL(subList, 12)).withValue(DataBaseContants.GPS_SYNC, "1").withYieldAllowed(true).build());
                    }
                } else if (mFile.equals("operation_info_down")) {
                    for (i = 0; i < resLists.size(); i++) {
                        subList = (ArrayList) resLists.get(i);
                        Log.i("hello", "subList2:" + subList.size());
                        operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI_OPERATION).withValue("_id", V_NULL(subList, 0)).withValue(DataBaseContants.OPERATION_TIME, V_NULL(subList, 2)).withValue(DataBaseContants.OPERATION_SYSTIME, V_NULL(subList, 3)).withValue(DataBaseContants.OPERATION_STATE, V_NULL(subList, 4)).withValue(DataBaseContants.GPS_SYNC, "1").withYieldAllowed(true).build());
                    }
                } else if (mFile.equals("gps_info_down")) {
                    for (i = 0; i < resLists.size(); i++) {
                        subList = (ArrayList) resLists.get(i);
                        Log.i("hello", "subList3:" + subList.size());
                        Log.i("lsj", "V_NULL(subList,18)==" + V_NULL(subList, 18));
                        operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI_GPSSPORT).withValue("_id", V_NULL(subList, 0)).withValue(DataBaseContants.GPS_STARTTIME, V_NULL(subList, 2)).withValue(DataBaseContants.GPS_ENDTIME, V_NULL(subList, 3)).withValue(DataBaseContants.GPS_SYSSTARTTIME, V_NULL(subList, 4)).withValue(DataBaseContants.GPS_SYSENDTIME, V_NULL(subList, 5)).withValue(DataBaseContants.GPS_DURATIONTIME, V_NULL(subList, 6)).withValue(DataBaseContants.AVESPEED, V_NULL(subList, 7)).withValue(DataBaseContants.TOTAL_DISTANCE, V_NULL(subList, 8)).withValue("steps", V_NULL(subList, 9)).withValue(DataBaseContants.GPS_CALORIE, V_NULL(subList, 10)).withValue(DataBaseContants.GPS_SYNC, "1").withValue(DataBaseContants.HEART_RATE_COUNT, V_NULL(subList, 18)).withYieldAllowed(true).build());
                    }
                }
            }
        }
        int res = -1;
        if (operations == null || operations.size() <= 0) {
            return res;
        }
        try {
            return this.mContext.getContentResolver().applyBatch(DataBaseContants.AUTHORITY, operations).length;
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return res;
        }
    }

    static String V_NULL(String s) {
        if (s == null || s == "" || s.equals("")) {
            return null;
        }
        return s;
    }

    static String V_NULL(ArrayList<String> list, int index) {
        String s = null;
        if (list == null || list.size() == 0) {
            return null;
        }
        if (list.size() > index) {
            s = V_NULL((String) list.get(index));
        }
        return s;
    }
}
