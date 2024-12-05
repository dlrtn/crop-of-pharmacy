package lalalabs.pharmacy_crop.business.user.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.user.api.dto.UpdateUserRequest;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    @Transactional
    public void updateUserNickname(OauthUser user, UpdateUserRequest updateUserRequest) {
        user.updateNickname(updateUserRequest.nickname());
    }
}
