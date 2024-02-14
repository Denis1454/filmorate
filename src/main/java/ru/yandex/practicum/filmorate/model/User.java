package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class User {
    int id;
    @NotBlank(message = "Email не должно быть пустым!")
    @Email(message = "Email должен быть валидным")
    String email;
    @NotBlank(message = "Логин не должен быть пустым!")
    String login;

    String name;
    LocalDate birthday;
    Set<Integer> friends;
}
