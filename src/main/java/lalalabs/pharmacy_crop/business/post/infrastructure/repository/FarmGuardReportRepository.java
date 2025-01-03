package lalalabs.pharmacy_crop.business.post.infrastructure.repository;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuardReportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmGuardReportRepository extends JpaRepository<FarmGuardReportHistory, Long> {
    boolean existsByFarmGuardId(Long farmGuardId);
}
