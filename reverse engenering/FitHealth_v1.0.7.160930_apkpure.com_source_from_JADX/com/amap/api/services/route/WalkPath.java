package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class WalkPath extends Path implements Parcelable {
    public static final Creator<WalkPath> CREATOR = new C04321();
    private List<WalkStep> f4414a = new ArrayList();

    static class C04321 implements Creator<WalkPath> {
        C04321() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1713a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1714a(i);
        }

        public WalkPath m1713a(Parcel parcel) {
            return new WalkPath(parcel);
        }

        public WalkPath[] m1714a(int i) {
            return null;
        }
    }

    public List<WalkStep> getSteps() {
        return this.f4414a;
    }

    public void setSteps(List<WalkStep> list) {
        this.f4414a = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeTypedList(this.f4414a);
    }

    public WalkPath(Parcel parcel) {
        super(parcel);
        this.f4414a = parcel.createTypedArrayList(WalkStep.CREATOR);
    }
}
