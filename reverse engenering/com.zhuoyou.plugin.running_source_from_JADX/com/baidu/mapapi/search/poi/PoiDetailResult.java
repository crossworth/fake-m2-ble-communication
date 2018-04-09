package com.baidu.mapapi.search.poi;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.core.SearchResult.ERRORNO;
import com.droi.btlib.connection.MapConstants;
import com.umeng.facebook.share.internal.ShareConstants;
import com.umeng.socialize.common.SocializeConstants;
import org.json.JSONException;
import org.json.JSONObject;

public class PoiDetailResult extends SearchResult implements Parcelable {
    public static final Creator<PoiDetailResult> CREATOR = new C0549a();
    int f1602a;
    String f1603b;
    String f1604c;
    LatLng f1605d;
    String f1606e;
    String f1607f;
    String f1608g;
    String f1609h;
    String f1610i;
    String f1611j;
    double f1612k;
    double f1613l;
    double f1614m;
    double f1615n;
    double f1616o;
    double f1617p;
    double f1618q;
    double f1619r;
    int f1620s;
    int f1621t;
    int f1622u;
    int f1623v;
    int f1624w;
    String f1625x;

    PoiDetailResult() {
    }

    protected PoiDetailResult(Parcel parcel) {
        this.f1602a = parcel.readInt();
        this.f1603b = parcel.readString();
        this.f1604c = parcel.readString();
        this.f1605d = (LatLng) parcel.readValue(LatLng.class.getClassLoader());
        this.f1606e = parcel.readString();
        this.f1607f = parcel.readString();
        this.f1608g = parcel.readString();
        this.f1609h = parcel.readString();
        this.f1610i = parcel.readString();
        this.f1611j = parcel.readString();
        this.f1612k = parcel.readDouble();
        this.f1613l = parcel.readDouble();
        this.f1614m = parcel.readDouble();
        this.f1615n = parcel.readDouble();
        this.f1616o = parcel.readDouble();
        this.f1617p = parcel.readDouble();
        this.f1618q = parcel.readDouble();
        this.f1619r = parcel.readDouble();
        this.f1620s = parcel.readInt();
        this.f1621t = parcel.readInt();
        this.f1622u = parcel.readInt();
        this.f1623v = parcel.readInt();
        this.f1624w = parcel.readInt();
        this.f1625x = parcel.readString();
    }

    public PoiDetailResult(ERRORNO errorno) {
        super(errorno);
    }

    boolean m1529a(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f1602a = jSONObject.optInt("status");
            if (this.f1602a != 0) {
                return false;
            }
            this.f1603b = jSONObject.optString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
            jSONObject = jSONObject.optJSONObject("result");
            if (jSONObject == null) {
                return false;
            }
            this.f1604c = jSONObject.optString("name");
            JSONObject optJSONObject = jSONObject.optJSONObject(SocializeConstants.KEY_LOCATION);
            this.f1605d = new LatLng(optJSONObject.optDouble("lat"), optJSONObject.optDouble("lng"));
            this.f1606e = jSONObject.optString(MapConstants.ADDRESS);
            this.f1607f = jSONObject.optString("telephone");
            this.f1608g = jSONObject.optString("uid");
            jSONObject = jSONObject.optJSONObject("detail_info");
            if (jSONObject != null) {
                this.f1609h = jSONObject.optString("tag");
                this.f1610i = jSONObject.optString("detail_url");
                this.f1611j = jSONObject.optString("type");
                this.f1612k = jSONObject.optDouble("price", 0.0d);
                this.f1613l = jSONObject.optDouble("overall_rating", 0.0d);
                this.f1614m = jSONObject.optDouble("taste_rating", 0.0d);
                this.f1615n = jSONObject.optDouble("service_rating", 0.0d);
                this.f1616o = jSONObject.optDouble("environment_rating", 0.0d);
                this.f1617p = jSONObject.optDouble("facility_rating", 0.0d);
                this.f1618q = jSONObject.optDouble("hygiene_rating", 0.0d);
                this.f1619r = jSONObject.optDouble("technology_rating", 0.0d);
                this.f1620s = jSONObject.optInt("image_num");
                this.f1621t = jSONObject.optInt("groupon_num");
                this.f1622u = jSONObject.optInt("comment_num");
                this.f1623v = jSONObject.optInt("favorite_num");
                this.f1624w = jSONObject.optInt("checkin_num");
                this.f1625x = jSONObject.optString("shop_hours");
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int describeContents() {
        return 0;
    }

    public String getAddress() {
        return this.f1606e;
    }

    public int getCheckinNum() {
        return this.f1624w;
    }

    public int getCommentNum() {
        return this.f1622u;
    }

    public String getDetailUrl() {
        return this.f1610i;
    }

    public double getEnvironmentRating() {
        return this.f1616o;
    }

    public double getFacilityRating() {
        return this.f1617p;
    }

    public int getFavoriteNum() {
        return this.f1623v;
    }

    public int getGrouponNum() {
        return this.f1621t;
    }

    public double getHygieneRating() {
        return this.f1618q;
    }

    public int getImageNum() {
        return this.f1620s;
    }

    public LatLng getLocation() {
        return this.f1605d;
    }

    public String getName() {
        return this.f1604c;
    }

    public double getOverallRating() {
        return this.f1613l;
    }

    public double getPrice() {
        return this.f1612k;
    }

    public double getServiceRating() {
        return this.f1615n;
    }

    public String getShopHours() {
        return this.f1625x;
    }

    public String getTag() {
        return this.f1609h;
    }

    public double getTasteRating() {
        return this.f1614m;
    }

    public double getTechnologyRating() {
        return this.f1619r;
    }

    public String getTelephone() {
        return this.f1607f;
    }

    public String getType() {
        return this.f1611j;
    }

    public String getUid() {
        return this.f1608g;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f1602a);
        parcel.writeString(this.f1603b);
        parcel.writeString(this.f1604c);
        parcel.writeValue(this.f1605d);
        parcel.writeString(this.f1606e);
        parcel.writeString(this.f1607f);
        parcel.writeString(this.f1608g);
        parcel.writeString(this.f1609h);
        parcel.writeString(this.f1610i);
        parcel.writeString(this.f1611j);
        parcel.writeDouble(this.f1612k);
        parcel.writeDouble(this.f1613l);
        parcel.writeDouble(this.f1614m);
        parcel.writeDouble(this.f1615n);
        parcel.writeDouble(this.f1616o);
        parcel.writeDouble(this.f1617p);
        parcel.writeDouble(this.f1618q);
        parcel.writeDouble(this.f1619r);
        parcel.writeInt(this.f1620s);
        parcel.writeInt(this.f1621t);
        parcel.writeInt(this.f1622u);
        parcel.writeInt(this.f1623v);
        parcel.writeInt(this.f1624w);
        parcel.writeString(this.f1625x);
    }
}
