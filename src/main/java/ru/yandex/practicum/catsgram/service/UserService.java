package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    private final Map<String, User> users = new HashMap<>();

    public List<User> findAll() {
        System.out.println("users.size() = " + users.size());
        users.values();
        return List.copyOf(users.values());
    }

    public User update(@RequestBody User user) {
        try {
            if (!user.getEmail().isBlank()) {
                users.replace(user.getEmail(), user);
            }

        } catch (RuntimeException e) {
            throw new InvalidEmailException("Не указана почта. Укажите и попробуйте снова");
        }
        return user;
    }

    public User create(@RequestBody User user) {
        checkNewUserHasEmail(user);
        return user;
    }

    public Optional<User> findByEmail(@PathVariable String email) {
        if (users.containsKey(email)) {
            return Optional.ofNullable(users.get(email));
        }
        return Optional.empty();
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
