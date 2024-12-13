package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.business.authorization.domain.model.exception.InvalidJsonWebTokenException;
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

    public void verifyToken(String token) {
        if (!jwtUtils.validateToken(token)) {
            throw new InvalidJsonWebTokenException();
        }
    }

    public JwtTokensDto reissueTokens(String userId) {
        return jwtUtils.issueTokens(userId);
    }

    public String getPayloadFromToken(String token) {
        return jwtUtils.extractSubject(token);
    }
}
