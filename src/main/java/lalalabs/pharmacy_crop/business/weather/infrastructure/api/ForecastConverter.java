package lalalabs.pharmacy_crop.business.weather.infrastructure.api;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lalalabs.pharmacy_crop.business.weather.domain.TodayWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.domain.dto.HourlyWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermForecastData.CategoryData;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class ForecastConverter {
    private <T> T findValue(List<CategoryData> categories, String category, Function<String, T> converter,
                            T defaultValue) {
        return categories.stream()
                .filter(data -> data.category().equals(category))
                .map(data -> converter.apply(data.fcstValue()))
                .findFirst()
                .orElse(defaultValue);
    }

    @SneakyThrows
    public TodayWeatherForecast convert(
            Map<String, List<CategoryData>> data) {// Extract max and min temperatures from the dataset
        double maxTemperature = extractExtremeTemperature(data, "TMX");
        double minTemperature = extractExtremeTemperature(data, "TMN");

        List<HourlyWeatherForecast> hourlyForecasts = data.entrySet().stream()
                .map(entry -> convertToHourlyForecast(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return new TodayWeatherForecast(maxTemperature, minTemperature, hourlyForecasts);
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
}
