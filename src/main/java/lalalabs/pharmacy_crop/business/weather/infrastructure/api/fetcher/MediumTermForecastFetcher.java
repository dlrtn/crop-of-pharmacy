package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.client.WeatherApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MediumTermForecastFetcher {

    private final WeatherApiClient apiClient;

    public String getTemperatureForecastResponse() {
        return apiClient.getMediumTermTemperatureForecast();
    }

    public String getWeatherForecastResponse() {
        return apiClient.getMediumTermWeatherForecast();
    }
}
