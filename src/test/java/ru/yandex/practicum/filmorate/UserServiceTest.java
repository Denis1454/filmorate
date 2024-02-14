package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;
    User user;

    @BeforeEach
    public void createUser(){
        user = User.builder()
                .email("Denis261094@mail.ru")
                .login("Demon")
                .name("Denis")
                .birthday(LocalDate.of(1994, 10, 26))
                .build();
    }

    @Test
    void shouldAddUser() throws ValidationException {
        assertNotNull(service.createUser(user));
    }

    @Test
    void shouldGetAllUser() throws ValidationException {
        User user1 = User.builder()
                .email("Denis261094@mail.ru")
                .login("ff")
                .name("Deniss")
                .birthday(LocalDate.of(1990, 10, 26))
                .build();

        List<User> users = List.of(user, user1);

        service.createUser(user);
        service.createUser(user1);

        List<User> allUser = service.getAllUser();

        assertThat(users, is(allUser));
    }

    @Test
    void shouldUpdateUser() throws ValidationException {
        User user1 = User.builder()
                .id(1)
                .email("Denis261094@mail.ru")
                .login("ff")
                .name("Deniss")
                .birthday(LocalDate.of(1990, 10, 26))
                .build();

        service.createUser(user);
        User user2 = service.updateUser(user1);

        assertEquals(user1, user2);
    }

    @Test
    void shouldAddFriendsToUser() throws ValidationException {
        User user1 = service.createUser(user);
        User user2 = service.createUser(user);
        service.addFriends(user1.getId(),user2.getId());

        assertEquals(1, user.getFriends().size());
    }

    @Test
    void shouldDeleteFriendsToUser() throws ValidationException {
        User user1 = service.createUser(user);
        User user2 = service.createUser(user);
        service.addFriends(user1.getId(),user2.getId());
        service.deleteFriends(user1.getId(),user2.getId());

        assertEquals(0, user.getFriends().size());
    }

    @Test
    void shouldGetAllMutualFriends() throws ValidationException {
        User user1 = service.createUser(user);
        User user2 = service.createUser(user);
        User user3 = service.createUser(user);

        service.addFriends(user1.getId(),user3.getId());
        service.addFriends(user2.getId(),user3.getId());

        List<Integer> expected = List.of(3);

        List<Integer> allMutualFriends = service.getAllMutualFriends(1, 2);

        assertThat(expected, is(allMutualFriends));
    }
}
