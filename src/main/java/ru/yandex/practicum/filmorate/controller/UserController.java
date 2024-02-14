package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/find-all")
    public List<User> findAll() {
        return userService.getAllUser();
    }

    @PostMapping("/create")
    public User create(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update")
    public User update(@Valid @RequestBody User user) {
        return userService.updateUser(user);
    }

    @PostMapping("/add-friend/{userId}/{friendId}")
    public User addFriend(@PathVariable int userId, @PathVariable int friendId) {
        return userService.addFriends(userId, friendId);
    }

    @PostMapping("/delete-friend/{userId}/{friendId}")
    public User deleteFriend(@PathVariable int userId, @PathVariable int friendId) {
        return userService.deleteFriends(userId, friendId);
    }

    @GetMapping("/mutual-friend/{userId}/{friendId}")
    public List<Integer> getAllMutualFriends(@PathVariable int userId, @PathVariable int friendId) {
        return userService.getAllMutualFriends(userId, friendId);
    }
}
