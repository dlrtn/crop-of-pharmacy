package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.WeatherFetchProperty;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.clinet.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
@Slf4j
public class GridNumberFetcher {

    private static final int X_INDEX = 2;
    private static final int Y_INDEX = 3;

    private final WeatherApiClient weatherApiClient;
    private final WeatherFetchProperty weatherFetchProperty;

    public ForecastPoint fetchWeatherInformation(Coordinate coordinate) {
        String uri = buildUri(coordinate);
        String response = weatherApiClient.getGridNumber(uri);

        return parseResponse(response);
    }

    private String buildUri(Coordinate coordinate) {
        return UriComponentsBuilder.fromUriString(weatherFetchProperty.getGridNumberUri())
                .queryParam("lon", coordinate.getLongitude())
                .queryParam("lat", coordinate.getLatitude())
                .queryParam("authKey", weatherFetchProperty.getEncodingKey())
                .toUriString();
    }

    private ForecastPoint parseResponse(String response) {
        try {
            String[] lines = response.split("\n");
            String[] values = lines[2].trim().split(",");

            int x = Integer.parseInt(values[X_INDEX].trim());
            int y = Integer.parseInt(values[Y_INDEX].trim());

            return new ForecastPoint(x, y);
        } catch (Exception e) {
            log.error("Error parsing response: {}", response, e);
            throw new RuntimeException("Failed to parse weather information response", e);
        }
    }
}