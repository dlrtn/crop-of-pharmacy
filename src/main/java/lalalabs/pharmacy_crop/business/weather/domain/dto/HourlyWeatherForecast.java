package lalalabs.pharmacy_crop.business.weather.domain.dto;

public record HourlyWeatherForecast(int time, int temperature, Double windSpeed,
                                    int sky, int probability, int precipitationType
) {
}
