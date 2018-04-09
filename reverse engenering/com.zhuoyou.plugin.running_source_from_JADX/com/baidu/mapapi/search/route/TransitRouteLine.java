package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.core.TaxiInfo;
import com.baidu.mapapi.search.core.VehicleInfo;
import java.util.List;

public final class TransitRouteLine extends RouteLine<TransitStep> implements Parcelable {
    public static final Creator<TransitRouteLine> CREATOR = new C0570p();
    private TaxiInfo f1774b;

    public static class TransitStep extends RouteStep implements Parcelable {
        public static final Creator<TransitStep> CREATOR = new C0571q();
        private VehicleInfo f1768c;
        private RouteNode f1769d;
        private RouteNode f1770e;
        private TransitRouteStepType f1771f;
        private String f1772g;
        private String f1773h;

        public enum TransitRouteStepType {
            BUSLINE,
            SUBWAY,
            WAKLING
        }

        protected TransitStep(Parcel parcel) {
            super(parcel);
            this.f1768c = (VehicleInfo) parcel.readParcelable(VehicleInfo.class.getClassLoader());
            this.f1769d = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1770e = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            int readInt = parcel.readInt();
            this.f1771f = readInt == -1 ? null : TransitRouteStepType.values()[readInt];
            this.f1772g = parcel.readString();
            this.f1773h = parcel.readString();
        }

        void m1639a(RouteNode routeNode) {
            this.f1769d = routeNode;
        }

        void m1640a(VehicleInfo vehicleInfo) {
            this.f1768c = vehicleInfo;
        }

        void m1641a(TransitRouteStepType transitRouteStepType) {
            this.f1771f = transitRouteStepType;
        }

        void m1642a(String str) {
            this.f1772g = str;
        }

        void m1643b(RouteNode routeNode) {
            this.f1770e = routeNode;
        }

        void m1644b(String str) {
            this.f1773h = str;
        }

        public int describeContents() {
            return 0;
        }

        public RouteNode getEntrance() {
            return this.f1769d;
        }

        public RouteNode getExit() {
            return this.f1770e;
        }

        public String getInstructions() {
            return this.f1772g;
        }

        public TransitRouteStepType getStepType() {
            return this.f1771f;
        }

        public VehicleInfo getVehicleInfo() {
            return this.f1768c;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = CoordUtil.decodeLocationList(this.f1773h);
            }
            return this.mWayPoints;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.f1768c, 1);
            parcel.writeParcelable(this.f1769d, 1);
            parcel.writeParcelable(this.f1770e, 1);
            parcel.writeInt(this.f1771f == null ? -1 : this.f1771f.ordinal());
            parcel.writeString(this.f1772g);
            parcel.writeString(this.f1773h);
        }
    }

    TransitRouteLine() {
    }

    protected TransitRouteLine(Parcel parcel) {
        super(parcel);
        this.f1774b = (TaxiInfo) parcel.readParcelable(TaxiInfo.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    @Deprecated
    public TaxiInfo getTaxitInfo() {
        return this.f1774b;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.setType(TYPE.TRANSITSTEP);
        super.writeToParcel(parcel, i);
        parcel.writeParcelable(this.f1774b, 1);
    }
}
