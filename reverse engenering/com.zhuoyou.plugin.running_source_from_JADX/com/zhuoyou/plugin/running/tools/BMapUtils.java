package com.zhuoyou.plugin.running.tools;

import android.content.Context;
import android.graphics.BitmapFactory;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.droi.greendao.bean.GpsPointBean;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.TheApp;
import java.util.LinkedList;
import java.util.List;

public class BMapUtils {
    public static Marker addMakerToMap(BaiduMap bMap, int resId, LatLng position) {
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(position).anchor(0.5f, 0.5f);
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(TheApp.getInstance().getResources(), resId)));
        return (Marker) bMap.addOverlay(markerOption);
    }

    public static Polyline addLineToMap(BaiduMap bMap, List<LatLng> list, boolean isVirtual) {
        if (list.size() < 2) {
            return null;
        }
        return (Polyline) bMap.addOverlay(getPolylineOptions().points(list).dottedLine(isVirtual));
    }

    private static Polyline initLineToMap(BaiduMap bMap, List<GpsPointBean> list, boolean isVirtual) {
        List<LatLng> temp = new LinkedList();
        for (GpsPointBean item : list) {
            temp.add(new LatLng(item.getLatitude(), item.getLongitude()));
        }
        return addLineToMap(bMap, temp, isVirtual);
    }

    private static PolylineOptions getPolylineOptions() {
        return new PolylineOptions().color(TheApp.getContext().getResources().getColor(C1680R.color.background_color_main)).width(Tools.dip2px(3.0f));
    }

    public static LocationClient getDefLocationClient(Context context, LocationClient client) {
        if (client == null) {
            client = new LocationClient(context);
        }
        client.setLocOption(getDefLocationClientOption());
        return client;
    }

    public static LocationClientOption getDefLocationClientOption() {
        LocationClientOption mOption = new LocationClientOption();
        mOption.setLocationMode(LocationMode.Hight_Accuracy);
        mOption.setCoorType("bd09ll");
        mOption.setScanSpan(MessageHandler.WHAT_ITEM_SELECTED);
        mOption.setIsNeedAddress(true);
        mOption.setIsNeedLocationDescribe(true);
        mOption.setNeedDeviceDirect(true);
        mOption.setLocationNotify(false);
        mOption.setIgnoreKillProcess(true);
        mOption.setIsNeedLocationDescribe(true);
        mOption.setIsNeedLocationPoiList(false);
        mOption.SetIgnoreCacheException(false);
        mOption.setIsNeedAltitude(false);
        mOption.setOpenGps(true);
        return mOption;
    }

    public static boolean isLocationSuccess(BDLocation location) {
        if (location == null) {
            return false;
        }
        int type = location.getLocType();
        if (type == 61 || type == 161 || type == 66) {
            return true;
        }
        return false;
    }
}
