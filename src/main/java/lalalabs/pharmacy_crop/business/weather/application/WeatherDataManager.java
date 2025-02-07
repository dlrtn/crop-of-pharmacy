package lalalabs.pharmacy_crop.business.weather.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WeatherDataManager {

    private final WeeklyForecastManager weeklyForecastManager;
    private final TodayForecastManager todayForecastManager;

    @Transactional
    public void refreshWeeklyForecast() {
        weeklyForecastManager.delete();

        weeklyForecastManager.fetch();
    }

    @Transactional
    public void refreshTodayForecast() {
        todayForecastManager.delete();

        todayForecastManager.fetch();
    }
}
