package com.baidu.mapapi.search.geocode;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import java.util.List;

public class ReverseGeoCodeResult extends SearchResult {
    public static final Creator<ReverseGeoCodeResult> CREATOR = new C0545c();
    private String f1586a;
    private String f1587b;
    private AddressComponent f1588c;
    private LatLng f1589d;
    private List<PoiInfo> f1590e;

    public static class AddressComponent implements Parcelable {
        public static final Creator<AddressComponent> CREATOR = new C0546d();
        public String city;
        public String district;
        public String province;
        public String street;
        public String streetNumber;

        protected AddressComponent(Parcel parcel) {
            this.streetNumber = parcel.readString();
            this.street = parcel.readString();
            this.district = parcel.readString();
            this.city = parcel.readString();
            this.province = parcel.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.streetNumber);
            parcel.writeString(this.street);
            parcel.writeString(this.district);
            parcel.writeString(this.city);
            parcel.writeString(this.province);
        }
    }

    ReverseGeoCodeResult() {
    }

    protected ReverseGeoCodeResult(Parcel parcel) {
        super(parcel);
        this.f1586a = parcel.readString();
        this.f1587b = parcel.readString();
        this.f1588c = (AddressComponent) parcel.readParcelable(AddressComponent.class.getClassLoader());
        this.f1589d = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.f1590e = parcel.createTypedArrayList(PoiInfo.CREATOR);
    }

    ReverseGeoCodeResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1513a(LatLng latLng) {
        this.f1589d = latLng;
    }

    void m1514a(AddressComponent addressComponent) {
        this.f1588c = addressComponent;
    }

    void m1515a(String str) {
        this.f1586a = str;
    }

    void m1516a(List<PoiInfo> list) {
        this.f1590e = list;
    }

    void m1517b(String str) {
        this.f1587b = str;
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.f1587b;
    }

    public AddressComponent getAddressDetail() {
        return this.f1588c;
    }

    public String getBusinessCircle() {
        return this.f1586a;
    }

    public LatLng getLocation() {
        return this.f1589d;
    }

    public List<PoiInfo> getPoiList() {
        return this.f1590e;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f1586a);
        parcel.writeString(this.f1587b);
        parcel.writeParcelable(this.f1588c, 0);
        parcel.writeValue(this.f1589d);
        parcel.writeTypedList(this.f1590e);
    }
}
