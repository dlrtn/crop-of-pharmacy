package lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto;

import java.util.List;

public record ShortTermWeatherApiResponse(
        ShortTermForecastResponse response
) {
    public List<ShortTermForecastItem> getShortTermForecastItems() {
        return response.body.items.item;
    }

    public record ShortTermForecastResponse(
            ResponseHeader header,
            ShortTermForecastBody body
    ) {
    }

    public record ResponseHeader(
            String resultCode,
            String resultMessage
    ) {
    }

    public record ShortTermForecastBody(
            String dataType,
            ShortTermForecastItems items
    ) {
    }

    public record ShortTermForecastItems(
            List<ShortTermForecastItem> item
    ) {
    }

    public record ShortTermForecastItem(
            String baseDate,
            String baseTime,
            String category,
            String fcstDate,
            String fcstTime,
            String fcstValue,
            int nx,
            int ny
    ) {
    }
}

