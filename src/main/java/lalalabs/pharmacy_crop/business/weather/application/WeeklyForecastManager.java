package lalalabs.pharmacy_crop.business.weather.application;

import java.time.LocalDateTime;
import java.util.List;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.MediumTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.ShortTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.MediumTemperatureForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.MediumWeatherForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.ShortTermWeatherForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumTemperatureForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortForecast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeeklyForecastManager {

    private final ShortTermForecastFetcher shortTermForecastFetcher;
    private final MediumTermForecastFetcher mediumTermForecastFetcher;
    private final ForecastConverter converter;
    private final MediumWeatherForecastRepository weatherForecastRepository;
    private final MediumTemperatureForecastRepository temperatureForecastRepository;
    private final ShortTermWeatherForecastRepository shortTermWeatherForecastRepository;

    public List<WeeklyWeatherForecastDto> getWeeklyWeatherForecast(ForecastAreaCode forecastAreaCode) {
        List<ShortForecast> shortForecasts = getShortTermForecast(forecastAreaCode.getCode());


        List<MediumWeatherForecast> mediumWeatherForecasts = getMediumTermWeatherForecast(
                forecastAreaCode.getGeneralCode());
        List<MediumTemperatureForecast> mediumTemperatureForecasts = getMediumTermTemperatureForecast(
                forecastAreaCode.getCode());

        return converter.convertWeeklyWeatherForecast(shortForecasts, mediumWeatherForecasts,
                mediumTemperatureForecasts);
    }

    private List<ShortForecast> getShortTermForecast(String regionCode) {
        if (shortTermWeatherForecastRepository.existsByRegId(regionCode)) {
            return shortTermWeatherForecastRepository.findByRegId(regionCode);
        }

        String response = shortTermForecastFetcher.getShortOverlandForecast();

        List<ShortForecast> shortForecasts = converter.convertShortTermWeatherForecast(response);

        LocalDateTime now = LocalDateTime.now();

        List<ShortForecast> filteredShortForecasts = shortForecasts.stream()
                .filter(weatherForecast -> weatherForecast.getTmEf().isAfter(now)).toList();

        filteredShortForecasts.forEach(filteredShortForecast -> log.info(filteredShortForecast.toString()));

        shortTermWeatherForecastRepository.saveAll(shortForecasts);

        return filteredShortForecasts.stream().filter(weatherForecast -> weatherForecast.getRegId().equals(regionCode))
                .toList();
    }

    private List<MediumWeatherForecast> getMediumTermWeatherForecast(String regionCode) {
        if (weatherForecastRepository.existsByRegId(regionCode)) {
            return weatherForecastRepository.findByRegId(regionCode);
        }

        String response = mediumTermForecastFetcher.getWeatherForecastResponse();

        List<MediumWeatherForecast> weatherForecasts = converter.convertMediumWeatherForecast(response);

        weatherForecastRepository.saveAll(weatherForecasts);

        return weatherForecasts.stream().filter(weatherForecast -> weatherForecast.getRegId().equals(regionCode))
                .toList();
    }

    private List<MediumTemperatureForecast> getMediumTermTemperatureForecast(String regionCode) {
        if (temperatureForecastRepository.existsByRegId(regionCode)) {
            return temperatureForecastRepository.findByRegId(regionCode);
        }

        String response = mediumTermForecastFetcher.getTemperatureForecastResponse();

        List<MediumTemperatureForecast> temperatureForecasts = converter.convertMediumTemperatureForecast(response);

        temperatureForecastRepository.saveAll(temperatureForecasts);

        return temperatureForecasts.stream()
                .filter(temperatureForecast -> temperatureForecast.getRegId().equals(regionCode)).toList();
    }
}
