package com.baidu.mapapi.map;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.platform.comapi.map.C0612B;

public final class BaiduMapOptions implements Parcelable {
    public static final Creator<BaiduMapOptions> CREATOR = new C0488e();
    MapStatus f1041a = new MapStatus(0.0f, new LatLng(39.914935d, 116.403119d), 0.0f, 12.0f, null, null);
    boolean f1042b = true;
    int f1043c = 1;
    boolean f1044d = true;
    boolean f1045e = true;
    boolean f1046f = true;
    boolean f1047g = true;
    boolean f1048h = true;
    boolean f1049i = true;
    LogoPosition f1050j;
    Point f1051k;
    Point f1052l;

    protected BaiduMapOptions(Parcel parcel) {
        boolean z = true;
        this.f1041a = (MapStatus) parcel.readParcelable(MapStatus.class.getClassLoader());
        this.f1042b = parcel.readByte() != (byte) 0;
        this.f1043c = parcel.readInt();
        this.f1044d = parcel.readByte() != (byte) 0;
        this.f1045e = parcel.readByte() != (byte) 0;
        this.f1046f = parcel.readByte() != (byte) 0;
        this.f1047g = parcel.readByte() != (byte) 0;
        this.f1048h = parcel.readByte() != (byte) 0;
        if (parcel.readByte() == (byte) 0) {
            z = false;
        }
        this.f1049i = z;
        this.f1051k = (Point) parcel.readParcelable(Point.class.getClassLoader());
        this.f1052l = (Point) parcel.readParcelable(Point.class.getClassLoader());
    }

    C0612B m1102a() {
        return new C0612B().m1899a(this.f1041a.m1149c()).m1900a(this.f1042b).m1898a(this.f1043c).m1901b(this.f1044d).m1902c(this.f1045e).m1903d(this.f1046f).m1904e(this.f1047g);
    }

    public BaiduMapOptions compassEnabled(boolean z) {
        this.f1042b = z;
        return this;
    }

    public int describeContents() {
        return 0;
    }

    public BaiduMapOptions logoPosition(LogoPosition logoPosition) {
        this.f1050j = logoPosition;
        return this;
    }

    public BaiduMapOptions mapStatus(MapStatus mapStatus) {
        if (mapStatus != null) {
            this.f1041a = mapStatus;
        }
        return this;
    }

    public BaiduMapOptions mapType(int i) {
        this.f1043c = i;
        return this;
    }

    public BaiduMapOptions overlookingGesturesEnabled(boolean z) {
        this.f1046f = z;
        return this;
    }

    public BaiduMapOptions rotateGesturesEnabled(boolean z) {
        this.f1044d = z;
        return this;
    }

    public BaiduMapOptions scaleControlEnabled(boolean z) {
        this.f1049i = z;
        return this;
    }

    public BaiduMapOptions scaleControlPosition(Point point) {
        this.f1051k = point;
        return this;
    }

    public BaiduMapOptions scrollGesturesEnabled(boolean z) {
        this.f1045e = z;
        return this;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeParcelable(this.f1041a, i);
        parcel.writeByte((byte) (this.f1042b ? 1 : 0));
        parcel.writeInt(this.f1043c);
        parcel.writeByte((byte) (this.f1044d ? 1 : 0));
        parcel.writeByte((byte) (this.f1045e ? 1 : 0));
        parcel.writeByte((byte) (this.f1046f ? 1 : 0));
        parcel.writeByte((byte) (this.f1047g ? 1 : 0));
        parcel.writeByte((byte) (this.f1048h ? 1 : 0));
        if (!this.f1049i) {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
        parcel.writeParcelable(this.f1051k, i);
        parcel.writeParcelable(this.f1052l, i);
    }

    public BaiduMapOptions zoomControlsEnabled(boolean z) {
        this.f1048h = z;
        return this;
    }

    public BaiduMapOptions zoomControlsPosition(Point point) {
        this.f1052l = point;
        return this;
    }

    public BaiduMapOptions zoomGesturesEnabled(boolean z) {
        this.f1047g = z;
        return this;
    }
}
