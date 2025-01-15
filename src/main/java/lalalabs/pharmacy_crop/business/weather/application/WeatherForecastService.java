package lalalabs.pharmacy_crop.business.weather.application;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.api.dto.TodayWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCodeResolver;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.GridNumberFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.TodayTermForecastFetcher;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherForecastService {

    private final ForecastAreaCodeResolver forecastAreaCodeResolver;
    private final GridNumberFetcher gridNumberFetcher;
    private final TodayTermForecastFetcher todayTermForecastFetcher;
    private final ForecastConverter forecastConverter;
    private final WeeklyForecastManager weeklyForecastManager;

    public TodayWeatherForecastDto getTodayWeatherForecast(Coordinate coordinate) {
        ForecastPoint gridNumber = gridNumberFetcher.fetchGridNumber(coordinate);

        List<ShortTermForecastItem> shortTermForecastItemList = todayTermForecastFetcher.fetchWeatherInformation(
                gridNumber);

        return forecastConverter.convert(shortTermForecastItemList);
    }

    public List<WeeklyWeatherForecastDto> getWeeklyWeatherForecast(Coordinate coordinate) {
        ForecastAreaCode forecastAreaCode = forecastAreaCodeResolver.resolve(coordinate);

        return weeklyForecastManager.getWeeklyWeatherForecast(forecastAreaCode);
    }
}
