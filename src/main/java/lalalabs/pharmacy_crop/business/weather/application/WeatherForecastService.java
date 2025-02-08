package lalalabs.pharmacy_crop.business.weather.application;

import lalalabs.pharmacy_crop.business.weather.api.dto.TodayWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.api.dto.WeeklyWeatherForecastDto;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCode;
import lalalabs.pharmacy_crop.business.weather.domain.ForecastAreaCodeResolver;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ForecastPoint;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse.ShortTermForecastItem;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.GridNumberFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.api.fetcher.TodayTermForecastFetcher;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.GridRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.ShortTermForecastRepository;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.Grid;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortTermForecast;
import lalalabs.pharmacy_crop.common.coordinate.Coordinate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherForecastService {

    private final ForecastAreaCodeResolver forecastAreaCodeResolver;
    private final GridNumberFetcher gridNumberFetcher;
    private final TodayTermForecastFetcher todayTermForecastFetcher;
    private final ForecastConverter forecastConverter;
    private final WeeklyForecastManager weeklyForecastManager;
    private final GridRepository gridRepository;
    private final ShortTermForecastRepository shortTermForecastRepository;

    public TodayWeatherForecastDto getTodayWeatherForecast(Coordinate coordinate) {
        ForecastPoint gridNumber = gridNumberFetcher.getGridNumberFromDatabase(coordinate);

        List<ShortTermForecastItem> shortTermForecastItemList;

        if (!shortTermForecastRepository.existsByNxAndNy(gridNumber.nx(), gridNumber.ny())) {
            shortTermForecastItemList = todayTermForecastFetcher.fetchWeatherInformation(gridNumber);

            shortTermForecastItemList.forEach(item -> shortTermForecastRepository.save(ShortTermForecast.of(item)));
        } else {
            shortTermForecastItemList = shortTermForecastRepository.findAllByNxAndNy(gridNumber.nx(), gridNumber.ny());
        }

        return forecastConverter.convert(shortTermForecastItemList);
    }

    public void fetchTodayWeatherForecast() {
        List<Grid> grids = gridRepository.findAllByIsUsed(true);

        for (Grid grid : grids) {
            ForecastPoint gridNumber = new ForecastPoint(grid.getNx(), grid.getNy());
            List<ShortTermForecastItem> shortTermForecastItemList = todayTermForecastFetcher.fetchWeatherInformation(
                    gridNumber);

            shortTermForecastItemList.forEach(item -> shortTermForecastRepository.save(ShortTermForecast.of(item)));
        }
    }

    public List<WeeklyWeatherForecastDto> getWeeklyWeatherForecast(Coordinate coordinate) {
        ForecastAreaCode forecastAreaCode = forecastAreaCodeResolver.resolve(coordinate);

        return weeklyForecastManager.getWeeklyWeatherForecast(forecastAreaCode);
    }
}
