package lalalabs.pharmacy_crop.business.weather.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermForecastData.CategoryData;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import org.springframework.stereotype.Component;

@Component
public class ForecastGrouper {

    public Map<String, List<CategoryData>> groupDataByTime(List<ShortTermForecastItem> items) {
        Map<String, List<CategoryData>> groupedData = new TreeMap<>();

        items.forEach(item -> {
            String forecastTime = item.fcstTime();
            CategoryData categoryData = new CategoryData(item.category(), item.fcstValue());
            groupedData.computeIfAbsent(forecastTime, k -> new ArrayList<>()).add(categoryData);
        });

        return groupedData;
    }
}
