package com.login.payload.request.abstracts;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseUserRequest extends AbstractUserRequest {

    @NotNull
    private String sifre;
}
