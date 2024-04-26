package com.katerynamykh.testtask.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

public record PartialUpdateUserDto(
        @Email(regexp = "^(?=.{1,64}@)(\\.[\\w-]+?)?@[^-][\\w-]+?(\\.[\\w-]+)*?(\\.[A-Za-z]{2,})$", message = "Email should be valid") 
        String email,
        String firstName,  
        String lastName,
        String address,
        @Pattern(regexp = "\\+??\\d{12}|\\d{10}", message = "Phone number should be valid")
        String phoneNumber) {

}
