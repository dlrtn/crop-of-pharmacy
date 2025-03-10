package lalalabs.pharmacy_crop.business.user.infrastructure.repository;

import io.lettuce.core.dynamic.annotation.Param;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<OauthUser, String> {

    OauthUser findByNickname(String nickname);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM OauthUser u WHERE u.oauthId.oauthServerId = :oauthServerId AND u.oauthId.oauthServiceType = :oauthServiceType")
    Boolean existsByOauthId(@Param("oauthServerId") String oauthServerId, @Param("oauthServiceType") OauthServiceType oauthServiceType);

    @Query("SELECT u FROM OauthUser u WHERE u.oauthId.oauthServerId = :oauthServerId AND u.oauthId.oauthServiceType = :oauthServiceType")
    Optional<OauthUser> findByOauthId(@Param("oauthServerId") String oauthServerId, @Param("oauthServiceType") OauthServiceType oauthServiceType);

    Optional<OauthUser> findByUsernamePasswordUsername(String username);

    Page<OauthUser> findAll(Pageable pageable);
}
