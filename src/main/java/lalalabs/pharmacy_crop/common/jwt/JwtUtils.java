package lalalabs.pharmacy_crop.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lalalabs.pharmacy_crop.business.authorization.api.dto.JwtTokensDto;
import lalalabs.pharmacy_crop.common.time.TimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${authorization.jwt.secret}")
    private String secret;

    @Value("${authorization.jwt.expiration.access-token}")
    private int accessTokenExpiration;

    @Value("${authorization.jwt.expiration.refresh-token}")
    private int refreshTokenExpiration;

    private SecretKey secretKey;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public JwtTokensDto issueTokens(String payload) {
        return JwtTokensDto.builder()
                .accessToken(generateAccessToken(payload))
                .refreshToken(generateRefreshToken(payload))
                .build();
    }

    public String generateRefreshToken(String payload) {
        return generateJsonWebToken(payload, refreshTokenExpiration);
    }

    public String generateAccessToken(String payload) {
        return generateJsonWebToken(payload, accessTokenExpiration);
    }

    private String generateJsonWebToken(String payload, int expiration) {
        return Jwts.builder()
                .setSubject(payload)
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                .setHeaderParam("kid", "pharmacy-crop")
                .setIssuer("pharmacy-crop")
                .setAudience("pharmacy-crop")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUtils.secondsToMilliseconds(expiration)))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserIdFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Authentication getAuthentication(String accessToken) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUserIdFromToken(accessToken));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getAccessTokenFromHeader(HttpServletRequest request) {
        String accessToken = request.getHeader("Authorization");

        if (accessToken != null && accessToken.startsWith("Bearer ")) {
            return accessToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
