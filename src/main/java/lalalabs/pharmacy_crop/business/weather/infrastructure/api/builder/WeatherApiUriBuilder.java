package lalalabs.pharmacy_crop.business.weather.infrastructure.api.builder;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.WeatherFetchProperty;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class WeatherApiUriBuilder {

    private final WeatherFetchProperty weatherFetchProperty;

    private UriComponentsBuilder initializeUriBuilder(String baseUri) {
        return UriComponentsBuilder.fromUriString(baseUri)
                .queryParam("authKey", weatherFetchProperty.getEncodingKey());
    }

    public String buildShortTermForecastUri(ForecastPoint forecastPoint, WeatherApiDateTime weatherApiDateTime) {
        return initializeUriBuilder(weatherFetchProperty.getShortTermUri())
                .queryParam("pageNo", 1)
                .queryParam("numOfRows", 1000)
                .queryParam("dataType", "JSON")
                .queryParam("base_date", weatherApiDateTime.baseDate())
                .queryParam("base_time", "0200")
                .queryParam("nx", forecastPoint.nx())
                .queryParam("ny", forecastPoint.ny())
                .toUriString();
    }

    public String buildMediumTermTemperatureForecastUri(String regionCode) {
        return initializeUriBuilder(weatherFetchProperty.getMediumTermTemperatureUri())
                .queryParam("reg", regionCode)
                .toUriString();
    }

    public String buildMediumTermWeatherForecastUri(String regionCode) {
        return initializeUriBuilder(weatherFetchProperty.getMediumTermWeatherUri())
                .queryParam("reg", "11" + regionCode.charAt(2) + "00000")
                .toUriString();
    }

    public String buildShortTermOverlandForecastUri(String regionCode) {
        return initializeUriBuilder(weatherFetchProperty.getShortTermOverlandForecastUri())
                .queryParam("reg", regionCode)
                .toUriString();
    }
}
