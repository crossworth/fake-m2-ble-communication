package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class DrivePath extends Path implements Parcelable {
    public static final Creator<DrivePath> CREATOR = new C04181();
    private String f4399a;
    private float f4400b;
    private float f4401c;
    private int f4402d;
    private List<DriveStep> f4403e = new ArrayList();

    static class C04181 implements Creator<DrivePath> {
        C04181() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1685a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1686a(i);
        }

        public DrivePath m1685a(Parcel parcel) {
            return new DrivePath(parcel);
        }

        public DrivePath[] m1686a(int i) {
            return null;
        }
    }

    public String getStrategy() {
        return this.f4399a;
    }

    public void setStrategy(String str) {
        this.f4399a = str;
    }

    public float getTolls() {
        return this.f4400b;
    }

    public void setTolls(float f) {
        this.f4400b = f;
    }

    public float getTollDistance() {
        return this.f4401c;
    }

    public void setTollDistance(float f) {
        this.f4401c = f;
    }

    public int getTotalTrafficlights() {
        return this.f4402d;
    }

    public void setTotalTrafficlights(int i) {
        this.f4402d = i;
    }

    public List<DriveStep> getSteps() {
        return this.f4403e;
    }

    public void setSteps(List<DriveStep> list) {
        this.f4403e = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.f4399a);
        parcel.writeFloat(this.f4400b);
        parcel.writeFloat(this.f4401c);
        parcel.writeTypedList(this.f4403e);
        parcel.writeInt(this.f4402d);
    }

    public DrivePath(Parcel parcel) {
        super(parcel);
        this.f4399a = parcel.readString();
        this.f4400b = parcel.readFloat();
        this.f4401c = parcel.readFloat();
        this.f4403e = parcel.createTypedArrayList(DriveStep.CREATOR);
        this.f4402d = parcel.readInt();
    }
}
