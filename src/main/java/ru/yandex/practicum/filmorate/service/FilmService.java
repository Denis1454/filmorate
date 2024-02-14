package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    Film createFilm(Film film);

    List<Film> getAllFilm();

    Film updateFilm(Film film);

    Film addLike(int filmId,int userId);

    Film deleteLike(int filmId,int userId);

    List<Film> getPopularFilms(int count);
}
