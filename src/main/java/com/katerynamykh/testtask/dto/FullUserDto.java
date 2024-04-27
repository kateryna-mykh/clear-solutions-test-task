package com.katerynamykh.testtask.dto;

import java.time.LocalDate;

public record FullUserDto(
        Long id,
        String email,
        String firstName, 
        String lastName,
        LocalDate birthDate,
        String address,
        String phoneNumber) {

}
