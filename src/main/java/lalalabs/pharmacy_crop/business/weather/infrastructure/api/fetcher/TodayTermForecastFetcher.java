package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.application.ForecastConverter;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.BaseDateTimeUtils;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.client.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodayTermForecastFetcher {

    private final WeatherApiClient apiClient;
    private final BaseDateTimeUtils baseDateTimeUtils;
    private final ForecastConverter forecastConverter;

    public List<ShortTermForecastItem> fetchWeatherInformation(ForecastPoint forecastPoint) {
        WeatherApiDateTime weatherApiDateTime = baseDateTimeUtils.calculateBaseDateTime();

        ShortTermWeatherApiResponse response = apiClient.getShortTermWeatherForecast(forecastPoint, weatherApiDateTime);

        return filterForecastItems(response, weatherApiDateTime);
    }

    private List<ShortTermForecastItem> filterForecastItems(ShortTermWeatherApiResponse response,
                                                            WeatherApiDateTime weatherApiDateTime) {
        return response.getShortTermForecastItems().stream()
                .filter(item -> item.fcstDate().equals(weatherApiDateTime.baseDate()))
                .toList();
    }
}
