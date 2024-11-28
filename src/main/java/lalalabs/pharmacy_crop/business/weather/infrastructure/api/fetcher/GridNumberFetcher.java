package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.WeatherFetchProperty;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.client.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GridNumberFetcher {

    private static final int X_INDEX = 2;
    private static final int Y_INDEX = 3;

    private final WeatherApiClient weatherApiClient;
    private final WeatherFetchProperty weatherFetchProperty;

    public ForecastPoint fetchWeatherInformation(Coordinate coordinate) {
        String response = weatherApiClient.getGridNumber(coordinate);

        return parseResponse(response);
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