package com.katerynamykh.testtask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

public record CreateUserDto(
        @NotBlank(message = "Email is required")
        @Email(regexp = "^(?=.{1,64}@)(\\.[\\w-]+?)?@[^-][\\w-]+?(\\.[\\w-]+)*?(\\.[A-Za-z]{2,})$", message = "Email should be valid") 
        String email,
        @NotBlank(message = "First name is required") 
        String firstName, 
        @NotBlank(message = "Last Name is required") 
        String lastName,
        @NotNull(message = "Birth date is required")
        @DateTimeFormat(pattern="yyyy-MM-dd")
        @Past(message = "Date of birth must be less than today")
        LocalDate birthDate, 
        String address,
        @Pattern(regexp = "\\+??\\d{12}|\\d{10}", message = "Phone number should be valid")
        String phoneNumber) {

}
