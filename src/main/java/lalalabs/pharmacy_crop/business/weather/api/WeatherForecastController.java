package lalalabs.pharmacy_crop.business.weather.api;

import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.weather.application.WeatherForecastService;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherForecastController {

    private final WeatherForecastService weatherForecastService;

    @GetMapping("/weather")
    public ResponseEntity<Object> getWeather(@AuthenticationPrincipal OauthUser oauthUser,
                                             @RequestBody Coordinate coordinate) {
        Object weatherForecast = weatherForecastService.getWeatherForecast(coordinate);

        return ResponseEntity.ok(weatherForecast);
    }
}
