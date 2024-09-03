package com.login.controller.User;
import com.login.payload.request.user.UserUpdatePasswordRequest;
import com.login.payload.response.ResponseMessage;
import com.login.service.user.AuthService;
import jakarta.validation.Valid;
import com.login.entity.user.User;
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

  //  @PostMapping("/update-password")
  //  public ResponseEntity<?> updatePassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
  //      // Kullanıcıyı email ve reset kodu ile doğrula
  //      Optional<User> optionalUser = userService
  //              .findByEmailAndResetCode(userUpdatePasswordRequest.getEmail()
  //                      ,userUpdatePasswordRequest.getReset_password_codee() );
  //      if (!optionalUser.isPresent()) {
  //          return ResponseEntity.badRequest().body("Geçersiz reset kodu veya e-posta!");
  //      }
//
  //      User user = optionalUser.get();
//
  //      // Şifreyi güncelle ve reset kodunu temizle
  //      userService.updatePassword(user, userUpdatePasswordRequest.getNewPassword());
//
  //      return ResponseEntity.ok("Şifreniz başarıyla güncellendi.");
//
    }
}
