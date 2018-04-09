package com.baidu.location;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.amap.api.maps.model.GroundOverlayOptions;
import com.baidu.location.p000a.C0495a;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import org.json.JSONObject;

public final class BDLocation implements C1619j, Parcelable {
    public static final Creator CREATOR = new C04881();
    public static final int TypeCacheLocation = 65;
    public static final int TypeCriteriaException = 62;
    public static final int TypeGpsLocation = 61;
    public static final int TypeNetWorkException = 63;
    public static final int TypeNetWorkLocation = 161;
    public static final int TypeNone = 0;
    public static final int TypeOffLineLocation = 66;
    public static final int TypeOffLineLocationFail = 67;
    public static final int TypeOffLineLocationNetworkFail = 68;
    public static final int TypeServerError = 167;
    private String h0;
    private int h1;
    private int h2;
    private double h3;
    private boolean h4;
    private float h5;
    private boolean h6;
    private String h7;
    private boolean h8;
    private float h9;
    private double ia;
    private String ib;
    private float ic;
    private boolean id;
    private boolean ie;
    private String ig;
    private boolean ih;
    private String ii;
    private boolean ij;
    private double ik;
    private C0489a il;

    final class C04881 implements Creator {
        C04881() {
        }

        public BDLocation m2120a(Parcel parcel) {
            return new BDLocation(parcel);
        }

        public BDLocation[] m2121a(int i) {
            return new BDLocation[i];
        }

        public /* bridge */ /* synthetic */ Object createFromParcel(Parcel parcel) {
            return m2120a(parcel);
        }

        public /* bridge */ /* synthetic */ Object[] newArray(int i) {
            return m2121a(i);
        }
    }

    public class C0489a {
        final /* synthetic */ BDLocation f2092a;
        public String f2093byte = null;
        public String f2094do = null;
        public String f2095for = null;
        public String f2096if = null;
        public String f2097int = null;
        public String f2098new = null;
        public String f2099try = null;

        public C0489a(BDLocation bDLocation) {
            this.f2092a = bDLocation;
        }
    }

    public BDLocation() {
        this.h1 = 0;
        this.ii = null;
        this.ia = Double.MIN_VALUE;
        this.h3 = Double.MIN_VALUE;
        this.ih = false;
        this.ik = Double.MIN_VALUE;
        this.h4 = false;
        this.h5 = 0.0f;
        this.h6 = false;
        this.ic = 0.0f;
        this.ie = false;
        this.h2 = -1;
        this.h9 = GroundOverlayOptions.NO_DIMENSION;
        this.ib = null;
        this.h7 = null;
        this.id = false;
        this.h8 = false;
        this.h0 = null;
        this.ij = false;
        this.il = new C0489a(this);
        this.ig = null;
    }

    private BDLocation(Parcel parcel) {
        this.h1 = 0;
        this.ii = null;
        this.ia = Double.MIN_VALUE;
        this.h3 = Double.MIN_VALUE;
        this.ih = false;
        this.ik = Double.MIN_VALUE;
        this.h4 = false;
        this.h5 = 0.0f;
        this.h6 = false;
        this.ic = 0.0f;
        this.ie = false;
        this.h2 = -1;
        this.h9 = GroundOverlayOptions.NO_DIMENSION;
        this.ib = null;
        this.h7 = null;
        this.id = false;
        this.h8 = false;
        this.h0 = null;
        this.ij = false;
        this.il = new C0489a(this);
        this.ig = null;
        this.h1 = parcel.readInt();
        this.ii = parcel.readString();
        this.ia = parcel.readDouble();
        this.h3 = parcel.readDouble();
        this.ik = parcel.readDouble();
        this.h5 = parcel.readFloat();
        this.ic = parcel.readFloat();
        this.h2 = parcel.readInt();
        this.h9 = parcel.readFloat();
        this.h7 = parcel.readString();
        this.ig = parcel.readString();
        this.il.f2096if = parcel.readString();
        this.il.f2098new = parcel.readString();
        this.il.f2097int = parcel.readString();
        this.il.f2093byte = parcel.readString();
        this.il.f2094do = parcel.readString();
        this.il.f2095for = parcel.readString();
        this.il.f2099try = parcel.readString();
        boolean[] zArr = new boolean[7];
        parcel.readBooleanArray(zArr);
        this.ih = zArr[0];
        this.h4 = zArr[1];
        this.h6 = zArr[2];
        this.ie = zArr[3];
        this.id = zArr[4];
        this.h8 = zArr[5];
        this.ij = zArr[6];
    }

