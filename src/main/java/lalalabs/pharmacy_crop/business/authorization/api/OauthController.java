package lalalabs.pharmacy_crop.business.authorization.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lalalabs.pharmacy_crop.business.authorization.api.docs.ApiDescriptions;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokens;
import lalalabs.pharmacy_crop.business.authorization.application.OauthService;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Oauth", description = "소셜 사용자의 인증, 탈퇴 등을 처리합니다.")
@RequestMapping("/oauth")
public class OauthController {

    private final OauthService oauthService;

    @Operation(summary = ApiDescriptions.REDIRECT_TO_SOCIAL_LOGIN_SUMMARY, description = ApiDescriptions.REDIRECT_TO_SOCIAL_LOGIN_DESCRIPTION)
    @SneakyThrows
    @GetMapping("/{oauthServiceType}")
    public ResponseEntity<ApiResponse> sendRedirect(@PathVariable OauthServiceType oauthServiceType,
                                                    HttpServletResponse response) {
        String redirectUri = oauthService.getAuthorizationCodeRequestUri(oauthServiceType);

        response.sendRedirect(redirectUri);

        return ResponseEntity.ok().body(SuccessResponse.of());
    }

    @Operation(summary = ApiDescriptions.SOCIAL_LOGIN_SUMMARY, description = ApiDescriptions.SOCIAL_LOGIN_DESCRIPTION)
    @GetMapping("/login/{oauthServiceType}")
    public ResponseEntity<ApiResponse> login(@PathVariable OauthServiceType oauthServiceType,
                                             @RequestParam("code") String code) {
        JwtTokens jwtTokens = oauthService.login(oauthServiceType, code);

        return ResponseEntity.ok().body(SuccessResponse.of(jwtTokens));
    }

    @Operation(summary = ApiDescriptions.WITHDRAW_SOCIAL_USER_SUMMARY, description = ApiDescriptions.WITHDRAW_SOCIAL_USER_DESCRIPTION)
    @DeleteMapping("/withdraw")
    public ResponseEntity<ApiResponse> withdrawUser(@AuthenticationPrincipal OauthUserDetails oauthUser) {
        oauthService.withdrawUser(oauthUser.getUser());

        return ResponseEntity.ok().body(SuccessResponse.of());
    }
}
