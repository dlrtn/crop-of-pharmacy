package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import java.util.List;
import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.MediumTemperatureForecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediumTemperatureForecastRepository extends JpaRepository<MediumTemperatureForecast, Long> {
    boolean existsByRegId(String regId);

    List<MediumTemperatureForecast> findByRegId(String regId);
}
