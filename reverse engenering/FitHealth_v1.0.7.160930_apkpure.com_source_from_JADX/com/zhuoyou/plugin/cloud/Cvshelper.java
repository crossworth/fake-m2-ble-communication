package com.zhuoyou.plugin.cloud;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.os.RemoteException;
import android.util.Log;
import com.zhuoyou.plugin.database.DBOpenHelper;
import com.zhuoyou.plugin.database.DataBaseContants;
import com.zhuoyou.plugin.running.Tools;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Cvshelper {
    public static final String SPORT_ADD_FILE = "sport_info_add";
    public static final String SPORT_DELETE_FILE = "sport_info_delete";
    public static final String SPORT_DOWN_FILE = "mars_info_down";
    public static final String SPORT_LOCAL_FILE = "sport_info_local";
    public static final String SPORT_UPDATE_FILE = "sport_info_update";
    private static ContentResolver mContentResolver;
    boolean Debug = true;
    String TAG = "CVS";
    String accountid = "";
    SQLiteDatabase database;
    Context mContext;
    private int postSportType;

    public Cvshelper(Context context, int type) {
        this.mContext = context;
        this.accountid = Tools.getOpenId(this.mContext);
        mContentResolver = context.getContentResolver();
        this.postSportType = type;
    }

    public void ExportDateCSVBYTYPE(int type) {
        Cursor cursor = null;
        ContentResolver cr = this.mContext.getContentResolver();
        File file = new File(GetDir(), SPORT_ADD_FILE);
        file = new File(GetDir(), SPORT_UPDATE_FILE);
        file = new File(GetDir(), SPORT_LOCAL_FILE);
        file = new File(GetDir(), SPORT_DELETE_FILE);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e22) {
                e22.printStackTrace();
            }
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e222) {
                e222.printStackTrace();
            }
        }
        if (this.postSportType == 2) {
            if (type == 0) {
                cursor = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "date", DataBaseContants.TIME_DURATION, DataBaseContants.TIME_START, DataBaseContants.TIME_END, "steps", DataBaseContants.KILOMETER, DataBaseContants.CALORIES, DataBaseContants.CONF_WEIGHT, DataBaseContants.BMI, DataBaseContants.IMG_URI, DataBaseContants.EXPLAIN, DataBaseContants.SPORTS_TYPE, "type", DataBaseContants.IMG_CLOUD, DataBaseContants.COMPLETE, DataBaseContants.STATISTICS, DataBaseContants.DATA_FROM, DataBaseContants.HEART_RATE_COUNT}, "sync  = ? ", new String[]{Integer.toString(0)}, null);
            } else if (type == 1) {
                cursor = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "date", DataBaseContants.TIME_DURATION, DataBaseContants.TIME_START, DataBaseContants.TIME_END, "steps", DataBaseContants.KILOMETER, DataBaseContants.CALORIES, DataBaseContants.CONF_WEIGHT, DataBaseContants.BMI, DataBaseContants.IMG_URI, DataBaseContants.EXPLAIN, DataBaseContants.SPORTS_TYPE, "type", DataBaseContants.IMG_CLOUD, DataBaseContants.COMPLETE, DataBaseContants.STATISTICS, DataBaseContants.DATA_FROM, DataBaseContants.HEART_RATE_COUNT}, "sync  = ? ", new String[]{Integer.toString(2)}, null);
            } else if (type == 2) {
                cursor = cr.query(DataBaseContants.CONTENT_DELETE_URI, new String[]{DataBaseContants.DELETE_VALUE}, null, null, null);
            } else if (type == 3) {
                cursor = cr.query(DataBaseContants.CONTENT_URI, new String[]{"_id"}, null, null, null);
            }
            int count = cursor.getCount();
            if (count > 0 && cursor.moveToFirst()) {
                BufferedWriter bfw;
                int i;
                if (type == 0) {
                    bfw = new BufferedWriter(new FileWriter(file));
                    do {
                        i = 0;
                        while (i < 19) {
                            if (i == 0) {
                                bfw.write(cursor.getString(i) + ',' + this.accountid + ',');
                            } else if (i == 18) {
                                try {
                                    if (!(cursor.getString(i) == null || cursor.getString(i) == "")) {
                                        bfw.write(cursor.getString(i));
                                    }
                                } catch (IOException e2222) {
                                    e2222.printStackTrace();
                                }
                            } else if (cursor.getString(i) == null || cursor.getString(i) == "") {
                                bfw.write(44);
                            } else {
                                bfw.write(cursor.getString(i) + ',');
                            }
                            i++;
                        }
                        bfw.newLine();
                    } while (cursor.moveToNext());
                    bfw.flush();
                    bfw.close();
                } else if (type == 1) {
                    try {
                        bfw = new BufferedWriter(new FileWriter(file));
                        do {
                            i = 0;
                            while (i < 19) {
                                if (i == 0) {
                                    bfw.write(cursor.getString(i) + ',' + this.accountid + ',');
                                } else if (i == 18) {
                                    if (!(cursor.getString(i) == null || cursor.getString(i) == "")) {
                                        bfw.write(cursor.getString(i));
                                    }
                                } else if (cursor.getString(i) == null || cursor.getString(i) == "") {
                                    bfw.write(44);
                                } else {
                                    bfw.write(cursor.getString(i) + ',');
                                }
                                i++;
                            }
                            bfw.newLine();
                        } while (cursor.moveToNext());
                        bfw.flush();
                        bfw.close();
                    } catch (IOException e22222) {
                        e22222.printStackTrace();
                    }
                } else if (type == 2) {
                    try {
                        bfw = new BufferedWriter(new FileWriter(file));
                        for (i = 0; i < count; i++) {
                            bfw.write(cursor.getLong(cursor.getColumnIndex(DataBaseContants.DELETE_VALUE)) + "");
                            bfw.newLine();
                            cursor.moveToNext();
                        }
                        bfw.flush();
                        bfw.close();
                    } catch (IOException e222222) {
                        e222222.printStackTrace();
                    }
                } else if (type == 3) {
                    try {
                        bfw = new BufferedWriter(new FileWriter(file));
                        for (i = 0; i < count; i++) {
                            bfw.write(cursor.getLong(cursor.getColumnIndex("_id")) + "");
                            bfw.newLine();
                            cursor.moveToNext();
                        }
                        bfw.flush();
                        bfw.close();
                    } catch (IOException e2222222) {
                        e2222222.printStackTrace();
                    }
                }
            }
            cursor.close();
        }
    }

    public void ExportCVSToZip(String zipFileString) throws Exception {
        String path = GetDir() + "/" + zipFileString;
        ZipOutputStream outZip = new ZipOutputStream(new FileOutputStream(path));
        String[] filename = new String[]{SPORT_ADD_FILE, SPORT_UPDATE_FILE, SPORT_LOCAL_FILE, SPORT_DELETE_FILE, GPSDataSync.GPS_SPORT_ADD, GPSDataSync.GPS_SPORT_DELETE, GPSDataSync.GPS_SPORT_UPDATE, GPSDataSync.GPS_OPERATION_ADD, GPSDataSync.GPS_OPERATION_DELETE, GPSDataSync.GPS_OPERATION_UPDATE, GPSDataSync.GPS_POINT_ADD, GPSDataSync.GPS_POINT_DELETE, GPSDataSync.GPS_POINT_UPDATE};
        if (this.Debug) {
            Log.d("zhouzhongbo", "path = " + path);
        }
        String filePath = GetDir();
        for (int i = 0; i < filename.length; i++) {
            if (new File(filePath, filename[i]).exists()) {
                ZipEntry zipEntry = new ZipEntry(filename[i]);
                FileInputStream inputStream = new FileInputStream(new File(GetDir() + "/" + filename[i]));
                outZip.putNextEntry(zipEntry);
                byte[] buffer = new byte[4096];
                while (true) {
                    int len = inputStream.read(buffer);
                    if (len == -1) {
                        break;
                    }
                    outZip.write(buffer, 0, len);
                }
                outZip.closeEntry();
                inputStream.close();
            }
        }
        outZip.close();
    }

    public void ImportCSVFile(Context con) {
        List<String> tmp = new ArrayList();
        List<String> ids = new ArrayList();
        int last_position = 0;
        File downfile = new File(GetDir() + "/" + SPORT_DOWN_FILE);
        if (downfile != null && downfile.exists()) {
            ArrayList<ContentProviderOperation> operations = new ArrayList();
            try {
                BufferedReader br = new BufferedReader(new FileReader(downfile));
                while (true) {
                    String s = br.readLine();
                    if (s == null) {
                        break;
                    }
                    Log.i("wangchao", "s= " + s);
                    int i = 0;
                    while (i < s.length()) {
                        if (s.charAt(i) == ',') {
                            if (last_position != 0) {
                                tmp.add(s.substring(last_position + 1, i));
                            } else {
                                tmp.add(s.substring(last_position, i));
                            }
                            last_position = i;
                        }
                        i++;
                    }
                    tmp.add(s.substring(last_position + 1, i));
                    String id = (String) tmp.get(0);
                    String day;
                    Cursor c;
                    if (((String) tmp.get(16)).equals("1")) {
                        day = (String) tmp.get(1);
                        c = mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id"}, "date  = ? AND statistics = ? ", new String[]{day, "1"}, null);
                        if (c.getCount() > 0 && c.moveToFirst()) {
                            deleteDateInfo(con, Long.valueOf((String) tmp.get(0)).longValue());
                        } else if (ids.indexOf(id) == -1) {
                            operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue("steps", tmp.get(5) != "" ? (String) tmp.get(5) : null).withValue(DataBaseContants.KILOMETER, tmp.get(6) != "" ? (String) tmp.get(6) : null).withValue(DataBaseContants.CALORIES, tmp.get(7) != "" ? (String) tmp.get(7) : null).withValue(DataBaseContants.BMI, tmp.get(9) != "" ? (String) tmp.get(9) : null).withValue("type", tmp.get(13) != "" ? (String) tmp.get(13) : null).withValue(DataBaseContants.COMPLETE, tmp.get(15)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(1)).withYieldAllowed(true).build());
                            ids.add(id);
                        }
                        c.close();
                    } else if (((String) tmp.get(16)).equals("2")) {
                        day = (String) tmp.get(1);
                        c = mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id"}, "date  = ? AND statistics = ? AND data_from = ? ", new String[]{day, "2", (String) tmp.get(17)}, null);
                        if (c.getCount() > 0 && c.moveToFirst()) {
                            deleteDateInfo(con, Long.valueOf((String) tmp.get(0)).longValue());
                        } else if (ids.indexOf(id) == -1) {
                            operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue("steps", tmp.get(5) != "" ? (String) tmp.get(5) : null).withValue(DataBaseContants.KILOMETER, tmp.get(6) != "" ? (String) tmp.get(6) : null).withValue(DataBaseContants.CALORIES, tmp.get(7) != "" ? (String) tmp.get(7) : null).withValue("type", tmp.get(13) != "" ? (String) tmp.get(13) : null).withValue(DataBaseContants.COMPLETE, tmp.get(15)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(2)).withValue(DataBaseContants.DATA_FROM, data_from).withYieldAllowed(true).build());
                            ids.add(id);
                        }
                        c.close();
                    } else {
                        int type = Integer.parseInt((String) tmp.get(13));
                        if (type == 1) {
                            day = (String) tmp.get(1);
                            c = mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id"}, "date = ? AND type = ? AND statistics = ? ", new String[]{day, "1", "0"}, null);
                            if (c.getCount() > 0 && c.moveToFirst()) {
                                deleteDateInfo(con, Long.valueOf((String) tmp.get(0)).longValue());
                            } else if (ids.indexOf(id) == -1) {
                                operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.CONF_WEIGHT, tmp.get(8) != "" ? (String) tmp.get(8) : null).withValue(DataBaseContants.BMI, tmp.get(9) != "" ? (String) tmp.get(9) : null).withValue("type", Integer.valueOf(type)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withYieldAllowed(true).build());
                                ids.add(id);
                            }
                        } else if (type == 2) {
                            if (Integer.parseInt((String) tmp.get(12)) == 0) {
                                day = (String) tmp.get(1);
                                String start = (String) tmp.get(3);
                                String end = (String) tmp.get(4);
                                c = mContentResolver.query(DataBaseContants.CONTENT_URI, new String[]{"_id", "steps"}, "date  = ? AND time_start = ? AND sports_type = ? AND statistics = ? AND data_from = ? ", new String[]{day, start, "0", "0", (String) tmp.get(17)}, null);
                                if (c.getCount() > 0 && c.moveToFirst()) {
                                    deleteDateInfo(con, Long.valueOf((String) tmp.get(0)).longValue());
                                } else if (ids.indexOf(id) == -1) {
                                    operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_DURATION, tmp.get(2) != "" ? (String) tmp.get(2) : null).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.TIME_END, tmp.get(4) != "" ? (String) tmp.get(4) : null).withValue("steps", tmp.get(5) != "" ? (String) tmp.get(5) : null).withValue(DataBaseContants.KILOMETER, tmp.get(6) != "" ? (String) tmp.get(6) : null).withValue(DataBaseContants.CALORIES, tmp.get(7) != "" ? (String) tmp.get(7) : null).withValue(DataBaseContants.SPORTS_TYPE, tmp.get(12)).withValue("type", tmp.get(13)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withValue(DataBaseContants.DATA_FROM, data_from).withYieldAllowed(true).build());
                                    ids.add(id);
                                }
                                c.close();
                            } else if (ids.indexOf(id) == -1) {
                                operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_DURATION, tmp.get(2) != "" ? (String) tmp.get(2) : null).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.TIME_END, tmp.get(4) != "" ? (String) tmp.get(4) : null).withValue("steps", tmp.get(5) != "" ? (String) tmp.get(5) : null).withValue(DataBaseContants.KILOMETER, tmp.get(6) != "" ? (String) tmp.get(6) : null).withValue(DataBaseContants.CALORIES, tmp.get(7) != "" ? (String) tmp.get(7) : null).withValue(DataBaseContants.SPORTS_TYPE, tmp.get(12)).withValue("type", Integer.valueOf(type)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withYieldAllowed(true).build());
                                ids.add(id);
                            }
                        } else if (type == 3) {
                            if (ids.indexOf(id) == -1) {
                                operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.IMG_URI, tmp.get(10) != "" ? (String) tmp.get(10) : null).withValue(DataBaseContants.EXPLAIN, tmp.get(11) != "" ? (String) tmp.get(11) : null).withValue("type", Integer.valueOf(type)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withYieldAllowed(true).build());
                                ids.add(id);
                            }
                        } else if (type == 4) {
                            if (ids.indexOf(id) == -1) {
                                operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.EXPLAIN, tmp.get(11) != "" ? (String) tmp.get(11) : null).withValue("type", Integer.valueOf(type)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withYieldAllowed(true).build());
                                ids.add(id);
                            }
                        } else if (type == 5) {
                            if (ids.indexOf(id) == -1) {
                                Log.i("lsj", "tmp size==" + tmp.size());
                                r3 = ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_DURATION, tmp.get(2) != "" ? (String) tmp.get(2) : null).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.TIME_END, tmp.get(4) != "" ? (String) tmp.get(4) : null).withValue("steps", tmp.get(5) != "" ? (String) tmp.get(5) : null).withValue(DataBaseContants.KILOMETER, tmp.get(6) != "" ? (String) tmp.get(6) : null).withValue(DataBaseContants.CALORIES, tmp.get(7) != "" ? (String) tmp.get(7) : null).withValue(DataBaseContants.CONF_WEIGHT, tmp.get(8) != "" ? (String) tmp.get(8) : null).withValue(DataBaseContants.BMI, tmp.get(9) != "" ? (String) tmp.get(9) : null).withValue(DataBaseContants.IMG_URI, tmp.get(10) != "" ? (String) tmp.get(10) : null).withValue(DataBaseContants.EXPLAIN, tmp.get(11) != "" ? (String) tmp.get(11) : null).withValue("type", Integer.valueOf(type)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withValue(DataBaseContants.DATA_FROM, tmp.get(17) != "" ? (String) tmp.get(17) : null);
                                r4 = DataBaseContants.HEART_RATE_COUNT;
                                r2 = tmp.size() >= 19 ? tmp.get(18) != "" ? (String) tmp.get(18) : null : null;
                                operations.add(r3.withValue(r4, r2).withYieldAllowed(true).build());
                                ids.add(id);
                            }
                        } else if (type == 7 && ids.indexOf(id) == -1) {
                            r3 = ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI).withValue("_id", tmp.get(0)).withValue("date", tmp.get(1)).withValue(DataBaseContants.TIME_DURATION, tmp.get(2) != "" ? (String) tmp.get(2) : null).withValue(DataBaseContants.TIME_START, tmp.get(3) != "" ? (String) tmp.get(3) : null).withValue(DataBaseContants.TIME_END, tmp.get(4) != "" ? (String) tmp.get(4) : null).withValue("steps", tmp.get(5) != "" ? (String) tmp.get(5) : null).withValue("type", Integer.valueOf(type)).withValue(DataBaseContants.SYNC_STATE, Integer.valueOf(1)).withValue(DataBaseContants.STATISTICS, Integer.valueOf(0)).withValue(DataBaseContants.DATA_FROM, tmp.get(17) != "" ? (String) tmp.get(17) : null);
                            r4 = DataBaseContants.HEART_RATE_COUNT;
                            r2 = tmp.size() >= 19 ? tmp.get(18) != "" ? (String) tmp.get(18) : null : null;
                            operations.add(r3.withValue(r4, r2).withYieldAllowed(true).build());
                            ids.add(id);
                        }
                    }
                    last_position = 0;
                    tmp.clear();
                }
                br.close();
                ids.clear();
                if (operations != null && operations.size() > 0) {
                    mContentResolver.applyBatch(DataBaseContants.AUTHORITY, operations);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (RemoteException e3) {
                e3.printStackTrace();
            } catch (OperationApplicationException e4) {
                e4.printStackTrace();
            }
        }
    }

    public void UpdataLocalDate() {
        if (this.postSportType == 2) {
            emove_db_update();
            emove_db_delete();
        }
    }

    private void emove_db_update() {
        ContentResolver cr = this.mContext.getContentResolver();
        ContentValues updateValues = new ContentValues();
        updateValues.put(DataBaseContants.SYNC_STATE, Integer.valueOf(1));
        cr.update(DataBaseContants.CONTENT_URI, updateValues, null, null);
    }

    private void emove_db_delete() {
        DBOpenHelper mDBOpenHelper = new DBOpenHelper(this.mContext);
        SQLiteDatabase db = mDBOpenHelper.getWritableDatabase();
        db.execSQL("DELETE FROM cloud;");
        db.execSQL("update sqlite_sequence set seq=0 where name='cloud'");
        mDBOpenHelper.close();
    }

    public String GetDir() {
        String MOFEI_DIR;
        if (Environment.getExternalStorageState().equals("mounted")) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            MOFEI_DIR = Environment.getExternalStorageDirectory() + "/emove_tmp";
        } else {
            MOFEI_DIR = "com/zhuoyou/plugin/running/emove_tmp";
        }
        if (this.Debug) {
            Log.d("zhouzhongbo", "my dir = " + MOFEI_DIR);
        }
        File destDir = new File(MOFEI_DIR);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        return MOFEI_DIR;
    }

    public void CVSUnzipFile(String zipFileString, String outPathString) throws Exception {
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        String szName = "";
        File decodepath = new File(outPathString);
        if (!decodepath.exists()) {
            decodepath.mkdirs();
        } else if (!decodepath.isDirectory()) {
            decodepath.delete();
            decodepath.mkdir();
        }
        while (true) {
            ZipEntry zipEntry = inZip.getNextEntry();
            if (zipEntry != null) {
                szName = zipEntry.getName();
                if (this.Debug) {
                    Log.d("zhouzhongbo", "szName =" + szName);
                }
                if (zipEntry.isDirectory()) {
                    new File(outPathString + File.separator + szName.substring(0, szName.length() - 1)).mkdirs();
                } else {
                    File file = new File(outPathString + File.separator + szName);
                    file.createNewFile();
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = inZip.read(buffer);
                        if (len == -1) {
                            break;
                        }
                        out.write(buffer, 0, len);
                        out.flush();
                    }
                    out.close();
                }
            } else {
                inZip.close();
                return;
            }
        }
    }

    public void CVSCloseDb() {
        this.database.close();
    }

    private static void deleteDateInfo(Context con, long id) {
        ContentValues values = new ContentValues();
        values.put(DataBaseContants.DELETE_VALUE, Long.valueOf(id));
        con.getContentResolver().insert(DataBaseContants.CONTENT_DELETE_URI, values);
    }
}
