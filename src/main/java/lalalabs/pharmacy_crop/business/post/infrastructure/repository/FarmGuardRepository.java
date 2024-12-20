package lalalabs.pharmacy_crop.business.post.infrastructure.repository;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmGuardRepository extends JpaRepository<FarmGuard, Long> {
}
