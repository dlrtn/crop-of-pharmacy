package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lalalabs.pharmacy_crop.business.weather.domain.TodayWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.BaseDateTimeUtils;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.ForecastConverter;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.client.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermForecastData.CategoryData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
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

    public TodayWeatherForecast fetchWeatherInformation(ForecastPoint forecastPoint) {
        WeatherApiDateTime weatherApiDateTime = baseDateTimeUtils.calculateBaseDateTime();

        ShortTermWeatherApiResponse response = apiClient.getShortTermWeatherForecast(forecastPoint, weatherApiDateTime);

        List<ShortTermForecastItem> filteredItems = filterForecastItems(response, weatherApiDateTime);

        Map<String, List<CategoryData>> groupedData = groupDataByTime(filteredItems);

        return forecastConverter.convert(groupedData);
    }

    private List<ShortTermForecastItem> filterForecastItems(ShortTermWeatherApiResponse response,
                                                            WeatherApiDateTime weatherApiDateTime) {
        return response.getShortTermForecastItems().stream()
                .filter(item -> item.fcstDate().equals(weatherApiDateTime.baseDate()))
                .toList();
    }

    private Map<String, List<CategoryData>> groupDataByTime(List<ShortTermForecastItem> items) {
        Map<String, List<CategoryData>> groupedData = new TreeMap<>();

        items.forEach(item -> {
            String forecastTime = item.fcstTime();
            CategoryData categoryData = new CategoryData(item.category(), item.fcstValue());
            groupedData.computeIfAbsent(forecastTime, k -> new ArrayList<>()).add(categoryData);
        });

        return groupedData;
    }
}
