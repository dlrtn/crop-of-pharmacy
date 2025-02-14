package lalalabs.pharmacy_crop.business.user.application;

import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.domain.Role;
import org.springframework.stereotype.Component;

@Component
public class AuthorityChecker {

    public void isMaster(OauthUser user) {
        if (user.getRole() != Role.ROLE_MASTER) {
            throw new IllegalArgumentException("마스터 권한이 없습니다.");
        }
    }

    public boolean isAdmin(OauthUser user) {
        return user.getRole() == Role.ROLE_ADMIN || user.getRole() == Role.ROLE_MASTER;
    }

}
