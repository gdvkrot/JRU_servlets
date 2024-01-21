package ua.javarush.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j2;
import ua.javarush.models.User;
import ua.javarush.utilities.LocalDateDeserializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Log4j2
public class UserRepositoryImpl implements UserRepository {
    private static ConcurrentHashMap<UUID, User> db = new ConcurrentHashMap<>();

    static {
        final String fileName = "users.json";
        ClassLoader classLoader = UserRepositoryImpl.class.getClassLoader();
        URL resourceUrl = classLoader.getResource(fileName);

        if (resourceUrl == null) {
            log.error(String.format("File not found in resources folder!: %s", fileName));
            throw new RuntimeException(String.format("File not found in resources folder!: %s", fileName));
        }

        byte[] bytes;
        try {
            synchronized (UserRepositoryImpl.class) {
                bytes = Files.readAllBytes(Paths.get(resourceUrl.toURI()));
            }
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
        String jsonContent = new String(bytes);

        Type listType = new TypeToken<List<User>>() {
        }.getType();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        List<User> users = gson.fromJson(jsonContent, listType);

        log.info(String.format("Deserialized Users List in Init method: " + users));

        db = users.stream()
                .collect(Collectors.toConcurrentMap(
                        User::getId,                            // Key mapper: Use age as the key
                        user -> user,                           // Value mapper: Use the Person object as the value
                        (existingValue, newValue) -> newValue,  // Merge function (in case of key collision)
                        ConcurrentHashMap::new                  // Supplier for the target map
                ));
    }

    @Override
    public void add(User user) {
        db.put(user.getId(), user);
        log.info(String.format("Created user with id: %s", user.getId()));
    }

    @Override
    public void delete(UUID id) {
        db.remove(id);
        log.info(String.format("Deleted user with id: %s", id));
    }

    @Override
    public Optional<List<User>> getAll() {
        log.info("Get All users from DB!");
        return Optional.of(new ArrayList<>(db.values()));
    }

    @Override
    public Optional<User> getById(UUID id) {
        log.info(String.format("Getting user with id: %s", id));
        return Optional.ofNullable(db.get(id));
    }
}
