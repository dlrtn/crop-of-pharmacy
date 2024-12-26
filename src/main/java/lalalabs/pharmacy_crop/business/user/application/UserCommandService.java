package lalalabs.pharmacy_crop.business.user.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.user.api.dto.UpdateUserRequest;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCommandService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserNickname(OauthUser user, UpdateUserRequest updateUserRequest) {
        user.updateNickname(updateUserRequest.nickname());

        userRepository.save(user);
    }

    public void withdrawUser(OauthUser user) {
        userRepository.delete(user);
    }
}
