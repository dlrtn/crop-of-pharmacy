package lalalabs.pharmacy_crop.business.authorization.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
import lalalabs.pharmacy_crop.business.user.domain.OauthUser;
import lalalabs.pharmacy_crop.business.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

    private final OauthHelperComposite oauthHelperComposite;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    @Transactional
    public JwtTokensDto login(OauthServiceType oauthServiceType, OauthTokenDto oauthToken) {
        OIDCDecodePayload oidcPayload = oauthHelperComposite.decode(oauthServiceType, oauthToken);
        String oauthId = oidcPayload.sub();

        OauthUser oauthUser = userRepository.findByOauthId(oauthId, oauthServiceType)
                .orElseGet(() -> signUpUser(oauthServiceType, oauthToken));

        restoreWithdrawUser(oauthServiceType, oauthToken, oauthUser);

        return tokenService.issueTokensByUserId(oauthUser.getId());
    }

    private void restoreWithdrawUser(OauthServiceType oauthServiceType, OauthTokenDto oauthToken, OauthUser oauthUser) {
        if (Boolean.TRUE.equals(oauthUser.getIsDeleted())) {
            OauthUserInfoDto oauthUserInfo = oauthHelperComposite.fetchUserInfo(oauthServiceType, oauthToken);

            oauthUser.restore(oauthUserInfo);
        }
    }

    private OauthUser signUpUser(OauthServiceType oauthServiceType, OauthTokenDto oauthTokenDto) {
        log.info("Sign up new user with oauth service type: {}", oauthServiceType);

        OauthUserInfoDto oauthUserInfo = oauthHelperComposite.fetchUserInfo(oauthServiceType,
                oauthTokenDto);
        OauthUser newUser = OauthUser.create(oauthUserInfo);

        return userRepository.save(newUser);
    }

    public void unlink(OauthUser user, OauthTokenDto oauthTokenDto) {
        oauthHelperComposite.unlink(user, oauthTokenDto);
    }

    public JwtTokensDto refreshToken(JwtTokensDto jwtTokensDto) {
        tokenService.verifyToken(jwtTokensDto.refreshToken());

        String payload = tokenService.getPayloadFromToken(jwtTokensDto.refreshToken());

        return tokenService.reissueTokens(payload);
    }
}
