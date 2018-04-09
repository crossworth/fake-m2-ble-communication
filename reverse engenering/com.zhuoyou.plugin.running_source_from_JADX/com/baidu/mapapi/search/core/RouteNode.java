package com.baidu.mapapi.search.core;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;

public class RouteNode implements Parcelable {
    public static final Creator<RouteNode> CREATOR = new C0529g();
    private String f1499a;
    private LatLng f1500b;
    private String f1501c;

    protected RouteNode(Parcel parcel) {
        this.f1499a = parcel.readString();
        this.f1500b = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.f1501c = parcel.readString();
    }

    public static RouteNode location(LatLng latLng) {
        RouteNode routeNode = new RouteNode();
        routeNode.setLocation(latLng);
        return routeNode;
    }

    public static RouteNode titleAndLocation(String str, LatLng latLng) {
        RouteNode routeNode = new RouteNode();
        routeNode.setTitle(str);
        routeNode.setLocation(latLng);
        return routeNode;
    }

    public int describeContents() {
        return 0;
    }

    public LatLng getLocation() {
        return this.f1500b;
    }

    public String getTitle() {
        return this.f1499a;
    }

    public String getUid() {
        return this.f1501c;
    }

    public void setLocation(LatLng latLng) {
        this.f1500b = latLng;
    }

    public void setTitle(String str) {
        this.f1499a = str;
    }

    public void setUid(String str) {
        this.f1501c = str;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1499a);
        parcel.writeValue(this.f1500b);
        parcel.writeString(this.f1501c);
    }
}
