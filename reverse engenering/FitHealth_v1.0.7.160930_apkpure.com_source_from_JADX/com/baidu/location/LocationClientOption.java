package com.baidu.location;

public final class LocationClientOption {
    public static final int GpsFirst = 1;
    public static final int MIN_SCAN_SPAN = 1000;
    public static final int NetWorkFirst = 2;
    protected int f2105a = 3;
    protected String f2106byte = "com.baidu.location.service_v2.9";
    protected boolean f2107case = false;
    protected String f2108char = "detail";
    protected float f2109do = 500.0f;
    protected boolean f2110else = false;
    protected boolean f2111for = true;
    protected int f2112goto = 1;
    protected boolean f2113if = false;
    protected int f2114int = 0;
    protected int f2115long = an.f2195I;
    protected String f2116new = "SDK2.0";
    protected String f2117try = BDGeofence.COORD_TYPE_GCJ;
    protected boolean f2118void = false;

    public LocationClientOption(LocationClientOption locationClientOption) {
        this.f2117try = locationClientOption.f2117try;
        this.f2108char = locationClientOption.f2108char;
        this.f2107case = locationClientOption.f2107case;
        this.f2114int = locationClientOption.f2114int;
        this.f2115long = locationClientOption.f2115long;
        this.f2116new = locationClientOption.f2116new;
        this.f2112goto = locationClientOption.f2112goto;
        this.f2118void = locationClientOption.f2118void;
        this.f2113if = locationClientOption.f2113if;
        this.f2109do = locationClientOption.f2109do;
        this.f2105a = locationClientOption.f2105a;
        this.f2106byte = locationClientOption.f2106byte;
        this.f2111for = locationClientOption.f2111for;
    }

    public void disableCache(boolean z) {
        this.f2111for = z;
    }

    public boolean equals(LocationClientOption locationClientOption) {
        return this.f2117try.equals(locationClientOption.f2117try) && this.f2108char.equals(locationClientOption.f2108char) && this.f2107case == locationClientOption.f2107case && this.f2114int == locationClientOption.f2114int && this.f2115long == locationClientOption.f2115long && this.f2116new.equals(locationClientOption.f2116new) && this.f2118void == locationClientOption.f2118void && this.f2112goto == locationClientOption.f2112goto && this.f2105a == locationClientOption.f2105a && this.f2113if == locationClientOption.f2113if && this.f2109do == locationClientOption.f2109do && this.f2111for == locationClientOption.f2111for;
    }

    public String getAddrType() {
        return this.f2108char;
    }

    public String getCoorType() {
        return this.f2117try;
    }

    public float getPoiDistance() {
        return this.f2109do;
    }

    public boolean getPoiExtranInfo() {
        return this.f2113if;
    }

    public int getPoiNumber() {
        return this.f2105a;
    }

    public int getPriority() {
        return this.f2112goto;
    }

    public String getProdName() {
        return this.f2116new;
    }

    public int getScanSpan() {
        return this.f2114int;
    }

    public String getServiceName() {
        return this.f2106byte;
    }

    public int getTimeOut() {
        return this.f2115long;
    }

    public boolean isDisableCache() {
        return this.f2111for;
    }

    public boolean isLocationNotify() {
        return this.f2118void;
    }

    public boolean isOpenGps() {
        return this.f2107case;
    }

    public void setAddrType(String str) {
        if (str.length() > 32) {
            str = str.substring(0, 32);
        }
        this.f2108char = str;
    }

    public void setCoorType(String str) {
        String toLowerCase = str.toLowerCase();
        if (toLowerCase.equals(BDGeofence.COORD_TYPE_GCJ) || toLowerCase.equals(BDGeofence.COORD_TYPE_BD09) || toLowerCase.equals(BDGeofence.COORD_TYPE_BD09LL)) {
            this.f2117try = toLowerCase;
        }
    }

    public void setLocationNotify(boolean z) {
        this.f2118void = z;
    }

    public void setOpenGps(boolean z) {
        this.f2107case = z;
    }

    public void setPoiDistance(float f) {
        this.f2109do = f;
    }

    public void setPoiExtraInfo(boolean z) {
        this.f2113if = z;
    }

    public void setPoiNumber(int i) {
        if (i > 10) {
            i = 10;
        }
        this.f2105a = i;
    }

    public void setPriority(int i) {
        if (i == 1 || i == 2) {
            this.f2112goto = i;
        }
    }

    public void setProdName(String str) {
        if (str.length() > 64) {
            str = str.substring(0, 64);
        }
        this.f2116new = str;
    }

    public void setScanSpan(int i) {
        this.f2114int = i;
    }

    public void setServiceName(String str) {
        this.f2106byte = str;
    }

    public void setTimeOut(int i) {
        this.f2115long = i;
    }
}
