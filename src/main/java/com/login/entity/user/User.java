package com.login.entity.user;

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

    // 6 haneli rastgele kod üretir
    public void generateResetPasswordCode() {
        int code = RANDOM.nextInt((int) Math.pow(10, CODE_LENGTH));
        this.resetPasswordCode = String.format("%0" + CODE_LENGTH + "d", code);
        this.resetPasswordCodeExpiry = LocalDateTime.now().plusMinutes(3);
    //plusHours(1); // Kodun geçerlilik süresi
    }

    // Kodun geçerliliğini kontrol eder
    public boolean isResetPasswordCodeValid(String code) {
        if (resetPasswordCodeExpiry == null || LocalDateTime.now().isAfter(resetPasswordCodeExpiry)) {
            return false; // Kodun süresi dolmuş
        }
        return resetPasswordCode.equals(code); // Kodu doğrulama
    }
}
