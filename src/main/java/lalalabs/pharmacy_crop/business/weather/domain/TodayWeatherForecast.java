package lalalabs.pharmacy_crop.business.weather.domain;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.domain.dto.HourlyWeatherForecast;

public record TodayWeatherForecast(
        double maxTemperature, double minTemperature, List<HourlyWeatherForecast> hourlyWeatherForecasts
) {
}
