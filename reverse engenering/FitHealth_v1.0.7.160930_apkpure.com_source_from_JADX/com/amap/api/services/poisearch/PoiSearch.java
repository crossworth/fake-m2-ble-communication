package com.amap.api.services.poisearch;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.interfaces.IPoiSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.C0390i;
import com.amap.api.services.proguard.an;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;
import java.util.List;

public class PoiSearch {
    public static final String CHINESE = "zh-CN";
    public static final String ENGLISH = "en";
    private IPoiSearch f1266a = null;

    public interface OnPoiSearchListener {
        void onPoiItemSearched(PoiItem poiItem, int i);

        void onPoiSearched(PoiResult poiResult, int i);
    }

    public static class Query implements Cloneable {
        private String f1251a;
        private String f1252b;
        private String f1253c;
        private int f1254d;
        private int f1255e;
        private String f1256f;
        private boolean f1257g;
        private boolean f1258h;

        public Query(String str, String str2) {
            this(str, str2, null);
        }

        public Query(String str, String str2, String str3) {
            this.f1254d = 0;
            this.f1255e = 20;
            this.f1256f = "zh-CN";
            this.f1257g = false;
            this.f1258h = false;
            this.f1251a = str;
            this.f1252b = str2;
            this.f1253c = str3;
        }

        public String getQueryString() {
            return this.f1251a;
        }

        public void setQueryLanguage(String str) {
            if ("en".equals(str)) {
                this.f1256f = "en";
            } else {
                this.f1256f = "zh-CN";
            }
        }

        protected String getQueryLanguage() {
            return this.f1256f;
        }

        public String getCategory() {
            if (this.f1252b == null || this.f1252b.equals("00") || this.f1252b.equals("00|")) {
                return m1195a();
            }
            return this.f1252b;
        }

        private String m1195a() {
            return "";
        }

        public String getCity() {
            return this.f1253c;
        }

        public int getPageNum() {
            return this.f1254d;
        }

        public void setPageNum(int i) {
            this.f1254d = i;
        }

        public void setPageSize(int i) {
            if (i <= 0) {
                this.f1255e = 20;
            } else if (i > 30) {
                this.f1255e = 30;
            } else {
                this.f1255e = i;
            }
        }

        public int getPageSize() {
            return this.f1255e;
        }

        public void setCityLimit(boolean z) {
            this.f1257g = z;
        }

        public boolean getCityLimit() {
            return this.f1257g;
        }

        public void requireSubPois(boolean z) {
            this.f1258h = z;
        }

        public boolean isRequireSubPois() {
            return this.f1258h;
        }

        public boolean queryEquals(Query query) {
            if (query == null) {
                return false;
            }
            if (query == this) {
                return true;
            }
            if (PoiSearch.m1199b(query.f1251a, this.f1251a) && PoiSearch.m1199b(query.f1252b, this.f1252b) && PoiSearch.m1199b(query.f1256f, this.f1256f) && PoiSearch.m1199b(query.f1253c, this.f1253c) && query.f1257g == this.f1257g && query.f1255e == this.f1255e) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            int i;
            int i2 = 1231;
            int i3 = 0;
            int hashCode = ((this.f1253c == null ? 0 : this.f1253c.hashCode()) + (((this.f1252b == null ? 0 : this.f1252b.hashCode()) + 31) * 31)) * 31;
            if (this.f1257g) {
                i = 1231;
            } else {
                i = 1237;
            }
            i = (i + hashCode) * 31;
            if (!this.f1258h) {
                i2 = 1237;
            }
            i = ((((((this.f1256f == null ? 0 : this.f1256f.hashCode()) + ((i + i2) * 31)) * 31) + this.f1254d) * 31) + this.f1255e) * 31;
            if (this.f1251a != null) {
                i3 = this.f1251a.hashCode();
            }
            return i + i3;
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
            Query query = (Query) obj;
            if (this.f1252b == null) {
                if (query.f1252b != null) {
                    return false;
                }
            } else if (!this.f1252b.equals(query.f1252b)) {
                return false;
            }
            if (this.f1253c == null) {
                if (query.f1253c != null) {
                    return false;
                }
            } else if (!this.f1253c.equals(query.f1253c)) {
                return false;
            }
            if (this.f1256f == null) {
                if (query.f1256f != null) {
                    return false;
                }
            } else if (!this.f1256f.equals(query.f1256f)) {
                return false;
            }
            if (this.f1254d != query.f1254d) {
                return false;
            }
            if (this.f1255e != query.f1255e) {
                return false;
            }
            if (this.f1251a == null) {
                if (query.f1251a != null) {
                    return false;
                }
            } else if (!this.f1251a.equals(query.f1251a)) {
                return false;
            }
            if (this.f1257g != query.f1257g) {
                return false;
            }
            if (this.f1258h != query.f1258h) {
                return false;
            }
            return true;
        }

