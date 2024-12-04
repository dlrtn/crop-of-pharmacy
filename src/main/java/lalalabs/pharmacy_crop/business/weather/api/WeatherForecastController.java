package lalalabs.pharmacy_crop.business.weather.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lalalabs.pharmacy_crop.business.weather.api.dto.TodayWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.application.WeatherForecastService;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Weather", description = "일기예보 정보를 조회합니다")
@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;

    @GetMapping("/today")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = TodayWeatherForecastDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            })
    public ResponseEntity<Object> getTodayWeather(
            @Parameter(description = "위도", required = true) @RequestParam double latitude,
            @Parameter(description = "경도", required = true) @RequestParam double longitude) {
        Coordinate coordinate = new Coordinate(latitude, longitude);

        TodayWeatherForecastDto todayWeatherForecast = weatherForecastService.getTodayWeatherForecast(coordinate);

        return ResponseEntity.ok(SuccessResponse.of(todayWeatherForecast));
    }

    @GetMapping("/weekly")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "성공", content = @Content(schema = @Schema(implementation = WeeklyWeatherForecastDto.class))),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청")
            }
    )
    public ResponseEntity<Object> getWeeklyWeather(
            @Parameter(description = "위도", required = true) @RequestParam double latitude,
            @Parameter(description = "경도", required = true) @RequestParam double longitude) {
        Coordinate coordinate = new Coordinate(latitude, longitude);

        List<WeeklyWeatherForecastDto> weeklyWeatherForecast = weatherForecastService.getWeeklyWeatherForecast(
                coordinate);

        return ResponseEntity.ok(SuccessResponse.of(weeklyWeatherForecast));
    }
}
