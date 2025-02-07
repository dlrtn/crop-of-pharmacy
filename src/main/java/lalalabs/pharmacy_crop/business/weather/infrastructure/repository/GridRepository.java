package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.Grid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GridRepository extends JpaRepository<Grid, Integer> {
}
