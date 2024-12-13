package lalalabs.pharmacy_crop.business.authorization.domain.model.exception;

public class InvalidJsonWebTokenException extends RuntimeException {
    public InvalidJsonWebTokenException() {
        super("유효하지 않은 토큰입니다.");
    }
}
