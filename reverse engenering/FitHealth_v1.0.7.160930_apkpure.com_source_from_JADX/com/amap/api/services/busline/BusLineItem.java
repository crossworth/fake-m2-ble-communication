package com.amap.api.services.busline;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.proguard.C0390i;
import com.umeng.socialize.common.SocializeConstants;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusLineItem implements Parcelable {
    public static final Creator<BusLineItem> CREATOR = new C03151();
    private float f1022a;
    private String f1023b;
    private String f1024c;
    private String f1025d;
    private List<LatLonPoint> f1026e = new ArrayList();
    private List<LatLonPoint> f1027f = new ArrayList();
    private String f1028g;
    private String f1029h;
    private String f1030i;
    private Date f1031j;
    private Date f1032k;
    private String f1033l;
    private float f1034m;
    private float f1035n;
    private List<BusStationItem> f1036o = new ArrayList();

    static class C03151 implements Creator<BusLineItem> {
        C03151() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1135a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1136a(i);
        }

        public BusLineItem m1135a(Parcel parcel) {
            return new BusLineItem(parcel);
        }

        public BusLineItem[] m1136a(int i) {
            return null;
        }
    }

    public float getDistance() {
        return this.f1022a;
    }

    public void setDistance(float f) {
        this.f1022a = f;
    }

    public String getBusLineName() {
        return this.f1023b;
    }

    public void setBusLineName(String str) {
        this.f1023b = str;
    }

    public String getBusLineType() {
        return this.f1024c;
    }

    public void setBusLineType(String str) {
        this.f1024c = str;
    }

    public String getCityCode() {
        return this.f1025d;
    }

    public void setCityCode(String str) {
        this.f1025d = str;
    }

    public List<LatLonPoint> getDirectionsCoordinates() {
        return this.f1026e;
    }

    public void setDirectionsCoordinates(List<LatLonPoint> list) {
        this.f1026e = list;
    }

    public List<LatLonPoint> getBounds() {
        return this.f1027f;
    }

    public void setBounds(List<LatLonPoint> list) {
        this.f1027f = list;
    }

    public String getBusLineId() {
        return this.f1028g;
    }

    public void setBusLineId(String str) {
        this.f1028g = str;
    }

    public String getOriginatingStation() {
        return this.f1029h;
    }

    public void setOriginatingStation(String str) {
        this.f1029h = str;
    }

    public String getTerminalStation() {
        return this.f1030i;
    }

    public void setTerminalStation(String str) {
        this.f1030i = str;
    }

    public Date getFirstBusTime() {
        if (this.f1031j == null) {
            return null;
        }
        return (Date) this.f1031j.clone();
    }

    public void setFirstBusTime(Date date) {
        if (date == null) {
            this.f1031j = null;
        } else {
            this.f1031j = (Date) date.clone();
        }
    }

    public Date getLastBusTime() {
        if (this.f1032k == null) {
            return null;
        }
        return (Date) this.f1032k.clone();
    }

    public void setLastBusTime(Date date) {
        if (date == null) {
            this.f1032k = null;
        } else {
            this.f1032k = (Date) date.clone();
        }
    }

    public String getBusCompany() {
        return this.f1033l;
    }

    public void setBusCompany(String str) {
        this.f1033l = str;
    }

    public float getBasicPrice() {
        return this.f1034m;
    }

    public void setBasicPrice(float f) {
        this.f1034m = f;
    }

    public float getTotalPrice() {
        return this.f1035n;
    }

    public void setTotalPrice(float f) {
        this.f1035n = f;
    }

    public List<BusStationItem> getBusStations() {
        return this.f1036o;
    }

    public void setBusStations(List<BusStationItem> list) {
        this.f1036o = list;
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
        BusLineItem busLineItem = (BusLineItem) obj;
        if (this.f1028g == null) {
            if (busLineItem.f1028g != null) {
                return false;
            }
            return true;
        } else if (this.f1028g.equals(busLineItem.f1028g)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        int i;
        if (this.f1028g == null) {
            i = 0;
        } else {
            i = this.f1028g.hashCode();
        }
        return i + 31;
    }

    public String toString() {
        return this.f1023b + " " + C0390i.m1592a(this.f1031j) + SocializeConstants.OP_DIVIDER_MINUS + C0390i.m1592a(this.f1032k);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.f1022a);
        parcel.writeString(this.f1023b);
        parcel.writeString(this.f1024c);
        parcel.writeString(this.f1025d);
        parcel.writeList(this.f1026e);
        parcel.writeList(this.f1027f);
        parcel.writeString(this.f1028g);
        parcel.writeString(this.f1029h);
        parcel.writeString(this.f1030i);
        parcel.writeString(C0390i.m1592a(this.f1031j));
        parcel.writeString(C0390i.m1592a(this.f1032k));
        parcel.writeString(this.f1033l);
        parcel.writeFloat(this.f1034m);
        parcel.writeFloat(this.f1035n);
        parcel.writeList(this.f1036o);
    }

    public BusLineItem(Parcel parcel) {
        this.f1022a = parcel.readFloat();
        this.f1023b = parcel.readString();
        this.f1024c = parcel.readString();
        this.f1025d = parcel.readString();
        this.f1026e = parcel.readArrayList(LatLonPoint.class.getClassLoader());
        this.f1027f = parcel.readArrayList(LatLonPoint.class.getClassLoader());
        this.f1028g = parcel.readString();
        this.f1029h = parcel.readString();
        this.f1030i = parcel.readString();
        this.f1031j = C0390i.m1598d(parcel.readString());
        this.f1032k = C0390i.m1598d(parcel.readString());
        this.f1033l = parcel.readString();
        this.f1034m = parcel.readFloat();
        this.f1035n = parcel.readFloat();
        this.f1036o = parcel.readArrayList(BusStationItem.class.getClassLoader());
    }
}
