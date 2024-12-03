package lalalabs.pharmacy_crop.business.weather.application;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import lalalabs.pharmacy_crop.business.weather.api.dto.HourlyWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.api.dto.TodayWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.domain.type.PrecipitationType;
import lalalabs.pharmacy_crop.business.weather.domain.type.SkyType;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.ForecastParser;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermForecastData.CategoryData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumTemperatureForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortTermWeatherForecast;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ForecastConverter {
    private final ForecastGrouper forecastGrouper;
    private final ForecastParser forecastParser;

    private <T> T findValue(List<CategoryData> categories, String category, Function<String, T> converter,
                            T defaultValue) {
        return categories.stream()
                .filter(data -> data.category().equals(category))
                .map(data -> converter.apply(data.fcstValue()))
                .findFirst()
                .orElse(defaultValue);
    }

    @SneakyThrows
    public TodayWeatherForecastDto convert(List<ShortTermForecastItem> shortTermForecastItemList) {
        Map<String, List<CategoryData>> data = forecastGrouper.groupDataByTime(shortTermForecastItemList);

        double maxTemperature = extractExtremeTemperature(data, "TMX");
        double minTemperature = extractExtremeTemperature(data, "TMN");

        List<HourlyWeatherForecast> hourlyForecasts = data.entrySet().stream()
                .map(entry -> convertToHourlyForecast(entry.getKey(), entry.getValue()))
                .toList();

        return new TodayWeatherForecastDto(maxTemperature, minTemperature, hourlyForecasts);
    }

    private HourlyWeatherForecast convertToHourlyForecast(String timeKey, List<CategoryData> categories) {
        int time = Integer.parseInt(timeKey);

        int temperature = findValue(categories, "TMP", Integer::parseInt, -1) / 100;
        double windSpeed = findValue(categories, "WSD", Double::parseDouble, 0.0);
        int sky = findValue(categories, "SKY", Integer::parseInt, -1);
        int probability = findValue(categories, "POP", Integer::parseInt, 0);
        int precipitationType = findValue(categories, "PTY", Integer::parseInt, 0);

        return new HourlyWeatherForecast(time, temperature, windSpeed, sky, probability, precipitationType);
    }

    private double extractExtremeTemperature(Map<String, List<CategoryData>> data, String category) {
        return data.values().stream()
                .flatMap(List::stream)
                .filter(cat -> cat.category().equals(category))
                .map(cat -> Double.parseDouble(cat.fcstValue()))
                .findFirst()
                .orElse(-999.0);
    }

    public List<WeeklyWeatherForecastDto> convertWeeklyForecast(
            Map<LocalDate, ShortTermOverlandForecastItem> shortTermOverlandForecastItemMap,
            Map<LocalDate, MediumTermForecastItem> mediumTermForecastItemMap) {
        List<WeeklyWeatherForecastDto> weeklyWeatherForecast = new LinkedList<>();

        weeklyWeatherForecast.addAll(convertShortTermOverlandToWeeklyForecast(shortTermOverlandForecastItemMap));
        weeklyWeatherForecast.addAll(convertMediumTermToWeeklyForecast(mediumTermForecastItemMap));

        return weeklyWeatherForecast;
    }

    private List<WeeklyWeatherForecastDto> convertShortTermOverlandToWeeklyForecast(
            Map<LocalDate, ShortTermOverlandForecastItem> shortTermOverlandForecastItem) {
        return shortTermOverlandForecastItem.keySet().stream()
                .map(date -> {
                    ShortTermOverlandForecastItem forecastItem = shortTermOverlandForecastItem.get(date);

                    return new WeeklyWeatherForecastDto(
                            date.getDayOfWeek().getValue(),
                            forecastItem.maxTemperature(),
                            forecastItem.minTemperature(),
                            SkyType.fromShortTermOverlandForecastCode(forecastItem.sky()),
                            forecastItem.rainProbability(),
                            PrecipitationType.fromCode(forecastItem.precipitation())
                    );
                })
                .toList();
    }

    private List<WeeklyWeatherForecastDto> convertMediumTermToWeeklyForecast(
            Map<LocalDate, MediumTermForecastItem> mediumTermForecastItem) {
        return mediumTermForecastItem.keySet().stream()
                .map(date -> {
                    MediumTermForecastItem forecastItem = mediumTermForecastItem.get(date);

                    return new WeeklyWeatherForecastDto(
                            date.getDayOfWeek().getValue(),
                            forecastItem.maxTemperature(),
                            forecastItem.minTemperature(),
                            SkyType.fromMediumTermForecastCode(forecastItem.sky()),
                            forecastItem.rainProbability(),
                            PrecipitationType.fromMediumForecastCode(forecastItem.precipitation())
                    );
                })
                .toList();
    }

    public List<MediumTemperatureForecast> convertMediumTemperatureForecast(String response) {
        return forecastParser.parseTemperatureForecast(response);
    }

    public List<MediumWeatherForecast> convertMediumWeatherForecast(String response) {
        return forecastParser.parseWeatherForecast(response);
    }

    public List<WeeklyWeatherForecastDto> convertWeeklyWeatherForecast(List<MediumWeatherForecast> weatherForecast,
                                                                       List<MediumTemperatureForecast> temperatureForecast) {
        // just logging
        weatherForecast.forEach(System.out::println);
        temperatureForecast.forEach(System.out::println);

        return List.of();
    }

    public List<ShortTermWeatherForecast> convertShortTermWeatherForecast(String response) {
        return null;
    }
}
