package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    UserService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldAddUser() throws ValidationException {
        User user = User.builder()
                .email("Denis261094@mail.ru")
                .login("Demon")
                .name("Denis")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();
        assertNotNull(service.createUser(user));
    }

    @Test
    void shouldSetNameWhenNameIsNull() throws ValidationException {
        User user = User.builder()
                .email("Denis261094@mail.ru")
                .login("Demon")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();

        User user1 = service.createUser(user);

        assertEquals(user.getLogin(), user1.getName());
    }

    @Test
    void shouldNotAddUserTestWhenBirthDayInFuture() throws ValidationException {
        User user = User.builder()
                .email("Denis261094@mail.ru")
                .login("Demon")
                .birthday(LocalDate.of(2100, 10, 26))
                .build();

        assertThrows(ValidationException.class, () -> service.createUser(user));
    }

    @Test
    void shouldNotAddUserTestWhenNotValidEmail() throws Exception {
        User user = User.builder()
                .email("Denis261094mail.ru")
                .login("Demon")
                .name("Denis")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    void shouldNotAddUserTestWhenBlankEmail() throws Exception {
        User user = User.builder()
                .email(" ")
                .login("Demon")
                .name("Denis")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    void shouldNotAddUserTestWhenNullEmail() throws Exception {
        User user = User.builder()
                .login("Demon")
                .name("Denis")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(user));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }
}