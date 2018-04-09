package com.amap.api.location;

import com.amap.api.location.core.AMapLocException;
import java.util.List;

public class AMapLocalWeatherForecast {
    private String f16a;
    private List<AMapLocalDayWeatherForecast> f17b;
    private AMapLocException f18c;

    public AMapLocException getAMapException() {
        return this.f18c;
    }

    void m19a(AMapLocException aMapLocException) {
        this.f18c = aMapLocException;
    }

    public String getReportTime() {
        return this.f16a;
    }

    void m20a(String str) {
        this.f16a = str;
    }

    public List<AMapLocalDayWeatherForecast> getWeatherForecast() {
        return this.f17b;
    }

    void m21a(List<AMapLocalDayWeatherForecast> list) {
        this.f17b = list;
    }
}
