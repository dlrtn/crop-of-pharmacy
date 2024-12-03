package lalalabs.pharmacy_crop.business.weather.infrastructure.api;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class WeatherFetchProperty {

    @Value("${forecast.uri.short-term.detail}")
    private String shortTermUri;

    @Value("${forecast.uri.short-term.overland}")
    private String shortTermOverlandForecastUri;

    @Value("${forecast.uri.medium-term.weather}")
    private String mediumTermWeatherUri;

    @Value("${forecast.uri.medium-term.temperature}")
    private String mediumTermTemperatureUri;

    @Value("${forecast.uri.grid-number}")
    private String gridNumberUri;

    @Value("${forecast.key.encoding}")
    private String encodingKey;
}
