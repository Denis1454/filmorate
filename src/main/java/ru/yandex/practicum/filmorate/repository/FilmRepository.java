package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

@Repository
public class FilmRepository {
    private static int countId = 1;
    private final HashMap<Integer, Film> films = new HashMap<>();

    public Film findById(int id) {
        if (films.containsKey(id)) {
            return films.get(id);
        }
        throw new FilmNotFoundException("Фильма с таким Id не существует");
    }

    public Film createFilm(Film film) {
        film.setId(countId);
        films.put(countId, film);
        countId++;
        return film;
    }

    public List<Film> getPopularFilms(int count) {
        List<Film> filmList = films.values().stream().toList();
        return filmList.stream()
                .sorted(Comparator.comparingInt(film -> film.getLike().size()))
                .limit(count)
                .toList();
    }

    public List<Film> getAllFilm() {
        return films.values().stream().toList();
    }

    public Film updateFilm(Film film) {
        films.replace(film.getId(), film);
        return films.get(film.getId());
    }
}
