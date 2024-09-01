package com.login.entity.user;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.time.LocalDate;

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
    private String first_name;

    @NotNull(message = "Soyad boş olmamalıdır!")
    @Size(min = 2, max = 30, message = "Soyad en az 2 karakter olmalıdır!")
    private String last_name;

    @NotNull(message = "E-posta boş olmamalıdır!")
    @Email(message = "Lütfen geçerli bir e-posta giriniz!")
    @Size(min = 10, max = 80, message = "E-postanız 10 ile 80 karakter arasında olmalıdır!")
    @Column(unique = true)
    private String email;

    @NotNull(message = "şifre  boş olmamalıdır!")
    private String sifre;

    @OneToOne()
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserRole userRole;

    private Boolean built_in;

    private String telephone;
    private LocalDate dogumTarihi;
    private String dogumYeri;
    private String uyruk;
    private String babaAdi;
    private String anneAdi;

    @Builder.Default
    private Long point = 0L;

}
