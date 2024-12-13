package lalalabs.pharmacy_crop.business.authorization.domain.model.exception;

public class KakaoUnlinkFailedException extends RuntimeException {
    public KakaoUnlinkFailedException(String userId) {
        super("카카오 계정 연동 해제에 실패했습니다. userId: " + userId);
    }
}
