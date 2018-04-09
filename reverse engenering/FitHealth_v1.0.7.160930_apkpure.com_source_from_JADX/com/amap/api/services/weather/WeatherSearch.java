package com.amap.api.services.weather;

import android.content.Context;
import com.amap.api.services.interfaces.IWeatherSearch;
import com.amap.api.services.proguard.C0389h;
import com.amap.api.services.proguard.aq;
import com.amap.api.services.proguard.ar;
import com.amap.api.services.proguard.ch;

public class WeatherSearch {
    private IWeatherSearch f1681a = null;

    public interface OnWeatherSearchListener {
        void onWeatherForecastSearched(LocalWeatherForecastResult localWeatherForecastResult, int i);

        void onWeatherLiveSearched(LocalWeatherLiveResult localWeatherLiveResult, int i);
    }

    public WeatherSearch(Context context) {
        try {
            Context context2 = context;
            this.f1681a = (IWeatherSearch) ch.m1482a(context2, C0389h.m1584a(true), "com.amap.api.services.dynamic.WeatherSearchWrapper", aq.class, new Class[]{Context.class}, new Object[]{context});
        } catch (ar e) {
            e.printStackTrace();
        }
        if (this.f1681a == null) {
            this.f1681a = new aq(context);
        }
    }

    public WeatherSearchQuery getQuery() {
        if (this.f1681a != null) {
            return this.f1681a.getQuery();
        }
        return null;
    }

    public void setQuery(WeatherSearchQuery weatherSearchQuery) {
        if (this.f1681a != null) {
            this.f1681a.setQuery(weatherSearchQuery);
        }
    }

    public void searchWeatherAsyn() {
        if (this.f1681a != null) {
            this.f1681a.searchWeatherAsyn();
        }
    }

    public void setOnWeatherSearchListener(OnWeatherSearchListener onWeatherSearchListener) {
        if (this.f1681a != null) {
            this.f1681a.setOnWeatherSearchListener(onWeatherSearchListener);
        }
    }
}
