package lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto;

public record MediumTermForecastItem(String sky, String precipitation, int rainProbability,
                                     int maxTemperature, int minTemperature) {

    public record WeatherForecastData(String sky, String precipitation, int rainProbability) {
    }

    public record TemperatureForecastData(int maxTemperature, int minTemperature) {
    }
}
