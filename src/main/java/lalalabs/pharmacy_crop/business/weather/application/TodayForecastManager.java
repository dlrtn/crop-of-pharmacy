package lalalabs.pharmacy_crop.business.weather.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.ShortTermForecastRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodayForecastManager {

    private final ShortTermForecastRepository shortTermForecastRepository;
    private final WeatherForecastService weatherForecastService;

    @Transactional
    public void delete() {
        shortTermForecastRepository.deleteAll();
    }

    @Transactional
    public void fetch() {
        weatherForecastService.fetchTodayWeatherForecast();
    }
}
