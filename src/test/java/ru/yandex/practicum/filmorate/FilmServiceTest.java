package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class FilmServiceTest {

    @Autowired
    private FilmService service;
    Film film;

    User user;

    @BeforeEach
    public void createFilm(){
        film = Film.builder()
                .name("Титаник")
                .description("Бывает")
                .duration(Duration.of(80, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1900, 10, 29))
                .like(new HashSet<>())
                .build();

        user = User.builder()
                .email("Denis261094@mail.ru")
                .login("Demon")
                .name("Denis")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();
    }

    @Test
    void shouldAddFilm() throws ValidationException {
        assertNotNull(service.createFilm(film));
    }

    @Test
    void shouldGetAllFilm() throws ValidationException {
        Film film1 = Film.builder()
                .name("Аватар")
                .description("Ага")
                .duration(Duration.of(70, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1900, 10, 29))
                .build();

        List<Film> films = List.of(film, film1);

        service.createFilm(film);
        service.createFilm(film1);

        List<Film> allFilm = service.getAllFilm();

        assertThat(films, is(allFilm));
    }

    @Test
    void shouldUpdateFilm() throws ValidationException {
        Film film1 = Film.builder()
                .id(1)
                .name("Аватар")
                .description("Ага")
                .duration(Duration.of(70, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1900, 10, 29))
                .build();

        service.createFilm(film);
        Film film2 = service.updateFilm(film1);

        assertEquals(film1, film2);
    }

    @Test
    void shouldAddLikeToFilm() throws ValidationException {
        Film film1 = service.createFilm(film);
        service.addLike(film1.getId(),user.getId());

        assertEquals(1,film.getLike().size());
    }

    @Test
    void shouldDeleteLikeToFilm() throws ValidationException {
        Film film1 = service.createFilm(film);
        service.addLike(film1.getId(),user.getId());
        service.deleteLike(film1.getId(),user.getId());

        assertEquals(0,film.getLike().size());
    }

    @Test
    void shouldGetPopularFilm() throws ValidationException {
        Film film2 = Film.builder()
                .id(1)
                .name("Аватар")
                .description("Ага")
                .duration(Duration.of(70, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1900, 10, 29))
                .like(new HashSet<>())
                .build();

        Film film3 = service.createFilm(film2);
        Film film1 = service.createFilm(film);
        service.addLike(film1.getId(),user.getId());
        List<Film> popularFilms = service.getPopularFilms(10);

        List<Film> film11 = List.of(film1, film3);
        List<Film> list = film11.stream()
                .sorted(Comparator.comparingInt(film -> film.getLike().size()))
                .toList();

        assertThat(list, is(popularFilms));
    }
}
