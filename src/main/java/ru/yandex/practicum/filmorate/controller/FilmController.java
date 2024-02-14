package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {

    final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/find-all")
    public List<Film> findAll() {
        return filmService.getAllFilm();
    }

    @PostMapping(value = "/create")
    public Film create(@Valid @RequestBody Film film) {
        return filmService.createFilm(film);
    }

    @PutMapping(value = "/update")
    public Film update(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @PostMapping("/add-like/{filmId}/{userId}")
    public Film addLike(@PathVariable int filmId, @PathVariable int userId) {
        return filmService.addLike(filmId, userId);
    }

    @PostMapping("/delete-like/{filmId}/{userId}")
    public Film deleteLike(@PathVariable int filmId, @PathVariable int userId) {
        return filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular?count={count}")
    public List<Film> findPopularFilm(@PathVariable int count) {
        return filmService.getPopularFilms(count);
    }
}
