package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.poisearch.PoiResult;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import com.zhuoyou.plugin.database.DataBaseContants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

/* compiled from: PoiSearchKeywordsHandler */
public class C2070w extends C2055u<C0410z, PoiResult> {
    private int f5576h = 0;
    private List<String> f5577i = new ArrayList();
    private List<SuggestionCity> f5578j = new ArrayList();

    public /* synthetic */ Object mo3042a(String str) throws AMapException {
        return m6315e(str);
    }

    public C2070w(Context context, C0410z c0410z) {
        super(context, c0410z);
    }

    public String mo1759g() {
        String str = C0389h.m1585a() + "/place";
        if (((C0410z) this.a).f1579b == null) {
            return str + "/text?";
        }
        if (((C0410z) this.a).f1579b.getShape().equals("Bound")) {
            return str + "/around?";
        }
        return (((C0410z) this.a).f1579b.getShape().equals("Rectangle") || ((C0410z) this.a).f1579b.getShape().equals("Polygon")) ? str + "/polygon?" : str;
    }

    public PoiResult m6315e(String str) throws AMapException {
        ArrayList arrayList = new ArrayList();
        if (str == null) {
            return PoiResult.createPagedResult(((C0410z) this.a).f1578a, ((C0410z) this.a).f1579b, this.f5577i, this.f5578j, ((C0410z) this.a).f1578a.getPageSize(), this.f5576h, arrayList);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            this.f5576h = jSONObject.optInt("count");
            arrayList = C0391n.m1615c(jSONObject);
            if (!jSONObject.has("suggestion")) {
                return PoiResult.createPagedResult(((C0410z) this.a).f1578a, ((C0410z) this.a).f1579b, this.f5577i, this.f5578j, ((C0410z) this.a).f1578a.getPageSize(), this.f5576h, arrayList);
            }
            jSONObject = jSONObject.optJSONObject("suggestion");
            if (jSONObject == null) {
                return PoiResult.createPagedResult(((C0410z) this.a).f1578a, ((C0410z) this.a).f1579b, this.f5577i, this.f5578j, ((C0410z) this.a).f1578a.getPageSize(), this.f5576h, arrayList);
            }
            this.f5578j = C0391n.m1601a(jSONObject);
            this.f5577i = C0391n.m1611b(jSONObject);
            return PoiResult.createPagedResult(((C0410z) this.a).f1578a, ((C0410z) this.a).f1579b, this.f5577i, this.f5578j, ((C0410z) this.a).f1578a.getPageSize(), this.f5576h, arrayList);
        } catch (Throwable e) {
            C0390i.m1594a(e, "PoiSearchKeywordHandler", "paseJSONJSONException");
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "PoiSearchKeywordHandler", "paseJSONException");
        }
    }

    protected String mo3048e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("output=json");
        if (((C0410z) this.a).f1579b != null) {
            double a;
            if (((C0410z) this.a).f1579b.getShape().equals("Bound")) {
                a = C0390i.m1589a(((C0410z) this.a).f1579b.getCenter().getLongitude());
                stringBuilder.append("&location=").append(a + SeparatorConstants.SEPARATOR_ADS_ID + C0390i.m1589a(((C0410z) this.a).f1579b.getCenter().getLatitude()));
                stringBuilder.append("&radius=").append(((C0410z) this.a).f1579b.getRange());
                stringBuilder.append("&sortrule=").append(m6313h());
            } else if (((C0410z) this.a).f1579b.getShape().equals("Rectangle")) {
                LatLonPoint lowerLeft = ((C0410z) this.a).f1579b.getLowerLeft();
                LatLonPoint upperRight = ((C0410z) this.a).f1579b.getUpperRight();
                double a2 = C0390i.m1589a(lowerLeft.getLatitude());
                a = C0390i.m1589a(lowerLeft.getLongitude());
                stringBuilder.append("&polygon=" + a + SeparatorConstants.SEPARATOR_ADS_ID + a2 + ";" + C0390i.m1589a(upperRight.getLongitude()) + SeparatorConstants.SEPARATOR_ADS_ID + C0390i.m1589a(upperRight.getLatitude()));
            } else if (((C0410z) this.a).f1579b.getShape().equals("Polygon")) {
                List polyGonList = ((C0410z) this.a).f1579b.getPolyGonList();
                if (polyGonList != null && polyGonList.size() > 0) {
                    stringBuilder.append("&polygon=" + C0390i.m1593a(polyGonList));
                }
            }
        }
        String city = ((C0410z) this.a).f1578a.getCity();
        if (!mo3703d(city)) {
            stringBuilder.append("&city=").append(m5789b(city));
        }
        stringBuilder.append("&keywords=" + m5789b(((C0410z) this.a).f1578a.getQueryString()));
        stringBuilder.append("&language=").append(C0389h.m1588c());
        stringBuilder.append("&offset=" + ((C0410z) this.a).f1578a.getPageSize());
        stringBuilder.append("&page=" + (((C0410z) this.a).f1578a.getPageNum() + 1));
        stringBuilder.append("&types=" + m5789b(((C0410z) this.a).f1578a.getCategory()));
        stringBuilder.append("&extensions=all");
        stringBuilder.append("&key=" + as.m1215f(this.d));
        if (((C0410z) this.a).f1578a.getCityLimit()) {
            stringBuilder.append("&citylimit=true");
        } else {
            stringBuilder.append("&citylimit=false");
        }
        if (((C0410z) this.a).f1578a.isRequireSubPois()) {
            stringBuilder.append("&children=1");
        } else {
            stringBuilder.append("&children=0");
        }
        return stringBuilder.toString();
    }

    private String m6313h() {
        if (((C0410z) this.a).f1579b.isDistanceSort()) {
            return "distance";
        }
        return DataBaseContants.CONF_WEIGHT;
    }
}
