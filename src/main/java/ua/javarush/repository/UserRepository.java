package ua.javarush.repository;

import ua.javarush.models.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    void add(User user);

    void delete(UUID id);

    Optional<List<User>> getAll();

    Optional<User> getById(UUID id);
}
