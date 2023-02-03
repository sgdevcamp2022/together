package com.example.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestFriend {
    @Email
    private String myEmail;
    @Email
    private String friendEmail;
}
