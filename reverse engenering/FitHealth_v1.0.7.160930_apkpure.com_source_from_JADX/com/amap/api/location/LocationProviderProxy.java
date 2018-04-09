package com.amap.api.location;

import android.location.Criteria;
import android.location.LocationManager;
import android.location.LocationProvider;

public class LocationProviderProxy {
    public static final String AMapNetwork = "lbs";
    public static final int AVAILABLE = 2;
    public static final int OUT_OF_SERVICE = 0;
    public static final int TEMPORARILY_UNAVAILABLE = 1;
    private LocationManager f50a;
    private String f51b;

    protected LocationProviderProxy(LocationManager locationManager, String str) {
        this.f50a = locationManager;
        this.f51b = str;
    }

    static LocationProviderProxy m41a(LocationManager locationManager, String str) {
        return new LocationProviderProxy(locationManager, str);
    }

    private LocationProvider m40a() {
        try {
            if (this.f50a != null) {
                return this.f50a.getProvider(this.f51b);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return null;
    }

    public int getAccuracy() {
        try {
            if (AMapNetwork != null && AMapNetwork.equals(this.f51b)) {
                return 2;
            }
            if (m40a() != null) {
                return m40a().getAccuracy();
            }
            return -1;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public String getName() {
        try {
            if (AMapNetwork != null && AMapNetwork.equals(this.f51b)) {
                return AMapNetwork;
            }
            if (m40a() != null) {
                return m40a().getName();
            }
            return "null";
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public int getPowerRequirement() {
        try {
            if (AMapNetwork != null && AMapNetwork.equals(this.f51b)) {
                return 2;
            }
            if (m40a() != null) {
                return m40a().getPowerRequirement();
            }
            return -1;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean hasMonetaryCost() {
        boolean z = false;
        try {
            if ((AMapNetwork == null || !AMapNetwork.equals(this.f51b)) && m40a() != null) {
                z = m40a().hasMonetaryCost();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return z;
    }

    public boolean meetsCriteria(Criteria criteria) {
        try {
            if (AMapNetwork == null || !AMapNetwork.equals(this.f51b)) {
                if (m40a() != null) {
                    return m40a().meetsCriteria(criteria);
                }
                return false;
            } else if (criteria == null) {
                return true;
            } else {
                if (criteria.isAltitudeRequired() || criteria.isBearingRequired() || criteria.isSpeedRequired() || criteria.getAccuracy() == 1) {
                    return false;
                }
                return true;
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean requiresCell() {
        boolean z = true;
        try {
            if ((AMapNetwork == null || !AMapNetwork.equals(this.f51b)) && m40a() != null) {
                z = m40a().requiresCell();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return z;
    }

    public boolean requiresNetwork() {
        boolean z = true;
        try {
            if ((AMapNetwork == null || !AMapNetwork.equals(this.f51b)) && m40a() != null) {
                z = m40a().requiresNetwork();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return z;
    }

    public boolean requiresSatellite() {
        try {
            if (AMapNetwork != null && AMapNetwork.equals(this.f51b)) {
                return false;
            }
            if (m40a() != null) {
                return m40a().requiresNetwork();
            }
            return true;
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public boolean supportsAltitude() {
        boolean z = false;
        try {
            if ((AMapNetwork == null || !AMapNetwork.equals(this.f51b)) && m40a() != null) {
                z = m40a().supportsAltitude();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return z;
    }

    public boolean supportsBearing() {
        boolean z = false;
        try {
            if ((AMapNetwork == null || !AMapNetwork.equals(this.f51b)) && m40a() != null) {
                z = m40a().supportsBearing();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return z;
    }

    public boolean supportsSpeed() {
        boolean z = false;
        try {
            if ((AMapNetwork == null || !AMapNetwork.equals(this.f51b)) && m40a() != null) {
                z = m40a().supportsSpeed();
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        return z;
    }
}
