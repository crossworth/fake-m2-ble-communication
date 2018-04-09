package com.amap.api.services.busline;

import com.amap.api.services.proguard.C0390i;

public class BusStationQuery implements Cloneable {
    private String f1055a;
    private String f1056b;
    private int f1057c = 10;
    private int f1058d = 0;

    public BusStationQuery(String str, String str2) {
        this.f1055a = str;
        this.f1056b = str2;
        if (!m1141a()) {
            throw new IllegalArgumentException("Empty query");
        }
    }

    private boolean m1141a() {
        return !C0390i.m1595a(this.f1055a);
    }

    public String getQueryString() {
        return this.f1055a;
    }

    public String getCity() {
        return this.f1056b;
    }

    public int getPageSize() {
        return this.f1057c;
    }

    public int getPageNumber() {
        return this.f1058d;
    }

    public void setQueryString(String str) {
        this.f1055a = str;
    }

    public void setCity(String str) {
        this.f1056b = str;
    }

    public void setPageSize(int i) {
        int i2 = 20;
        if (i <= 20) {
            i2 = i;
        }
        if (i2 <= 0) {
            i2 = 10;
        }
        this.f1057c = i2;
    }

    public void setPageNumber(int i) {
        this.f1058d = i;
    }

    public BusStationQuery clone() {
        BusStationQuery busStationQuery = new BusStationQuery(this.f1055a, this.f1056b);
        busStationQuery.setPageNumber(this.f1058d);
        busStationQuery.setPageSize(this.f1057c);
        return busStationQuery;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((this.f1056b == null ? 0 : this.f1056b.hashCode()) + 31) * 31) + this.f1058d) * 31) + this.f1057c) * 31;
        if (this.f1055a != null) {
            i = this.f1055a.hashCode();
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
        BusStationQuery busStationQuery = (BusStationQuery) obj;
        if (this.f1056b == null) {
            if (busStationQuery.f1056b != null) {
                return false;
            }
        } else if (!this.f1056b.equals(busStationQuery.f1056b)) {
            return false;
        }
        if (this.f1058d != busStationQuery.f1058d) {
            return false;
        }
        if (this.f1057c != busStationQuery.f1057c) {
            return false;
        }
        if (this.f1055a == null) {
            if (busStationQuery.f1055a != null) {
                return false;
            }
            return true;
        } else if (this.f1055a.equals(busStationQuery.f1055a)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean weakEquals(BusStationQuery busStationQuery) {
        if (this == busStationQuery) {
            return true;
        }
        if (busStationQuery == null) {
            return false;
        }
        if (this.f1056b == null) {
            if (busStationQuery.f1056b != null) {
                return false;
            }
        } else if (!this.f1056b.equals(busStationQuery.f1056b)) {
            return false;
        }
        if (this.f1057c != busStationQuery.f1057c) {
            return false;
        }
        if (this.f1055a == null) {
            if (busStationQuery.f1055a != null) {
                return false;
            }
            return true;
        } else if (this.f1055a.equals(busStationQuery.f1055a)) {
            return true;
        } else {
            return false;
        }
    }
}
