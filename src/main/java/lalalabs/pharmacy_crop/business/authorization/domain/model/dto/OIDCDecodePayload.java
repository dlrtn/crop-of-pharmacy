package lalalabs.pharmacy_crop.business.authorization.domain.model.dto;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;

public record OIDCDecodePayload(
        String sub,
        String iss,
        String aud
) {
    public static OIDCDecodePayload fromGoogleIdToken(GoogleIdToken googleIdToken) {
        return new OIDCDecodePayload(
                googleIdToken.getPayload().getSubject(),
                googleIdToken.getPayload().getIssuer(),
                googleIdToken.getHeader().getKeyId()
        );
    }
}
