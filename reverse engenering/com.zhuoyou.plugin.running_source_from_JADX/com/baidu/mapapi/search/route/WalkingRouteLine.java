package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import java.util.List;

public class WalkingRouteLine extends RouteLine<WalkingStep> implements Parcelable {
    public static final Creator<WalkingRouteLine> CREATOR = new C0573s();

    public static class WalkingStep extends RouteStep implements Parcelable {
        public static final Creator<WalkingStep> CREATOR = new C0574t();
        private int f1784c;
        private RouteNode f1785d;
        private RouteNode f1786e;
        private String f1787f;
        private String f1788g;
        private String f1789h;
        private String f1790i;

        protected WalkingStep(Parcel parcel) {
            super(parcel);
            this.f1784c = parcel.readInt();
            this.f1785d = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1786e = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1787f = parcel.readString();
            this.f1788g = parcel.readString();
            this.f1789h = parcel.readString();
            this.f1790i = parcel.readString();
        }

        void m1648a(int i) {
            this.f1784c = i;
        }

        void m1649a(RouteNode routeNode) {
            this.f1785d = routeNode;
        }

        void m1650a(String str) {
            this.f1787f = str;
        }

        void m1651b(RouteNode routeNode) {
            this.f1786e = routeNode;
        }

        void m1652b(String str) {
            this.f1788g = str;
        }

        void m1653c(String str) {
            this.f1789h = str;
        }

        void m1654d(String str) {
            this.f1790i = str;
        }

        public int describeContents() {
            return 0;
        }

        public int getDirection() {
            return this.f1784c;
        }

        public RouteNode getEntrance() {
            return this.f1785d;
        }

        public String getEntranceInstructions() {
            return this.f1788g;
        }

        public RouteNode getExit() {
            return this.f1786e;
        }

        public String getExitInstructions() {
            return this.f1789h;
        }

        public String getInstructions() {
            return this.f1790i;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = CoordUtil.decodeLocationList(this.f1787f);
            }
            return this.mWayPoints;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, 1);
            parcel.writeInt(this.f1784c);
            parcel.writeParcelable(this.f1785d, 1);
            parcel.writeParcelable(this.f1786e, 1);
            parcel.writeString(this.f1787f);
            parcel.writeString(this.f1788g);
            parcel.writeString(this.f1789h);
            parcel.writeString(this.f1790i);
        }
    }

    WalkingRouteLine() {
    }

    protected WalkingRouteLine(Parcel parcel) {
        super(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public List<WalkingStep> getAllStep() {
        return super.getAllStep();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.setType(TYPE.WALKSTEP);
        super.writeToParcel(parcel, 1);
    }
}
