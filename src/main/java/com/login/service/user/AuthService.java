package com.login.service.user;

import com.login.entity.user.User;
import com.login.exeption.ResourceNotFoundException;
import com.login.payload.messeges.ErrorMessages;
import com.login.payload.messeges.SuccessMessages;
import com.login.payload.request.user.UserUpdatePasswordRequest;
import com.login.payload.response.ResponseMessage;
import com.login.repository.User.UserRepository;
import com.login.service.mail.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserService userService;

    public ResponseMessage<Void> resetPassword(UserUpdatePasswordRequest passwordResetRequest) {
        User user = userRepository.findByEmail(passwordResetRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", passwordResetRequest.getEmail()));

        // Reset kodunu oluştur ve kaydet
        generateResetPasswordCode(user);

        // Reset kodunu e-posta olarak gönder
        try {
            emailService.sendResetCode(user.getEmail(), user.getResetPasswordCode());
        } catch (MessagingException e) {
            return new ResponseMessage<>(null, HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.EMAIL_CANNOT_BE_NULL);
        }

        return new ResponseMessage<>(null, HttpStatus.OK, SuccessMessages.MAIL_GONDERILDI);
    }

    public ResponseMessage<Void> updatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest) {
        User user = userRepository.findByEmailAndResetPasswordCode(userUpdatePasswordRequest.getEmail(), userUpdatePasswordRequest.getReset_password_codee())
                .orElseThrow(() -> new ResourceNotFoundException("User", "email or reset code", userUpdatePasswordRequest.getEmail()));

        // Şifreyi güncelle ve reset kodunu temizle
        userService.updatePassword(user, userUpdatePasswordRequest.getNewPassword());

        return new ResponseMessage<>(null, HttpStatus.OK, SuccessMessages.PASSWORD_UPDATE);
    }

    // Şifre sıfırlama kodunu oluştur ve kaydet
    private void generateResetPasswordCode(User user) {
        user.generateResetPasswordCode();
        userRepository.save(user);
    }


}
