package com.login.payload.request.user;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotNull(message = "Email must not be empty")
    private String email;

    @NotNull(message = "Password must not be empty")
    private String sifre;

}
