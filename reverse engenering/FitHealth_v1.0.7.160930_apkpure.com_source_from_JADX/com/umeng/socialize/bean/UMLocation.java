package com.umeng.socialize.bean;

import android.location.Location;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.io.Serializable;

public class UMLocation implements Serializable {
    private static final long f3254a = 1;
    private double f3255b;
    private double f3256c;

    public UMLocation(double d, double d2) {
        this.f3255b = d;
        this.f3256c = d2;
    }

    public double getLatitude() {
        return this.f3255b;
    }

    public void setLatitude(double d) {
        this.f3255b = d;
    }

    public double getLongitude() {
        return this.f3256c;
    }

    public void setLongitude(double d) {
        this.f3256c = d;
    }

    public String toString() {
        return SocializeConstants.OP_OPEN_PAREN + this.f3256c + SeparatorConstants.SEPARATOR_ADS_ID + this.f3255b + SocializeConstants.OP_CLOSE_PAREN;
    }

    public static UMLocation build(String str) {
        try {
            String[] split = str.substring(1, str.length() - 1).split(SeparatorConstants.SEPARATOR_ADS_ID);
            return new UMLocation(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
        } catch (Exception e) {
            return null;
        }
    }

    public static UMLocation build(Location location) {
        try {
            if (!(location.getLatitude() == 0.0d || location.getLongitude() == 0.0d)) {
                return new UMLocation(location.getLatitude(), location.getLongitude());
            }
        } catch (Exception e) {
        }
        return null;
    }
}
