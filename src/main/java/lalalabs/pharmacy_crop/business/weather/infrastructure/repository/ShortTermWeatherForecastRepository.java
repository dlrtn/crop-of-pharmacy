package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.ShortForecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShortTermWeatherForecastRepository extends JpaRepository<ShortForecast, Long> {

    boolean existsByRegId(String regId);

    List<ShortForecast> findByRegId(String regId);
}
