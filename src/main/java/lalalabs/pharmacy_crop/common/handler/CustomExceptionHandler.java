package lalalabs.pharmacy_crop.common.handler;

import com.google.protobuf.Api;
import lalalabs.pharmacy_crop.common.response.ApiResponse;
import lalalabs.pharmacy_crop.common.response.ClientErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse> handleMissingParamsException(MissingServletRequestParameterException e) {
        log.warn("Missing Request Parameter: {}", e.getParameterName());
        return ResponseEntity.status(400).body(ClientErrorResponse.of("Missing parameter: " + e.getParameterName()));
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse> handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("No Resource Found: {}", e.getMessage());
        return ResponseEntity.status(404).body(ClientErrorResponse.of(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleUnexpectedException(Exception e) {
        log.error("Unexpected Exception", e);
        return ResponseEntity.status(500).body(ClientErrorResponse.of(e));
    }
}