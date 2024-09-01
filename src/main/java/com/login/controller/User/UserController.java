package com.login.controller.User;

import com.login.payload.request.user.LoginRequest;
import com.login.payload.request.user.UserRequest;
import com.login.payload.response.ResponseMessage;
import com.login.payload.response.user.AuthResponse;
import com.login.payload.response.user.UserResponse;
import com.login.service.user.UserServise;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserServise userServise;

    // F01 - login
    @PostMapping("/login") // http://localhost:8080/login

    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){

        return userServise.authenticateUser(loginRequest);
    }

    //F02 - register
    @PostMapping("/register") // http://localhost:8080/register

    public ResponseEntity<ResponseMessage<UserResponse>> saveUser(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.ok(userServise.saveUser(userRequest));
    }




}
