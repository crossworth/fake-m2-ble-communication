package twitter4j;

import java.io.Serializable;

public class GeoLocation implements Serializable {
    private static final long serialVersionUID = 6353721071298376949L;
    private double latitude;
    private double longitude;

    public GeoLocation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    GeoLocation() {
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GeoLocation)) {
            return false;
        }
        GeoLocation that = (GeoLocation) o;
        if (Double.compare(that.getLatitude(), this.latitude) != 0) {
            return false;
        }
        if (Double.compare(that.getLongitude(), this.longitude) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        long temp = Double.doubleToLongBits(this.latitude);
        int result = (int) ((temp >>> 32) ^ temp);
        temp = Double.doubleToLongBits(this.longitude);
        return (result * 31) + ((int) ((temp >>> 32) ^ temp));
    }

    public String toString() {
        return "GeoLocation{latitude=" + this.latitude + ", longitude=" + this.longitude + '}';
    }
}
