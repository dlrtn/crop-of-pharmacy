package lalalabs.pharmacy_crop.business.weather.infrastructure.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.WeatherApiDateTime;
import org.springframework.stereotype.Component;

@Component
public class BaseDateTimeUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int[] BASE_HOURS = {2, 5, 8, 11, 14, 17, 20, 23};

    public String calculateNowTime(LocalDateTime now) {
        return String.format("%02d00", now.getHour());
    }

    public WeatherApiDateTime calculateBaseDateTime() {
        LocalDateTime now = LocalDateTime.now();

        return new WeatherApiDateTime(calculateBaseDate(now), calculateBaseTime(now), calculateNowTime(now));
    }

    private String calculateBaseTime(LocalDateTime now) {
        int hour = now.getHour();

        int baseHour = Arrays.stream(BASE_HOURS)
                .filter(base -> base <= hour)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid hour: " + hour));

        return String.format("%02d00", baseHour);
    }

    private String calculateBaseDate(LocalDateTime now) {
        return now.format(DATE_FORMATTER);
    }

    public static String getYesterday() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return yesterday.format(DATE_FORMATTER);
    }
}
