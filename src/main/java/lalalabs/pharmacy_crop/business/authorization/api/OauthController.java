package lalalabs.pharmacy_crop.business.authorization.api;

import jakarta.servlet.http.HttpServletResponse;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokens;
import lalalabs.pharmacy_crop.business.authorization.application.OauthService;
import lalalabs.pharmacy_crop.business.authorization.domain.model.OauthServiceType;
import lalalabs.pharmacy_crop.business.user.domain.OauthUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows
    @GetMapping("/oauth/{oauthServiceType}")
    public ResponseEntity<Void> sendRedirect(@PathVariable OauthServiceType oauthServiceType,
                                             HttpServletResponse response) {
        String redirectUri = oauthService.getAuthorizationCodeRequestUri(oauthServiceType);

        response.sendRedirect(redirectUri);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/oauth/login/{oauthServiceType}")
    public ResponseEntity<JwtTokens> login(@PathVariable OauthServiceType oauthServiceType,
                                           @RequestParam("code") String code) {
        JwtTokens jwtTokens = oauthService.login(oauthServiceType, code);

        return ResponseEntity.ok().body(jwtTokens);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<Void> withdrawUser(@AuthenticationPrincipal OauthUserDetails oauthUser) {
        oauthService.withdrawUser(oauthUser.getUser());

        return ResponseEntity.ok().build();
    }
}
