package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearchQuery;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: DistrictServerHandler */
public class C2048j extends C1972b<DistrictSearchQuery, DistrictResult> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2048j(Context context, DistrictSearchQuery districtSearchQuery) {
        super(context, districtSearchQuery);
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json");
        stringBuffer.append("&page=").append(((DistrictSearchQuery) this.a).getPageNum() + 1);
        stringBuffer.append("&offset=").append(((DistrictSearchQuery) this.a).getPageSize());
        stringBuffer.append("&showChild=").append(((DistrictSearchQuery) this.a).isShowChild());
        if (((DistrictSearchQuery) this.a).isShowBoundary()) {
            stringBuffer.append("&extensions=all");
        } else {
            stringBuffer.append("&extensions=base");
        }
        if (((DistrictSearchQuery) this.a).checkKeyWords()) {
            stringBuffer.append("&keywords=").append(m5789b(((DistrictSearchQuery) this.a).getKeywords()));
        }
        if (((DistrictSearchQuery) this.a).checkLevels()) {
            stringBuffer.append("&level=").append(((DistrictSearchQuery) this.a).getKeywordsLevel());
        }
        stringBuffer.append("&key=" + as.m1215f(this.d));
        return stringBuffer.toString();
    }

    protected DistrictResult mo3703d(String str) throws AMapException {
        ArrayList arrayList = new ArrayList();
        DistrictResult districtResult = new DistrictResult((DistrictSearchQuery) this.a, arrayList);
        try {
            JSONObject jSONObject = new JSONObject(str);
            districtResult.setPageCount(jSONObject.optInt("count"));
            JSONArray optJSONArray = jSONObject.optJSONArray("districts");
            if (optJSONArray == null) {
                return districtResult;
            }
            C0391n.m1607a(optJSONArray, arrayList, null);
            return districtResult;
        } catch (Throwable e) {
            C0390i.m1594a(e, "DistrictServerHandler", "paseJSONJSONException");
        } catch (Throwable e2) {
            C0390i.m1594a(e2, "DistrictServerHandler", "paseJSONException");
        }
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/config/district?";
    }
}
