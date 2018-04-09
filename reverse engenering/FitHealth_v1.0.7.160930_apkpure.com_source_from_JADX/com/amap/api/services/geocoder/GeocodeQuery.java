package com.amap.api.services.geocoder;

public class GeocodeQuery {
    private String f1169a;
    private String f1170b;

    public GeocodeQuery(String str, String str2) {
        this.f1169a = str;
        this.f1170b = str2;
    }

    public String getLocationName() {
        return this.f1169a;
    }

    public void setLocationName(String str) {
        this.f1169a = str;
    }

    public String getCity() {
        return this.f1170b;
    }

    public void setCity(String str) {
        this.f1170b = str;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.f1170b == null ? 0 : this.f1170b.hashCode()) + 31) * 31;
        if (this.f1169a != null) {
            i = this.f1169a.hashCode();
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
        GeocodeQuery geocodeQuery = (GeocodeQuery) obj;
        if (this.f1170b == null) {
            if (geocodeQuery.f1170b != null) {
                return false;
            }
        } else if (!this.f1170b.equals(geocodeQuery.f1170b)) {
            return false;
        }
        if (this.f1169a == null) {
            if (geocodeQuery.f1169a != null) {
                return false;
            }
            return true;
        } else if (this.f1169a.equals(geocodeQuery.f1169a)) {
            return true;
        } else {
            return false;
        }
    }
}
