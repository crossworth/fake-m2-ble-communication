package com.baidu.mapapi.search.route;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.BusInfo;
import com.baidu.mapapi.search.core.CoachInfo;
import com.baidu.mapapi.search.core.PlaneInfo;
import com.baidu.mapapi.search.core.PriceInfo;
import com.baidu.mapapi.search.core.RouteLine;
import com.baidu.mapapi.search.core.RouteStep;
import com.baidu.mapapi.search.core.TrainInfo;
import java.util.ArrayList;
import java.util.List;

public final class MassTransitRouteLine extends RouteLine<TransitStep> implements Parcelable {
    public static final Creator<MassTransitRouteLine> CREATOR = new C0563i();
    private String f1728b;
    private double f1729c;
    private List<PriceInfo> f1730d;
    private List<List<TransitStep>> f1731e = null;

    public static class TransitStep extends RouteStep implements Parcelable {
        public static final Creator<TransitStep> CREATOR = new C0564j();
        private List<TrafficCondition> f1718c;
        private LatLng f1719d;
        private LatLng f1720e;
        private TrainInfo f1721f;
        private PlaneInfo f1722g;
        private CoachInfo f1723h;
        private BusInfo f1724i;
        private StepVehicleInfoType f1725j;
        private String f1726k;
        private String f1727l;

        public enum StepVehicleInfoType {
            ESTEP_TRAIN(1),
            ESTEP_PLANE(2),
            ESTEP_BUS(3),
            ESTEP_DRIVING(4),
            ESTEP_WALK(5),
            ESTEP_COACH(6);
            
            private int f1715a;

            private StepVehicleInfoType(int i) {
                this.f1715a = 0;
                this.f1715a = i;
            }

            public int getInt() {
                return this.f1715a;
            }
        }

        public static class TrafficCondition implements Parcelable {
            public static final Creator<TrafficCondition> CREATOR = new C0565k();
            private int f1716a;
            private int f1717b;

            TrafficCondition() {
            }

            protected TrafficCondition(Parcel parcel) {
                this.f1716a = parcel.readInt();
                this.f1717b = parcel.readInt();
            }

            public int describeContents() {
                return 0;
            }

            public int getTrafficGeoCnt() {
                return this.f1717b;
            }

            public int getTrafficStatus() {
                return this.f1716a;
            }

            public void setTrafficGeoCnt(int i) {
                this.f1717b = i;
            }

