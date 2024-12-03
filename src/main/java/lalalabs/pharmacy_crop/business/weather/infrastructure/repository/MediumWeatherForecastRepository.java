package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumWeatherForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediumWeatherForecastRepository extends JpaRepository<MediumWeatherForecast, Long> {

    boolean existsByRegId(String regId);

    List<MediumWeatherForecast> findByRegId(String regId);
}
