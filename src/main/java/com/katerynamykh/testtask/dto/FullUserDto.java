package com.katerynamykh.testtask.dto;

import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;

public record FullUserDto(
        Long id,
        String email,
        String firstName, 
        String lastName,
        LocalDate birthDate,
        String address,
        @Pattern(regexp = "\\+??\\d{12}|\\d{10}", message = "Phone number should be valid")
        String phoneNumber) {

}
