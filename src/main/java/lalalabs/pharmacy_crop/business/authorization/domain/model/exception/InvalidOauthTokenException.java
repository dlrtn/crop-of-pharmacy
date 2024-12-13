package lalalabs.pharmacy_crop.business.authorization.domain.model.exception;

public class InvalidOauthTokenException extends RuntimeException {
    public InvalidOauthTokenException(String userId) {
        super("유효하지 않은 Oauth 토큰입니다. userId: " + userId);
    }
}