        public Query clone() {
            try {
                super.clone();
            } catch (Throwable e) {
                C0390i.m1594a(e, "PoiSearch", "queryclone");
            }
            Query query = new Query(this.f1251a, this.f1252b, this.f1253c);
            query.setPageNum(this.f1254d);
            query.setPageSize(this.f1255e);
            query.setQueryLanguage(this.f1256f);
            query.setCityLimit(this.f1257g);
            query.requireSubPois(this.f1258h);
            return query;
        }
    }

    public static class SearchBound implements Cloneable {
        public static final String BOUND_SHAPE = "Bound";
        public static final String ELLIPSE_SHAPE = "Ellipse";
        public static final String POLYGON_SHAPE = "Polygon";
        public static final String RECTANGLE_SHAPE = "Rectangle";
        private LatLonPoint f1259a;
        private LatLonPoint f1260b;
        private int f1261c;
        private LatLonPoint f1262d;
        private String f1263e;
        private boolean f1264f;
        private List<LatLonPoint> f1265g;

        public SearchBound(LatLonPoint latLonPoint, int i) {
            this.f1264f = true;
            this.f1263e = "Bound";
            this.f1261c = i;
            this.f1262d = latLonPoint;
            m1196a(latLonPoint, C0390i.m1590a(i), C0390i.m1590a(i));
        }

        public SearchBound(LatLonPoint latLonPoint, int i, boolean z) {
            this.f1264f = true;
            this.f1263e = "Bound";
            this.f1261c = i;
            this.f1262d = latLonPoint;
            m1196a(latLonPoint, C0390i.m1590a(i), C0390i.m1590a(i));
            this.f1264f = z;
        }

        public SearchBound(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
            this.f1264f = true;
            this.f1263e = "Rectangle";
            m1197a(latLonPoint, latLonPoint2);
        }

        public SearchBound(List<LatLonPoint> list) {
            this.f1264f = true;
            this.f1263e = "Polygon";
            this.f1265g = list;
        }

        private SearchBound(LatLonPoint latLonPoint, LatLonPoint latLonPoint2, int i, LatLonPoint latLonPoint3, String str, List<LatLonPoint> list, boolean z) {
            this.f1264f = true;
            this.f1259a = latLonPoint;
            this.f1260b = latLonPoint2;
            this.f1261c = i;
            this.f1262d = latLonPoint3;
            this.f1263e = str;
            this.f1265g = list;
            this.f1264f = z;
        }

        private void m1197a(LatLonPoint latLonPoint, LatLonPoint latLonPoint2) {
            this.f1259a = latLonPoint;
            this.f1260b = latLonPoint2;
            if (this.f1259a.getLatitude() >= this.f1260b.getLatitude() || this.f1259a.getLongitude() >= this.f1260b.getLongitude()) {
                throw new IllegalArgumentException("invalid rect ");
            }
            this.f1262d = new LatLonPoint((this.f1259a.getLatitude() + this.f1260b.getLatitude()) / 2.0d, (this.f1259a.getLongitude() + this.f1260b.getLongitude()) / 2.0d);
        }

        private void m1196a(LatLonPoint latLonPoint, double d, double d2) {
            double d3 = d / 2.0d;
            double d4 = d2 / 2.0d;
            double latitude = latLonPoint.getLatitude();
            double longitude = latLonPoint.getLongitude();
            m1197a(new LatLonPoint(latitude - d3, longitude - d4), new LatLonPoint(d3 + latitude, d4 + longitude));
        }

        public LatLonPoint getLowerLeft() {
            return this.f1259a;
        }

        public LatLonPoint getUpperRight() {
            return this.f1260b;
        }

        public LatLonPoint getCenter() {
            return this.f1262d;
        }

        public int getRange() {
            return this.f1261c;
        }

        public String getShape() {
            return this.f1263e;
        }

