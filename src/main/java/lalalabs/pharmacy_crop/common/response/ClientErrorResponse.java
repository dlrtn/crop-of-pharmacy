package lalalabs.pharmacy_crop.common.response;

public class ClientErrorResponse extends ApiResponse {

    public ClientErrorResponse(Exception e) {
        super(400, "Bad Request", e.getMessage());
    }

    public ClientErrorResponse(String message) {
        super(400, "Bad Request", message);
    }

    public static ClientErrorResponse of(Exception e) {
        return new ClientErrorResponse(e);
    }

    public static ClientErrorResponse of(String message) {
        return new ClientErrorResponse(message);
    }
}
