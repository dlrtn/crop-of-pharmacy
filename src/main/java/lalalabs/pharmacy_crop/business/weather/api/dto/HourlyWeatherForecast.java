package lalalabs.pharmacy_crop.business.weather.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public record HourlyWeatherForecast(
        @Schema(description = "시간") int time,
        @Schema(description = "온도") int temperature,
        @Schema(description = "풍속") Double windSpeed,
        @Schema(description = "하늘 상태, 1: 맑음, 3: 구름많음, 4: 흐림") int sky,
        @Schema(description = "강수 확률") int probability,
        @Schema(description = "강수 형태, 0: 없음, 1: 비, 2: 비/눈, 3: 눈, 4: 소나기") int precipitationType
) {
}
