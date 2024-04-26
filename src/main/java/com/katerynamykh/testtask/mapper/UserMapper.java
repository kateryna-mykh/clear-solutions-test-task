package com.katerynamykh.testtask.mapper;

import com.katerynamykh.testtask.dto.CreateUserDto;
import com.katerynamykh.testtask.dto.FullUserDto;
import com.katerynamykh.testtask.dto.PartialUpdateUserDto;
import com.katerynamykh.testtask.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public FullUserDto toDto(User userModel) {
        if (userModel == null) {
            return null;
        }
        return new FullUserDto(userModel.getId(), userModel.getEmail(), userModel.getFirstName(),
                userModel.getLastName(), userModel.getBirthDate(), userModel.getAddress(),
                userModel.getPhoneNumber());
    }

    public User toModel(CreateUserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User model = new User();
        model.setEmail(userDto.email());
        model.setFirstName(userDto.firstName());
        model.setLastName(userDto.lastName());
        model.setBirthDate(userDto.birthDate());
        model.setAddress(userDto.address());
        model.setPhoneNumber(userDto.phoneNumber());
        return model;
    }

    public User partialUpdate(User model, PartialUpdateUserDto userDto) {
        if (userDto == null)
            return null;
        if (userDto.email() != null) {
            model.setEmail(userDto.email());
        }
        if (userDto.firstName() != null) {
            model.setFirstName(userDto.firstName());
        }
        if (userDto.lastName() != null) {
            model.setLastName(userDto.lastName());
        }
        if (userDto.address() != null) {
            model.setAddress(userDto.address());
        }
        if (userDto.phoneNumber() != null) {
            model.setPhoneNumber(userDto.phoneNumber());
        }
        return model;
    }
}
