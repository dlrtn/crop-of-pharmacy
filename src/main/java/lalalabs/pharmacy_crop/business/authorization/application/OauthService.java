package lalalabs.pharmacy_crop.business.authorization.application;

import jakarta.transaction.Transactional;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.business.authorization.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OIDCDecodePayload;
import lalalabs.pharmacy_crop.business.authorization.domain.model.dto.OauthUserInfoDto;
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

    @Transactional
    public JwtTokensDto login(OauthServiceType oauthServiceType, OauthTokenDto oauthToken) {
        OIDCDecodePayload oidcPayload = oauthHelperComposite.decode(oauthServiceType, oauthToken);
        String oauthId = oidcPayload.sub();

        OauthUser oauthUser = userRepository.findByOauthId(oauthId, oauthServiceType)
                .orElseGet(() -> signUpUser(oauthServiceType, oauthToken));

        restoreWithdrawUser(oauthServiceType, oauthToken, oauthUser);

        oauthTokenRepository.save(oauthToken.toEntity(oauthUser.getId()));

        return tokenService.issueTokensByUserId(oauthUser.getId());
    }

    private void restoreWithdrawUser(OauthServiceType oauthServiceType, OauthTokenDto oauthToken, OauthUser oauthUser) {
        if (oauthUser.getDeleted()) {
            OauthUserInfoDto oauthUserInfo = oauthHelperComposite.fetchUserInfo(oauthServiceType, oauthToken);

            oauthUser.restore(oauthUserInfo);
            userRepository.save(oauthUser);
        }
    }

    private OauthUser signUpUser(OauthServiceType oauthServiceType, OauthTokenDto oauthTokenDto) {
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
