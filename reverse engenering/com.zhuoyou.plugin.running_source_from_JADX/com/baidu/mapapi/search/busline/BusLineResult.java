package com.baidu.mapapi.search.busline;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.Date;
import java.util.List;

public class BusLineResult extends SearchResult implements Parcelable {
    public static final Creator<BusLineResult> CREATOR = new C0521a();
    private String f1504a = null;
    private String f1505b = null;
    private boolean f1506c;
    private Date f1507d;
    private Date f1508e;
    private String f1509f;
    private List<BusStation> f1510g = null;
    private List<BusStep> f1511h = null;
    private float f1512i;
    private float f1513j;
    private String f1514k = null;

    public static class BusStation extends RouteNode {
    }

    public static class BusStep extends RouteStep {
    }

    BusLineResult() {
    }

    BusLineResult(Parcel parcel) {
        this.f1504a = parcel.readString();
        this.f1505b = parcel.readString();
        this.f1506c = ((Boolean) parcel.readValue(Boolean.class.getClassLoader())).booleanValue();
        this.f1507d = (Date) parcel.readValue(Date.class.getClassLoader());
        this.f1508e = (Date) parcel.readValue(Date.class.getClassLoader());
        this.f1509f = parcel.readString();
        this.f1510g = parcel.readArrayList(BusStation.class.getClassLoader());
        this.f1511h = parcel.readArrayList(RouteStep.class.getClassLoader());
    }

    BusLineResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1387a(String str) {
        this.f1505b = str;
    }

    void m1388a(Date date) {
        this.f1507d = date;
    }

    void m1389a(List<BusStation> list) {
        this.f1510g = list;
    }

    void m1390a(boolean z) {
        this.f1506c = z;
    }

    void m1391b(String str) {
        this.f1509f = str;
    }

    void m1392b(Date date) {
        this.f1508e = date;
    }

    void m1393b(List<BusStep> list) {
        this.f1511h = list;
    }

    public int describeContents() {
        return 0;
    }

    public float getBasePrice() {
        return this.f1512i;
    }

    public String getBusCompany() {
        return this.f1504a;
    }

    public String getBusLineName() {
        return this.f1505b;
    }

    public Date getEndTime() {
        return this.f1508e;
    }

    public String getLineDirection() {
        return this.f1514k;
    }

    public float getMaxPrice() {
        return this.f1513j;
    }

    public Date getStartTime() {
        return this.f1507d;
    }

    public List<BusStation> getStations() {
        return this.f1510g;
    }

    public List<BusStep> getSteps() {
        return this.f1511h;
    }

    public String getUid() {
        return this.f1509f;
    }

    public boolean isMonthTicket() {
        return this.f1506c;
    }

    public void setBasePrice(float f) {
        this.f1512i = f;
    }

    public void setLineDirection(String str) {
        this.f1514k = str;
    }

    public void setMaxPrice(float f) {
        this.f1513j = f;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1504a);
        parcel.writeString(this.f1505b);
        parcel.writeValue(Boolean.valueOf(this.f1506c));
        parcel.writeValue(this.f1507d);
        parcel.writeValue(this.f1508e);
        parcel.writeString(this.f1509f);
        parcel.writeList(this.f1510g);
        parcel.writeList(this.f1511h);
    }
}
