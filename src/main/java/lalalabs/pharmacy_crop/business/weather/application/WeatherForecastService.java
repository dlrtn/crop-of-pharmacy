package lalalabs.pharmacy_crop.business.weather.application;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lalalabs.pharmacy_crop.business.weather.api.dto.TodayWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCodeResolver;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.GridNumberFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.MediumTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.ShortTermForecastFetcher;
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
    private final ShortTermForecastFetcher shortTermForecastFetcher;
    private final MediumTermForecastFetcher mediumTermForecastFetcher;
    private final ForecastConverter forecastConverter;

    public TodayWeatherForecastDto getTodayWeatherForecast(Coordinate coordinate) {
        ForecastPoint gridNumber = gridNumberFetcher.fetchWeatherInformation(coordinate);

        List<ShortTermForecastItem> shortTermForecastItemList = todayTermForecastFetcher.fetchWeatherInformation(
                gridNumber);

        return forecastConverter.convert(shortTermForecastItemList);
    }

    public List<WeeklyWeatherForecastDto> getWeeklyWeatherForecast(Coordinate coordinate) {
        ForecastAreaCode forecastAreaCode = forecastAreaCodeResolver.resolve(coordinate);

        CompletableFuture<Map<LocalDate, ShortTermOverlandForecastItem>> future1 = CompletableFuture.supplyAsync(() -> {
            return shortTermForecastFetcher.fetchWeatherInformation(forecastAreaCode);
        });

        CompletableFuture<Map<LocalDate, MediumTermForecastItem>> future2 = CompletableFuture.supplyAsync(() -> {
            return mediumTermForecastFetcher.fetchWeatherInformation(forecastAreaCode);
        });

        CompletableFuture.allOf(future1, future2).join();

        Map<LocalDate, ShortTermOverlandForecastItem> list = future1.join();
        Map<LocalDate, MediumTermForecastItem> list2 = future2.join();

        return forecastConverter.convertWeeklyForecast(list, list2);
    }
}
