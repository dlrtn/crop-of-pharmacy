package lalalabs.pharmacy_crop.business.weather.application;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.MediumTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.ShortTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.MediumTemperatureForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.MediumWeatherForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.ShortTermWeatherForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumTemperatureForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortTermWeatherForecast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeeklyForecastManager {

    private final ShortTermForecastFetcher shortTermForecastFetcher;
    private final MediumTermForecastFetcher mediumTermForecastFetcher;
    private final ForecastConverter converter;
    private final MediumWeatherForecastRepository weatherForecastRepository;
    private final MediumTemperatureForecastRepository temperatureForecastRepository;
    private final ShortTermWeatherForecastRepository shortTermWeatherForecastRepository;

    public List<WeeklyWeatherForecastDto> getWeeklyWeatherForecast(String code) {
        List<ShortTermWeatherForecast> shortTermWeatherForecasts = getNextTwoDaysWeatherForecast(code);
        converter.convertWeeklyWeatherForecast(getMediumTermWeatherForecast(code),
                getMediumTermTemperatureForecast(code));

        return List.of();
    }

    public List<WeeklyWeatherForecastDto> getBetweenThreeToSevenDaysWeatherForecast(String code) {
        return converter.convertWeeklyWeatherForecast(getMediumTermWeatherForecast(code),
                getMediumTermTemperatureForecast(code));
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

    public List<ShortTermWeatherForecast> getNextTwoDaysWeatherForecast(String code) {
        if (shortTermWeatherForecastRepository.existsByRegId(code)) {
            return shortTermWeatherForecastRepository.findByRegId(code);
        }

        String response = shortTermForecastFetcher.getShortOverlandForecast();

        List<ShortTermWeatherForecast> shortTermWeatherForecasts = converter.convertShortTermWeatherForecast(response);

        shortTermWeatherForecastRepository.saveAll(shortTermWeatherForecasts);

        return shortTermWeatherForecasts.stream().filter(weatherForecast -> weatherForecast.getRegId().equals(code))
                .toList();
    }

}
