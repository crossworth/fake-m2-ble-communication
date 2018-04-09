package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.cloud.CloudItem;
import com.amap.api.services.cloud.CloudResult;
import com.amap.api.services.cloud.CloudSearch.Query;
import com.amap.api.services.cloud.CloudSearch.SearchBound;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.umeng.socialize.common.SocializeConstants;
import com.zhuoyi.system.util.constant.SeparatorConstants;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: CloudSearchKeywordsHandler */
public class C2068g extends C2047e<Query, CloudResult> {
    private int f5575h = 0;

    public /* synthetic */ Object mo3042a(String str) throws AMapException {
        return m6304e(str);
    }

    public C2068g(Context context, Query query) {
        super(context, query);
    }

    public String mo1759g() {
        String str = C0389h.m1587b() + "/datasearch";
        String shape = ((Query) this.a).getBound().getShape();
        if (shape.equals("Bound")) {
            return str + "/around?";
        }
        if (shape.equals("Polygon") || shape.equals("Rectangle")) {
            return str + "/polygon?";
        }
        return shape.equals(SearchBound.LOCAL_SHAPE) ? str + "/local?" : str;
    }

    public CloudResult m6304e(String str) throws AMapException {
        ArrayList arrayList = null;
        if (str == null || str.equals("")) {
            return CloudResult.createPagedResult((Query) this.a, this.f5575h, ((Query) this.a).getBound(), ((Query) this.a).getPageSize(), null);
        }
        try {
            arrayList = m6300b(new JSONObject(str));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return CloudResult.createPagedResult((Query) this.a, this.f5575h, ((Query) this.a).getBound(), ((Query) this.a).getPageSize(), arrayList);
    }

    private ArrayList<CloudItem> m6300b(JSONObject jSONObject) throws JSONException {
        ArrayList<CloudItem> arrayList = new ArrayList();
        if (!jSONObject.has("datas")) {
            return arrayList;
        }
        JSONArray optJSONArray = jSONObject.optJSONArray("datas");
        this.f5575h = jSONObject.getInt("count");
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            CloudItem a = m6220a(optJSONObject);
            m6221a(a, optJSONObject);
            arrayList.add(a);
        }
        return arrayList;
    }

    protected String mo3048e() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("output=json");
        if (((Query) this.a).getBound() != null) {
            double a;
            if (((Query) this.a).getBound().getShape().equals("Bound")) {
                a = C0390i.m1589a(((Query) this.a).getBound().getCenter().getLongitude());
                stringBuilder.append("&center=").append(a + SeparatorConstants.SEPARATOR_ADS_ID + C0390i.m1589a(((Query) this.a).getBound().getCenter().getLatitude()));
                stringBuilder.append("&radius=").append(((Query) this.a).getBound().getRange());
            } else if (((Query) this.a).getBound().getShape().equals("Rectangle")) {
                LatLonPoint lowerLeft = ((Query) this.a).getBound().getLowerLeft();
                LatLonPoint upperRight = ((Query) this.a).getBound().getUpperRight();
                double a2 = C0390i.m1589a(lowerLeft.getLatitude());
                a = C0390i.m1589a(lowerLeft.getLongitude());
                stringBuilder.append("&polygon=" + a + SeparatorConstants.SEPARATOR_ADS_ID + a2 + ";" + C0390i.m1589a(upperRight.getLongitude()) + SeparatorConstants.SEPARATOR_ADS_ID + C0390i.m1589a(upperRight.getLatitude()));
            } else if (((Query) this.a).getBound().getShape().equals("Polygon")) {
                List polyGonList = ((Query) this.a).getBound().getPolyGonList();
                if (polyGonList != null && polyGonList.size() > 0) {
                    stringBuilder.append("&polygon=" + C0390i.m1593a(polyGonList));
                }
            } else if (((Query) this.a).getBound().getShape().equals(SearchBound.LOCAL_SHAPE)) {
                stringBuilder.append("&city=").append(m5789b(((Query) this.a).getBound().getCity()));
            }
        }
        stringBuilder.append("&tableid=" + ((Query) this.a).getTableID());
        if (!C0390i.m1595a(m6302k())) {
            m6302k();
            stringBuilder.append("&filter=").append(m5789b(m6302k()));
        }
        if (!C0390i.m1595a(m6301h())) {
            stringBuilder.append("&sortrule=").append(m6301h());
        }
        String str = "";
        String b = m5789b(((Query) this.a).getQueryString());
        if (((Query) this.a).getQueryString() == null || ((Query) this.a).getQueryString().equals("")) {
            stringBuilder.append("&keywords=");
        } else {
            stringBuilder.append("&keywords=" + b);
        }
        stringBuilder.append("&limit=" + ((Query) this.a).getPageSize());
        stringBuilder.append("&page=" + (((Query) this.a).getPageNum() + 1));
        stringBuilder.append("&key=" + as.m1215f(this.d));
        return stringBuilder.toString();
    }

    private String m6301h() {
        if (((Query) this.a).getSortingrules() != null) {
            return ((Query) this.a).getSortingrules().toString();
        }
        return "";
    }

    private String m6302k() {
        StringBuffer stringBuffer = new StringBuffer();
        String filterString = ((Query) this.a).getFilterString();
        String filterNumString = ((Query) this.a).getFilterNumString();
        stringBuffer.append(filterString);
        if (!(C0390i.m1595a(filterString) || C0390i.m1595a(filterNumString))) {
            stringBuffer.append(SocializeConstants.OP_DIVIDER_PLUS);
        }
        stringBuffer.append(filterNumString);
        return stringBuffer.toString();
    }
}
