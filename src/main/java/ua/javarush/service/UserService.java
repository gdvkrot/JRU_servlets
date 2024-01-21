package ua.javarush.service;

import lombok.extern.log4j.Log4j2;
import ua.javarush.exceptions.InvalidParamException;
import ua.javarush.exceptions.UserNotFoundException;
import ua.javarush.models.User;
import ua.javarush.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Log4j2
public class UserService {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        log.info("User received: " + user);
        validation(user);
        userRepository.add(user);
    }

    public void deleteUser(UUID id) {
        userRepository.getById(id).orElseThrow(() -> new UserNotFoundException(
                String.format("User with id = %s not found!", id)
        ));
        userRepository.delete(id);
    }

    public List<User> getAllUsers() {
        log.info("Get all users!");
        Optional<List<User>> result = userRepository.getAll();
        return Collections.unmodifiableList(result.orElse(new ArrayList<>()));
    }

    private void validation(User user) {
        if (Objects.isNull(user)) {
            log.warn("User is null");
            throw new InvalidParamException("User is null");
        }
        if (user.getName().isEmpty()) {
            log.warn("User firstName is empty");
            throw new InvalidParamException("User firstName is empty");
        }
        if (user.getBirthDate().getYear() <= 0) {
            log.warn("User age is 0 or less than zero!");
            throw new InvalidParamException("User age is 0 or less than zero!");
        }
        if (user.getEmail().isEmpty()) {
            log.warn("User email is empty");
            throw new InvalidParamException("User email is empty");
        }
        if (!VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail()).matches()) {
            log.warn("User email is invalid");
            throw new InvalidParamException("User email is invalid");
        }
    }
}
