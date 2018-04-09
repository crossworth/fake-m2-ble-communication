package com.zhuoyou.plugin.database;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.amap.api.services.core.AMapException;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.gps.GpsSportDataModel;
import com.zhuoyou.plugin.gps.GuidePointModel;
import com.zhuoyou.plugin.gps.OperationTimeModel;
import com.zhuoyou.plugin.running.RunningItem;
import com.zhuoyou.plugin.running.SleepBean;
import com.zhuoyou.plugin.running.SleepItem;
import com.zhuoyou.plugin.running.SleepTools;
import com.zhuoyou.plugin.running.Tools;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DataBaseUtil {
    private Context mContext;

    public DataBaseUtil(Context mCon) {
        this.mContext = mCon;
    }

    public void inserPoint(GuidePointModel guidePointModel) {
        ContentValues pointItem = new ContentValues();
        if (guidePointModel != null) {
            pointItem.put("_id", Long.valueOf(guidePointModel.getGuideId()));
            pointItem.put("latitude", Double.valueOf(guidePointModel.getLatitude()));
            pointItem.put(DataBaseContants.LONGTITUDE, Double.valueOf(guidePointModel.getLongitude()));
            pointItem.put("address", guidePointModel.getAddress());
            pointItem.put(DataBaseContants.ACCURACY, Float.valueOf(guidePointModel.getAccuracy()));
            pointItem.put("provider", guidePointModel.getProvider());
            pointItem.put(DataBaseContants.LOCATION_TIME, Long.valueOf(guidePointModel.getTime()));
            pointItem.put(DataBaseContants.SPEED, Float.valueOf(guidePointModel.getSpeed()));
            pointItem.put(DataBaseContants.ALTITUDE, Double.valueOf(guidePointModel.getAltitude()));
            pointItem.put(DataBaseContants.GPS_NUMBER, Integer.valueOf(guidePointModel.getGpsStatus()));
            pointItem.put(DataBaseContants.LOCATION_SYS_TIME, Long.valueOf(guidePointModel.getSysTime()));
            pointItem.put(DataBaseContants.LOCATION_POINT_STATE, Integer.valueOf(guidePointModel.getPointState()));
            pointItem.put(DataBaseContants.GPS_SYNC, Integer.valueOf(guidePointModel.getSyncState()));
        }
        Uri result = this.mContext.getContentResolver().insert(DataBaseContants.CONTENT_URI_POINT, pointItem);
    }

    public void inserTempPoint(GuidePointModel guidePointModel) {
        ContentValues pointItem = new ContentValues();
        if (guidePointModel != null) {
            pointItem.put("latitude", Double.valueOf(guidePointModel.getLatitude()));
            pointItem.put(DataBaseContants.LONGTITUDE, Double.valueOf(guidePointModel.getLongitude()));
            pointItem.put("address", guidePointModel.getAddress());
            pointItem.put(DataBaseContants.ACCURACY, Float.valueOf(guidePointModel.getAccuracy()));
            pointItem.put("provider", guidePointModel.getProvider());
            pointItem.put(DataBaseContants.LOCATION_TIME, Long.valueOf(guidePointModel.getTime()));
            pointItem.put(DataBaseContants.SPEED, Float.valueOf(guidePointModel.getSpeed()));
            pointItem.put(DataBaseContants.ALTITUDE, Double.valueOf(guidePointModel.getAltitude()));
            pointItem.put(DataBaseContants.GPS_NUMBER, Integer.valueOf(guidePointModel.getGpsStatus()));
            pointItem.put(DataBaseContants.LOCATION_SYS_TIME, Long.valueOf(guidePointModel.getSysTime()));
            pointItem.put(DataBaseContants.LOCATION_POINT_STATE, Integer.valueOf(guidePointModel.getPointState()));
            pointItem.put(DataBaseContants.GPS_SYNC, Integer.valueOf(guidePointModel.getSyncState()));
        }
        Uri result = this.mContext.getContentResolver().insert(DataBaseContants.CONTENT_URI_TEMPPOINT, pointItem);
    }

    public void inserPoint(List<GuidePointModel> listPoint) {
        ArrayList<ContentProviderOperation> operations = new ArrayList();
        for (int i = 0; i < listPoint.size(); i++) {
            GuidePointModel guidePointModel = (GuidePointModel) listPoint.get(i);
            if (guidePointModel != null) {
                operations.add(ContentProviderOperation.newInsert(DataBaseContants.CONTENT_URI_POINT).withValue("_id", Long.valueOf(guidePointModel.getGuideId())).withValue("latitude", Double.valueOf(guidePointModel.getLatitude())).withValue(DataBaseContants.LONGTITUDE, Double.valueOf(guidePointModel.getLongitude())).withValue("address", guidePointModel.getAddress()).withValue(DataBaseContants.ACCURACY, Float.valueOf(guidePointModel.getAccuracy())).withValue("provider", guidePointModel.getProvider()).withValue(DataBaseContants.LOCATION_TIME, Long.valueOf(guidePointModel.getTime())).withValue(DataBaseContants.LOCATION_SYS_TIME, Long.valueOf(guidePointModel.getSysTime())).withValue(DataBaseContants.SPEED, Float.valueOf(guidePointModel.getSpeed())).withValue(DataBaseContants.ALTITUDE, Double.valueOf(guidePointModel.getAltitude())).withValue(DataBaseContants.LOCATION_POINT_STATE, Integer.valueOf(guidePointModel.getPointState())).withValue(DataBaseContants.GPS_NUMBER, Integer.valueOf(guidePointModel.getGpsStatus())).withValue(DataBaseContants.GPS_SYNC, Integer.valueOf(guidePointModel.getSyncState())).withYieldAllowed(true).build());
            }
        }
        if (operations != null && operations.size() > 0) {
            try {
                this.mContext.getContentResolver().applyBatch(DataBaseContants.AUTHORITY, operations);
            } catch (Exception e) {
                Log.e("DataBaseUtil", e.getMessage());
            }
        }
    }

    public void insertOperation(OperationTimeModel operationTimeModel) {
        ContentValues operationItem = new ContentValues();
        if (operationTimeModel != null) {
            operationItem.put("_id", Long.valueOf(operationTimeModel.getOperatId()));
            operationItem.put(DataBaseContants.OPERATION_TIME, Long.valueOf(operationTimeModel.getOperationtime()));
            operationItem.put(DataBaseContants.OPERATION_SYSTIME, Long.valueOf(operationTimeModel.getOperationSystime()));
            operationItem.put(DataBaseContants.OPERATION_STATE, Integer.valueOf(operationTimeModel.getOperationState()));
            operationItem.put(DataBaseContants.GPS_SYNC, Integer.valueOf(operationTimeModel.getSyncState()));
        }
        this.mContext.getContentResolver().insert(DataBaseContants.CONTENT_URI_OPERATION, operationItem);
    }

    public void insertGpsInfo(GpsSportDataModel gpsSportDataModel) {
        ContentValues gpsInfoItem = new ContentValues();
        if (gpsSportDataModel != null) {
            Log.i("lsj", "gpsSportDataModel.getGpsId() =" + gpsSportDataModel.getGpsId());
            gpsInfoItem.put("_id", Long.valueOf(gpsSportDataModel.getGpsId()));
            gpsInfoItem.put(DataBaseContants.GPS_STARTTIME, Long.valueOf(gpsSportDataModel.getStarttime()));
            gpsInfoItem.put(DataBaseContants.GPS_ENDTIME, Long.valueOf(gpsSportDataModel.getEndtime()));
            gpsInfoItem.put(DataBaseContants.GPS_SYSSTARTTIME, Long.valueOf(gpsSportDataModel.getStarSysttime()));
            gpsInfoItem.put(DataBaseContants.GPS_SYSENDTIME, Long.valueOf(gpsSportDataModel.getEndSystime()));
            gpsInfoItem.put(DataBaseContants.GPS_DURATIONTIME, Long.valueOf(gpsSportDataModel.getDurationtime()));
            gpsInfoItem.put(DataBaseContants.AVESPEED, Double.valueOf(gpsSportDataModel.getAvespeed()));
            gpsInfoItem.put(DataBaseContants.TOTAL_DISTANCE, Double.valueOf(gpsSportDataModel.getTotalDistance()));
            gpsInfoItem.put("steps", Integer.valueOf(gpsSportDataModel.getSteps()));
            gpsInfoItem.put(DataBaseContants.GPS_CALORIE, Double.valueOf(gpsSportDataModel.getCalorie()));
            gpsInfoItem.put(DataBaseContants.GPS_STARTADDRESS, gpsSportDataModel.getStartAddress());
            gpsInfoItem.put(DataBaseContants.GPS_ENDADDRESS, gpsSportDataModel.getEndAddress());
            gpsInfoItem.put(DataBaseContants.GPS_SYNC, Integer.valueOf(gpsSportDataModel.getSyncState()));
            gpsInfoItem.put(DataBaseContants.HEART_RATE_COUNT, gpsSportDataModel.getHeartCount());
        }
        this.mContext.getContentResolver().insert(DataBaseContants.CONTENT_URI_GPSSPORT, gpsInfoItem);
    }

    public void deletePoint(long starttime, long endtime) {
        this.mContext.getContentResolver().delete(DataBaseContants.CONTENT_URI_POINT, "location_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)});
    }

    public void deleteTempPoint(long starttime, long endtime) {
        this.mContext.getContentResolver().delete(DataBaseContants.CONTENT_URI_TEMPPOINT, "location_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)});
    }

    public void deleteOperation(long starttime, long endtime) {
        this.mContext.getContentResolver().delete(DataBaseContants.CONTENT_URI_OPERATION, "operation_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)});
    }

    public void deleteGpsInfo(long starttime) {
        this.mContext.getContentResolver().delete(DataBaseContants.CONTENT_URI_GPSSPORT, "starttime = ?", new String[]{Long.toString(starttime)});
    }

    public void deleteGpsFromID(long gpsId) {
        this.mContext.getContentResolver().delete(DataBaseContants.CONTENT_URI_GPSSPORT, "_id = ?", new String[]{Long.toString(gpsId)});
    }

    public void deleteGpsInfo(long starttime, long endtime) {
        this.mContext.getContentResolver().delete(DataBaseContants.CONTENT_URI_GPSSPORT, "starttime between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)});
    }

    public List<GuidePointModel> selectPoint(long starttime, long endtime) {
        List<GuidePointModel> pointList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_POINT, new String[]{"latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.GPS_NUMBER, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_SYNC}, "location_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                GuidePointModel mpoint = new GuidePointModel();
                mpoint.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                mpoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.LONGTITUDE)));
                mpoint.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                mpoint.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.ACCURACY)));
                mpoint.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
                mpoint.setTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_TIME)));
                mpoint.setSysTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_SYS_TIME)));
                mpoint.setSpeed(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.SPEED)));
                mpoint.setAltitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.ALTITUDE)));
                mpoint.setGpsStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_NUMBER)));
                mpoint.setPointState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.LOCATION_POINT_STATE)));
                mpoint.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                pointList.add(mpoint);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return pointList;
    }

    public List<GuidePointModel> selectTempPoint() {
        List<GuidePointModel> pointList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_TEMPPOINT, new String[]{"latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.GPS_NUMBER, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_SYNC}, null, null, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                GuidePointModel mpoint = new GuidePointModel();
                mpoint.setGuideId(Tools.getPKL());
                mpoint.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                mpoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.LONGTITUDE)));
                mpoint.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                mpoint.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.ACCURACY)));
                mpoint.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
                mpoint.setTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_TIME)));
                mpoint.setSysTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_SYS_TIME)));
                mpoint.setSpeed(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.SPEED)));
                mpoint.setAltitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.ALTITUDE)));
                mpoint.setGpsStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_NUMBER)));
                mpoint.setPointState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.LOCATION_POINT_STATE)));
                mpoint.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                pointList.add(mpoint);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return pointList;
    }

    public List<GuidePointModel> selectPoint(long starttime, long endtime, int pointstate) {
        List<GuidePointModel> pointList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_POINT, new String[]{"latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.GPS_NUMBER, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_SYNC}, "location_time between ? and ? and point_state > ?", new String[]{Long.toString(starttime), Long.toString(endtime), pointstate + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                GuidePointModel mpoint = new GuidePointModel();
                mpoint.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                mpoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.LONGTITUDE)));
                mpoint.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                mpoint.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.ACCURACY)));
                mpoint.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
                mpoint.setTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_TIME)));
                mpoint.setSysTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_SYS_TIME)));
                mpoint.setSpeed(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.SPEED)));
                mpoint.setAltitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.ALTITUDE)));
                mpoint.setGpsStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_NUMBER)));
                mpoint.setPointState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.LOCATION_POINT_STATE)));
                mpoint.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                pointList.add(mpoint);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return pointList;
    }

    public List<GuidePointModel> selectTempPoint(int pointstate) {
        List<GuidePointModel> pointList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_TEMPPOINT, new String[]{"latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.GPS_NUMBER, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_SYNC}, "point_state < ?", new String[]{pointstate + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                GuidePointModel mpoint = new GuidePointModel();
                mpoint.setGuideId(Tools.getPKL());
                mpoint.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
                mpoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.LONGTITUDE)));
                mpoint.setAddress(cursor.getString(cursor.getColumnIndex("address")));
                mpoint.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.ACCURACY)));
                mpoint.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
                mpoint.setTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_TIME)));
                mpoint.setSysTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_SYS_TIME)));
                mpoint.setSpeed(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.SPEED)));
                mpoint.setAltitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.ALTITUDE)));
                mpoint.setGpsStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_NUMBER)));
                mpoint.setPointState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.LOCATION_POINT_STATE)));
                mpoint.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                pointList.add(mpoint);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return pointList;
    }

    public List<Integer> selectPointID(long starttime, long endtime, int syncState) {
        List<Integer> listPointId = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_POINT, new String[]{"_id"}, "location_time between ? and ? and sync_state != ?", new String[]{Long.toString(starttime), Long.toString(endtime), syncState + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                listPointId.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return listPointId;
    }

    public GuidePointModel selectFirstPoint(long starttime, long endtime) {
        GuidePointModel firstPonit = new GuidePointModel();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_POINT, new String[]{"latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.GPS_NUMBER, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_SYNC}, "location_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            firstPonit.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            firstPonit.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.LONGTITUDE)));
            firstPonit.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            firstPonit.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.ACCURACY)));
            firstPonit.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
            firstPonit.setTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_TIME)));
            firstPonit.setSysTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_SYS_TIME)));
            firstPonit.setSpeed(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.SPEED)));
            firstPonit.setAltitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.ALTITUDE)));
            firstPonit.setGpsStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_NUMBER)));
            firstPonit.setPointState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.LOCATION_POINT_STATE)));
            firstPonit.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
        }
        cursor.close();
        return firstPonit;
    }

    public GuidePointModel selectLasttPoint(long starttime, long endtime) {
        GuidePointModel firstPonit = new GuidePointModel();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_POINT, new String[]{"latitude", DataBaseContants.LONGTITUDE, "address", DataBaseContants.ACCURACY, "provider", DataBaseContants.LOCATION_TIME, DataBaseContants.LOCATION_SYS_TIME, DataBaseContants.SPEED, DataBaseContants.ALTITUDE, DataBaseContants.GPS_NUMBER, DataBaseContants.LOCATION_POINT_STATE, DataBaseContants.GPS_SYNC}, "location_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)}, null);
        if (cursor.getCount() > 0 && cursor.moveToLast()) {
            firstPonit.setLatitude(cursor.getDouble(cursor.getColumnIndex("latitude")));
            firstPonit.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.LONGTITUDE)));
            firstPonit.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            firstPonit.setAccuracy(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.ACCURACY)));
            firstPonit.setProvider(cursor.getString(cursor.getColumnIndex("provider")));
            firstPonit.setTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_TIME)));
            firstPonit.setSysTime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.LOCATION_SYS_TIME)));
            firstPonit.setSpeed(cursor.getFloat(cursor.getColumnIndex(DataBaseContants.SPEED)));
            firstPonit.setAltitude(cursor.getDouble(cursor.getColumnIndex(DataBaseContants.ALTITUDE)));
            firstPonit.setGpsStatus(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_NUMBER)));
            firstPonit.setPointState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.LOCATION_POINT_STATE)));
            firstPonit.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
        }
        cursor.close();
        return firstPonit;
    }

    public List<OperationTimeModel> selectOperation(long starttime, long endtime) {
        List<OperationTimeModel> operationList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_OPERATION, new String[]{"*"}, "operation_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                OperationTimeModel mOperation = new OperationTimeModel();
                mOperation.setOperationtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_TIME)));
                mOperation.setOperationSystime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_SYSTIME)));
                mOperation.setOperationState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.OPERATION_STATE)));
                mOperation.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                operationList.add(mOperation);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return operationList;
    }

    public List<OperationTimeModel> selectOperation(long starttime, long endtime, int operation) {
        List<OperationTimeModel> operationList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_OPERATION, new String[]{"*"}, "operation_time between ? and ? and operation_state = ? ", new String[]{Long.toString(starttime), Long.toString(endtime), operation + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                OperationTimeModel mOperation = new OperationTimeModel();
                mOperation.setOperationtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_TIME)));
                mOperation.setOperationSystime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_SYSTIME)));
                mOperation.setOperationState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.OPERATION_STATE)));
                mOperation.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                operationList.add(mOperation);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return operationList;
    }

    public List<Integer> selectOperationId(long starttime, long endtime, int syncState) {
        List<Integer> operationListId = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_OPERATION, new String[]{"*"}, "operation_time between ? and ? and sync_state != ? ", new String[]{Long.toString(starttime), Long.toString(endtime), syncState + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                operationListId.add(Integer.valueOf(cursor.getInt(cursor.getColumnIndex("_id"))));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return operationListId;
    }

    public long selectLastOperation(int operation) {
        long startTime = 0;
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_OPERATION, new String[]{DataBaseContants.OPERATION_TIME}, "operation_state = ?", new String[]{operation + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToLast()) {
            startTime = cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_TIME));
        }
        cursor.close();
        return startTime;
    }

    public long selectOperSysTime(int operation) {
        long startTime = 0;
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_OPERATION, new String[]{DataBaseContants.OPERATION_SYSTIME}, "operation_state = ?", new String[]{operation + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToLast()) {
            startTime = cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_SYSTIME));
        }
        cursor.close();
        return startTime;
    }

    public List<OperationTimeModel> selectlistOperTime(int operation) {
        List<OperationTimeModel> operationList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_OPERATION, new String[]{"*"}, "operation_state = ?", new String[]{operation + ""}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                OperationTimeModel mOperation = new OperationTimeModel();
                mOperation.setOperationtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_TIME)));
                mOperation.setOperationSystime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.OPERATION_SYSTIME)));
                mOperation.setOperationState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.OPERATION_STATE)));
                mOperation.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                operationList.add(mOperation);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return operationList;
    }

    public List<GpsSportDataModel> selectGpsInfo(long starttime, long endtime) {
        List<GpsSportDataModel> gpsInfoList = new ArrayList();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_GPSSPORT, new String[]{"*"}, "starttime between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getCount(); i++) {
                GpsSportDataModel mGpsInfo = new GpsSportDataModel();
                mGpsInfo.setStarttime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_STARTTIME)));
                mGpsInfo.setEndtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_ENDTIME)));
                mGpsInfo.setStarSysttime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_SYSSTARTTIME)));
                mGpsInfo.setEndSystime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_SYSENDTIME)));
                mGpsInfo.setDurationtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_DURATIONTIME)));
                mGpsInfo.setAvespeed((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.AVESPEED)));
                mGpsInfo.setTotalDistance((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.TOTAL_DISTANCE)));
                mGpsInfo.setSteps(cursor.getInt(cursor.getColumnIndex("steps")));
                mGpsInfo.setCalorie((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.GPS_CALORIE)));
                mGpsInfo.setStartAddress(cursor.getString(cursor.getColumnIndex(DataBaseContants.GPS_STARTADDRESS)));
                mGpsInfo.setEndAddress(cursor.getString(cursor.getColumnIndex(DataBaseContants.GPS_ENDADDRESS)));
                mGpsInfo.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
                mGpsInfo.setHeartCount(cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT)));
                gpsInfoList.add(mGpsInfo);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return gpsInfoList;
    }

    public GpsSportDataModel selectGpsInfo(long starttime) {
        GpsSportDataModel mGpsInfo = new GpsSportDataModel();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_GPSSPORT, new String[]{"*"}, "starttime = ?", new String[]{Long.toString(starttime)}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            mGpsInfo.setStarttime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_STARTTIME)));
            mGpsInfo.setEndtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_ENDTIME)));
            mGpsInfo.setStarSysttime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_SYSSTARTTIME)));
            mGpsInfo.setEndSystime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_SYSENDTIME)));
            mGpsInfo.setDurationtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_DURATIONTIME)));
            mGpsInfo.setAvespeed((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.AVESPEED)));
            mGpsInfo.setTotalDistance((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.TOTAL_DISTANCE)));
            mGpsInfo.setSteps(cursor.getInt(cursor.getColumnIndex("steps")));
            mGpsInfo.setCalorie((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.GPS_CALORIE)));
            mGpsInfo.setStartAddress(cursor.getString(cursor.getColumnIndex(DataBaseContants.GPS_STARTADDRESS)));
            mGpsInfo.setEndAddress(cursor.getString(cursor.getColumnIndex(DataBaseContants.GPS_ENDADDRESS)));
            mGpsInfo.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
            mGpsInfo.setHeartCount(cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT)));
        }
        cursor.close();
        return mGpsInfo;
    }

    public GpsSportDataModel selectGpsInfoForID(long gpsId) {
        GpsSportDataModel mGpsInfo = new GpsSportDataModel();
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_GPSSPORT, new String[]{"*"}, "_id = ?", new String[]{Long.toString(gpsId)}, null);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            mGpsInfo.setStarttime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_STARTTIME)));
            mGpsInfo.setEndtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_ENDTIME)));
            mGpsInfo.setStarSysttime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_SYSSTARTTIME)));
            mGpsInfo.setEndSystime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_SYSENDTIME)));
            mGpsInfo.setDurationtime(cursor.getLong(cursor.getColumnIndex(DataBaseContants.GPS_DURATIONTIME)));
            mGpsInfo.setAvespeed((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.AVESPEED)));
            mGpsInfo.setTotalDistance((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.TOTAL_DISTANCE)));
            mGpsInfo.setSteps(cursor.getInt(cursor.getColumnIndex("steps")));
            mGpsInfo.setCalorie((double) cursor.getFloat(cursor.getColumnIndex(DataBaseContants.GPS_CALORIE)));
            mGpsInfo.setStartAddress(cursor.getString(cursor.getColumnIndex(DataBaseContants.GPS_STARTADDRESS)));
            mGpsInfo.setEndAddress(cursor.getString(cursor.getColumnIndex(DataBaseContants.GPS_ENDADDRESS)));
            mGpsInfo.setSyncState(cursor.getInt(cursor.getColumnIndex(DataBaseContants.GPS_SYNC)));
            mGpsInfo.setHeartCount(cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT)));
            Log.i("lsj", "heart count = " + cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT)));
        }
        cursor.close();
        return mGpsInfo;
    }

    public double[] findGpsBound(long starttime, long endtime) {
        infos = new double[4];
        Cursor cursor = this.mContext.getContentResolver().query(DataBaseContants.CONTENT_URI_POINT, new String[]{"MAX(latitude),MAX(longtitude),MIN(latitude),MIN(longtitude)"}, "location_time between ? and ?", new String[]{Long.toString(starttime), Long.toString(endtime)}, null);
        Log.i("hello", "s:" + starttime + ",e:" + endtime);
        if (cursor.getCount() > 0 && cursor.moveToFirst()) {
            infos[0] = cursor.getDouble(0);
            infos[1] = cursor.getDouble(1);
            infos[2] = cursor.getDouble(2);
            infos[3] = cursor.getDouble(3);
            Log.i("hello", infos[0] + SeparatorConstants.SEPARATOR_ADS_ID + infos[1] + SeparatorConstants.SEPARATOR_ADS_ID + infos[2] + SeparatorConstants.SEPARATOR_ADS_ID + infos[3] + SeparatorConstants.SEPARATOR_ADS_ID);
        }
        cursor.close();
        for (double info : infos) {
            if (info == 0.0d) {
                return null;
            }
        }
        return infos;
    }

    public void updateGpsInfo(GpsSportDataModel gpsSportDataModel) {
        ContentValues gpsInfoItem = new ContentValues();
        if (gpsSportDataModel != null) {
            gpsInfoItem.put(DataBaseContants.GPS_STARTTIME, Long.valueOf(gpsSportDataModel.getStarttime()));
            gpsInfoItem.put(DataBaseContants.GPS_ENDTIME, Long.valueOf(gpsSportDataModel.getEndtime()));
            gpsInfoItem.put(DataBaseContants.GPS_SYSSTARTTIME, Long.valueOf(gpsSportDataModel.getStarSysttime()));
            gpsInfoItem.put(DataBaseContants.GPS_SYSENDTIME, Long.valueOf(gpsSportDataModel.getEndSystime()));
            gpsInfoItem.put(DataBaseContants.GPS_DURATIONTIME, Long.valueOf(gpsSportDataModel.getDurationtime()));
            gpsInfoItem.put(DataBaseContants.AVESPEED, Double.valueOf(gpsSportDataModel.getAvespeed()));
            gpsInfoItem.put(DataBaseContants.TOTAL_DISTANCE, Double.valueOf(gpsSportDataModel.getTotalDistance()));
            gpsInfoItem.put("steps", Integer.valueOf(gpsSportDataModel.getSteps()));
            gpsInfoItem.put(DataBaseContants.GPS_CALORIE, Double.valueOf(gpsSportDataModel.getCalorie()));
            gpsInfoItem.put(DataBaseContants.GPS_STARTADDRESS, gpsSportDataModel.getStartAddress());
            gpsInfoItem.put(DataBaseContants.GPS_ENDADDRESS, gpsSportDataModel.getEndAddress());
            gpsInfoItem.put(DataBaseContants.GPS_SYNC, Integer.valueOf(gpsSportDataModel.getSyncState()));
            gpsInfoItem.put(DataBaseContants.HEART_RATE_COUNT, gpsSportDataModel.getHeartCount());
        }
        this.mContext.getContentResolver().update(DataBaseContants.CONTENT_URI_GPSSPORT, gpsInfoItem, "_id= ?", new String[]{Long.toString(gpsSportDataModel.getGpsId())});
    }

    public static boolean UpdateGPSInfo(Context mCtx, double latitude, double longtitude, String address) {
        ContentResolver cr = mCtx.getContentResolver();
        String[] selectionArgs = new String[]{latitude + "", longtitude + ""};
        ContentValues values = new ContentValues();
        values.put("address", address);
        int res = cr.update(DataBaseContants.CONTENT_URI_POINT, values, "latitude= ? and longtitude = ? ", selectionArgs);
        Log.i("hello", "updateData:" + res);
        if (res == -1) {
            return false;
        }
        return true;
    }

    public static void insertSleep(Context mCtx, int type, long startTime, long endTime, String turnData) {
        SQLiteDatabase sqlDB = new DBOpenHelper(mCtx).getWritableDatabase();
        String[] columns = new String[]{"_id"};
        String[] selectionArgs = new String[]{"" + type, "" + startTime, "" + endTime, turnData};
        Cursor mCursor = sqlDB.query(DataBaseContants.TABLE_SLEEP, columns, "type = ? and start_time = ? and end_time = ? and turn_data = ?  ", selectionArgs, null, null, null);
        if (mCursor == null || (mCursor != null && mCursor.getCount() == 0)) {
            ContentValues values = new ContentValues();
            values.put("_id", Long.valueOf(Tools.getPKL()));
            values.put("type", Integer.valueOf(type));
            values.put("start_time", Long.valueOf(startTime));
            values.put("end_time", Long.valueOf(endTime));
            values.put(DataBaseContants.SLEEP_TURNDATA, turnData);
            sqlDB.insert(DataBaseContants.TABLE_SLEEP, null, values);
        }
        if (mCursor != null) {
            mCursor.close();
        }
    }

    public static List<SleepItem> getSleepItem(Context mCtx, long start, long end) {
        DecimalFormat decimalFormat = new DecimalFormat("#00");
        List<SleepItem> sleepItems = new ArrayList();
        String[] columns = new String[]{"_id", "type", "start_time", "end_time", DataBaseContants.SLEEP_TURNDATA};
        String[] selectionArgs = new String[]{"0", "" + start, "" + end};
        Cursor mCursor = new DBOpenHelper(mCtx).getWritableDatabase().query(DataBaseContants.TABLE_SLEEP, columns, "type = ? and end_time >= ?  and  end_time <= ? ", selectionArgs, null, null, null);
        if (mCursor != null) {
            while (mCursor.moveToNext()) {
                long id = mCursor.getLong(mCursor.getColumnIndex("_id"));
                int type = mCursor.getInt(mCursor.getColumnIndex("type"));
                long startTime = mCursor.getLong(mCursor.getColumnIndex("start_time"));
                long endTime = mCursor.getLong(mCursor.getColumnIndex("end_time"));
                String turnData = mCursor.getString(mCursor.getColumnIndex(DataBaseContants.SLEEP_TURNDATA));
                Calendar startCal = SleepTools.getCalendar(startTime);
                Calendar endCal = SleepTools.getCalendar(endTime);
                Log.i("hepenghui", "startCal=" + SleepTools.getCalendar(startTime));
                Log.i("hepenghui", "endCal=" + SleepTools.getCalendar(endTime));
                long timeSub = endCal.getTimeInMillis() - startCal.getTimeInMillis();
                Log.i("hepenghui", "timeSub=" + endCal.getTimeInMillis());
                Log.i("hepenghui", "timeSub=" + startCal.getTimeInMillis());
                int sub = (((int) timeSub) / 1000) / AMapException.CODE_AMAP_CLIENT_ERRORCODE_MISSSING;
                Log.i("hepenghui", "sub=" + ((((int) timeSub) / 1000) / AMapException.CODE_AMAP_CLIENT_ERRORCODE_MISSSING));
                List<Integer> turnArray = SleepTools.getData(turnData, sub);
                List<SleepBean> beans = SleepTools.getSleepBean(startCal, endCal, turnArray);
                Log.i("hph1", "beans=" + beans);
                int deepSleep = SleepTools.getDeepSleep(turnArray);
                int lightSleep = (int) ((timeSub / 1000) - ((long) deepSleep));
                SleepItem item = new SleepItem();
                item.setId(id);
                item.setStartCal(startCal);
                item.setEndCal(endCal);
                item.setmDSleepT(deepSleep);
                item.setmWSleepT(lightSleep);
                item.setData(beans);
                item.setmSleepT(SleepTools.getDurationTime(startTime, endTime));
                String startTimeString = decimalFormat.format((long) startCal.get(11)) + ":" + decimalFormat.format((long) startCal.get(12));
                String endTimeString = decimalFormat.format((long) endCal.get(11)) + ":" + decimalFormat.format((long) endCal.get(12));
                item.setmStartT(startTimeString);
                item.setmEndT(endTimeString);
                Log.i("hepenghui", "setmStartT=" + startTimeString);
                Log.i("hepenghui", "setmEndT=" + endTimeString);
                sleepItems.add(item);
            }
        }
        return sleepItems;
    }

    public static void insertClassicSleep(Context mCtx, String date, String details) {
        SQLiteDatabase sqlDB = new DBOpenHelper(mCtx).getWritableDatabase();
        String[] columns = new String[]{"_id"};
        String[] selectionArgs = new String[]{date, details};
        Cursor mCursor = sqlDB.query(DataBaseContants.TABLE_SLEEP_2, columns, "date = ? and sleep_details =? ", selectionArgs, null, null, null);
        mCursor.moveToFirst();
        if (mCursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put(DataBaseContants.SLEEP_DETAILS, details);
            sqlDB.insert(DataBaseContants.TABLE_SLEEP_2, null, values);
        }
        sqlDB.close();
        mCursor.close();
    }

    public static List<SleepItem> getClassicSleepItem(Context mCtx, String date) {
        SQLiteDatabase sqlDB = new DBOpenHelper(mCtx).getWritableDatabase();
        String[] columns = new String[]{"_id", "date", DataBaseContants.SLEEP_DETAILS};
        String[] selectionArgs = new String[]{date};
        Cursor mCursor = sqlDB.query(DataBaseContants.TABLE_SLEEP_2, columns, "date = ? ", selectionArgs, null, null, null);
        mCursor.moveToFirst();
        StringBuilder details = new StringBuilder();
        int count = mCursor.getCount();
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                details.append(mCursor.getString(mCursor.getColumnIndex(DataBaseContants.SLEEP_DETAILS)));
                mCursor.moveToNext();
            }
        }
        sqlDB.close();
        mCursor.close();
        List<SleepItem> sleepItems = new ArrayList();
        String[] s = details.toString().split("\\|");
        int number = s.length;
        if (number > 1) {
            String startTime;
            String endTime;
            if (Integer.parseInt(s[1]) >= 2100) {
                startTime = Tools.getDate(date, 1).replaceAll(SocializeConstants.OP_DIVIDER_MINUS, "") + s[1];
            } else {
                startTime = date.replaceAll(SocializeConstants.OP_DIVIDER_MINUS, "") + s[1];
            }
            if (Integer.parseInt(s[number - 1]) >= 2100) {
                endTime = date.replaceAll(SocializeConstants.OP_DIVIDER_MINUS, "") + s[number - 1];
            } else {
                endTime = Tools.getDate(date, -1).replaceAll(SocializeConstants.OP_DIVIDER_MINUS, "") + s[number - 1];
            }
            Calendar startCal = SleepTools.getCalendar(Long.valueOf(startTime).longValue());
            Calendar endCal = SleepTools.getCalendar(Long.valueOf(endTime).longValue());
            int deepSleep = SleepTools.getDeepSleep2(s);
            int lightSleep = SleepTools.getlightSleep2(s);
            List<SleepBean> beans = SleepTools.getSleepBean2(s);
            SleepItem item = new SleepItem();
            item.setStartCal(startCal);
            item.setEndCal(endCal);
            item.setmDSleepT(deepSleep);
            item.setmWSleepT(lightSleep);
            item.setData(beans);
            item.setmSleepT(SleepTools.getIntervalTime(startTime, endTime));
            item.setmStartT(SleepTools.formatRemoteTime(s[1]));
            item.setmEndT(SleepTools.formatRemoteTime(s[number - 1]));
            sleepItems.add(item);
        }
        return sleepItems;
    }

    public static int deleteHeartDateByTime(String date, String time, Context mCtx) {
        return mCtx.getContentResolver().delete(DataBaseContants.CONTENT_URI, "type = ? AND date<=? AND time_start<?", new String[]{"7", date, time});
    }

    public static List<RunningItem> getHeartRateList(Context mCtx) {
        List<RunningItem> runningItemList = new ArrayList();
        Cursor cursor = mCtx.getContentResolver().query(DataBaseContants.CONTENT_URI, null, "type = 7 ", null, "date DESC, time_start DESC, _id DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                RunningItem runningItem = new RunningItem();
                String count = cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT));
                if (!(TextUtils.isEmpty(count) || count.equals("null"))) {
                    runningItem.setHeart_rate_count(cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT)));
                    runningItem.setID(cursor.getLong(cursor.getColumnIndex("_id")));
                    runningItem.setmType(cursor.getInt(cursor.getColumnIndex("type")));
                    runningItem.setHeart_rate_time(cursor.getLong(cursor.getColumnIndex(DataBaseContants.HEART_RATE_TIME)));
                    runningItem.setDate(cursor.getString(cursor.getColumnIndex("date")));
                    runningItem.setStartTime(cursor.getString(cursor.getColumnIndex(DataBaseContants.TIME_START)));
                    runningItemList.add(runningItem);
                }
            }
        }
        cursor.close();
        if (runningItemList.size() > 20) {
            return runningItemList.subList(0, 20);
        }
        return runningItemList;
    }

    public static List<RunningItem> getHeartRateByDate(Context mCtx, String date) {
        List<RunningItem> runningItemList = new ArrayList();
        Cursor cursor = mCtx.getContentResolver().query(DataBaseContants.CONTENT_URI, null, "type = ? AND date = ? ", new String[]{"7", date}, "date DESC, time_start DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                RunningItem runningItem = new RunningItem();
                String count = cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT));
                if (!(TextUtils.isEmpty(count) || count.equals("null"))) {
                    runningItem.setID(cursor.getLong(cursor.getColumnIndex("_id")));
                    runningItem.setmType(cursor.getInt(cursor.getColumnIndex("type")));
                    runningItem.setHeart_rate_count(cursor.getString(cursor.getColumnIndex(DataBaseContants.HEART_RATE_COUNT)));
                    runningItem.setHeart_rate_time(cursor.getLong(cursor.getColumnIndex(DataBaseContants.HEART_RATE_TIME)));
                    runningItem.setDate(cursor.getString(cursor.getColumnIndex("date")));
                    runningItem.setStartTime(cursor.getString(cursor.getColumnIndex(DataBaseContants.TIME_START)));
                    runningItemList.add(runningItem);
                }
            }
        }
        cursor.close();
        return runningItemList;
    }
}
