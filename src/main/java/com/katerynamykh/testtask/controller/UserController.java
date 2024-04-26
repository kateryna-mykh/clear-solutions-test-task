package com.katerynamykh.testtask.controller;

import com.katerynamykh.testtask.dto.CreateUserDto;
import com.katerynamykh.testtask.dto.FullUserDto;
import com.katerynamykh.testtask.dto.PartialUpdateUserDto;
import com.katerynamykh.testtask.mapper.UserMapper;
import com.katerynamykh.testtask.model.User;
import com.katerynamykh.testtask.repository.UserRepository;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository repository;
    private final UserMapper mapper;
    @Value(value = "${limit.age}")
    private Integer ageLimit;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    FullUserDto create(@Valid @RequestBody CreateUserDto userDto) {
        ageValidation(userDto);
        return mapper.toDto(repository.save(mapper.toModel(userDto)));
    }

    @PatchMapping("/{id}")
    FullUserDto partialUpdate(@PathVariable Long id,
            @Valid @RequestBody PartialUpdateUserDto userDto) {
        User foundUser = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User not found by id " + id));
        return mapper.toDto(repository.save(mapper.partialUpdate(foundUser, userDto)));
    }

    @PutMapping("/{id}")
    FullUserDto update(@PathVariable Long id, @Valid @RequestBody CreateUserDto userDto) {
        repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "User not found by id " + id));
        ageValidation(userDto);
        User model = mapper.toModel(userDto);
        model.setId(id);
        return mapper.toDto(repository.save(model));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }

    @GetMapping("/by-birth-range")
    List<FullUserDto> searchByBirthRange(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        if (from.isAfter(to))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid birth range");
        return repository.getAllByBirthDateRange(from, to).stream().map(mapper::toDto).toList();
    }

    private boolean ageValidation(CreateUserDto userDto) {
        int userYears = Period.between(userDto.birthDate(), LocalDate.now()).getYears();
        if (userYears <= ageLimit) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User age is less then " + ageLimit);
        }
        return true;
    }
}
