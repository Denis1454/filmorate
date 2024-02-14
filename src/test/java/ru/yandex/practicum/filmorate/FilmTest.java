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
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FilmTest {

    @Autowired
    ObjectMapper mapper;
    @Autowired
    FilmService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldNotAddFilmTestWithWrongDate() throws ValidationException {
        Film film = Film.builder()
                .name("Титаник")
                .description("Бывает")
                .duration(Duration.of(80, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1800, 10, 29))
                .build();

        assertThrows(ValidationException.class, () -> service.createFilm(film));
    }

    @Test
    void shouldNotAddFilmTestWithNegativeDuration() throws ValidationException {
        Film film = Film.builder()
                .name("Титаник")
                .description("Бывает")
                .duration(Duration.of(-1, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1800, 10, 29))
                .build();

        assertThrows(ValidationException.class, () -> service.createFilm(film));
    }

    @Test
    void shouldNotAddFilmTestWithEmptyDescription() throws Exception {
        Film film = Film.builder()
                .name("Титаник")
                .description("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD" +
                        "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD" +
                        "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD ")
                .duration(Duration.of(80, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1900, 10, 29))
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/film/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(film));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }

    @Test
    void shouldNotAddFilmTestWithEmptyName() throws Exception {
        Film film = Film.builder()
                .description(" ")
                .duration(Duration.of(80, ChronoUnit.MINUTES))
                .releaseDate(LocalDate.of(1900, 10, 29))
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/film/create")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(film));

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(400));
    }


}