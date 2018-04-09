package com.amap.api.services.busline;

public class BusLineQuery implements Cloneable {
    private String f1038a;
    private String f1039b;
    private int f1040c = 10;
    private int f1041d = 0;
    private SearchType f1042e;

    public enum SearchType {
        BY_LINE_ID,
        BY_LINE_NAME
    }

    public BusLineQuery(String str, SearchType searchType, String str2) {
        this.f1038a = str;
        this.f1042e = searchType;
        this.f1039b = str2;
    }

    public SearchType getCategory() {
        return this.f1042e;
    }

    public String getQueryString() {
        return this.f1038a;
    }

    public void setQueryString(String str) {
        this.f1038a = str;
    }

    public String getCity() {
        return this.f1039b;
    }

    public void setCity(String str) {
        this.f1039b = str;
    }

    public int getPageSize() {
        return this.f1040c;
    }

    public void setPageSize(int i) {
        this.f1040c = i;
    }

    public int getPageNumber() {
        return this.f1041d;
    }

    public void setPageNumber(int i) {
        this.f1041d = i;
    }

    public void setCategory(SearchType searchType) {
        this.f1042e = searchType;
    }

    public BusLineQuery clone() {
        BusLineQuery busLineQuery = new BusLineQuery(this.f1038a, this.f1042e, this.f1039b);
        busLineQuery.setPageNumber(this.f1041d);
        busLineQuery.setPageSize(this.f1040c);
        return busLineQuery;
    }

    public boolean weakEquals(BusLineQuery busLineQuery) {
        if (busLineQuery.getQueryString().equals(this.f1038a) && busLineQuery.getCity().equals(this.f1039b) && busLineQuery.getPageSize() == this.f1040c && busLineQuery.getCategory().compareTo(this.f1042e) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i;
        int i2 = 0;
        if (this.f1042e == null) {
            i = 0;
        } else {
            i = this.f1042e.hashCode();
        }
        i = ((((((this.f1039b == null ? 0 : this.f1039b.hashCode()) + ((i + 31) * 31)) * 31) + this.f1041d) * 31) + this.f1040c) * 31;
        if (this.f1038a != null) {
            i2 = this.f1038a.hashCode();
        }
        return i + i2;
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
        BusLineQuery busLineQuery = (BusLineQuery) obj;
        if (this.f1042e != busLineQuery.f1042e) {
            return false;
        }
        if (this.f1039b == null) {
            if (busLineQuery.f1039b != null) {
                return false;
            }
        } else if (!this.f1039b.equals(busLineQuery.f1039b)) {
            return false;
        }
        if (this.f1041d != busLineQuery.f1041d) {
            return false;
        }
        if (this.f1040c != busLineQuery.f1040c) {
            return false;
        }
        if (this.f1038a == null) {
            if (busLineQuery.f1038a != null) {
                return false;
            }
            return true;
        } else if (this.f1038a.equals(busLineQuery.f1038a)) {
            return true;
        } else {
            return false;
        }
    }
}
