package lalalabs.pharmacy_crop.business.weather.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "오늘의 날씨 정보")
public record TodayWeatherForecastDto(
    @Schema(description = "해당 날짜의 최고 기온") double maxTemperature,
    @Schema(description = "해당 날짜의 최저 기온") double minTemperature,
    @Schema(description = "시간별 날씨 정보") List<HourlyWeatherForecast> hourlyWeatherForecasts
) {
}
