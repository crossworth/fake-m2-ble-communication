package com.amap.api.services.route;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.interfaces.IRouteSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.C0390i;
import com.amap.api.services.proguard.ao;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.List;

public class RouteSearch {
    public static final int BusComfortable = 4;
    public static final int BusDefault = 0;
    public static final int BusLeaseChange = 2;
    public static final int BusLeaseWalk = 3;
    public static final int BusNoSubway = 5;
    public static final int BusSaveMoney = 1;
    public static final int DrivingAvoidCongestion = 4;
    public static final int DrivingDefault = 0;
    public static final int DrivingMultiStrategy = 5;
    public static final int DrivingNoExpressways = 3;
    public static final int DrivingNoHighAvoidCongestionSaveMoney = 9;
    public static final int DrivingNoHighWay = 6;
    public static final int DrivingNoHighWaySaveMoney = 7;
    public static final int DrivingSaveMoney = 1;
    public static final int DrivingSaveMoneyAvoidCongestion = 8;
    public static final int DrivingShortDistance = 2;
    public static final int WalkDefault = 0;
    public static final int WalkMultipath = 1;
    private IRouteSearch f1626a;

    public static class BusRouteQuery implements Parcelable, Cloneable {
        public static final Creator<BusRouteQuery> CREATOR = new C04251();
        private FromAndTo f1611a;
        private int f1612b;
        private String f1613c;
        private int f1614d;

        static class C04251 implements Creator<BusRouteQuery> {
            C04251() {
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m1699a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m1700a(i);
            }

            public BusRouteQuery m1699a(Parcel parcel) {
                return new BusRouteQuery(parcel);
            }

            public BusRouteQuery[] m1700a(int i) {
                return new BusRouteQuery[i];
            }
        }

        public BusRouteQuery(FromAndTo fromAndTo, int i, String str, int i2) {
            this.f1611a = fromAndTo;
            this.f1612b = i;
            this.f1613c = str;
            this.f1614d = i2;
        }

        public FromAndTo getFromAndTo() {
            return this.f1611a;
        }

        public int getMode() {
            return this.f1612b;
        }

        public String getCity() {
            return this.f1613c;
        }

        public int getNightFlag() {
            return this.f1614d;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.f1611a, i);
            parcel.writeInt(this.f1612b);
            parcel.writeString(this.f1613c);
            parcel.writeInt(this.f1614d);
        }

