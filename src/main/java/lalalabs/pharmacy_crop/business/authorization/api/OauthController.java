package lalalabs.pharmacy_crop.business.authorization.api;

import jakarta.servlet.http.HttpServletResponse;
import lalalabs.pharmacy_crop.business.authorization.application.OauthService;
import lalalabs.pharmacy_crop.business.authorization.domain.common.model.OauthServiceType;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OauthController {

    private final OauthService oauthService;

    @SneakyThrows
    @GetMapping("/{oauthServiceType}")
    public ResponseEntity<Void> sendRedirect(
            @PathVariable OauthServiceType oauthServiceType,
            HttpServletResponse response) {
        String redirectUri = oauthService.getAuthorizationCodeRequestUri(oauthServiceType);

        response.sendRedirect(redirectUri);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/login/{oauthServiceType}")
    public ResponseEntity<Void> login(
            @PathVariable OauthServiceType oauthServiceType,
            @RequestParam("code") String code) {
        oauthService.login(oauthServiceType, code);

        return ResponseEntity.ok().build();
    }

}

