package com.baidu.mapapi.search.route;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteNode;
import com.baidu.mapapi.search.core.RouteStep;
import java.util.ArrayList;
import java.util.List;

@SuppressLint({"ParcelCreator"})
public class IndoorRouteLine extends RouteLine<IndoorRouteStep> {
    public static final Creator<IndoorRouteLine> CREATOR = new C0561g();

    public static class IndoorRouteStep extends RouteStep {
        private RouteNode f1705c;
        private RouteNode f1706d;
        private String f1707e;
        private String f1708f;
        private String f1709g;
        private List<IndoorStepNode> f1710h;
        private List<Double> f1711i;

        public static class IndoorStepNode {
            private String f1701a;
            private int f1702b;
            private LatLng f1703c;
            private String f1704d;

            public String getDetail() {
                return this.f1704d;
            }

            public LatLng getLocation() {
                return this.f1703c;
            }

            public String getName() {
                return this.f1701a;
            }

            public int getType() {
                return this.f1702b;
            }

            public void setDetail(String str) {
                this.f1704d = str;
            }

            public void setLocation(LatLng latLng) {
                this.f1703c = latLng;
            }

            public void setName(String str) {
                this.f1701a = str;
            }

            public void setType(int i) {
                this.f1702b = i;
            }
        }

        private List<LatLng> m1593a(List<Double> list) {
            List<LatLng> arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i += 2) {
                arrayList.add(new LatLng(((Double) list.get(i)).doubleValue(), ((Double) list.get(i + 1)).doubleValue()));
            }
            return arrayList;
        }

        void m1594a(String str) {
            this.f1707e = str;
        }

        void m1595b(String str) {
            this.f1709g = str;
        }

        void m1596c(String str) {
            this.f1708f = str;
        }

        public String getBuildingId() {
            return this.f1709g;
        }

        public RouteNode getEntrace() {
            return this.f1705c;
        }

        public RouteNode getExit() {
            return this.f1706d;
        }

        public String getFloorId() {
            return this.f1708f;
        }

        public String getInstructions() {
            return this.f1707e;
        }

        public List<IndoorStepNode> getStepNodes() {
            return this.f1710h;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = m1593a(this.f1711i);
            }
            return this.mWayPoints;
        }

        public void setEntrace(RouteNode routeNode) {
            this.f1705c = routeNode;
        }

        public void setExit(RouteNode routeNode) {
            this.f1706d = routeNode;
        }

        public void setPath(List<Double> list) {
            this.f1711i = list;
        }

        public void setStepNodes(List<IndoorStepNode> list) {
            this.f1710h = list;
        }
    }

    IndoorRouteLine() {
        setType(TYPE.WALKSTEP);
    }

    protected IndoorRouteLine(Parcel parcel) {
        super(parcel);
    }

    public int describeContents() {
        return 0;
    }

    public List<IndoorRouteStep> getAllStep() {
        return super.getAllStep();
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
    }
}
