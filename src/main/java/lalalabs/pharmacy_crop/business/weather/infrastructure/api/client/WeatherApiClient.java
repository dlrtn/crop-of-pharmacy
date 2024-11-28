package lalalabs.pharmacy_crop.business.weather.infrastructure.api.client;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.builder.WeatherApiUriBuilder;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class WeatherApiClient {
    private final RestClient restClient = RestClient.create();
    private final WeatherApiUriBuilder weatherApiUriBuilder;

    public ShortTermWeatherApiResponse getShortTermWeatherForecast(ForecastPoint forecastPoint,
                                                                   WeatherApiDateTime weatherApiDateTime) {
        return restClient
                .get()
                .uri(weatherApiUriBuilder.buildShortTermForecastUri(forecastPoint, weatherApiDateTime))
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(ShortTermWeatherApiResponse.class);
    }

    public String getGridNumber(Coordinate coordinate) {
        return restClient
                .get()
                .uri(weatherApiUriBuilder.buildGridNumberUri(coordinate))
                .header("Content-Type", "text/plain")
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(String.class);
    }

    public String getMediumTermWeatherForecast(String regionCode) {
        return restClient
                .get()
                .uri(weatherApiUriBuilder.buildMediumTermWeatherForecastUri(regionCode))
                .header("Content-Type", "text/plain")
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(String.class);
    }

    public String getMediumTermTemperatureForecast(String regionCode) {
        return restClient
                .get()
                .uri(weatherApiUriBuilder.buildMediumTermTemperatureForecastUri(regionCode))
                .header("Content-Type", "text/plain")
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(String.class);
    }

    public String getShortOverlandForecast(String regionCode) {
        return restClient
                .get()
                .uri(weatherApiUriBuilder.buildShortTermOverlandForecastUri(regionCode))
                .header("Content-Type", "text/plain")
                .retrieve()
                .onStatus(HttpStatusCode::isError, ((request, response) -> {
                    throw new RuntimeException(String.valueOf(response.getStatusCode()));
                }))
                .body(String.class);
    }
}
