package com.amap.api.services.proguard;

import android.content.Context;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.weather.LocalWeatherForecast;
import com.amap.api.services.weather.WeatherSearchQuery;

/* compiled from: WeatherForecastHandler */
public class ad extends af<WeatherSearchQuery, LocalWeatherForecast> {
    private LocalWeatherForecast f5573h = new LocalWeatherForecast();

    protected /* synthetic */ Object mo3042a(String str) throws AMapException {
        return mo3703d(str);
    }

    public /* bridge */ /* synthetic */ String mo1759g() {
        return super.mo1759g();
    }

    public ad(Context context, WeatherSearchQuery weatherSearchQuery) {
        super(context, weatherSearchQuery);
    }

    protected String mo3048e() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("output=json");
        String city = ((WeatherSearchQuery) this.a).getCity();
        if (!C0391n.m1630i(city)) {
            stringBuffer.append("&city=").append(m5789b(city));
        }
        stringBuffer.append("&extensions=all");
        stringBuffer.append("&key=" + as.m1215f(this.d));
        return stringBuffer.toString();
    }

    protected LocalWeatherForecast mo3703d(String str) throws AMapException {
        this.f5573h = C0391n.m1621e(str);
        return this.f5573h;
    }
}
