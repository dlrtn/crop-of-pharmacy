package lalalabs.pharmacy_crop.business.weather.application;

import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCodeResolver;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.GridNumberFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.MediumTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.ShortTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.TodayTermForecastFetcher;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherForecastService {

    private final ForecastAreaCodeResolver forecastAreaCodeResolver;
    private final GridNumberFetcher gridNumberFetcher;
    private final TodayTermForecastFetcher todayTermForecastFetcher;
    private final ShortTermForecastFetcher shortTermForecastFetcher;
    private final MediumTermForecastFetcher mediumTermForecastFetcher;

    public Object getWeatherForecast(Coordinate coordinate) {
        ForecastAreaCode forecastAreaCode = forecastAreaCodeResolver.resolve(coordinate);

        mediumTermForecastFetcher.fetchWeatherInformation(forecastAreaCode);
        shortTermForecastFetcher.fetchWeatherInformation(forecastAreaCode);

        ForecastPoint gridNumber = gridNumberFetcher.fetchWeatherInformation(coordinate);
        return todayTermForecastFetcher.fetchWeatherInformation(gridNumber);
    }
}
