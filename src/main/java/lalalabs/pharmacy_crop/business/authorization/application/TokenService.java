package lalalabs.pharmacy_crop.business.authorization.application;

import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokens;
import lalalabs.pharmacy_crop.common.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtils jwtUtils;

    public JwtTokens issueTokensByUserId(String userId) {
        return jwtUtils.issueTokens(userId);
    }

}
