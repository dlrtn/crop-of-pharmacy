package lalalabs.pharmacy_crop.business.user.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.business.authorization.application.TokenService;
import lalalabs.pharmacy_crop.business.user.api.dto.LoginAdminRequest;
import lalalabs.pharmacy_crop.business.user.api.dto.RegisterAdminRequest;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void createUser(RegisterAdminRequest request) {
        String encodedPassword = passwordEncoder.encode(request.password());

        OauthUser user = OauthUser.createAdmin(request, encodedPassword);

        userRepository.save(user);
    }

    public JwtTokensDto login(LoginAdminRequest request) {
        OauthUser user = userRepository.findByUsernamePasswordUsername(request.username())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.password(), user.getUsernamePassword().getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return tokenService.issueTokensByUserId(user.getId());
    }
}
