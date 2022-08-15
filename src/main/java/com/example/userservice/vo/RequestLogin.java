package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestLogin {
    @NotNull
    @Size(min = 2, message = "Email not be less than 2 charters")
    @Email
    private String email;

    @NotNull
    @Size(min = 8, message = "Email not be less than 8 charters")
    private String password;
}
