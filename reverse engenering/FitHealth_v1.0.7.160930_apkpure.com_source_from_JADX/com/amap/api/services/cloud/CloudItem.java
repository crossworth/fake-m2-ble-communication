package com.amap.api.services.cloud;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.LatLonPoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CloudItem implements Parcelable {
    public static final Creator<CloudItem> CREATOR = new C03181();
    private String f1068a;
    private int f1069b = -1;
    private String f1070c;
    private String f1071d;
    private HashMap<String, String> f1072e;
    private List<CloudImage> f1073f;
    protected final LatLonPoint mPoint;
    protected final String mSnippet;
    protected final String mTitle;

    static class C03181 implements Creator<CloudItem> {
        C03181() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1145a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1146a(i);
        }

        public CloudItem m1145a(Parcel parcel) {
            return new CloudItem(parcel);
        }

        public CloudItem[] m1146a(int i) {
            return new CloudItem[i];
        }
    }

    public CloudItem(String str, LatLonPoint latLonPoint, String str2, String str3) {
        this.f1068a = str;
        this.mPoint = latLonPoint;
        this.mTitle = str2;
        this.mSnippet = str3;
    }

    public String getID() {
        return this.f1068a;
    }

    public int getDistance() {
        return this.f1069b;
    }

    public void setDistance(int i) {
        this.f1069b = i;
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

    public String getCreatetime() {
        return this.f1070c;
    }

    public void setCreatetime(String str) {
        this.f1070c = str;
    }

    public String getUpdatetime() {
        return this.f1071d;
    }

    public void setUpdatetime(String str) {
        this.f1071d = str;
    }

    public HashMap<String, String> getCustomfield() {
        return this.f1072e;
    }

    public void setCustomfield(HashMap<String, String> hashMap) {
        this.f1072e = hashMap;
    }

    public List<CloudImage> getCloudImage() {
        return this.f1073f;
    }

    public void setmCloudImage(List<CloudImage> list) {
        this.f1073f = list;
    }

    protected CloudItem(Parcel parcel) {
        this.f1068a = parcel.readString();
        this.f1069b = parcel.readInt();
        this.mPoint = (LatLonPoint) parcel.readValue(LatLonPoint.class.getClassLoader());
        this.mTitle = parcel.readString();
        this.mSnippet = parcel.readString();
        this.f1070c = parcel.readString();
        this.f1071d = parcel.readString();
        this.f1072e = new HashMap();
        parcel.readMap(this.f1072e, HashMap.class.getClassLoader());
        this.f1073f = new ArrayList();
        parcel.readList(this.f1073f, getClass().getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1068a);
        parcel.writeInt(this.f1069b);
        parcel.writeValue(this.mPoint);
        parcel.writeString(this.mTitle);
        parcel.writeString(this.mSnippet);
        parcel.writeString(this.f1070c);
        parcel.writeString(this.f1071d);
        parcel.writeMap(this.f1072e);
        parcel.writeList(this.f1073f);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass() && CloudItem.class != obj.getClass() && CloudItemDetail.class != obj.getClass()) {
            return false;
        }
        CloudItem cloudItem = (CloudItem) obj;
        if (this.f1068a == null) {
            if (cloudItem.f1068a != null) {
                return false;
            }
            return true;
        } else if (this.f1068a.equals(cloudItem.f1068a)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return (this.f1068a == null ? 0 : this.f1068a.hashCode()) + 31;
    }

    public String toString() {
        return this.mTitle;
    }
}
