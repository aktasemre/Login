package com.login.payload.response.abstracts;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class BaseUserResponse {
    private Long id;
    private String first_name;
    private String last_name;
    private String email;

    private LocalDateTime create_at;
    private LocalDateTime update_at;
    private String role;

    private String telephone; // Telefon numarası (+90 555 555 5555 gibi)

    private LocalDate birthDate; // Doğum tarihi


    private String birthPlace; // Doğum yeri


    private String nationality; // Uyruk


    private String fatherName; // Baba adı


    private String motherName; // Anne adı



}
