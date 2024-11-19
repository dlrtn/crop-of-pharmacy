package lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.ForecastParser;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.clinet.WeatherApiClient;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem.AfternoonShortTermOverlandForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermOverlandForecastItem.MorningShortTermOverlandForecastItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShortTermForecastFetcher implements WeatherInformationFetcher {

    private final WeatherApiClient apiClient;
    private final ForecastParser parser;

    @Override
    public Map<LocalDate, ShortTermOverlandForecastItem> fetchWeatherInformation(ForecastAreaCode forecastAreaCode) {
        return getData(forecastAreaCode.getCode());
    }

    private Map<LocalDate, ShortTermOverlandForecastItem> getData(String forecastAreaCode) {
        String response = apiClient.getShortOverlandForecast(forecastAreaCode);

        Map<LocalDate, MorningShortTermOverlandForecastItem> morningData = getMorningData(response);
        Map<LocalDate, AfternoonShortTermOverlandForecastItem> afternoonData = getAfternoonData(response);

        Map<LocalDate, ShortTermOverlandForecastItem> result = new TreeMap<>();
        for (LocalDate dateTime : morningData.keySet()) {
            MorningShortTermOverlandForecastItem morning = morningData.get(dateTime);
            AfternoonShortTermOverlandForecastItem afternoon = afternoonData.get(dateTime);

            ShortTermOverlandForecastItem item = ShortTermOverlandForecastItem.createNew(morning, afternoon);

            result.put(dateTime, item);
        }

        return result;
    }

    private Map<LocalDate, MorningShortTermOverlandForecastItem> getMorningData(String forecast) {
        return parser.parseMorningForecast(forecast);
    }

    private Map<LocalDate, AfternoonShortTermOverlandForecastItem> getAfternoonData(String forecast) {
        return parser.parseAfternoonForecast(forecast);
    }
}
