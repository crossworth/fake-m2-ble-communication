package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineQuery.SearchType;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusStationQuery;
import com.amap.api.services.busline.BusStationResult;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.SuggestionCity;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: BusSearchServerHandler */
public class C2046d<T> extends C1972b<T, Object> {
    private int f5561h = 0;
    private List<String> f5562i = new ArrayList();
    private List<SuggestionCity> f5563j = new ArrayList();

    public C2046d(Context context, T t) {
        super(context, t);
    }

    public String mo1759g() {
        String str = this.a instanceof BusLineQuery ? ((BusLineQuery) this.a).getCategory() == SearchType.BY_LINE_ID ? "lineid" : ((BusLineQuery) this.a).getCategory() == SearchType.BY_LINE_NAME ? "linename" : "" : "stopname";
        return C0389h.m1585a() + "/bus/" + str + "?";
    }

    protected Object mo3042a(String str) throws AMapException {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject optJSONObject = jSONObject.optJSONObject("suggestion");
            if (optJSONObject != null) {
                this.f5563j = C0391n.m1601a(optJSONObject);
                this.f5562i = C0391n.m1611b(optJSONObject);
            }
            this.f5561h = jSONObject.optInt("count");
            if (this.a instanceof BusLineQuery) {
                return BusLineResult.createPagedResult((BusLineQuery) this.a, this.f5561h, this.f5563j, this.f5562i, C0391n.m1629i(jSONObject));
            }
            return BusStationResult.createPagedResult((BusStationQuery) this.a, this.f5561h, this.f5563j, this.f5562i, C0391n.m1622e(jSONObject));
        } catch (Throwable e) {
            C0390i.m1594a(e, "BusSearchServerHandler", "paseJSON");
            return null;
        }
    }

    protected String mo3048e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("output=json");
        String city;
        if (this.a instanceof BusLineQuery) {
            BusLineQuery busLineQuery = (BusLineQuery) this.a;
            stringBuilder.append("&extensions=all");
            if (busLineQuery.getCategory() == SearchType.BY_LINE_ID) {
                stringBuilder.append("&id=").append(m5789b(((BusLineQuery) this.a).getQueryString()));
            } else {
                city = busLineQuery.getCity();
                if (!C0391n.m1630i(city)) {
                    stringBuilder.append("&city=").append(m5789b(city));
                }
                stringBuilder.append("&keywords=" + m5789b(busLineQuery.getQueryString()));
                stringBuilder.append("&offset=" + busLineQuery.getPageSize());
                stringBuilder.append("&page=" + (busLineQuery.getPageNumber() + 1));
            }
        } else {
            BusStationQuery busStationQuery = (BusStationQuery) this.a;
            city = busStationQuery.getCity();
            if (!C0391n.m1630i(city)) {
                stringBuilder.append("&city=").append(m5789b(city));
            }
            stringBuilder.append("&keywords=" + m5789b(busStationQuery.getQueryString()));
            stringBuilder.append("&offset=" + busStationQuery.getPageSize());
            stringBuilder.append("&page=" + (busStationQuery.getPageNumber() + 1));
        }
        stringBuilder.append("&key=" + as.m1215f(this.d));
        return stringBuilder.toString();
    }
}
