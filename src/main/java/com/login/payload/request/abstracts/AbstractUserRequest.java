package com.login.payload.request.abstracts;

import com.login.entity.user.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class AbstractUserRequest {

    @NotNull(message = "Please enter your name")
    private String first_name;

    @NotNull(message = "Please enter your last name")
    private String last_name;

    @NotNull(message = "Please enter your email")
    @Email(message = "Please enter valid email")
    @Size(min=5, max=50 , message = "Your email should be between 5 and 50 chars")
    private String email;




    private UserRole userRole;










}