    public BDLocation(BDLocation bDLocation) {
        this.h1 = 0;
        this.ii = null;
        this.ia = Double.MIN_VALUE;
        this.h3 = Double.MIN_VALUE;
        this.ih = false;
        this.ik = Double.MIN_VALUE;
        this.h4 = false;
        this.h5 = 0.0f;
        this.h6 = false;
        this.ic = 0.0f;
        this.ie = false;
        this.h2 = -1;
        this.h9 = GroundOverlayOptions.NO_DIMENSION;
        this.ib = null;
        this.h7 = null;
        this.id = false;
        this.h8 = false;
        this.h0 = null;
        this.ij = false;
        this.il = new C0489a(this);
        this.ig = null;
        this.h1 = bDLocation.h1;
        this.ii = bDLocation.ii;
        this.ia = bDLocation.ia;
        this.h3 = bDLocation.h3;
        this.ih = bDLocation.ih;
        bDLocation.ik = bDLocation.ik;
        this.h4 = bDLocation.h4;
        this.h5 = bDLocation.h5;
        this.h6 = bDLocation.h6;
        this.ic = bDLocation.ic;
        this.ie = bDLocation.ie;
        this.h2 = bDLocation.h2;
        this.h9 = bDLocation.h9;
        this.ib = bDLocation.ib;
        this.h7 = bDLocation.h7;
        this.id = bDLocation.id;
        this.h8 = bDLocation.h8;
        this.h0 = bDLocation.h0;
        this.ij = bDLocation.ij;
        this.il = new C0489a(this);
        this.il.f2096if = bDLocation.il.f2096if;
        this.il.f2098new = bDLocation.il.f2098new;
        this.il.f2097int = bDLocation.il.f2097int;
        this.il.f2093byte = bDLocation.il.f2093byte;
        this.il.f2094do = bDLocation.il.f2094do;
        this.il.f2095for = bDLocation.il.f2095for;
        this.il.f2099try = bDLocation.il.f2099try;
        this.ig = bDLocation.ig;
    }

