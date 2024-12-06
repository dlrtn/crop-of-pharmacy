package lalalabs.pharmacy_crop.business.authorization.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lalalabs.pharmacy_crop.business.authorization.api.docs.ApiDescriptions;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokens;
import lalalabs.pharmacy_crop.business.authorization.application.OauthService;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.authorization.infrastructure.api.dto.OauthTokenDto;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lalalabs.pharmacy_crop.common.swagger.ApiHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Oauth", description = "소셜 사용자의 인증, 탈퇴 등을 처리합니다.")
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;

    @Operation(
            summary = ApiDescriptions.SOCIAL_LOGIN_SUMMARY,
            description = ApiDescriptions.SOCIAL_LOGIN_DESCRIPTION
    )
    @Parameter(name = "oauthServiceType", description = "소셜 로그인 서비스 타입", required = true)
    @PostMapping("/login/{oauthServiceType}")
    public ResponseEntity<ApiResponse> login(@PathVariable OauthServiceType oauthServiceType,
                                             @RequestBody() OauthTokenDto oauthTokenDto) {
        JwtTokens jwtTokens = oauthService.login(oauthServiceType, oauthTokenDto);

        return ResponseEntity.ok().body(SuccessResponse.of(jwtTokens));
    }

    @ApiHeader
    @Operation(
            summary = ApiDescriptions.WITHDRAW_SOCIAL_USER_SUMMARY,
            description = ApiDescriptions.WITHDRAW_SOCIAL_USER_DESCRIPTION
    )
    @Parameter(name = "Authorization", description = "액세스 토큰", required = true)
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawUser(@AuthenticationPrincipal OauthUserDetails oauthUser) {
        oauthService.withdrawUser(oauthUser.getUser());

        return ResponseEntity.ok().body(SuccessResponse.of());
    }
}
