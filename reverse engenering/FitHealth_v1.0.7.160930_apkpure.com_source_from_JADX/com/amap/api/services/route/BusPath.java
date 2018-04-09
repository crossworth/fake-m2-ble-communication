package com.amap.api.services.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public class BusPath extends Path implements Parcelable {
    public static final Creator<BusPath> CREATOR = new C04131();
    private float f4391a;
    private boolean f4392b;
    private float f4393c;
    private float f4394d;
    private List<BusStep> f4395e = new ArrayList();

    static class C04131 implements Creator<BusPath> {
        C04131() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m1675a(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return m1676a(i);
        }

        public BusPath m1675a(Parcel parcel) {
            return new BusPath(parcel);
        }

        public BusPath[] m1676a(int i) {
            return null;
        }
    }

    public float getCost() {
        return this.f4391a;
    }

    public void setCost(float f) {
        this.f4391a = f;
    }

    public boolean isNightBus() {
        return this.f4392b;
    }

    public void setNightBus(boolean z) {
        this.f4392b = z;
    }

    public float getDistance() {
        return this.f4393c + this.f4394d;
    }

    public float getWalkDistance() {
        return this.f4393c;
    }

    public void setWalkDistance(float f) {
        this.f4393c = f;
    }

    public float getBusDistance() {
        return this.f4394d;
    }

    public void setBusDistance(float f) {
        this.f4394d = f;
    }

    public List<BusStep> getSteps() {
        return this.f4395e;
    }

    public void setSteps(List<BusStep> list) {
        this.f4395e = list;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeFloat(this.f4391a);
        parcel.writeBooleanArray(new boolean[]{this.f4392b});
        parcel.writeFloat(this.f4393c);
        parcel.writeFloat(this.f4394d);
        parcel.writeTypedList(this.f4395e);
    }

    public BusPath(Parcel parcel) {
        super(parcel);
        this.f4391a = parcel.readFloat();
        boolean[] zArr = new boolean[1];
        parcel.readBooleanArray(zArr);
        this.f4392b = zArr[0];
        this.f4393c = parcel.readFloat();
        this.f4394d = parcel.readFloat();
        this.f4395e = parcel.createTypedArrayList(BusStep.CREATOR);
    }
}
