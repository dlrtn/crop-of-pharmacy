package lalalabs.pharmacy_crop.common.response;

import lombok.Getter;

@Getter
public class SuccessResponse<T> extends ApiResponse {

    private T data;

    public SuccessResponse() {
        super(200, "OK", "요청이 성공적으로 처리되었습니다.");
    }

    public SuccessResponse(T data) {
        super(200, "OK", "요청이 성공적으로 처리되었습니다.");
        this.data = data;
    }

    public static <T> SuccessResponse<T> of() {
        return new SuccessResponse<>();
    }

    public static <T> SuccessResponse<T> of(T data) {
        return new SuccessResponse<>(data);
    }
}
