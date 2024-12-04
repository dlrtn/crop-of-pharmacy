package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokens;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.repository.OauthTokenRepository;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OauthService {

    private final OauthHelperComposite oauthHelperComposite;
    private final UserRepository userRepository;
    private final OauthTokenRepository oauthTokenRepository;
    private final TokenService tokenService;

    public JwtTokens login(OauthServiceType oauthServiceType, OauthTokenDto oauthToken) {
        OIDCDecodePayload oidcPayload = oauthHelperComposite.decode(oauthServiceType, oauthToken);
        String oauthId = oidcPayload.sub();

        OauthUser oauthUser = userRepository.findByOauthId(oauthId, oauthServiceType)
                .orElseGet(() -> signUpUser(oauthServiceType, oauthToken));
        oauthTokenRepository.save(oauthToken.toEntity(oauthUser.getId()));

        return tokenService.issueTokensByUserId(oauthUser.getId());
    }

    private OauthUser signUpUser(OauthServiceType oauthServiceType, OauthTokenDto oauthTokenDto) {
        OauthUserInfoDto oauthUserInfo = oauthHelperComposite.fetchUserInfo(oauthServiceType,
                oauthTokenDto);
        OauthUser newUser = OauthUser.create(oauthUserInfo);

        return userRepository.save(newUser);
    }

    public void withdrawUser(OauthUser user) {
        oauthHelperComposite.unlink(user.getOauthId());
        userRepository.deleteById(user.getId());
    }
}
