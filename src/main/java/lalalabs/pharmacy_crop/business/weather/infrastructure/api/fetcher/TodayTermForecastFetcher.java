package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.BaseDateTimeUtils;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.client.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TodayTermForecastFetcher {

    private final WeatherApiClient apiClient;
    private final BaseDateTimeUtils baseDateTimeUtils;

    public List<ShortTermForecastItem> fetchWeatherInformation(ForecastPoint forecastPoint) {
        ShortTermWeatherApiResponse response = apiClient.getShortTermWeatherForecast(forecastPoint);

        log.info("{}", response);

        return filterForecastItems(response);
    }

    private List<ShortTermForecastItem> filterForecastItems(ShortTermWeatherApiResponse response) {
        WeatherApiDateTime weatherApiDateTime = baseDateTimeUtils.calculateBaseDateTime();

        return response.getShortTermForecastItems().stream().filter(item -> item.fcstDate().equals(weatherApiDateTime.baseDate())).toList();
    }
}
