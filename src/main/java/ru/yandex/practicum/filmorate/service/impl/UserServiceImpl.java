package ru.yandex.practicum.filmorate.service.impl;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.repository.UserRepository;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(@Valid User user) {
        log.info("Будет сохранен " + user);
        checkValidBirthday(user.getBirthday());
        setNameIfNull(user);
        user.setFriends(new HashSet<>());
        return userRepository.createUser(user);
    }

    @Override
    public List<User> getAllUser() {
        List<User> allUser = userRepository.getAllUser();
        log.info("Текущее количество пользователей: " + allUser.size());
        return allUser;
    }

    @Override
    public User updateUser(User user) {
        log.info("Будет изменен " + user);
        return userRepository.updateUser(user);
    }

    @Override
    public User addFriends(int userId, int friendId) {
        User friend = userRepository.findById(friendId);
        User user = userRepository.findById(userId);
        user.getFriends().add(friendId);
        friend.getFriends().add(user.getId());
        userRepository.updateUser(friend);
        userRepository.updateUser(user);
        return user;
    }

    @Override
    public User deleteFriends(int userId, int friendId) {
        User user = userRepository.findById(userId);
        User friend = userRepository.findById(friendId);
        user.getFriends().remove(friendId);
        friend.getFriends().remove(user.getId());
        return user;
    }

    @Override
    public List<Integer> getAllMutualFriends(int userId1, int userId2) {
        Set<Integer> users = new HashSet<>();
        User user1 = userRepository.findById(userId1);
        User user2 = userRepository.findById(userId2);
        Set<Integer> friends1 = user1.getFriends();
        Set<Integer> friends2 = user2.getFriends();
        for (Integer id1 : friends1) {
            for (Integer id2 : friends2) {
                if (id1 == id2) {
                    users.add(id2);
                }
            }
        }
        return users.stream().toList();
    }

    private void checkValidBirthday(LocalDate birthday) {
        LocalDate localDate = LocalDate.now();
        if (birthday.isAfter(localDate)) {
            throw new ValidationException("Нельзя создать аккаунт с датой рождения в будущем");
        }
    }

    private void setNameIfNull(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }
}
