package com.amap.api.services.geocoder;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.road.Crossroad;
import com.amap.api.services.road.Road;
import java.util.ArrayList;
import java.util.List;

public final class RegeocodeAddress implements Parcelable {
    public static final Creator<RegeocodeAddress> CREATOR = new C03291();
    private String f1174a;
    private String f1175b;
    private String f1176c;
    private String f1177d;
    private String f1178e;
    private String f1179f;
    private String f1180g;
    private StreetNumber f1181h;
    private String f1182i;
    private String f1183j;
    private String f1184k;
    private List<RegeocodeRoad> f1185l;
    private List<Crossroad> f1186m;
    private List<PoiItem> f1187n;
    private List<BusinessArea> f1188o;
    private List<AoiItem> f1189p;

    static class C03291 implements Creator<RegeocodeAddress> {
        C03291() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1178a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1179a(i);
        }

        public RegeocodeAddress m1178a(Parcel parcel) {
            return new RegeocodeAddress(parcel);
        }

        public RegeocodeAddress[] m1179a(int i) {
            return null;
        }
    }

    public RegeocodeAddress() {
        this.f1185l = new ArrayList();
        this.f1186m = new ArrayList();
        this.f1187n = new ArrayList();
        this.f1188o = new ArrayList();
        this.f1189p = new ArrayList();
    }

    public String getFormatAddress() {
        return this.f1174a;
    }

    public void setFormatAddress(String str) {
        this.f1174a = str;
    }

    public String getProvince() {
        return this.f1175b;
    }

    public void setProvince(String str) {
        this.f1175b = str;
    }

    public String getCity() {
        return this.f1176c;
    }

    public void setCity(String str) {
        this.f1176c = str;
    }

    public String getCityCode() {
        return this.f1182i;
    }

    public void setCityCode(String str) {
        this.f1182i = str;
    }

    public String getAdCode() {
        return this.f1183j;
    }

    public void setAdCode(String str) {
        this.f1183j = str;
    }

    public String getDistrict() {
        return this.f1177d;
    }

    public void setDistrict(String str) {
        this.f1177d = str;
    }

    public String getTownship() {
        return this.f1178e;
    }

    public void setTownship(String str) {
        this.f1178e = str;
    }

    public String getNeighborhood() {
        return this.f1179f;
    }

    public void setNeighborhood(String str) {
        this.f1179f = str;
    }

    public String getBuilding() {
        return this.f1180g;
    }

    public void setBuilding(String str) {
        this.f1180g = str;
    }

    public StreetNumber getStreetNumber() {
        return this.f1181h;
    }

    public void setStreetNumber(StreetNumber streetNumber) {
        this.f1181h = streetNumber;
    }

    public List<RegeocodeRoad> getRoads() {
        return this.f1185l;
    }

    public void setRoads(List<RegeocodeRoad> list) {
        this.f1185l = list;
    }

    public List<PoiItem> getPois() {
        return this.f1187n;
    }

    public void setPois(List<PoiItem> list) {
        this.f1187n = list;
    }

    public List<Crossroad> getCrossroads() {
        return this.f1186m;
    }

    public void setCrossroads(List<Crossroad> list) {
        this.f1186m = list;
    }

    public List<BusinessArea> getBusinessAreas() {
        return this.f1188o;
    }

    public void setBusinessAreas(List<BusinessArea> list) {
        this.f1188o = list;
    }

    public List<AoiItem> getAois() {
        return this.f1189p;
    }

    public void setAois(List<AoiItem> list) {
        this.f1189p = list;
    }

    public String getTowncode() {
        return this.f1184k;
    }

    public void setTowncode(String str) {
        this.f1184k = str;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1174a);
        parcel.writeString(this.f1175b);
        parcel.writeString(this.f1176c);
        parcel.writeString(this.f1177d);
        parcel.writeString(this.f1178e);
        parcel.writeString(this.f1179f);
        parcel.writeString(this.f1180g);
        parcel.writeValue(this.f1181h);
        parcel.writeList(this.f1185l);
        parcel.writeList(this.f1186m);
        parcel.writeList(this.f1187n);
        parcel.writeString(this.f1182i);
        parcel.writeString(this.f1183j);
        parcel.writeList(this.f1188o);
        parcel.writeList(this.f1189p);
        parcel.writeString(this.f1184k);
    }

    private RegeocodeAddress(Parcel parcel) {
        this.f1185l = new ArrayList();
        this.f1186m = new ArrayList();
        this.f1187n = new ArrayList();
        this.f1188o = new ArrayList();
        this.f1189p = new ArrayList();
        this.f1174a = parcel.readString();
        this.f1175b = parcel.readString();
        this.f1176c = parcel.readString();
        this.f1177d = parcel.readString();
        this.f1178e = parcel.readString();
        this.f1179f = parcel.readString();
        this.f1180g = parcel.readString();
        this.f1181h = (StreetNumber) parcel.readValue(StreetNumber.class.getClassLoader());
        this.f1185l = parcel.readArrayList(Road.class.getClassLoader());
        this.f1186m = parcel.readArrayList(Crossroad.class.getClassLoader());
        this.f1187n = parcel.readArrayList(PoiItem.class.getClassLoader());
        this.f1182i = parcel.readString();
        this.f1183j = parcel.readString();
        this.f1188o = parcel.readArrayList(BusinessArea.class.getClassLoader());
        this.f1189p = parcel.readArrayList(AoiItem.class.getClassLoader());
        this.f1184k = parcel.readString();
    }
}
