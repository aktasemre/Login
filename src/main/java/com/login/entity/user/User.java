package com.login.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.Nullable;


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
//deneme
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

    @NotNull(message = "şifre  boş olmamalıdır!")
    private String sifre;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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

    public void resetPasswordCode() {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = upperCaseLetters.toLowerCase();
        String numbers = "0123456789";
        String symbols = "!@#$%^&*()-_=+[{]}|;:',<.>/?";

        String combinedChars = upperCaseLetters + lowerCaseLetters + numbers + symbols;

        SecureRandom random = new SecureRandom();
        StringBuilder passwordBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(combinedChars.length());
            passwordBuilder.append(combinedChars.charAt(randomIndex));
        }

        this.resetPasswordCode = passwordBuilder.toString();
    }

}


