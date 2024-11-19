package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;

public interface WeatherInformationFetcher {
    Object fetchWeatherInformation(ForecastAreaCode forecastAreaCode);
}
