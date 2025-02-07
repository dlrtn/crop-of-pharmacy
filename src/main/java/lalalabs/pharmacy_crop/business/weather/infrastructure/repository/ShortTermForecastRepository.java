package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import lalalabs.pharmacy_crop.business.weather.infrastructure.api.dto.ShortTermWeatherApiResponse;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortTermForecast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortTermForecastRepository extends JpaRepository<ShortTermForecast, Integer> {
    List<ShortTermWeatherApiResponse.ShortTermForecastItem> findAllByNxAndNy(int attr0, int attr1);

    boolean existsByNxAndNy(Integer nx, Integer ny);
}
