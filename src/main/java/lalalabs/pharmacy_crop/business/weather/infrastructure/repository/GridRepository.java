package lalalabs.pharmacy_crop.business.weather.infrastructure.repository;

import lalalabs.pharmacy_crop.business.weather.infrastructure.repository.entity.Grid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GridRepository extends JpaRepository<Grid, Integer> {
    List<Grid> findAllByIsUsed(Boolean isUsed);

    Grid findByNxAndNy(int nx, int ny);
}
