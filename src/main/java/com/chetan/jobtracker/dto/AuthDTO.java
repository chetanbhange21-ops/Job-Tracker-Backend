package com.chetan.jobtracker.dto;

import com.chetan.jobtracker.model.JobApplication.ApplicationStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

// ─── Auth DTOs ───────────────────────────────────────────────
public class AuthDTO {

    @Data
    public static class RegisterRequest {
        @NotBlank(message = "Name is required")
        private String name;

        @Email(message = "Valid email required")
        @NotBlank(message = "Email is required")
        private String email;

        @NotBlank(message = "Password is required")
        private String password;
    }

    @Data
    public static class LoginRequest {
        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class AuthResponse {
        private String token;
        private String name;
        private String email;

        public AuthResponse(String token, String name, String email) {
            this.token = token;
            this.name = name;
            this.email = email;
        }
    }
}