    public BDLocation(String str) {
        this.h1 = 0;
        this.ii = null;
        this.ia = Double.MIN_VALUE;
        this.h3 = Double.MIN_VALUE;
        this.ih = false;
        this.ik = Double.MIN_VALUE;
        this.h4 = false;
        this.h5 = 0.0f;
        this.h6 = false;
        this.ic = 0.0f;
        this.ie = false;
        this.h2 = -1;
        this.h9 = GroundOverlayOptions.NO_DIMENSION;
        this.ib = null;
        this.h7 = null;
        this.id = false;
        this.h8 = false;
        this.h0 = null;
        this.ij = false;
        this.il = new C0489a(this);
        this.ig = null;
        if (str != null && !str.equals("")) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                JSONObject jSONObject2 = jSONObject.getJSONObject("result");
                int parseInt = Integer.parseInt(jSONObject2.getString("error"));
                setLocType(parseInt);
                setTime(jSONObject2.getString(LogColumns.TIME));
                if (parseInt == 61) {
                    jSONObject = jSONObject.getJSONObject("content");
                    jSONObject2 = jSONObject.getJSONObject("point");
                    setLatitude(Double.parseDouble(jSONObject2.getString("y")));
                    setLongitude(Double.parseDouble(jSONObject2.getString("x")));
                    setRadius(Float.parseFloat(jSONObject.getString(C0495a.f2122char)));
                    setSpeed(Float.parseFloat(jSONObject.getString("s")));
                    setDerect(Float.parseFloat(jSONObject.getString("d")));
                    setSatelliteNumber(Integer.parseInt(jSONObject.getString("n")));
                } else if (parseInt == TypeNetWorkLocation) {
                    jSONObject2 = jSONObject.getJSONObject("content");
                    jSONObject = jSONObject2.getJSONObject("point");
                    setLatitude(Double.parseDouble(jSONObject.getString("y")));
                    setLongitude(Double.parseDouble(jSONObject.getString("x")));
                    setRadius(Float.parseFloat(jSONObject2.getString(C0495a.f2122char)));
                    if (jSONObject2.has("addr")) {
                        String string = jSONObject2.getString("addr");
                        this.il.f2099try = string;
                        String[] split = string.split(SeparatorConstants.SEPARATOR_ADS_ID);
                        this.il.f2096if = split[0];
                        this.il.f2098new = split[1];
                        this.il.f2097int = split[2];
                        this.il.f2093byte = split[3];
                        this.il.f2094do = split[4];
                        this.il.f2095for = split[5];
                        string = ((this.il.f2096if.contains("北京") && this.il.f2098new.contains("北京")) || ((this.il.f2096if.contains("上海") && this.il.f2098new.contains("上海")) || ((this.il.f2096if.contains("天津") && this.il.f2098new.contains("天津")) || (this.il.f2096if.contains("重庆") && this.il.f2098new.contains("重庆"))))) ? this.il.f2096if : this.il.f2096if + this.il.f2098new;
                        this.il.f2099try = string + this.il.f2097int + this.il.f2093byte + this.il.f2094do;
                        this.id = true;
                    } else {
                        this.id = false;
                        setAddrStr(null);
                    }
                    if (jSONObject2.has("poi")) {
                        this.h8 = true;
                        this.h7 = jSONObject2.getJSONObject("poi").toString();
                    }
                    if (jSONObject2.has("floor")) {
                        this.ig = jSONObject2.getString("floor");
                        if (TextUtils.isEmpty(this.ig)) {
                            this.ig = null;
                        }
                    }
                } else if (parseInt == 66 || parseInt == 68) {
                    jSONObject = jSONObject.getJSONObject("content");
                    jSONObject2 = jSONObject.getJSONObject("point");
                    setLatitude(Double.parseDouble(jSONObject2.getString("y")));
                    setLongitude(Double.parseDouble(jSONObject2.getString("x")));
                    setRadius(Float.parseFloat(jSONObject.getString(C0495a.f2122char)));
                    m5798if(Boolean.valueOf(Boolean.parseBoolean(jSONObject.getString("isCellChanged"))));
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.h1 = 0;
                this.id = false;
            }
        }
    }

    private void m5798if(Boolean bool) {
        this.ij = bool.booleanValue();
    }

    public int describeContents() {
        return 0;
    }

    public String getAddrStr() {
        return this.il.f2099try;
    }

    public double getAltitude() {
        return this.ik;
    }

    public String getCity() {
        return this.il.f2098new;
    }

    public String getCityCode() {
        return this.il.f2095for;
    }

    public String getCoorType() {
        return this.ib;
    }

    public float getDerect() {
        return this.h9;
    }

    public String getDistrict() {
        return this.il.f2097int;
    }

    public String getFloor() {
        return this.ig;
    }

    public double getLatitude() {
        return this.ia;
    }

    public int getLocType() {
        return this.h1;
    }

    public double getLongitude() {
        return this.h3;
    }

    public String getPoi() {
        return this.h7;
    }

    public String getProvince() {
        return this.il.f2096if;
    }

    public float getRadius() {
        return this.ic;
    }

    public int getSatelliteNumber() {
        this.ie = true;
        return this.h2;
    }

    public float getSpeed() {
        return this.h5;
    }

    public String getStreet() {
        return this.il.f2093byte;
    }

    public String getStreetNumber() {
        return this.il.f2094do;
    }

    public String getTime() {
        return this.ii;
    }

    public boolean hasAddr() {
        return this.id;
    }

    public boolean hasAltitude() {
        return this.ih;
    }

    public boolean hasPoi() {
        return this.h8;
    }

    public boolean hasRadius() {
        return this.h6;
    }

    public boolean hasSateNumber() {
        return this.ie;
    }

    public boolean hasSpeed() {
        return this.h4;
    }

    public boolean isCellChangeFlag() {
        return this.ij;
    }

    public void setAddrStr(String str) {
        this.h0 = str;
        if (str == null) {
            this.id = false;
        } else {
            this.id = true;
        }
    }

    public void setAltitude(double d) {
        this.ik = d;
        this.ih = true;
    }

    public void setCoorType(String str) {
        this.ib = str;
    }

    public void setDerect(float f) {
        this.h9 = f;
    }

    public void setLatitude(double d) {
        this.ia = d;
    }

    public void setLocType(int i) {
        this.h1 = i;
    }

    public void setLongitude(double d) {
        this.h3 = d;
    }

    public void setPoi(String str) {
        this.h7 = str;
    }

    public void setRadius(float f) {
        this.ic = f;
        this.h6 = true;
    }

    public void setSatelliteNumber(int i) {
        this.h2 = i;
    }

    public void setSpeed(float f) {
        this.h5 = f;
        this.h4 = true;
    }

    public void setTime(String str) {
        this.ii = str;
    }

    public String toJsonString() {
        return null;
    }

    public BDLocation toNewLocation(String str) {
        return null;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.h1);
        parcel.writeString(this.ii);
        parcel.writeDouble(this.ia);
        parcel.writeDouble(this.h3);
        parcel.writeDouble(this.ik);
        parcel.writeFloat(this.h5);
        parcel.writeFloat(this.ic);
        parcel.writeInt(this.h2);
        parcel.writeFloat(this.h9);
        parcel.writeString(this.h7);
        parcel.writeString(this.ig);
        parcel.writeString(this.il.f2096if);
        parcel.writeString(this.il.f2098new);
        parcel.writeString(this.il.f2097int);
        parcel.writeString(this.il.f2093byte);
        parcel.writeString(this.il.f2094do);
        parcel.writeString(this.il.f2095for);
        parcel.writeString(this.il.f2099try);
        parcel.writeBooleanArray(new boolean[]{this.ih, this.h4, this.h6, this.ie, this.id, this.h8, this.ij});
    }
}
