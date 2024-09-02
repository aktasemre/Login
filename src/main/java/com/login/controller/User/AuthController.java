package com.login.controller.User;

import com.login.entity.user.User;
import com.login.payload.request.user.UserUpdatePasswordRequest;
import com.login.service.mail.EmailService;
import com.login.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("reset")
public class AuthController {


    private final UserService userService;
    private final EmailService emailService;

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestParam String email) {
        // Kullanıcıyı email ile bul ve reset kodunu kaydet

        Optional<User> optionalUser = userService.findByEmail(email);
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Kullanıcı bulunamadı!");
        }

        User user = optionalUser.get();

        // Reset kodunu oluştur ve kaydet
        userService.generateResetPasswordCode(user);

        // Reset kodunu e-posta olarak gönder

        try {
            // Reset kodunu e-posta olarak gönder
            emailService.sendResetCode(user.getEmail(), user.getResetPasswordCode());
        } catch (MessagingException e) {
            // Hata durumunda kullanıcıya bilgi verin
            return ResponseEntity.status(500).body("E-posta gönderiminde bir hata oluştu.");
        }
        return ResponseEntity.ok("Şifre sıfırlama kodu e-posta adresinize gönderildi.");
    }

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest) {
        // Kullanıcıyı email ve reset kodu ile doğrula
        Optional<User> optionalUser = userService
                .findByEmailAndResetCode(userUpdatePasswordRequest.getEmail()
                ,userUpdatePasswordRequest.getReset_password_codee() );
        if (!optionalUser.isPresent()) {
            return ResponseEntity.badRequest().body("Geçersiz reset kodu veya e-posta!");
        }

        User user = optionalUser.get();

        // Şifreyi güncelle ve reset kodunu temizle
        userService.updatePassword(user, userUpdatePasswordRequest.getNewPassword());

        return ResponseEntity.ok("Şifreniz başarıyla güncellendi.");
    }
}