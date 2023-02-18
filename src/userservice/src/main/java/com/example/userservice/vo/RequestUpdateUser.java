package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUpdateUser {
    @NotNull(message = "Name은 null이 될 수 없습니다.")
    @Size(min = 2, message = "Name의 길이는 2자보다 작을 수 없습니다.")
    private String name;

    @NotNull(message = "Password는 null이 될 수 없습니다.")
    @Size(min = 8, message = "Password 길이는 8자보다 작을 수 없습니다.")
    private String pwd;
}
