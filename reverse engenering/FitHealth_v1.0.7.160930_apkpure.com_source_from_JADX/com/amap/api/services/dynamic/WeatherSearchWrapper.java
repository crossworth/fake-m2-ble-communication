package com.amap.api.services.dynamic;

import android.content.Context;
import com.amap.api.services.interfaces.IWeatherSearch;
import com.amap.api.services.proguard.aq;
import com.amap.api.services.weather.WeatherSearch.OnWeatherSearchListener;
import com.amap.api.services.weather.WeatherSearchQuery;

public class WeatherSearchWrapper implements IWeatherSearch {
    private IWeatherSearch f4273a = null;

    public WeatherSearchWrapper(Context context) {
        this.f4273a = new aq(context);
    }

    public WeatherSearchQuery getQuery() {
        if (this.f4273a != null) {
            return this.f4273a.getQuery();
        }
        return null;
    }

    public void setQuery(WeatherSearchQuery weatherSearchQuery) {
        if (this.f4273a != null) {
            this.f4273a.setQuery(weatherSearchQuery);
        }
    }

    public void searchWeatherAsyn() {
        if (this.f4273a != null) {
            this.f4273a.searchWeatherAsyn();
        }
    }

    public void setOnWeatherSearchListener(OnWeatherSearchListener onWeatherSearchListener) {
        if (this.f4273a != null) {
            this.f4273a.setOnWeatherSearchListener(onWeatherSearchListener);
        }
    }
}
