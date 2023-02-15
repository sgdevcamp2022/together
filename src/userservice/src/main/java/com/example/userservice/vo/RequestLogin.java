package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull(message = "Email 입력은 null이 될 수 없습니다.")
    @Size(min = 2, message = "Email은 2글자 보다 커야합니다.")
    @Email
    private String email;

    @NotNull(message = "Password 입력은 null이 될 수 없습니다.")
    @Size(min = 8, message = "Password는 8글자보다 커야합니다.")
    private String password;
}
