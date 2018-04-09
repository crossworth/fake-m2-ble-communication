package com.amap.api.services.cloud;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.interfaces.ICloudSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.C0390i;
import com.amap.api.services.proguard.C0408x;
import com.amap.api.services.proguard.aj;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class CloudSearch {
    private ICloudSearch f1098a;

    public interface OnCloudSearchListener {
        void onCloudItemDetailSearched(CloudItemDetail cloudItemDetail, int i);

        void onCloudSearched(CloudResult cloudResult, int i);
    }

    public static class Query implements Cloneable {
        private String f1080a;
        private int f1081b = 0;
        private int f1082c = 20;
        private String f1083d;
        private SearchBound f1084e;
        private Sortingrules f1085f;
        private ArrayList<C0408x> f1086g = new ArrayList();
        private HashMap<String, String> f1087h = new HashMap();

        public Query(String str, String str2, SearchBound searchBound) throws AMapException {
            if (C0390i.m1595a(str) || searchBound == null) {
                throw new AMapException("无效的参数 - IllegalArgumentException");
            }
            this.f1083d = str;
            this.f1080a = str2;
            this.f1084e = searchBound;
        }

        private Query() {
        }

        public String getQueryString() {
            return this.f1080a;
        }

        public void setTableID(String str) {
            this.f1083d = str;
        }

        public String getTableID() {
            return this.f1083d;
        }

        public int getPageNum() {
            return this.f1081b;
        }

        public void setPageNum(int i) {
            this.f1081b = i;
        }

        public void setPageSize(int i) {
            if (i <= 0) {
                this.f1082c = 20;
            } else if (i > 100) {
                this.f1082c = 100;
            } else {
                this.f1082c = i;
            }
        }

        public int getPageSize() {
            return this.f1082c;
        }

        public void setBound(SearchBound searchBound) {
            this.f1084e = searchBound;
        }

        public SearchBound getBound() {
            return this.f1084e;
        }

        public void addFilterString(String str, String str2) {
            this.f1087h.put(str, str2);
        }

        public String getFilterString() {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                for (Entry entry : this.f1087h.entrySet()) {
                    stringBuffer.append(entry.getKey().toString()).append(":").append(entry.getValue().toString()).append(SocializeConstants.OP_DIVIDER_PLUS);
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return stringBuffer.toString();
        }

        public void addFilterNum(String str, String str2, String str3) {
            this.f1086g.add(new C0408x(str, str2, str3));
        }

        private ArrayList<C0408x> m1150a() {
            if (this.f1086g == null) {
                return null;
            }
            ArrayList<C0408x> arrayList = new ArrayList();
            arrayList.addAll(this.f1086g);
            return arrayList;
        }

        private HashMap<String, String> m1153b() {
            if (this.f1087h == null) {
                return null;
            }
            HashMap<String, String> hashMap = new HashMap();
            hashMap.putAll(this.f1087h);
            return hashMap;
        }

        public String getFilterNumString() {
            StringBuffer stringBuffer = new StringBuffer();
            try {
                Iterator it = this.f1086g.iterator();
                while (it.hasNext()) {
                    C0408x c0408x = (C0408x) it.next();
                    stringBuffer.append(c0408x.m1668a());
                    stringBuffer.append(":[");
                    stringBuffer.append(c0408x.m1669b());
                    stringBuffer.append(SeparatorConstants.SEPARATOR_ADS_ID);
                    stringBuffer.append(c0408x.m1670c());
                    stringBuffer.append("]");
                    stringBuffer.append(SocializeConstants.OP_DIVIDER_PLUS);
                }
                if (stringBuffer.length() > 0) {
                    stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            return stringBuffer.toString();
        }

        public void setSortingrules(Sortingrules sortingrules) {
            this.f1085f = sortingrules;
        }

        public Sortingrules getSortingrules() {
            return this.f1085f;
        }

        private boolean m1151a(SearchBound searchBound, SearchBound searchBound2) {
            if (searchBound == null && searchBound2 == null) {
                return true;
            }
            if (searchBound == null || searchBound2 == null) {
                return false;
            }
            return searchBound.equals(searchBound2);
        }

        private boolean m1152a(Sortingrules sortingrules, Sortingrules sortingrules2) {
            if (sortingrules == null && sortingrules2 == null) {
                return true;
            }
            if (sortingrules == null || sortingrules2 == null) {
                return false;
            }
            return sortingrules.equals(sortingrules2);
        }

        public boolean queryEquals(Query query) {
            if (query == null) {
                return false;
            }
            if (query == this) {
                return true;
            }
            if (CloudSearch.m1158b(query.f1080a, this.f1080a) && CloudSearch.m1158b(query.getTableID(), getTableID()) && CloudSearch.m1158b(query.getFilterString(), getFilterString()) && CloudSearch.m1158b(query.getFilterNumString(), getFilterNumString()) && query.f1082c == this.f1082c && m1151a(query.getBound(), getBound()) && m1152a(query.getSortingrules(), getSortingrules())) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i;
            int i2 = 0;
            if (this.f1086g == null) {
                i = 0;
            } else {
                i = this.f1086g.hashCode();
            }
            int i3 = (i + 31) * 31;
            if (this.f1087h == null) {
                i = 0;
            } else {
                i = this.f1087h.hashCode();
            }
            i3 = (i + i3) * 31;
            if (this.f1084e == null) {
                i = 0;
            } else {
                i = this.f1084e.hashCode();
            }
            i3 = (((((i + i3) * 31) + this.f1081b) * 31) + this.f1082c) * 31;
            if (this.f1080a == null) {
                i = 0;
            } else {
                i = this.f1080a.hashCode();
            }
            i3 = (i + i3) * 31;
            if (this.f1085f == null) {
                i = 0;
            } else {
                i = this.f1085f.hashCode();
            }
            i = (i + i3) * 31;
            if (this.f1083d != null) {
                i2 = this.f1083d.hashCode();
            }
            return i + i2;
        }

        public boolean equals(Object obj) {
            if (obj == null || !(obj instanceof Query)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            Query query = (Query) obj;
            if (queryEquals(query) && query.f1081b == this.f1081b) {
                return true;
            }
            return false;
        }

        public Query clone() {
            Query query;
            Query query2;
            AMapException e;
            try {
                super.clone();
            } catch (CloneNotSupportedException e2) {
                e2.printStackTrace();
            }
            try {
                query = new Query(this.f1083d, this.f1080a, this.f1084e);
                try {
                    query.setPageNum(this.f1081b);
                    query.setPageSize(this.f1082c);
                    query.setSortingrules(getSortingrules());
                    query.f1086g = m1150a();
                    query.f1087h = m1153b();
                    query2 = query;
                } catch (AMapException e3) {
                    e = e3;
                    e.printStackTrace();
                    query2 = query;
                    if (query2 == null) {
                        return new Query();
                    }
                    return query2;
                }
            } catch (AMapException e4) {
                e = e4;
                query = null;
                e.printStackTrace();
                query2 = query;
                if (query2 == null) {
                    return query2;
                }
                return new Query();
            }
            if (query2 == null) {
                return new Query();
            }
            return query2;
        }
    }

    public static class SearchBound implements Cloneable {
        public static final String BOUND_SHAPE = "Bound";
        public static final String LOCAL_SHAPE = "Local";
        public static final String POLYGON_SHAPE = "Polygon";
        public static final String RECTANGLE_SHAPE = "Rectangle";
        private LatLonPoint f1088a;
        private LatLonPoint f1089b;
        private int f1090c;
        private LatLonPoint f1091d;
        private String f1092e;
        private List<LatLonPoint> f1093f;
        private String f1094g;

        public SearchBound(LatLonPoint latLonPoint, int i) {
            this.f1092e = "Bound";
            this.f1090c = i;
            this.f1091d = latLonPoint;
        }

        public SearchBound(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
            this.f1092e = "Rectangle";
            m1155a(latLonPoint, latLonPoint2);
        }

        public SearchBound(List<LatLonPoint> list) {
            this.f1092e = "Polygon";
            this.f1093f = list;
        }

        public SearchBound(String str) {
            this.f1092e = LOCAL_SHAPE;
            this.f1094g = str;
        }

        private void m1155a(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
            this.f1088a = latLonPoint;
            this.f1089b = latLonPoint2;
            if (this.f1088a.getLatitude() >= this.f1089b.getLatitude() || this.f1088a.getLongitude() >= this.f1089b.getLongitude()) {
                throw new IllegalArgumentException("invalid rect ");
            }
        }

        public LatLonPoint getLowerLeft() {
            return this.f1088a;
        }

        public LatLonPoint getUpperRight() {
            return this.f1089b;
        }

        public LatLonPoint getCenter() {
            return this.f1091d;
        }

        public int getRange() {
            return this.f1090c;
        }

        public String getShape() {
            return this.f1092e;
        }

        public String getCity() {
            return this.f1094g;
        }

        public List<LatLonPoint> getPolyGonList() {
            return this.f1093f;
        }

        private boolean m1156a(List<LatLonPoint> list, List<LatLonPoint> list2) {
            if (list == null && list2 == null) {
                return true;
            }
            if (list == null || list2 == null || list.size() != list2.size()) {
                return false;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (!((LatLonPoint) list.get(i)).equals(list2.get(i))) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i;
            int i2 = 0;
            if (this.f1091d == null) {
                i = 0;
            } else {
                i = this.f1091d.hashCode();
            }
            int i3 = (i + 31) * 31;
            if (this.f1088a == null) {
                i = 0;
            } else {
                i = this.f1088a.hashCode();
            }
            i3 = (i + i3) * 31;
            if (this.f1089b == null) {
                i = 0;
            } else {
                i = this.f1089b.hashCode();
            }
            i3 = (i + i3) * 31;
            if (this.f1093f == null) {
                i = 0;
            } else {
                i = this.f1093f.hashCode();
            }
            i = ((this.f1092e == null ? 0 : this.f1092e.hashCode()) + ((((i + i3) * 31) + this.f1090c) * 31)) * 31;
            if (this.f1094g != null) {
                i2 = this.f1094g.hashCode();
            }
            return i + i2;
        }

        public boolean equals(Object obj) {
            boolean z = true;
            if (obj == null || !(obj instanceof SearchBound)) {
                return false;
            }
            SearchBound searchBound = (SearchBound) obj;
            if (!getShape().equalsIgnoreCase(searchBound.getShape())) {
                return false;
            }
            if (getShape().equals("Bound")) {
                if (!(searchBound.f1091d.equals(this.f1091d) && searchBound.f1090c == this.f1090c)) {
                    z = false;
                }
                return z;
            } else if (getShape().equals("Polygon")) {
                return m1156a(searchBound.f1093f, this.f1093f);
            } else {
                if (getShape().equals(LOCAL_SHAPE)) {
                    return searchBound.f1094g.equals(this.f1094g);
                }
                if (!(searchBound.f1088a.equals(this.f1088a) && searchBound.f1089b.equals(this.f1089b))) {
                    z = false;
                }
                return z;
            }
        }

        private List<LatLonPoint> m1154a() {
            if (this.f1093f == null) {
                return null;
            }
            List<LatLonPoint> arrayList = new ArrayList();
            for (LatLonPoint latLonPoint : this.f1093f) {
                arrayList.add(new LatLonPoint(latLonPoint.getLatitude(), latLonPoint.getLongitude()));
            }
            return arrayList;
        }

        public SearchBound clone() {
            try {
                super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            if (getShape().equals("Bound")) {
                return new SearchBound(this.f1091d, this.f1090c);
            }
            if (getShape().equals("Polygon")) {
                return new SearchBound(m1154a());
            }
            if (getShape().equals(LOCAL_SHAPE)) {
                return new SearchBound(this.f1094g);
            }
            return new SearchBound(this.f1088a, this.f1089b);
        }
    }

    public static class Sortingrules {
        public static final int DISTANCE = 1;
        public static final int WEIGHT = 0;
        private int f1095a = 0;
        private String f1096b;
        private boolean f1097c = true;

        public Sortingrules(String str, boolean z) {
            this.f1096b = str;
            this.f1097c = z;
        }

        public Sortingrules(int i) {
            this.f1095a = i;
        }

        public String toString() {
            String str = "";
            if (C0390i.m1595a(this.f1096b)) {
                if (this.f1095a == 0) {
                    return "_weight";
                }
                if (this.f1095a == 1) {
                    return "_distance";
                }
                return str;
            } else if (this.f1097c) {
                return this.f1096b + ":" + 1;
            } else {
                return this.f1096b + ":" + 0;
            }
        }

        public int hashCode() {
            return (((this.f1096b == null ? 0 : this.f1096b.hashCode()) + (((this.f1097c ? 1231 : 1237) + 31) * 31)) * 31) + this.f1095a;
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
            Sortingrules sortingrules = (Sortingrules) obj;
            if (this.f1097c != sortingrules.f1097c) {
                return false;
            }
            if (this.f1096b == null) {
                if (sortingrules.f1096b != null) {
                    return false;
                }
            } else if (!this.f1096b.equals(sortingrules.f1096b)) {
                return false;
            }
            if (this.f1095a != sortingrules.f1095a) {
                return false;
            }
            return true;
        }
    }

    public CloudSearch(Context context) {
        try {
            Context context2 = context;
            this.f1098a = (ICloudSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.CloudSearchWrapper", aj.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1098a == null) {
            this.f1098a = new aj(context);
        }
    }

    public void setOnCloudSearchListener(OnCloudSearchListener onCloudSearchListener) {
        if (this.f1098a != null) {
            this.f1098a.setOnCloudSearchListener(onCloudSearchListener);
        }
    }

    public void searchCloudAsyn(Query query) {
        if (this.f1098a != null) {
            this.f1098a.searchCloudAsyn(query);
        }
    }

    public void searchCloudDetailAsyn(String str, String str2) {
        if (this.f1098a != null) {
            this.f1098a.searchCloudDetailAsyn(str, str2);
        }
    }

    private static boolean m1158b(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }
}
