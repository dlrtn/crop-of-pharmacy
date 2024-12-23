package lalalabs.pharmacy_crop.business.post.infrastructure.repository;

import lalalabs.pharmacy_crop.business.post.domain.FarmGuardAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmGuardAnswerRepository extends JpaRepository<FarmGuardAnswer, Long> {
    boolean existsFarmGuardAnswerByFarmGuardId(Long farmGuardId);

    FarmGuardAnswer getFarmGuardAnswerByFarmGuardId(Long farmGuardId);

    FarmGuardAnswer findFarmGuardAnswerByFarmGuardId(Long farmGuardId);
}
