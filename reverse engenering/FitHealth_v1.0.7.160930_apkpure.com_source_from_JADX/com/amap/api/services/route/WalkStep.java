package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.List;

public class WalkStep implements Parcelable {
    public static final Creator<WalkStep> CREATOR = new C04341();
    private String f1632a;
    private String f1633b;
    private String f1634c;
    private float f1635d;
    private float f1636e;
    private List<LatLonPoint> f1637f = new ArrayList();
    private String f1638g;
    private String f1639h;

    static class C04341 implements Creator<WalkStep> {
        C04341() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1717a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1718a(i);
        }

        public WalkStep m1717a(Parcel parcel) {
            return new WalkStep(parcel);
        }

        public WalkStep[] m1718a(int i) {
            return null;
        }
    }

    public String getInstruction() {
        return this.f1632a;
    }

    public void setInstruction(String str) {
        this.f1632a = str;
    }

    public String getOrientation() {
        return this.f1633b;
    }

    public void setOrientation(String str) {
        this.f1633b = str;
    }

    public String getRoad() {
        return this.f1634c;
    }

    public void setRoad(String str) {
        this.f1634c = str;
    }

    public float getDistance() {
        return this.f1635d;
    }

    public void setDistance(float f) {
        this.f1635d = f;
    }

    public float getDuration() {
        return this.f1636e;
    }

    public void setDuration(float f) {
        this.f1636e = f;
    }

    public List<LatLonPoint> getPolyline() {
        return this.f1637f;
    }

    public void setPolyline(List<LatLonPoint> list) {
        this.f1637f = list;
    }

    public String getAction() {
        return this.f1638g;
    }

    public void setAction(String str) {
        this.f1638g = str;
    }

    public String getAssistantAction() {
        return this.f1639h;
    }

    public void setAssistantAction(String str) {
        this.f1639h = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1632a);
        parcel.writeString(this.f1633b);
        parcel.writeString(this.f1634c);
        parcel.writeFloat(this.f1635d);
        parcel.writeFloat(this.f1636e);
        parcel.writeTypedList(this.f1637f);
        parcel.writeString(this.f1638g);
        parcel.writeString(this.f1639h);
    }

    public WalkStep(Parcel parcel) {
        this.f1632a = parcel.readString();
        this.f1633b = parcel.readString();
        this.f1634c = parcel.readString();
        this.f1635d = parcel.readFloat();
        this.f1636e = parcel.readFloat();
        this.f1637f = parcel.createTypedArrayList(LatLonPoint.CREATOR);
        this.f1638g = parcel.readString();
        this.f1639h = parcel.readString();
    }
}
