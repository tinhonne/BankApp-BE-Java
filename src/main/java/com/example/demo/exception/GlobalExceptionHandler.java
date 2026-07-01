package com.example.demo.exception;


import com.example.demo.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value= AppException.class)
    public ResponseEntity<ApiResponse<?>> handlingAppException(AppException exception){
        ErrorCode errorCode=exception.getErrorCode();
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(ApiResponse.error(exception.getErrorCode()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handlingValidationExceptions(MethodArgumentNotValidException exception) {

        String message = exception.getFieldError().getDefaultMessage();
        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT.getHttpStatus())
                .body(ApiResponse.error(ErrorCode.INVALID_INPUT,message)
        );
    }
    @ExceptionHandler(value = Exception.class)
    public  ResponseEntity<ApiResponse<?>> handlingException(Exception exception){
        return ResponseEntity
                .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(ApiResponse.error(ErrorCode.INTERNAL_SERVER_ERROR)
        );
    }
}
