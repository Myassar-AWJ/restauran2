package com.restaurant.restaurantdemo.application.dto.auth;

import com.restaurant.restaurantdemo.domain.user.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    @NotBlank(message = "Email is required")
    @Email
    private String email;
    @NotBlank(message = "Password is required")
    private String password;

}
