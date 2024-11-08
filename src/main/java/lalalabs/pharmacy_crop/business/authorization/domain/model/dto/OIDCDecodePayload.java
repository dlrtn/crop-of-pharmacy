package lalalabs.pharmacy_crop.business.authorization.domain.model.dto;

public record OIDCDecodePayload(
        String sub,
        String iss,
        String aud
) {
}
