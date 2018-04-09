package com.baidu.mapapi.cloud;

import com.droi.btlib.connection.MapConstants;
import com.umeng.socialize.common.SocializeConstants;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CloudPoiInfo {
    public String address;
    public String city;
    public String direction;
    public int distance;
    public String district;
    public Map<String, Object> extras;
    public int geotableId;
    public double latitude;
    public double longitude;
    public String province;
    public String tags;
    public String title;
    public int uid;
    public int weight;

    void m1043a(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.uid = jSONObject.optInt("uid");
            jSONObject.remove("uid");
            this.geotableId = jSONObject.optInt("geotable_id");
            jSONObject.remove("geotable_id");
            this.title = jSONObject.optString("title");
            jSONObject.remove("title");
            this.address = jSONObject.optString(MapConstants.ADDRESS);
            jSONObject.remove(MapConstants.ADDRESS);
            this.province = jSONObject.optString("province");
            jSONObject.remove("province");
            this.city = jSONObject.optString("city");
            jSONObject.remove("city");
            this.district = jSONObject.optString("district");
            jSONObject.remove("district");
            JSONArray optJSONArray = jSONObject.optJSONArray(SocializeConstants.KEY_LOCATION);
            if (optJSONArray != null) {
                this.longitude = optJSONArray.optDouble(0);
                this.latitude = optJSONArray.optDouble(1);
            }
            jSONObject.remove(SocializeConstants.KEY_LOCATION);
            this.tags = jSONObject.optString("tags");
            jSONObject.remove("tags");
            this.distance = jSONObject.optInt("distance");
            jSONObject.remove("distance");
            this.weight = jSONObject.optInt("weight");
            jSONObject.remove("weight");
            this.extras = new HashMap();
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                this.extras.put(str, jSONObject.opt(str));
            }
        }
    }

    void m1044b(JSONObject jSONObject) throws JSONException {
        if (jSONObject != null) {
            this.title = jSONObject.optString("name");
            this.address = jSONObject.optString(MapConstants.ADDRESS);
            this.tags = jSONObject.optString("tag");
            JSONObject optJSONObject = jSONObject.optJSONObject(SocializeConstants.KEY_LOCATION);
            if (optJSONObject != null) {
                this.longitude = optJSONObject.optDouble("lng");
                this.latitude = optJSONObject.optDouble("lat");
            }
            this.direction = jSONObject.optString("direction");
            this.distance = jSONObject.optInt("distance");
        }
    }
}