            public void setTrafficStatus(int i) {
                this.f1716a = i;
            }

            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.f1716a);
                parcel.writeInt(this.f1717b);
            }
        }

        TransitStep() {
        }

        protected TransitStep(Parcel parcel) {
            super(parcel);
            this.f1718c = parcel.createTypedArrayList(TrafficCondition.CREATOR);
            this.f1719d = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
            this.f1720e = (LatLng) parcel.readParcelable(LatLng.class.getClassLoader());
            this.f1721f = (TrainInfo) parcel.readParcelable(TrainInfo.class.getClassLoader());
            this.f1722g = (PlaneInfo) parcel.readParcelable(PlaneInfo.class.getClassLoader());
            this.f1723h = (CoachInfo) parcel.readParcelable(CoachInfo.class.getClassLoader());
            this.f1724i = (BusInfo) parcel.readParcelable(BusInfo.class.getClassLoader());
            switch (parcel.readInt()) {
                case 1:
                    this.f1725j = StepVehicleInfoType.ESTEP_TRAIN;
                    break;
                case 2:
                    this.f1725j = StepVehicleInfoType.ESTEP_PLANE;
                    break;
                case 3:
                    this.f1725j = StepVehicleInfoType.ESTEP_BUS;
                    break;
                case 4:
                    this.f1725j = StepVehicleInfoType.ESTEP_DRIVING;
                    break;
                case 5:
                    this.f1725j = StepVehicleInfoType.ESTEP_WALK;
                    break;
                case 6:
                    this.f1725j = StepVehicleInfoType.ESTEP_COACH;
                    break;
            }
            this.f1726k = parcel.readString();
            this.f1727l = parcel.readString();
        }

        private List<LatLng> m1598c(String str) {
            List<LatLng> arrayList = new ArrayList();
            String[] split = str.split(";");
            if (split != null) {
                int i = 0;
                while (i < split.length) {
                    if (!(split[i] == null || split[i] == "")) {
                        String[] split2 = split[i].split(",");
                        if (!(split2 == null || split2[1] == "" || split2[0] == "")) {
                            arrayList.add(new LatLng(Double.parseDouble(split2[1]), Double.parseDouble(split2[0])));
                        }
                    }
                    i++;
                }
            }
            return arrayList;
        }

        void m1599a(LatLng latLng) {
            this.f1719d = latLng;
        }

        void m1600a(StepVehicleInfoType stepVehicleInfoType) {
            this.f1725j = stepVehicleInfoType;
        }

        void m1601a(String str) {
            this.f1726k = str;
        }

        void m1602b(LatLng latLng) {
            this.f1720e = latLng;
        }

        void m1603b(String str) {
            this.f1727l = str;
        }

        public int describeContents() {
            return 0;
        }

        public BusInfo getBusInfo() {
            return this.f1724i;
        }

        public CoachInfo getCoachInfo() {
            return this.f1723h;
        }

        public LatLng getEndLocation() {
            return this.f1720e;
        }

        public String getInstructions() {
            return this.f1726k;
        }

        public PlaneInfo getPlaneInfo() {
            return this.f1722g;
        }

        public LatLng getStartLocation() {
            return this.f1719d;
        }

        public List<TrafficCondition> getTrafficConditions() {
            return this.f1718c;
        }

        public TrainInfo getTrainInfo() {
            return this.f1721f;
        }

        public StepVehicleInfoType getVehileType() {
            return this.f1725j;
        }

        public List<LatLng> getWayPoints() {
            if (this.mWayPoints == null) {
                this.mWayPoints = m1598c(this.f1727l);
            }
            return this.mWayPoints;
        }

        public void setBusInfo(BusInfo busInfo) {
            this.f1724i = busInfo;
        }

        public void setCoachInfo(CoachInfo coachInfo) {
            this.f1723h = coachInfo;
        }

        public void setPlaneInfo(PlaneInfo planeInfo) {
            this.f1722g = planeInfo;
        }

        public void setTrafficConditions(List<TrafficCondition> list) {
            this.f1718c = list;
        }

        public void setTrainInfo(TrainInfo trainInfo) {
            this.f1721f = trainInfo;
        }

        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeTypedList(this.f1718c);
            parcel.writeParcelable(this.f1719d, i);
            parcel.writeParcelable(this.f1720e, i);
            parcel.writeParcelable(this.f1721f, i);
            parcel.writeParcelable(this.f1722g, i);
            parcel.writeParcelable(this.f1723h, i);
            parcel.writeParcelable(this.f1724i, i);
            parcel.writeInt(this.f1725j.getInt());
            parcel.writeString(this.f1726k);
            parcel.writeString(this.f1727l);
        }
    }

    MassTransitRouteLine() {
    }

    protected MassTransitRouteLine(Parcel parcel) {
        super(parcel);
        int readInt = parcel.readInt();
        this.f1728b = parcel.readString();
        this.f1729c = parcel.readDouble();
        this.f1730d = parcel.createTypedArrayList(PriceInfo.CREATOR);
        if (readInt > 0) {
            this.f1731e = new ArrayList();
            for (int i = 0; i < readInt; i++) {
                this.f1731e.add(parcel.createTypedArrayList(TransitStep.CREATOR));
            }
        }
    }

    void m1604a(double d) {
        this.f1729c = d;
    }

    void m1605a(String str) {
        this.f1728b = str;
    }

    void m1606a(List<PriceInfo> list) {
        this.f1730d = list;
    }

    public int describeContents() {
        return 0;
    }

    public String getArriveTime() {
        return this.f1728b;
    }

    public List<List<TransitStep>> getNewSteps() {
        return this.f1731e;
    }

    public double getPrice() {
        return this.f1729c;
    }

    public List<PriceInfo> getPriceInfo() {
        return this.f1730d;
    }

    public void setNewSteps(List<List<TransitStep>> list) {
        this.f1731e = list;
    }

    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeInt(this.f1731e == null ? 0 : this.f1731e.size());
        parcel.writeString(this.f1728b);
        parcel.writeDouble(this.f1729c);
        parcel.writeTypedList(this.f1730d);
        for (List writeTypedList : this.f1731e) {
            parcel.writeTypedList(writeTypedList);
        }
    }
}
