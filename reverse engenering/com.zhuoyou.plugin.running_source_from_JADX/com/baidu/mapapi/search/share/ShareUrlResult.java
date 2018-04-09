package com.baidu.mapapi.search.share;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;

public class ShareUrlResult extends SearchResult implements Parcelable {
    public static final Creator<ShareUrlResult> CREATOR = new C0578a();
    private String f1807a;
    private int f1808b;

    ShareUrlResult() {
    }

    protected ShareUrlResult(Parcel parcel) {
        this.f1807a = parcel.readString();
        this.f1808b = parcel.readInt();
    }

    ShareUrlResult(ERRORNO errorno) {
        super(errorno);
    }

    void m1725a(int i) {
        this.f1808b = i;
    }

    void m1726a(String str) {
        this.f1807a = str;
    }

    public int describeContents() {
        return 0;
    }

    public String getUrl() {
        return this.f1807a;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f1807a);
        parcel.writeInt(this.f1808b);
    }
}
