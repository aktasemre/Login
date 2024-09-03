package com.login.controller.User;

import com.login.payload.request.user.UserUpdatePasswordRequest;
import com.login.payload.response.ResponseMessage;
import com.login.service.user.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reset")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/resetPassword")
    public ResponseEntity<ResponseMessage<Void>> resetPassword(@RequestBody @Valid UserUpdatePasswordRequest passwordResetRequest) {
        ResponseMessage<Void> response = authService.resetPassword(passwordResetRequest);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<ResponseMessage<Void>> updatePassword(@RequestBody @Valid UserUpdatePasswordRequest userUpdatePasswordRequest) {
        ResponseMessage<Void> response = authService.updatePassword(userUpdatePasswordRequest);
        return ResponseEntity.status(response.getHttpStatus()).body(response);
    }
}
