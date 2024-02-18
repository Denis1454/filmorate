package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.type.Genre;
import ru.yandex.practicum.filmorate.model.type.Rating;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
public class Film {
    int id;
    @NotBlank(message = "Имя не должно быть пустым!")
    String name;
    @Size(max = 200, message = "Описание должно быть не больше 200 символов")
    String description;
    LocalDate releaseDate;
    Duration duration;
    Set<Integer> like;
    Genre filmGenre;
    Rating rating;
}

