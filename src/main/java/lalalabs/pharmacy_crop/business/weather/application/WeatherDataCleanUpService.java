package lalalabs.pharmacy_crop.business.weather.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.MediumTemperatureForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.MediumWeatherForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.ShortTermWeatherForecastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherDataCleanUpService {

    private final ShortTermWeatherForecastRepository shortTermWeatherForecastRepository;
    private final MediumWeatherForecastRepository mediumWeatherForecastRepository;
    private final MediumTemperatureForecastRepository mediumTemperatureForecastRepository;

    @Transactional
    public void cleanUp() {
        shortTermWeatherForecastRepository.deleteAll();
        mediumWeatherForecastRepository.deleteAll();
        mediumTemperatureForecastRepository.deleteAll();
    }
}
