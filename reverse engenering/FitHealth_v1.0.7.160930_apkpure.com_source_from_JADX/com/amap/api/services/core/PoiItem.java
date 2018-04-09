package com.amap.api.services.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.poisearch.IndoorData;
import com.amap.api.services.poisearch.SubPoiItem;
import java.util.ArrayList;
import java.util.List;

public class PoiItem implements Parcelable {
    public static final Creator<PoiItem> CREATOR = new C03221();
    private String f1103a;
    private String f1104b;
    private String f1105c;
    private String f1106d;
    private String f1107e = "";
    private int f1108f = -1;
    private LatLonPoint f1109g;
    private LatLonPoint f1110h;
    private String f1111i;
    private String f1112j;
    private String f1113k;
    private String f1114l;
    private String f1115m;
    protected final LatLonPoint mPoint;
    protected final String mSnippet;
    protected final String mTitle;
    private String f1116n;
    private String f1117o;
    private boolean f1118p;
    private IndoorData f1119q;
    private String f1120r;
    private String f1121s;
    private String f1122t;
    private List<SubPoiItem> f1123u = new ArrayList();

    static class C03221 implements Creator<PoiItem> {
        C03221() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1164a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1165a(i);
        }

        public PoiItem m1164a(Parcel parcel) {
            return new PoiItem(parcel);
        }

        public PoiItem[] m1165a(int i) {
            return new PoiItem[i];
        }
    }

    public PoiItem(String str, LatLonPoint latLonPoint, String str2, String str3) {
        this.f1103a = str;
        this.mPoint = latLonPoint;
        this.mTitle = str2;
        this.mSnippet = str3;
    }

    public String getBusinessArea() {
        return this.f1121s;
    }

    public void setBusinessArea(String str) {
        this.f1121s = str;
    }

    public String getAdName() {
        return this.f1117o;
    }

    public void setAdName(String str) {
        this.f1117o = str;
    }

    public String getCityName() {
        return this.f1116n;
    }

    public void setCityName(String str) {
        this.f1116n = str;
    }

    public String getProvinceName() {
        return this.f1115m;
    }

    public void setProvinceName(String str) {
        this.f1115m = str;
    }

    public String getTypeDes() {
        return this.f1107e;
    }

    public void setTypeDes(String str) {
        this.f1107e = str;
    }

    public String getTel() {
        return this.f1104b;
    }

    public void setTel(String str) {
        this.f1104b = str;
    }

    public String getAdCode() {
        return this.f1105c;
    }

    public void setAdCode(String str) {
        this.f1105c = str;
    }

    public String getPoiId() {
        return this.f1103a;
    }

    public int getDistance() {
        return this.f1108f;
    }

    public void setDistance(int i) {
        this.f1108f = i;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public String getSnippet() {
        return this.mSnippet;
    }

    public LatLonPoint getLatLonPoint() {
        return this.mPoint;
    }

    public String getCityCode() {
        return this.f1106d;
    }

    public void setCityCode(String str) {
        this.f1106d = str;
    }

    public LatLonPoint getEnter() {
        return this.f1109g;
    }

    public void setEnter(LatLonPoint latLonPoint) {
        this.f1109g = latLonPoint;
    }

    public LatLonPoint getExit() {
        return this.f1110h;
    }

    public void setExit(LatLonPoint latLonPoint) {
        this.f1110h = latLonPoint;
    }

    public String getWebsite() {
        return this.f1111i;
    }

    public void setWebsite(String str) {
        this.f1111i = str;
    }

    public String getPostcode() {
        return this.f1112j;
    }

    public void setPostcode(String str) {
        this.f1112j = str;
    }

    public String getEmail() {
        return this.f1113k;
    }

    public void setEmail(String str) {
        this.f1113k = str;
    }

    public String getDirection() {
        return this.f1114l;
    }

    public void setDirection(String str) {
        this.f1114l = str;
    }

    public void setIndoorMap(boolean z) {
        this.f1118p = z;
    }

    public boolean isIndoorMap() {
        return this.f1118p;
    }

    public void setProvinceCode(String str) {
        this.f1120r = str;
    }

    public String getProvinceCode() {
        return this.f1120r;
    }

    public void setParkingType(String str) {
        this.f1122t = str;
    }

    public String getParkingType() {
        return this.f1122t;
    }

    public void setSubPois(List<SubPoiItem> list) {
        this.f1123u = list;
    }

    public List<SubPoiItem> getSubPois() {
        return this.f1123u;
    }

    public IndoorData getIndoorData() {
        return this.f1119q;
    }

    public void setIndoorDate(IndoorData indoorData) {
        this.f1119q = indoorData;
    }

    protected PoiItem(Parcel parcel) {
        this.f1103a = parcel.readString();
        this.f1105c = parcel.readString();
        this.f1104b = parcel.readString();
        this.f1107e = parcel.readString();
        this.f1108f = parcel.readInt();
        this.mPoint = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.mTitle = parcel.readString();
        this.mSnippet = parcel.readString();
        this.f1106d = parcel.readString();
        this.f1109g = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1110h = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1111i = parcel.readString();
        this.f1112j = parcel.readString();
        this.f1113k = parcel.readString();
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.f1118p = zArr[0];
        this.f1114l = parcel.readString();
        this.f1115m = parcel.readString();
        this.f1116n = parcel.readString();
        this.f1117o = parcel.readString();
        this.f1120r = parcel.readString();
        this.f1121s = parcel.readString();
        this.f1122t = parcel.readString();
        this.f1123u = parcel.readArrayList(SubPoiItem.class.getClassLoader());
        this.f1119q = (IndoorData) parcel.readValue(IndoorData.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1103a);
        parcel.writeString(this.f1105c);
        parcel.writeString(this.f1104b);
        parcel.writeString(this.f1107e);
        parcel.writeInt(this.f1108f);
        parcel.writeValue(this.mPoint);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mSnippet);
        parcel.writeString(this.f1106d);
        parcel.writeValue(this.f1109g);
        parcel.writeValue(this.f1110h);
        parcel.writeString(this.f1111i);
        parcel.writeString(this.f1112j);
        parcel.writeString(this.f1113k);
        parcel.writeBooleanArray(new boolean[]{this.f1118p});
        parcel.writeString(this.f1114l);
        parcel.writeString(this.f1115m);
        parcel.writeString(this.f1116n);
        parcel.writeString(this.f1117o);
        parcel.writeString(this.f1120r);
        parcel.writeString(this.f1121s);
        parcel.writeString(this.f1122t);
        parcel.writeList(this.f1123u);
        parcel.writeValue(this.f1119q);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        PoiItem poiItem = (PoiItem) obj;
        if (this.f1103a == null) {
            if (poiItem.f1103a != null) {
                return false;
            }
            return true;
        } else if (this.f1103a.equals(poiItem.f1103a)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (this.f1103a == null ? 0 : this.f1103a.hashCode()) + 31;
    }

    public String toString() {
        return this.mTitle;
    }
}
