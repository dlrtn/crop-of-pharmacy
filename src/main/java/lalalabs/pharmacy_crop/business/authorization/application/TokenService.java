package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.common.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;

    public JwtTokensDto issueTokensByUserId(String userId) {
        return jwtUtils.issueTokens(userId);
    }
}
