package com.baidu.mapapi.map.offline;

import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.map.C0646s;
import com.baidu.platform.comapi.map.C0649v;
import java.util.ArrayList;
import java.util.Iterator;

public class OfflineMapUtil {
    public static MKOLSearchRecord getSearchRecordFromLocalCityInfo(C0646s c0646s) {
        if (c0646s == null) {
            return null;
        }
        int i;
        MKOLSearchRecord mKOLSearchRecord = new MKOLSearchRecord();
        mKOLSearchRecord.cityID = c0646s.f2109a;
        mKOLSearchRecord.cityName = c0646s.f2110b;
        mKOLSearchRecord.cityType = c0646s.f2112d;
        if (c0646s.m2060a() != null) {
            ArrayList arrayList = new ArrayList();
            Iterator it = c0646s.m2060a().iterator();
            i = 0;
            while (it.hasNext()) {
                C0646s c0646s2 = (C0646s) it.next();
                arrayList.add(getSearchRecordFromLocalCityInfo(c0646s2));
                int i2 = c0646s2.f2111c + i;
                mKOLSearchRecord.childCities = arrayList;
                i = i2;
            }
        } else {
            i = 0;
        }
        if (mKOLSearchRecord.cityType == 1) {
            mKOLSearchRecord.size = i;
        } else {
            mKOLSearchRecord.size = c0646s.f2111c;
        }
        return mKOLSearchRecord;
    }

    public static MKOLUpdateElement getUpdatElementFromLocalMapElement(C0649v c0649v) {
        if (c0649v == null) {
            return null;
        }
        MKOLUpdateElement mKOLUpdateElement = new MKOLUpdateElement();
        mKOLUpdateElement.cityID = c0649v.f2120a;
        mKOLUpdateElement.cityName = c0649v.f2121b;
        if (c0649v.f2126g != null) {
            mKOLUpdateElement.geoPt = CoordUtil.mc2ll(c0649v.f2126g);
        }
        mKOLUpdateElement.level = c0649v.f2124e;
        mKOLUpdateElement.ratio = c0649v.f2128i;
        mKOLUpdateElement.serversize = c0649v.f2127h;
        if (c0649v.f2128i == 100) {
            mKOLUpdateElement.size = c0649v.f2127h;
        } else {
            mKOLUpdateElement.size = (c0649v.f2127h / 100) * c0649v.f2128i;
        }
        mKOLUpdateElement.status = c0649v.f2131l;
        mKOLUpdateElement.update = c0649v.f2129j;
        return mKOLUpdateElement;
    }
}
