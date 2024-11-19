package lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto;

public record ShortTermOverlandForecastItem(
        String sky,
        int precipitation,
        int rainProbability,
        int minTemperature,
        int maxTemperature
) {

    public static ShortTermOverlandForecastItem createNew(MorningShortTermOverlandForecastItem morning,
                                                   AfternoonShortTermOverlandForecastItem afternoon) {
        return new ShortTermOverlandForecastItem(afternoon.sky(), afternoon.precipitation(),
                afternoon.rainProbability(), morning.minTemperature(), afternoon.maxTemperature());
    }

    public record MorningShortTermOverlandForecastItem(
            int minTemperature
    ) {
    }

    public record AfternoonShortTermOverlandForecastItem(
            int maxTemperature,
            String sky,
            int precipitation,
            int rainProbability
    ) {
    }
}
