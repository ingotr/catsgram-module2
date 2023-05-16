package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<String, User> users = new HashMap<>();

    @GetMapping
    public List<User> findAll() {
        System.out.println("users.size() = " + users.size());
        users.values();
        return List.copyOf(users.values());
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        try {
            if (!user.getEmail().isBlank()) {
                users.replace(user.getEmail(), user);
            }

        } catch (RuntimeException e) {
            throw new InvalidEmailException("Не указана почта. Укажите и попробуйте снова");
        }
        return user;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        checkNewUserHasEmail(user);
        return user;
    }

    private void checkNewUserHasEmail(User user) {
        if (!user.getEmail().isBlank()) {
            checkUserAlreadyExist(user);
        } else {
            throw new InvalidEmailException("Не указана почта. Укажите и попробуйте снова");
        }
    }

    private void checkUserAlreadyExist(User user) {
        if (!users.containsKey(user.getEmail())) {
            users.put(user.getEmail(), user);
        } else {
            throw new UserAlreadyExistException("Уже есть пользователь с такой почтой.");
        }
    }
}