        public boolean isDistanceSort() {
            return this.f1264f;
        }

        public List<LatLonPoint> getPolyGonList() {
            return this.f1265g;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = ((((this.f1265g == null ? 0 : this.f1265g.hashCode()) + (((this.f1260b == null ? 0 : this.f1260b.hashCode()) + (((this.f1259a == null ? 0 : this.f1259a.hashCode()) + (((this.f1264f ? 1231 : 1237) + (((this.f1262d == null ? 0 : this.f1262d.hashCode()) + 31) * 31)) * 31)) * 31)) * 31)) * 31) + this.f1261c) * 31;
            if (this.f1263e != null) {
                i = this.f1263e.hashCode();
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
            SearchBound searchBound = (SearchBound) obj;
            if (this.f1262d == null) {
                if (searchBound.f1262d != null) {
                    return false;
                }
            } else if (!this.f1262d.equals(searchBound.f1262d)) {
                return false;
            }
            if (this.f1264f != searchBound.f1264f) {
                return false;
            }
            if (this.f1259a == null) {
                if (searchBound.f1259a != null) {
                    return false;
                }
            } else if (!this.f1259a.equals(searchBound.f1259a)) {
                return false;
            }
            if (this.f1260b == null) {
                if (searchBound.f1260b != null) {
                    return false;
                }
            } else if (!this.f1260b.equals(searchBound.f1260b)) {
                return false;
            }
            if (this.f1265g == null) {
                if (searchBound.f1265g != null) {
                    return false;
                }
            } else if (!this.f1265g.equals(searchBound.f1265g)) {
                return false;
            }
            if (this.f1261c != searchBound.f1261c) {
                return false;
            }
            if (this.f1263e == null) {
                if (searchBound.f1263e != null) {
                    return false;
                }
                return true;
            } else if (this.f1263e.equals(searchBound.f1263e)) {
                return true;
            } else {
                return false;
            }
        }

        public SearchBound clone() {
            try {
                super.clone();
            } catch (Throwable e) {
                C0390i.m1594a(e, "PoiSearch", "SearchBoundClone");
            }
            return new SearchBound(this.f1259a, this.f1260b, this.f1261c, this.f1262d, this.f1263e, this.f1265g, this.f1264f);
        }
    }

    public PoiSearch(Context context, Query query) {
        try {
            Context context2 = context;
            this.f1266a = (IPoiSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.PoiSearchWrapper", an.class, new Class[]{Context.class, Query.class}, new Object[]{context, query});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1266a == null) {
            this.f1266a = new an(context, query);
        }
    }

    public void setOnPoiSearchListener(OnPoiSearchListener onPoiSearchListener) {
        if (this.f1266a != null) {
            this.f1266a.setOnPoiSearchListener(onPoiSearchListener);
        }
    }

    public void setLanguage(String str) {
        if (this.f1266a != null) {
            this.f1266a.setLanguage(str);
        }
    }

    public String getLanguage() {
        if (this.f1266a != null) {
            return this.f1266a.getLanguage();
        }
        return null;
    }

    public PoiResult searchPOI() throws AMapException {
        if (this.f1266a != null) {
            return this.f1266a.searchPOI();
        }
        return null;
    }

    public void searchPOIAsyn() {
        if (this.f1266a != null) {
            this.f1266a.searchPOIAsyn();
        }
    }

    public PoiItem searchPOIId(String str) throws AMapException {
        if (this.f1266a != null) {
            this.f1266a.searchPOIId(str);
        }
        return null;
    }

    public void searchPOIIdAsyn(String str) {
        if (this.f1266a != null) {
            this.f1266a.searchPOIIdAsyn(str);
        }
    }

    public void setQuery(Query query) {
        if (this.f1266a != null) {
            this.f1266a.setQuery(query);
        }
    }

    public void setBound(SearchBound searchBound) {
        if (this.f1266a != null) {
            this.f1266a.setBound(searchBound);
        }
    }

    public Query getQuery() {
        if (this.f1266a != null) {
            return this.f1266a.getQuery();
        }
        return null;
    }

    public SearchBound getBound() {
        if (this.f1266a != null) {
            return this.f1266a.getBound();
        }
        return null;
    }

    private static boolean m1199b(String str, String str2) {
        if (str == null && str2 == null) {
            return true;
        }
        if (str == null || str2 == null) {
            return false;
        }
        return str.equals(str2);
    }
}
