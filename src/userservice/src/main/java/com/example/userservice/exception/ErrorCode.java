package com.example.userservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    /**
     * 404 error
     */
    CANNOT_FIND_USER(HttpStatus.NOT_FOUND,"해당하는 유저를 찾을 수 없습니다."),
    /**
     * 409 error
     */
    CANNOT_CREATE_USER(HttpStatus.CONFLICT,"이미 가입된 유저입니다.")
    ;

    private final HttpStatus status;
    private final String message;
}
