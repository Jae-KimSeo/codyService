package org.cody.codyservice.common.exception;

import org.cody.codyservice.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 처리하는 핸들러
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외 처리
     * 비즈니스 로직에서 발생한 예외를 400 Bad Request로 변환
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
    }
    
    /**
     * 잘못된 인자 예외 처리
     * 입력값 검증 실패 시 400 Bad Request로 변환
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false, e.getMessage(), null));
    }
    
    /**
     * 기타 모든 예외 처리
     * 예상치 못한 서버 오류를 500 Internal Server Error로 변환
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(false, "서버 오류가 발생했습니다: " + e.getMessage(), null));
    }
} 