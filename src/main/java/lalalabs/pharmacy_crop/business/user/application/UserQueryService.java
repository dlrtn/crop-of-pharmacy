package lalalabs.pharmacy_crop.business.user.application;

import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserQueryService {

    private final UserRepository userRepository;

    public OauthUser findByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public String getUserNickname(OauthUser user) {
        OauthUser oauthUser = userRepository.findById(user.getId()).orElseThrow(() -> new IllegalArgumentException("User not found"));

        return oauthUser.getNickname();
    }

    public List<OauthUser> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).toList();
    }
}
