package com.amap.api.services.weather;

public class LocalWeatherForecastResult {
    private WeatherSearchQuery f1668a;
    private LocalWeatherForecast f1669b;

    public static LocalWeatherForecastResult createPagedResult(WeatherSearchQuery weatherSearchQuery, LocalWeatherForecast localWeatherForecast) {
        return new LocalWeatherForecastResult(weatherSearchQuery, localWeatherForecast);
    }

    private LocalWeatherForecastResult(WeatherSearchQuery weatherSearchQuery, LocalWeatherForecast localWeatherForecast) {
        this.f1668a = weatherSearchQuery;
        this.f1669b = localWeatherForecast;
    }

    public WeatherSearchQuery getWeatherForecastQuery() {
        return this.f1668a;
    }

    public LocalWeatherForecast getForecastResult() {
        return this.f1669b;
    }
}
