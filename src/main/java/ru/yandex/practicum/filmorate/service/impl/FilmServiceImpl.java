package ru.yandex.practicum.filmorate.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.repository.FilmRepository;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.time.LocalDate;
import java.util.List;

@Service
public class FilmServiceImpl implements FilmService {

    private static final Logger log = LoggerFactory.getLogger(FilmServiceImpl.class);

    private final FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public Film createFilm(Film film) {
        log.info("будет сохранен " + film);
        checkFilm(film);
        return filmRepository.createFilm(film);
    }

    @Override
    public List<Film> getAllFilm() {
        List<Film> allFilm = filmRepository.getAllFilm();
        log.info("Текущее количество фильмов: " + allFilm.size());
        return allFilm;
    }

    @Override
    public Film updateFilm(Film film) {
        log.info("Будет изменен " + film);
        return filmRepository.updateFilm(film);
    }

    @Override
    public Film addLike(int filmId,int userId) {
        Film film = filmRepository.findById(filmId);
        film.getLike().add(userId);
        filmRepository.updateFilm(film);
        return film;
    }

    @Override
    public Film deleteLike(int filmId,int userId) {
        Film film = filmRepository.findById(filmId);
        film.getLike().remove(userId);
        filmRepository.updateFilm(film);
        return film;
    }

    @Override
    public List<Film> getPopularFilms(int count) {
        return filmRepository.getPopularFilms(count);
    }

    private void checkFilm (Film film) {
        LocalDate minStartDate = LocalDate.of(1895, 12, 28);
        if (film.getReleaseDate().isBefore(minStartDate)) {
            log.info("Ошибка года выпуска " + film.getReleaseDate());
            throw new ValidationException("Фильм не соответствует году выпуска");
        }
        if (film.getDuration().isNegative()) {
            log.info("Ошибка продолжительности фильма " + film.getDuration());
            throw new ValidationException("Продолжительность фильма не может быть отрицательной");
        }
    }
}
