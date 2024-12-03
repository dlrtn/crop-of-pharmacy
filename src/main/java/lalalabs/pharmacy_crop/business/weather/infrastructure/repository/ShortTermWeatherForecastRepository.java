package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortTermWeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortTermWeatherForecastRepository extends JpaRepository<ShortTermWeatherForecast, Long> {

    boolean existsByRegId(String regId);

    List<ShortTermWeatherForecast> findByRegId(String regId);
}
