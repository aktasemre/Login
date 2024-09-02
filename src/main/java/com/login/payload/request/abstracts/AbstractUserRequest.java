package com.login.payload.request.abstracts;

import com.login.entity.user.UserRole;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

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

    @NotBlank(message = "Telefon numarası boş olamaz")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Geçersiz telefon numarası formatı")
    private String telephone; // Telefon numarası (+90 555 555 5555 gibi)

    @NotNull(message = "Doğum tarihi boş olamaz")
    @Past(message = "Doğum tarihi gelecekte olamaz")
    private LocalDate birthDate; // Doğum tarihi

    @NotBlank(message = "Doğum yeri boş olamaz")
    @Size(min = 2, max = 100, message = "Doğum yeri 2 ile 100 karakter arasında olmalıdır")
    private String birthPlace; // Doğum yeri

    @NotBlank(message = "Uyruk boş olamaz")
    @Size(min = 2, max = 50, message = "Uyruk bilgisi 2 ile 50 karakter arasında olmalıdır")
    private String nationality; // Uyruk

    @NotBlank(message = "Baba adı boş olamaz")
    @Size(min = 2, max = 50, message = "Baba adı 2 ile 50 karakter arasında olmalıdır")
    private String fatherName; // Baba adı

    @NotBlank(message = "Anne adı boş olamaz")
    @Size(min = 2, max = 50, message = "Anne adı 2 ile 50 karakter arasında olmalıdır")
    private String motherName; // Anne adı


    private UserRole userRole;










}
