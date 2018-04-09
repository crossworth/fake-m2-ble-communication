package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

public class DriveStep implements Parcelable {
    public static final Creator<DriveStep> CREATOR = new C04201();
    private String f1594a;
    private String f1595b;
    private String f1596c;
    private float f1597d;
    private float f1598e;
    private float f1599f;
    private String f1600g;
    private float f1601h;
    private List<LatLonPoint> f1602i = new ArrayList();
    private String f1603j;
    private String f1604k;
    private List<RouteSearchCity> f1605l = new ArrayList();
    private List<TMC> f1606m = new ArrayList();

    static class C04201 implements Creator<DriveStep> {
        C04201() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1689a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1690a(i);
        }

        public DriveStep m1689a(Parcel parcel) {
            return new DriveStep(parcel);
        }

        public DriveStep[] m1690a(int i) {
            return null;
        }
    }

    public String getInstruction() {
        return this.f1594a;
    }

    public void setInstruction(String str) {
        this.f1594a = str;
    }

    public String getOrientation() {
        return this.f1595b;
    }

    public void setOrientation(String str) {
        this.f1595b = str;
    }

    public String getRoad() {
        return this.f1596c;
    }

    public void setRoad(String str) {
        this.f1596c = str;
    }

    public float getDistance() {
        return this.f1597d;
    }

    public void setDistance(float f) {
        this.f1597d = f;
    }

    public float getTolls() {
        return this.f1598e;
    }

    public void setTolls(float f) {
        this.f1598e = f;
    }

    public float getTollDistance() {
        return this.f1599f;
    }

    public void setTollDistance(float f) {
        this.f1599f = f;
    }

    public String getTollRoad() {
        return this.f1600g;
    }

    public void setTollRoad(String str) {
        this.f1600g = str;
    }

    public float getDuration() {
        return this.f1601h;
    }

    public void setDuration(float f) {
        this.f1601h = f;
    }

    public List<LatLonPoint> getPolyline() {
        return this.f1602i;
    }

    public void setPolyline(List<LatLonPoint> list) {
        this.f1602i = list;
    }

    public String getAction() {
        return this.f1603j;
    }

    public void setAction(String str) {
        this.f1603j = str;
    }

    public String getAssistantAction() {
        return this.f1604k;
    }

    public void setAssistantAction(String str) {
        this.f1604k = str;
    }

    public List<RouteSearchCity> getRouteSearchCityList() {
        return this.f1605l;
    }

    public void setRouteSearchCityList(List<RouteSearchCity> list) {
        this.f1605l = list;
    }

    public List<TMC> getTMCs() {
        return this.f1606m;
    }

    public void setTMCs(List<TMC> list) {
        this.f1606m = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1594a);
        parcel.writeString(this.f1595b);
        parcel.writeString(this.f1596c);
        parcel.writeFloat(this.f1597d);
        parcel.writeFloat(this.f1598e);
        parcel.writeFloat(this.f1599f);
        parcel.writeString(this.f1600g);
        parcel.writeFloat(this.f1601h);
        parcel.writeTypedList(this.f1602i);
        parcel.writeString(this.f1603j);
        parcel.writeString(this.f1604k);
        parcel.writeTypedList(this.f1605l);
        parcel.writeTypedList(this.f1606m);
    }

    public DriveStep(Parcel parcel) {
        this.f1594a = parcel.readString();
        this.f1595b = parcel.readString();
        this.f1596c = parcel.readString();
        this.f1597d = parcel.readFloat();
        this.f1598e = parcel.readFloat();
        this.f1599f = parcel.readFloat();
        this.f1600g = parcel.readString();
        this.f1601h = parcel.readFloat();
        this.f1602i = parcel.createTypedArrayList(LatLonPoint.CREATOR);
        this.f1603j = parcel.readString();
        this.f1604k = parcel.readString();
        this.f1605l = parcel.createTypedArrayList(RouteSearchCity.CREATOR);
        this.f1606m = parcel.createTypedArrayList(TMC.CREATOR);
    }
}
