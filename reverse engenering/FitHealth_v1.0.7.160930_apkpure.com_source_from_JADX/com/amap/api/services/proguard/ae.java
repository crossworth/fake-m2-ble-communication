package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherLive;
import com.amap.api.services.weather.WeatherSearchQuery;

/* compiled from: WeatherLiveHandler */
public class ae extends af<WeatherSearchQuery, LocalWeatherLive> {
    private LocalWeatherLive f5574h = new LocalWeatherLive();

    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public /* bridge */ /* synthetic */ String mo1759g() {
        return super.mo1759g();
    }

    public ae(Context context, WeatherSearchQuery weatherSearchQuery) {
        super(context, weatherSearchQuery);
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json");
        String city = ((WeatherSearchQuery) this.a).getCity();
        if (!C0391n.m1630i(city)) {
            stringBuffer.append("&city=").append(m5789b(city));
        }
        stringBuffer.append("&extensions=base");
        stringBuffer.append("&key=" + as.m1215f(this.d));
        return stringBuffer.toString();
    }

    protected LocalWeatherLive mo3703d(String str) throws AMapException {
        this.f5574h = C0391n.m1620d(str);
        return this.f5574h;
    }
}
