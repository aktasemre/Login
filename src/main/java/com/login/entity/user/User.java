package com.login.entity.user;

import com.login.payload.messeges.ErrorMessages;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder(toBuilder = true)
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ad boş olmamalıdır!")
    @Size(min = 2, max = 30, message = "Ad en az 2 karakter olmalıdır!")
    private String firstName;

    @NotNull(message = "Soyad boş olmamalıdır!")
    @Size(min = 2, max = 30, message = "Soyad en az 2 karakter olmalıdır!")
    private String lastName;

    @NotNull(message = "E-posta boş olmamalıdır!")
    @Email(message = "Lütfen geçerli bir e-posta giriniz!")
    @Size(min = 10, max = 80, message = "E-postanız 10 ile 80 karakter arasında olmalıdır!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "Şifre boş olmamalıdır!")
    private String sifre;

    @ManyToOne()
    private UserRole userRole;

    private Boolean built_in;

    private String telephone;
    private LocalDate dogumTarihi;
    private String dogumYeri;
    private String uyruk;
    private String babaAdi;
    private String anneAdi;

    private LocalDateTime creatDate;
    private LocalDateTime updateAt;

    @Builder.Default
    private Long point = 0L;

    private String resetPasswordCode;
    private LocalDateTime resetPasswordCodeExpiry;

    private static final int CODE_LENGTH = 6;
    private static final SecureRandom RANDOM = new SecureRandom();

    private int failedAttempts = 3; // Yanlış kod denemesi sayacı
    private static final int MAX_FAILED_ATTEMPTS = 5; // Maksimum başarısız deneme hakkı

    //TODO bu kod baska clasta yazilabilir
    //TODO hatali reset codda mesaj donmeli ve hatali giris sayisinda sikinti var duzeltilmeli

    // 6 haneli rastgele alfanümerik kod üretir
    public void generateResetPasswordCode() {
        String possibleCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder codeBuilder = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            codeBuilder.append(possibleCharacters.charAt(RANDOM.nextInt(possibleCharacters.length())));
        }
        this.resetPasswordCode = codeBuilder.toString();
        this.resetPasswordCodeExpiry = LocalDateTime.now().plusMinutes(3); // Kodun geçerlilik süresi
    }

    // Kodun geçerliliğini kontrol eder
    public boolean isResetPasswordCodeValid(String code) {
        if (resetPasswordCodeExpiry == null || LocalDateTime.now().isAfter(resetPasswordCodeExpiry)) {
            throw new RuntimeException(ErrorMessages.RESET_CODE_EXPIRED);
           // Kodun süresi dolmuş
        }

        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            throw new RuntimeException(ErrorMessages.MAX_ATTEMPTS_REACHED); // Maksimum deneme sayısına ulaşıldı
             // Maksimum deneme sayısına ulaşıldı
        }

        if (!resetPasswordCode.equals(code)) {
            failedAttempts++;
            throw new RuntimeException(ErrorMessages.INCORRECT_RESET_CODE);
            // Yanlış kod
        }

        failedAttempts = 0; // Başarılı girişten sonra sıfırla
        return true; // Kod doğru
    }
}
