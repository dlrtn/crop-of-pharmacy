package lalalabs.pharmacy_crop.business.weather.application;

import lalalabs.pharmacy_crop.business.weather.api.dto.HourlyWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.api.dto.TodayWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.domain.type.PrecipitationType;
import lalalabs.pharmacy_crop.business.weather.domain.type.SkyType;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.ForecastParser;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermForecastData.CategoryData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumTemperatureForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortForecast;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class ForecastConverter {
    private final ForecastGrouper forecastGrouper;
    private final ForecastParser forecastParser;

    public List<ShortForecast> convertShortTermWeatherForecast(String response) {
        return forecastParser.parseShortTermWeatherForecast(response);
    }

    private <T> T findValue(List<CategoryData> categories, String category, Function<String, T> converter, T defaultValue) {
        return categories.stream().filter(data -> data.category().equals(category)).map(data -> converter.apply(data.fcstValue())).findFirst().orElse(defaultValue);
    }

    @SneakyThrows
    public TodayWeatherForecastDto convert(List<ShortTermForecastItem> shortTermForecastItemList) {
        Map<String, List<CategoryData>> data = forecastGrouper.groupDataByTime(shortTermForecastItemList);

        double maxTemperature = extractExtremeTemperature(data, "TMX");
        double minTemperature = extractExtremeTemperature(data, "TMN");

        List<HourlyWeatherForecast> hourlyForecasts = data.entrySet().stream().map(entry -> convertToHourlyForecast(entry.getKey(), entry.getValue())).toList();

        return new TodayWeatherForecastDto(maxTemperature, minTemperature, hourlyForecasts);
    }

    private HourlyWeatherForecast convertToHourlyForecast(String timeKey, List<CategoryData> categories) {
        int time = Integer.parseInt(timeKey);

        int temperature = findValue(categories, "TMP", Integer::parseInt, -1);
        double windSpeed = findValue(categories, "WSD", Double::parseDouble, 0.0);
        int sky = findValue(categories, "SKY", Integer::parseInt, -1);
        int probability = findValue(categories, "POP", Integer::parseInt, 0);
        int precipitationType = findValue(categories, "PTY", Integer::parseInt, 0);

        return new HourlyWeatherForecast(time, temperature, windSpeed, sky, probability, precipitationType);
    }

    private double extractExtremeTemperature(Map<String, List<CategoryData>> data, String category) {
        return data.values().stream().flatMap(List::stream).filter(cat -> cat.category().equals(category)).map(cat -> Double.parseDouble(cat.fcstValue())).findFirst().orElse(-999.0);
    }

    public List<MediumTemperatureForecast> convertMediumTemperatureForecast(String response) {
        return forecastParser.parseTemperatureForecast(response);
    }

    public List<MediumWeatherForecast> convertMediumWeatherForecast(String response) {
        return forecastParser.parseWeatherForecast(response);
    }

    public List<WeeklyWeatherForecastDto> convertWeeklyWeatherForecast(List<ShortForecast> shortForecast, List<MediumWeatherForecast> weatherForecast, List<MediumTemperatureForecast> temperatureForecast) {
        List<WeeklyWeatherForecastDto> weeklyWeatherForecast = convertShortForecastToWeeklyForecast(shortForecast);

        List<WeeklyWeatherForecastDto> threeToSevenDaysForecast = convertMediumWeatherForecastToWeeklyForecast(weatherForecast, temperatureForecast);

        weeklyWeatherForecast.addAll(threeToSevenDaysForecast);

        return weeklyWeatherForecast;
    }

    private List<WeeklyWeatherForecastDto> convertShortForecastToWeeklyForecast(List<ShortForecast> shortForecast) {
        List<ShortForecast> midnights = shortForecast.stream().filter(forecast -> forecast.getTmEf().getHour() == 0).filter(forecast -> forecast.getTmEf().isAfter(LocalDate.now().atStartOfDay())).toList();

        List<ShortForecast> noons = shortForecast.stream().filter(forecast -> forecast.getTmEf().getHour() == 12).filter(forecast -> forecast.getTmEf().isAfter(LocalDate.now().atStartOfDay())).toList();

        List<WeeklyWeatherForecastDto> weeklyWeatherForecast = new LinkedList<>();

        for (int i = 0; i < midnights.size(); i++) {
            ShortForecast midnight = midnights.get(i);
            ShortForecast noon = noons.get(i);

            WeeklyWeatherForecastDto weeklyForecast = new WeeklyWeatherForecastDto(midnight.getTmEf().getDayOfMonth(), noon.getTemperature(), midnight.getTemperature(), SkyType.fromShortTermOverlandForecastCode(noon.getSky()), noon.getRnSt(), PrecipitationType.fromCode(noon.getPre()));

            weeklyWeatherForecast.add(weeklyForecast);

        }

        return weeklyWeatherForecast;
    }

    private List<WeeklyWeatherForecastDto> convertMediumWeatherForecastToWeeklyForecast(List<MediumWeatherForecast> weatherForecast, List<MediumTemperatureForecast> temperatureForecast) {
        List<MediumWeatherForecast> midnightWeatherForecast = weatherForecast.stream().filter(forecast -> forecast.getTmEf().getHour() == 0).filter(forecast -> forecast.getTmEf().isBefore(LocalDate.now().plusDays(8).atStartOfDay())).toList();

        List<MediumTemperatureForecast> midnightTemperatureForecast = temperatureForecast.stream().filter(forecast -> forecast.getTmEf().isBefore(LocalDate.now().plusDays(8).atStartOfDay())).toList();

        List<WeeklyWeatherForecastDto> weeklyWeatherForecast = new LinkedList<>();
        for (int i = 0; i < midnightWeatherForecast.size(); i++) {
            MediumWeatherForecast midnight = midnightWeatherForecast.get(i);
            MediumTemperatureForecast temperature = midnightTemperatureForecast.get(i);

            WeeklyWeatherForecastDto weeklyForecast = new WeeklyWeatherForecastDto(midnight.getTmEf().getDayOfMonth(), temperature.getMax(), temperature.getMin(), SkyType.fromMediumTermForecastCode(midnight.getSky()), midnight.getRnSt(), PrecipitationType.fromMediumForecastCode(midnight.getPre()));

            weeklyWeatherForecast.add(weeklyForecast);
        }

        return weeklyWeatherForecast;
    }
}
