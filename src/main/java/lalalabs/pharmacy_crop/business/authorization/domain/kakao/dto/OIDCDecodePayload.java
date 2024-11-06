package lalalabs.pharmacy_crop.business.authorization.domain.kakao.dto;

public record OIDCDecodePayload(
        String sub,
        String iss,
        String aud,
        String email
) {
}
