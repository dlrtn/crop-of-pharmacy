package lalalabs.pharmacy_crop.business.authorization.domain.model.exception;

public class InvalidGoogleIdTokenException extends RuntimeException {
    public InvalidGoogleIdTokenException() {
        super("유효하지 않은 ID 토큰입니다.");
    }
}
