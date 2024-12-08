package lalalabs.pharmacy_crop.common.time;

import java.time.LocalDateTime;

public class TimeUtils {

    public static int secondsToMilliseconds(int s) {
        return s * 1000;
    }

    public static String convertToDateFormat(LocalDateTime date) {
        return "%d년 %d월 %d일".formatted(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
    }
}
