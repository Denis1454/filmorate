package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    List<User> getAllUser();

    User updateUser(User user);

    User addFriends(int userId, int friendId);

    User deleteFriends(int userId, int friendId);

    List<Integer> getAllMutualFriends(int id,int friendId);
}
