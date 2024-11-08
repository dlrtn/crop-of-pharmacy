package lalalabs.pharmacy_crop.business.authorization.infrastructure.repository;

import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OauthTokenRepository extends JpaRepository<OauthTokenEntity, String> {
}
