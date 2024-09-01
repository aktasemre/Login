package com.login.payload.response.abstracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

}
