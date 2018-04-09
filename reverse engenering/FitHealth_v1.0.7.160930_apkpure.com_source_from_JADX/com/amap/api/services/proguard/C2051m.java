package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import java.util.ArrayList;
import org.json.JSONObject;

/* compiled from: InputtipsHandler */
public class C2051m extends C1972b<InputtipsQuery, ArrayList<Tip>> {
    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public C2051m(Context context, InputtipsQuery inputtipsQuery) {
        super(context, inputtipsQuery);
    }

    protected ArrayList<Tip> mo3703d(String str) throws AMapException {
        ArrayList<Tip> arrayList = null;
        try {
            arrayList = C0391n.m1638m(new JSONObject(str));
        } catch (Throwable e) {
            C0390i.m1594a(e, "InputtipsHandler", "paseJSON");
        }
        return arrayList;
    }

    public String mo1759g() {
        return C0389h.m1585a() + "/assistant/inputtips?";
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json").append("&keywords=").append(m5789b(((InputtipsQuery) this.a).getKeyword()));
        String city = ((InputtipsQuery) this.a).getCity();
        if (!C0391n.m1630i(city)) {
            stringBuffer.append("&city=").append(m5789b(city));
        }
        city = ((InputtipsQuery) this.a).getType();
        if (!C0391n.m1630i(city)) {
            stringBuffer.append("&type=").append(m5789b(city));
        }
        if (((InputtipsQuery) this.a).getCityLimit()) {
            stringBuffer.append("&citylimit=true");
        } else {
            stringBuffer.append("&citylimit=false");
        }
        stringBuffer.append("&key=").append(as.m1215f(this.d));
        stringBuffer.append("&language=").append(C0389h.m1588c());
        return stringBuffer.toString();
    }
}
