package lalalabs.pharmacy_crop.business.weather.infrastructure.api.builder;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.BaseDateTimeUtils;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.WeatherFetchProperty;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherApiUriBuilder {

    private final WeatherFetchProperty weatherFetchProperty;

    private UriComponentsBuilder initializeUriBuilder(String baseUri) {
        return UriComponentsBuilder.fromUriString(baseUri)
                .queryParam("authKey", weatherFetchProperty.getEncodingKey());
    }

    public String buildGridNumberUri(Coordinate coordinate) {
        return UriComponentsBuilder.fromUriString(weatherFetchProperty.getGridNumberUri())
                .queryParam("lon", coordinate.getLongitude())
                .queryParam("lat", coordinate.getLatitude())
                .queryParam("authKey", weatherFetchProperty.getEncodingKey())
                .toUriString();
    }

    public String buildShortTermForecastUri(ForecastPoint forecastPoint) {
        log.info("{}", BaseDateTimeUtils.getYesterday());

        return initializeUriBuilder(weatherFetchProperty.getShortTermUri())
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", BaseDateTimeUtils.getYesterday())
                .queryParam("base_time", "2300")
                .queryParam("nx", forecastPoint.nx())
                .queryParam("ny", forecastPoint.ny())
                .toUriString();
    }

    public String buildMediumTermTemperatureForecastUri() {
        return initializeUriBuilder(weatherFetchProperty.getMediumTermTemperatureUri())
                .toUriString();
    }

    public String buildMediumTermWeatherForecastUri() {
        return initializeUriBuilder(weatherFetchProperty.getMediumTermWeatherUri())
                .toUriString();
    }

    public String buildShortTermOverlandForecastUri() {
        return initializeUriBuilder(weatherFetchProperty.getShortTermOverlandForecastUri())
                .toUriString();
    }
}
