package com.amap.api.services.busline;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

public class BusStationItem implements Parcelable {
    public static final Creator<BusStationItem> CREATOR = new C03161();
    private String f1049a;
    private String f1050b;
    private LatLonPoint f1051c;
    private String f1052d;
    private String f1053e;
    private List<BusLineItem> f1054f;

    static class C03161 implements Creator<BusStationItem> {
        C03161() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1138a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1139a(i);
        }

        public BusStationItem m1138a(Parcel parcel) {
            return new BusStationItem(parcel);
        }

        public BusStationItem[] m1139a(int i) {
            return null;
        }
    }

    public BusStationItem() {
        this.f1054f = new ArrayList();
    }

    public String getBusStationId() {
        return this.f1049a;
    }

    public void setBusStationId(String str) {
        this.f1049a = str;
    }

    public String getBusStationName() {
        return this.f1050b;
    }

    public void setBusStationName(String str) {
        this.f1050b = str;
    }

    public LatLonPoint getLatLonPoint() {
        return this.f1051c;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.f1051c = latLonPoint;
    }

    public String getCityCode() {
        return this.f1052d;
    }

    public void setCityCode(String str) {
        this.f1052d = str;
    }

    public String getAdCode() {
        return this.f1053e;
    }

    public void setAdCode(String str) {
        this.f1053e = str;
    }

    public List<BusLineItem> getBusLineItems() {
        return this.f1054f;
    }

    public void setBusLineItems(List<BusLineItem> list) {
        this.f1054f = list;
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
        BusStationItem busStationItem = (BusStationItem) obj;
        if (this.f1049a == null) {
            if (busStationItem.f1049a != null) {
                return false;
            }
            return true;
        } else if (this.f1049a.equals(busStationItem.f1049a)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int i;
        if (this.f1049a == null) {
            i = 0;
        } else {
            i = this.f1049a.hashCode();
        }
        return i + 31;
    }

    public String toString() {
        return "BusStationName: " + this.f1050b + " LatLonPoint: " + this.f1051c.toString() + " BusLines: " + m1140a(this.f1054f) + " CityCode: " + this.f1052d + " AdCode: " + this.f1053e;
    }

    private String m1140a(List<BusLineItem> list) {
        StringBuffer stringBuffer = new StringBuffer();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                stringBuffer.append(((BusLineItem) list.get(i)).getBusLineName());
                if (i < list.size() - 1) {
                    stringBuffer.append("|");
                }
            }
        }
        return stringBuffer.toString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1050b);
        parcel.writeString(this.f1049a);
        parcel.writeValue(this.f1051c);
        parcel.writeString(this.f1052d);
        parcel.writeString(this.f1053e);
        parcel.writeList(this.f1054f);
    }

    private BusStationItem(Parcel parcel) {
        this.f1054f = new ArrayList();
        this.f1050b = parcel.readString();
        this.f1049a = parcel.readString();
        this.f1051c = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.f1052d = parcel.readString();
        this.f1053e = parcel.readString();
        this.f1054f = parcel.readArrayList(BusLineItem.class.getClassLoader());
    }
}
