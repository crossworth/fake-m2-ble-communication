package com.amap.api.services.weather;

public class LocalWeatherLiveResult {
    private WeatherSearchQuery f1679a;
    private LocalWeatherLive f1680b;

    public static LocalWeatherLiveResult createPagedResult(WeatherSearchQuery weatherSearchQuery, LocalWeatherLive localWeatherLive) {
        return new LocalWeatherLiveResult(weatherSearchQuery, localWeatherLive);
    }

    private LocalWeatherLiveResult(WeatherSearchQuery weatherSearchQuery, LocalWeatherLive localWeatherLive) {
        this.f1679a = weatherSearchQuery;
        this.f1680b = localWeatherLive;
    }

    public WeatherSearchQuery getWeatherLiveQuery() {
        return this.f1679a;
    }

    public LocalWeatherLive getLiveResult() {
        return this.f1680b;
    }
}
