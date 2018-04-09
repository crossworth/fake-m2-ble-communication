package com.zhuoyou.plugin.gps;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.GeocodeSearch.OnGeocodeSearchListener;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.List;

public class GpsUtil {
    public static List<GuidePointModel> handlePoint(List<GuidePointModel> listPoint) {
        if (listPoint.size() < 5) {
            return listPoint;
        }
        if (listPoint.size() > 4 && listPoint.size() < 10) {
            return avePoint(listPoint, 2);
        }
        if (listPoint.size() <= 9 || listPoint.size() >= 20) {
            return avePoint(listPoint, 6);
        }
        return avePoint(listPoint, 4);
    }

    private static List<GuidePointModel> avePoint(List<GuidePointModel> listPoint, int avepoint) {
        List<GuidePointModel> filterList = new ArrayList();
        List<GuidePointModel> smallList = new ArrayList();
        for (int i = 0; i < listPoint.size(); i++) {
            smallList.add(listPoint.get(i));
            if (smallList.size() == avepoint) {
                double totalLatitude = 0.0d;
                double totalLongitude = 0.0d;
                for (int j = 0; j < smallList.size(); j++) {
                    totalLatitude += ((GuidePointModel) smallList.get(j)).getLatitude();
                    totalLongitude += ((GuidePointModel) smallList.get(j)).getLongitude();
                }
                filterList.add(new GuidePointModel(totalLatitude / ((double) avepoint), totalLongitude / ((double) avepoint)));
                smallList.remove(0);
            }
        }
        return filterList;
    }

    public static void getAdresByNet(Context mCtx, final GuidePointModel mPoint, final Handler handle, final int flag) {
        final Message msg = handle.obtainMessage();
        GeocodeSearch mGSearch = new GeocodeSearch(mCtx);
        mGSearch.getFromLocationAsyn(new RegeocodeQuery(new LatLonPoint(mPoint.getLatitude(), mPoint.getLongitude()), 200.0f, GeocodeSearch.AMAP));
        mGSearch.setOnGeocodeSearchListener(new OnGeocodeSearchListener() {
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
                msg.what = flag;
                String address = "";
                if (rCode == 0 || rCode == 1000) {
                    address = regeocodeResult.getRegeocodeAddress().getFormatAddress();
                } else if (rCode == 27) {
                    Log.i("hello", "error_network");
                } else if (rCode == 32) {
                    Log.i("hello", "error_key");
                } else {
                    Log.i("hello", "error_other");
                }
                mPoint.setAddress(address);
                msg.obj = mPoint;
                handle.sendMessage(msg);
            }

            public void onGeocodeSearched(GeocodeResult regeocodeResult, int rCode) {
            }
        });
    }

    public static int updateSport(Context mContext, Long gpsId, String startAddress, String endAddress, Long durationTime, Float avgSpeed, Float totalDis, Integer step, Float calorie) {
        ContentResolver cr = mContext.getContentResolver();
        ContentValues updateValues = new ContentValues();
        updateValues.put(DataBaseContants.GPS_STARTADDRESS, startAddress);
        updateValues.put(DataBaseContants.GPS_ENDADDRESS, endAddress);
        if (durationTime != null) {
            updateValues.put(DataBaseContants.GPS_DURATIONTIME, durationTime);
        }
        if (avgSpeed != null) {
            updateValues.put(DataBaseContants.AVESPEED, avgSpeed);
        }
        if (totalDis != null) {
            updateValues.put(DataBaseContants.TOTAL_DISTANCE, totalDis);
        }
        if (step != null) {
            updateValues.put("steps", step);
        }
        if (calorie != null) {
            updateValues.put(DataBaseContants.GPS_CALORIE, calorie);
        }
        return cr.update(DataBaseContants.CONTENT_URI_GPSSPORT, updateValues, "_id = ? ", new String[]{String.valueOf(gpsId)});
    }
}
