package lalalabs.pharmacy_crop.business.weather.infrastructure.api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem.TemperatureForecastData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.MediumTermForecastItem.WeatherForecastData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem.AfternoonShortTermOverlandForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem.MorningShortTermOverlandForecastItem;
import org.springframework.stereotype.Component;

@Component
public class ForecastParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public Map<LocalDate, MorningShortTermOverlandForecastItem> parseMorningForecast(String forecast) {
        return parseForecast(forecast, "0000", fields -> new MorningShortTermOverlandForecastItem(
                Integer.parseInt(fields[12])
        ));
    }

    public Map<LocalDate, AfternoonShortTermOverlandForecastItem> parseAfternoonForecast(String forecast) {
        return parseForecast(forecast, "1200", fields -> new AfternoonShortTermOverlandForecastItem(
                Integer.parseInt(fields[12]),
                fields[14],
                Integer.parseInt(fields[15]),
                Integer.parseInt(fields[13])
        ));
    }

    private <T> Map<LocalDate, T> parseForecast(String forecast, String timeSuffix, Function<String[], T> mapper) {
        Map<LocalDate, T> records = new TreeMap<>();
        String[] lines = forecast.split("\n");

        for (String line : lines) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            String[] fields = line.split("\\s+");
            LocalDate dateTime = LocalDate.parse(fields[2], FORMATTER);

            if (dateTime.isAfter(LocalDate.now().plusDays(2)) || dateTime.isBefore(LocalDate.now().plusDays(1))
                    || !fields[2].endsWith(timeSuffix)) {
                continue;
            }

            T item = mapper.apply(fields);
            records.putIfAbsent(dateTime, item);
        }

        return records;
    }

    public Map<LocalDate, TemperatureForecastData> parseTemperatureForecast(String forecast) {
        return parseMediumTermForecast(forecast, this::getTemperatureForecastData);
    }

    public Map<LocalDate, WeatherForecastData> parseWeatherForecast(String forecast) {
        return parseMediumTermForecast(forecast, this::getWeatherForecastData);
    }

    private <T> Map<LocalDate, T> parseMediumTermForecast(String forecast, ForecastDataMapper<T> mapper) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        Map<LocalDate, T> records = new TreeMap<>();

        String[] lines = forecast.split("\n");
        for (String line : lines) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            String[] fields = line.split("\\s+");

            T data = mapper.map(fields);
            LocalDate dateTime = LocalDate.parse(fields[2], formatter);

            records.putIfAbsent(dateTime, data);
        }

        return records;
    }

    private WeatherForecastData getWeatherForecastData(String[] fields) {
        return new WeatherForecastData(
                fields[6],
                fields[7],
                Integer.parseInt(fields[10])
        );
    }

    private TemperatureForecastData getTemperatureForecastData(String[] fields) {
        return new TemperatureForecastData(
                Integer.parseInt(fields[6]),
                Integer.parseInt(fields[7])
        );
    }

    @FunctionalInterface
    private interface ForecastDataMapper<T> {
        T map(String[] fields);
    }
}
