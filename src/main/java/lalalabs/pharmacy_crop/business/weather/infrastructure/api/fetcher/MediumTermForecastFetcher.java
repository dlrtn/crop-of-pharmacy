package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.ForecastParser;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.clinet.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem.TemperatureForecastData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem.WeatherForecastData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediumTermForecastFetcher implements WeatherInformationFetcher {

    private final WeatherApiClient apiClient;
    private final ForecastParser parser;

    @Override
    public Map<LocalDate, MediumTermForecastItem> fetchWeatherInformation(ForecastAreaCode forecastAreaCode) {
        Map<LocalDate, MediumTermForecastItem> data = getMediumTermForecastMap(forecastAreaCode.getCode());

        data.forEach((dateTime, item) -> log.info("Medium term forecast for {}: {}", dateTime, item));

        return data;
    }

    private Map<LocalDate, MediumTermForecastItem> getMediumTermForecastMap(String forecastAreaCode) {
        LocalDate cutOffTime = LocalDate.now().plusDays(7);

        Map<LocalDate, TemperatureForecastData> forecastDataMap = getTemperatureDataMap(forecastAreaCode);
        Map<LocalDate, WeatherForecastData> weatherDataMap = getWeatherDataMap(forecastAreaCode);

        Map<LocalDate, MediumTermForecastItem> resultMap = new ConcurrentSkipListMap<>();
        weatherDataMap.forEach((dateTime, weatherData) -> {
            if (!dateTime.isAfter(cutOffTime)) {
                TemperatureForecastData temperatureData = forecastDataMap.get(dateTime);
                mapAndPutIfValid(resultMap, dateTime, weatherData, temperatureData);
            }
        });

        return resultMap;
    }


    private void mapAndPutIfValid(Map<LocalDate, MediumTermForecastItem> resultMap,
                                  LocalDate date,
                                  WeatherForecastData weatherData,
                                  TemperatureForecastData temperatureData) {
        if (weatherData != null && temperatureData != null) {
            resultMap.put(date, new MediumTermForecastItem(
                    weatherData.sky(),
                    weatherData.precipitation(),
                    weatherData.rainProbability(),
                    temperatureData.maxTemperature(),
                    temperatureData.minTemperature()
            ));
        }
    }

    private Map<LocalDate, TemperatureForecastData> getTemperatureDataMap(String code) {
        String response = apiClient.getMediumTermTemperatureForecast(code);
        return parser.parseTemperatureForecast(response);
    }

    private Map<LocalDate, WeatherForecastData> getWeatherDataMap(String code) {
        String response = apiClient.getMediumTermWeatherForecast(code);
        return parser.parseWeatherForecast(response);
    }
}
