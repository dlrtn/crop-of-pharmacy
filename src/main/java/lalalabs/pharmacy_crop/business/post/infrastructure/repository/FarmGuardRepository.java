package lalalabs.pharmacy_crop.business.post.infrastructure.repository;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmGuardRepository extends JpaRepository<FarmGuard, Long> {
    List<FarmGuard> findAllByOftenViewedIsTrue(boolean oftenViewed, Pageable pageable);

    List<FarmGuard> findAllByOftenViewed(boolean oftenViewed, Pageable pageable);
}
