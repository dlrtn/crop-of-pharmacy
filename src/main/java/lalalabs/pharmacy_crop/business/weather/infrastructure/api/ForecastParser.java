package lalalabs.pharmacy_crop.business.weather.infrastructure.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumTemperatureForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumWeatherForecast;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortTermWeatherForecast;
import org.springframework.stereotype.Component;

@Component
public class ForecastParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

    public List<ShortTermWeatherForecast> parseShortTermWeatherForecast(String response) {
        return parseWeatherForecast(response, this::getShortTermWeatherForecastData);
    }

    public List<MediumTemperatureForecast> parseTemperatureForecast(String response) {
        return parseWeatherForecast(response, this::getTemperatureForecastData);
    }

    public List<MediumWeatherForecast> parseWeatherForecast(String response) {
        return parseWeatherForecast(response, this::getWeatherForecastData);
    }

    private <T> List<T> parseWeatherForecast(String forecast, ForecastDataMapper<T> mapper) {
        List<T> records = new ArrayList<>();

        Pattern pattern = Pattern.compile("\"([^\"]*)\"|(\\S+)");

        String[] lines = forecast.split("\n");
        for (String line : lines) {
            if (line.startsWith("#") || line.trim().isEmpty()) {
                continue;
            }

            Matcher matcher = pattern.matcher(line);

            List<String> fields = new ArrayList<>();
            while (matcher.find()) {
                if (matcher.group(1) != null) {
                    fields.add(matcher.group(1)); // 따옴표로 감싼 부분
                } else {
                    fields.add(matcher.group(2)); // 일반 단어
                }
            }

            T data = mapper.map(fields);

            records.add(data);
        }

        return records;
    }

    private MediumWeatherForecast getWeatherForecastData(List<String> fields) {
        return new MediumWeatherForecast(
                null,
                fields.get(0),
                LocalDateTime.parse(fields.get(1), FORMATTER),
                LocalDateTime.parse(fields.get(2), FORMATTER),
                fields.get(6),
                fields.get(7),
                Integer.parseInt(fields.get(10))
        );
    }

    private MediumTemperatureForecast getTemperatureForecastData(List<String> fields) {
        return new MediumTemperatureForecast(
                null,
                fields.get(0),
                LocalDateTime.parse(fields.get(1), FORMATTER),
                LocalDateTime.parse(fields.get(2), FORMATTER),
                Float.parseFloat(fields.get(6)),
                Float.parseFloat(fields.get(7))
        );
    }

    private ShortTermWeatherForecast getShortTermWeatherForecastData(List<String> fields) {
        return new ShortTermWeatherForecast(
                null,
                fields.get(0),
                LocalDateTime.parse(fields.get(1), FORMATTER),
                LocalDateTime.parse(fields.get(2), FORMATTER),
                Integer.parseInt(fields.get(12)),
                fields.get(14),
                Integer.parseInt(fields.get(15)),
                Integer.parseInt(fields.get(13))
        );
    }


    @FunctionalInterface
    private interface ForecastDataMapper<T> {
        T map(List<String> fields);
    }
}
