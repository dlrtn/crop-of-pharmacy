package lalalabs.pharmacy_crop.business.weather.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lalalabs.pharmacy_crop.business.weather.domain.type.PrecipitationType;
import lalalabs.pharmacy_crop.business.weather.domain.type.SkyType;

public record WeeklyWeatherForecastDto(
        @Schema(description = "요일") int day,
        @Schema(description = "최고 온도") double maxTemperature,
        @Schema(description = "최저 온도") double minTemperature,
        @Schema(description = "하늘 상태, 0 : 맑음, 1 : 구름 조금, 2 : 구름 많음, 3 : 흐림") SkyType skyType,
        @Schema(description = "강수 확률") int rainProbability,
        @Schema(description = "강수 형태, 0 : 없음, 1 : 비, 2 : 비/눈, 3 : 눈/비, 4 : 소나기") PrecipitationType precipitationType
){
}
