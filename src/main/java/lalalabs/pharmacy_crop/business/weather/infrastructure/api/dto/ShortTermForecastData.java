package lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto;

import java.util.List;

public record ShortTermForecastData(
        String fcstTime,
        List<CategoryData> fcstData
) {
    public record CategoryData(
            String category,
            String fcstValue
    ) {
    }
}
