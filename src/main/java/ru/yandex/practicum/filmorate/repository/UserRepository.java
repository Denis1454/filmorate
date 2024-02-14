package ru.yandex.practicum.filmorate.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.List;

@Repository
public class UserRepository {

    private static int countId = 1;

    private final HashMap<Integer, User> users = new HashMap<>();

    public User findById(int id) {
        if (users.containsKey(id)) {
            return users.get(id);
        }
        throw new UserNotFoundException("Пользователя с таким Id не существует");
    }

    public User createUser(User user) {
        user.setId(countId);
        users.put(countId, user);
        countId++;
        return user;
    }

    public List<User> getAllUser() {
        return users.values().stream().toList();
    }

    public User updateUser(User user) {
        users.replace(user.getId(), user);
        return users.get(user.getId());
    }
}
