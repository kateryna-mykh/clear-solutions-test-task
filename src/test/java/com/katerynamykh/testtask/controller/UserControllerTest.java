package com.katerynamykh.testtask.controller;

import static org.mockito.Mockito.when;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.katerynamykh.testtask.dto.CreateUserDto;
import com.katerynamykh.testtask.dto.FullUserDto;
import com.katerynamykh.testtask.dto.PartialUpdateUserDto;
import com.katerynamykh.testtask.mapper.UserMapper;
import com.katerynamykh.testtask.model.User;
import com.katerynamykh.testtask.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(UserController.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserRepository repository;
    @MockBean
    UserMapper mapper;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void create_validBody_ReturnStatusCreated() throws Exception {
        CreateUserDto inputDto = new CreateUserDto("test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        User model = new User(1L, "test@gmail.com", "name", "lastName", LocalDate.of(2000, 01, 01),
                null, null);
        FullUserDto expected = new FullUserDto(1L, "test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        when(mapper.toModel(inputDto)).thenReturn(model);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toDto(model)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void create_AgeLessThen18_ReturnStatusBad() throws Exception {
        CreateUserDto inputDto = new CreateUserDto("test@gmail.com", "name", "lastName",
                LocalDate.of(2006, 01, 01), null, null);

        mvc.perform(MockMvcRequestBuilders.post("/users")
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.status().reason("User age is less then 18"));
    }

    @Test
    void partialUpdate_ValidParams_Ok() throws Exception {
        PartialUpdateUserDto inputDto = new PartialUpdateUserDto("test@gmail.com", "name",
                "lastName", null, null);
        User model = new User(1L, "test@gmail.com", "name", "lastName", LocalDate.of(2000, 01, 01),
                null, null);
        FullUserDto expected = new FullUserDto(1L, "test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(mapper.partialUpdate(model, inputDto)).thenReturn(model);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toDto(model)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.patch("/users/{id}", 1L)
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void partialUpdate_NotExistingId_ReturnStatusNotFound() throws Exception {
        PartialUpdateUserDto inputDto = new PartialUpdateUserDto("test@gmail.com", "name",
                "lastName", null, null);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.patch("/users/{id}", 100L)
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found by id 100"));
    }

    @Test
    void update_ValidParams_Ok() throws Exception {
        CreateUserDto inputDto = new CreateUserDto("test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        User model = new User(1L, "test@gmail.com", "name", "lastName", LocalDate.of(2000, 01, 01),
                null, null);
        FullUserDto expected = new FullUserDto(1L, "test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        when(repository.findById(1L)).thenReturn(Optional.of(model));
        when(mapper.toModel(inputDto)).thenReturn(model);
        when(repository.save(model)).thenReturn(model);
        when(mapper.toDto(model)).thenReturn(expected);

        mvc.perform(MockMvcRequestBuilders.put("/users/{id}", 1L)
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void update_NotExistingId_ReturnStatusNotFound() throws Exception {
        CreateUserDto inputDto = new CreateUserDto("test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        when(repository.findById(1L)).thenReturn(Optional.empty());

        mvc.perform(MockMvcRequestBuilders.put("/users/{id}", 100L)
                .content(objectMapper.writeValueAsString(inputDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.status().reason("User not found by id 100"));
    }

    @Test
    void delete_ExistingId_ReturStatusNoContent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void searchByBirthRange_ValidRange_Ok() throws Exception {
        LocalDate from = LocalDate.of(1980, 01, 01);
        LocalDate to = LocalDate.of(2006, 01, 01);
        User model = new User(1L, "test@gmail.com", "name", "lastName", LocalDate.of(2000, 01, 01),
                null, null);
        FullUserDto expected = new FullUserDto(1L, "test@gmail.com", "name", "lastName",
                LocalDate.of(2000, 01, 01), null, null);
        when(repository.getAllByBirthDateRange(from, to)).thenReturn(List.of(model));
        when(mapper.toDto(model)).thenReturn(expected);
        mvc.perform(MockMvcRequestBuilders
                .get(String.format("/users/by-birth-range?from=%s&to=%s", from, to))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void searchByBirthRange_FromGraterThenTo_ReturnStatusBad() throws Exception {
        LocalDate from = LocalDate.of(2000, 01, 02);
        LocalDate to = LocalDate.of(2000, 01, 01);
        mvc.perform(MockMvcRequestBuilders
                .get(String.format("/users/by-birth-range?from=%s&to=%s", from, to))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.status().reason("Invalid birth range"));
    }
}
