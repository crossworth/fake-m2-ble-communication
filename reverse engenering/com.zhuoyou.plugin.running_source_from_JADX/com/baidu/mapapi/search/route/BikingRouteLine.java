package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import java.util.ArrayList;
import java.util.List;

public class BikingRouteLine extends RouteLine<BikingStep> implements Parcelable {
    public static final Creator<BikingRouteLine> CREATOR = new C0555a();

    public static class BikingStep extends RouteStep implements Parcelable {
        public static final Creator<BikingStep> CREATOR = new C0556b();
        private int f1660c;
        private RouteNode f1661d;
        private RouteNode f1662e;
        private String f1663f;
        private String f1664g;
        private String f1665h;
        private String f1666i;

        protected BikingStep(Parcel parcel) {
            super(parcel);
            this.f1660c = parcel.readInt();
            this.f1661d = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1662e = (RouteNode) parcel.readParcelable(RouteNode.class.getClassLoader());
            this.f1663f = parcel.readString();
            this.f1664g = parcel.readString();
            this.f1665h = parcel.readString();
            this.f1666i = parcel.readString();
        }

        private List<LatLng> m1569e(String str) {
            if (str == null || str.length() == 0) {
                return null;
            }
            List<LatLng> arrayList = new ArrayList();
            String[] split = str.split(";");
            if (split == null || split.length == 0) {
                return null;
            }
            for (String split2 : split) {
                String[] split3 = split2.split(",");
                if (split3 != null && split3.length >= 2) {
                    arrayList.add(new LatLng(Double.valueOf(split3[1]).doubleValue(), Double.valueOf(split3[0]).doubleValue()));
                }
            }
            return arrayList;
        }

        void m1570a(int i) {
            this.f1660c = i;
        }

        void m1571a(RouteNode routeNode) {
            this.f1661d = routeNode;
        }

        void m1572a(String str) {
            this.f1663f = str;
        }

        void m1573b(RouteNode routeNode) {
            this.f1662e = routeNode;
        }

        void m1574b(String str) {
            this.f1664g = str;
        }

        void m1575c(String str) {
            this.f1665h = str;
        }

        void m1576d(String str) {
            this.f1666i = str;
        }

        public int describeContents() {
            return 0;
        }

        public int getDirection() {
            return this.f1660c;
        }

        public RouteNode getEntrance() {
            return this.f1661d;
        }

        public String getEntranceInstructions() {
            return this.f1664g;
        }

        public RouteNode getExit() {
            return this.f1662e;
        }

        public String getExitInstructions() {
            return this.f1665h;
        }

        public String getInstructions() {
            return this.f1666i;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = m1569e(this.f1663f);
            }
            return this.mWayPoints;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, 1);
            parcel.writeInt(this.f1660c);
            parcel.writeParcelable(this.f1661d, 1);
            parcel.writeParcelable(this.f1662e, 1);
            parcel.writeString(this.f1663f);
            parcel.writeString(this.f1664g);
            parcel.writeString(this.f1665h);
            parcel.writeString(this.f1666i);
        }
    }

    BikingRouteLine() {
    }

    protected BikingRouteLine(Parcel parcel) {
        super(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public List<BikingStep> getAllStep() {
        return super.getAllStep();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.setType(TYPE.BIKINGSTEP);
        super.writeToParcel(parcel, 1);
    }
}