        public BusRouteQuery(Parcel parcel) {
            this.f1611a = (FromAndTo) parcel.readParcelable(FromAndTo.class.getClassLoader());
            this.f1612b = parcel.readInt();
            this.f1613c = parcel.readString();
            this.f1614d = parcel.readInt();
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.f1613c == null ? 0 : this.f1613c.hashCode()) + 31) * 31;
            if (this.f1611a != null) {
                i = this.f1611a.hashCode();
            }
            return ((((hashCode + i) * 31) + this.f1612b) * 31) + this.f1614d;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            BusRouteQuery busRouteQuery = (BusRouteQuery) obj;
            if (this.f1613c == null) {
                if (busRouteQuery.f1613c != null) {
                    return false;
                }
            } else if (!this.f1613c.equals(busRouteQuery.f1613c)) {
                return false;
            }
            if (this.f1611a == null) {
                if (busRouteQuery.f1611a != null) {
                    return false;
                }
            } else if (!this.f1611a.equals(busRouteQuery.f1611a)) {
                return false;
            }
            if (this.f1612b != busRouteQuery.f1612b) {
                return false;
            }
            if (this.f1614d != busRouteQuery.f1614d) {
                return false;
            }
            return true;
        }

        public BusRouteQuery clone() {
            try {
                super.clone();
            } catch (Throwable e) {
                C0390i.m1594a(e, "RouteSearch", "BusRouteQueryclone");
            }
            return new BusRouteQuery(this.f1611a, this.f1612b, this.f1613c, this.f1614d);
        }
    }

    public static class DriveRouteQuery implements Parcelable, Cloneable {
        public static final Creator<DriveRouteQuery> CREATOR = new C04261();
        private FromAndTo f1615a;
        private int f1616b;
        private List<LatLonPoint> f1617c;
        private List<List<LatLonPoint>> f1618d;
        private String f1619e;

        static class C04261 implements Creator<DriveRouteQuery> {
            C04261() {
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m1701a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m1702a(i);
            }

            public DriveRouteQuery m1701a(Parcel parcel) {
                return new DriveRouteQuery(parcel);
            }

            public DriveRouteQuery[] m1702a(int i) {
                return new DriveRouteQuery[i];
            }
        }

        public DriveRouteQuery(FromAndTo fromAndTo, int i, List<LatLonPoint> list, List<List<LatLonPoint>> list2, String str) {
            this.f1615a = fromAndTo;
            this.f1616b = i;
            this.f1617c = list;
            this.f1618d = list2;
            this.f1619e = str;
        }

        public FromAndTo getFromAndTo() {
            return this.f1615a;
        }

        public int getMode() {
            return this.f1616b;
        }

        public List<LatLonPoint> getPassedByPoints() {
            return this.f1617c;
        }

        public List<List<LatLonPoint>> getAvoidpolygons() {
            return this.f1618d;
        }

        public String getAvoidRoad() {
            return this.f1619e;
        }

        public String getPassedPointStr() {
            StringBuffer stringBuffer = new StringBuffer();
            if (this.f1617c == null || this.f1617c.size() == 0) {
                return null;
            }
            for (int i = 0; i < this.f1617c.size(); i++) {
                LatLonPoint latLonPoint = (LatLonPoint) this.f1617c.get(i);
                stringBuffer.append(latLonPoint.getLongitude());
                stringBuffer.append(SeparatorConstants.SEPARATOR_ADS_ID);
                stringBuffer.append(latLonPoint.getLatitude());
                if (i < this.f1617c.size() - 1) {
                    stringBuffer.append(";");
                }
            }
            return stringBuffer.toString();
        }

        public boolean hasPassPoint() {
            if (C0390i.m1595a(getPassedPointStr())) {
                return false;
            }
            return true;
        }

        public String getAvoidpolygonsStr() {
            StringBuffer stringBuffer = new StringBuffer();
            if (this.f1618d == null || this.f1618d.size() == 0) {
                return null;
            }
            for (int i = 0; i < this.f1618d.size(); i++) {
                List list = (List) this.f1618d.get(i);
                for (int i2 = 0; i2 < list.size(); i2++) {
                    LatLonPoint latLonPoint = (LatLonPoint) list.get(i2);
                    stringBuffer.append(latLonPoint.getLongitude());
                    stringBuffer.append(SeparatorConstants.SEPARATOR_ADS_ID);
                    stringBuffer.append(latLonPoint.getLatitude());
                    if (i2 < list.size() - 1) {
                        stringBuffer.append(";");
                    }
                }
                if (i < this.f1618d.size() - 1) {
                    stringBuffer.append("|");
                }
            }
            return stringBuffer.toString();
        }

        public boolean hasAvoidpolygons() {
            if (C0390i.m1595a(getAvoidpolygonsStr())) {
                return false;
            }
            return true;
        }

        public boolean hasAvoidRoad() {
            if (C0390i.m1595a(getAvoidRoad())) {
                return false;
            }
            return true;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.f1615a, i);
            parcel.writeInt(this.f1616b);
            parcel.writeTypedList(this.f1617c);
            if (this.f1618d == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(this.f1618d.size());
                for (List writeTypedList : this.f1618d) {
                    parcel.writeTypedList(writeTypedList);
                }
            }
            parcel.writeString(this.f1619e);
        }

        public DriveRouteQuery(Parcel parcel) {
            this.f1615a = (FromAndTo) parcel.readParcelable(FromAndTo.class.getClassLoader());
            this.f1616b = parcel.readInt();
            this.f1617c = parcel.createTypedArrayList(LatLonPoint.CREATOR);
            int readInt = parcel.readInt();
            if (readInt == 0) {
                this.f1618d = null;
            } else {
                this.f1618d = new ArrayList();
            }
            for (int i = 0; i < readInt; i++) {
                this.f1618d.add(parcel.createTypedArrayList(LatLonPoint.CREATOR));
            }
            this.f1619e = parcel.readString();
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((this.f1615a == null ? 0 : this.f1615a.hashCode()) + (((this.f1618d == null ? 0 : this.f1618d.hashCode()) + (((this.f1619e == null ? 0 : this.f1619e.hashCode()) + 31) * 31)) * 31)) * 31) + this.f1616b) * 31;
            if (this.f1617c != null) {
                i = this.f1617c.hashCode();
            }
            return hashCode + i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            DriveRouteQuery driveRouteQuery = (DriveRouteQuery) obj;
            if (this.f1619e == null) {
                if (driveRouteQuery.f1619e != null) {
                    return false;
                }
            } else if (!this.f1619e.equals(driveRouteQuery.f1619e)) {
                return false;
            }
            if (this.f1618d == null) {
                if (driveRouteQuery.f1618d != null) {
                    return false;
                }
            } else if (!this.f1618d.equals(driveRouteQuery.f1618d)) {
                return false;
            }
            if (this.f1615a == null) {
                if (driveRouteQuery.f1615a != null) {
                    return false;
                }
            } else if (!this.f1615a.equals(driveRouteQuery.f1615a)) {
                return false;
            }
            if (this.f1616b != driveRouteQuery.f1616b) {
                return false;
            }
            if (this.f1617c == null) {
                if (driveRouteQuery.f1617c != null) {
                    return false;
                }
                return true;
            } else if (this.f1617c.equals(driveRouteQuery.f1617c)) {
                return true;
            } else {
                return false;
            }
        }

        public DriveRouteQuery clone() {
            try {
                super.clone();
            } catch (Throwable e) {
                C0390i.m1594a(e, "RouteSearch", "DriveRouteQueryclone");
            }
            return new DriveRouteQuery(this.f1615a, this.f1616b, this.f1617c, this.f1618d, this.f1619e);
        }
    }

    public static class FromAndTo implements Parcelable, Cloneable {
        public static final Creator<FromAndTo> CREATOR = new C04271();
        private LatLonPoint f1620a;
        private LatLonPoint f1621b;
        private String f1622c;
        private String f1623d;

        static class C04271 implements Creator<FromAndTo> {
            C04271() {
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m1703a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m1704a(i);
            }

            public FromAndTo m1703a(Parcel parcel) {
                return new FromAndTo(parcel);
            }

            public FromAndTo[] m1704a(int i) {
                return new FromAndTo[i];
            }
        }

        public FromAndTo(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
            this.f1620a = latLonPoint;
            this.f1621b = latLonPoint2;
        }

        public LatLonPoint getFrom() {
            return this.f1620a;
        }

        public LatLonPoint getTo() {
            return this.f1621b;
        }

        public String getStartPoiID() {
            return this.f1622c;
        }

        public void setStartPoiID(String str) {
            this.f1622c = str;
        }

        public String getDestinationPoiID() {
            return this.f1623d;
        }

        public void setDestinationPoiID(String str) {
            this.f1623d = str;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.f1620a, i);
            parcel.writeParcelable(this.f1621b, i);
            parcel.writeString(this.f1622c);
            parcel.writeString(this.f1623d);
        }

        public FromAndTo(Parcel parcel) {
            this.f1620a = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
            this.f1621b = (LatLonPoint) parcel.readParcelable(LatLonPoint.class.getClassLoader());
            this.f1622c = parcel.readString();
            this.f1623d = parcel.readString();
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((this.f1622c == null ? 0 : this.f1622c.hashCode()) + (((this.f1620a == null ? 0 : this.f1620a.hashCode()) + (((this.f1623d == null ? 0 : this.f1623d.hashCode()) + 31) * 31)) * 31)) * 31;
            if (this.f1621b != null) {
                i = this.f1621b.hashCode();
            }
            return hashCode + i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            FromAndTo fromAndTo = (FromAndTo) obj;
            if (this.f1623d == null) {
                if (fromAndTo.f1623d != null) {
                    return false;
                }
            } else if (!this.f1623d.equals(fromAndTo.f1623d)) {
                return false;
            }
            if (this.f1620a == null) {
                if (fromAndTo.f1620a != null) {
                    return false;
                }
            } else if (!this.f1620a.equals(fromAndTo.f1620a)) {
                return false;
            }
            if (this.f1622c == null) {
                if (fromAndTo.f1622c != null) {
                    return false;
                }
            } else if (!this.f1622c.equals(fromAndTo.f1622c)) {
                return false;
            }
            if (this.f1621b == null) {
                if (fromAndTo.f1621b != null) {
                    return false;
                }
                return true;
            } else if (this.f1621b.equals(fromAndTo.f1621b)) {
                return true;
            } else {
                return false;
            }
        }

        public FromAndTo clone() {
            try {
                super.clone();
            } catch (Throwable e) {
                C0390i.m1594a(e, "RouteSearch", "FromAndToclone");
            }
            FromAndTo fromAndTo = new FromAndTo(this.f1620a, this.f1621b);
            fromAndTo.setStartPoiID(this.f1622c);
            fromAndTo.setDestinationPoiID(this.f1623d);
            return fromAndTo;
        }
    }

    public interface OnRouteSearchListener {
        void onBusRouteSearched(BusRouteResult busRouteResult, int i);

        void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i);

        void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i);
    }

    public static class WalkRouteQuery implements Parcelable, Cloneable {
        public static final Creator<WalkRouteQuery> CREATOR = new C04281();
        private FromAndTo f1624a;
        private int f1625b;

        static class C04281 implements Creator<WalkRouteQuery> {
            C04281() {
            }

            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m1705a(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return m1706a(i);
            }

            public WalkRouteQuery m1705a(Parcel parcel) {
                return new WalkRouteQuery(parcel);
            }

            public WalkRouteQuery[] m1706a(int i) {
                return new WalkRouteQuery[i];
            }
        }

        public WalkRouteQuery(FromAndTo fromAndTo, int i) {
            this.f1624a = fromAndTo;
            this.f1625b = i;
        }

        public FromAndTo getFromAndTo() {
            return this.f1624a;
        }

        public int getMode() {
            return this.f1625b;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.f1624a, i);
            parcel.writeInt(this.f1625b);
        }

        public WalkRouteQuery(Parcel parcel) {
            this.f1624a = (FromAndTo) parcel.readParcelable(FromAndTo.class.getClassLoader());
            this.f1625b = parcel.readInt();
        }

        public int hashCode() {
            return (((this.f1624a == null ? 0 : this.f1624a.hashCode()) + 31) * 31) + this.f1625b;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            WalkRouteQuery walkRouteQuery = (WalkRouteQuery) obj;
            if (this.f1624a == null) {
                if (walkRouteQuery.f1624a != null) {
                    return false;
                }
            } else if (!this.f1624a.equals(walkRouteQuery.f1624a)) {
                return false;
            }
            if (this.f1625b != walkRouteQuery.f1625b) {
                return false;
            }
            return true;
        }

        public WalkRouteQuery clone() {
            try {
                super.clone();
            } catch (Throwable e) {
                C0390i.m1594a(e, "RouteSearch", "WalkRouteQueryclone");
            }
            return new WalkRouteQuery(this.f1624a, this.f1625b);
        }
    }

    public RouteSearch(Context context) {
        try {
            Context context2 = context;
            this.f1626a = (IRouteSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.RouteSearchWrapper", ao.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1626a == null) {
            this.f1626a = new ao(context);
        }
    }

    public void setRouteSearchListener(OnRouteSearchListener onRouteSearchListener) {
        if (this.f1626a != null) {
            this.f1626a.setRouteSearchListener(onRouteSearchListener);
        }
    }

    public WalkRouteResult calculateWalkRoute(WalkRouteQuery walkRouteQuery) throws AMapException {
        if (this.f1626a != null) {
            return this.f1626a.calculateWalkRoute(walkRouteQuery);
        }
        return null;
    }

    public void calculateWalkRouteAsyn(WalkRouteQuery walkRouteQuery) {
        if (this.f1626a != null) {
            this.f1626a.calculateWalkRouteAsyn(walkRouteQuery);
        }
    }

    public BusRouteResult calculateBusRoute(BusRouteQuery busRouteQuery) throws AMapException {
        if (this.f1626a != null) {
            return this.f1626a.calculateBusRoute(busRouteQuery);
        }
        return null;
    }

    public void calculateBusRouteAsyn(BusRouteQuery busRouteQuery) {
        if (this.f1626a != null) {
            this.f1626a.calculateBusRouteAsyn(busRouteQuery);
        }
    }

    public DriveRouteResult calculateDriveRoute(DriveRouteQuery driveRouteQuery) throws AMapException {
        if (this.f1626a != null) {
            return this.f1626a.calculateDriveRoute(driveRouteQuery);
        }
        return null;
    }

    public void calculateDriveRouteAsyn(DriveRouteQuery driveRouteQuery) {
        if (this.f1626a != null) {
            this.f1626a.calculateDriveRouteAsyn(driveRouteQuery);
        }
    }
}
