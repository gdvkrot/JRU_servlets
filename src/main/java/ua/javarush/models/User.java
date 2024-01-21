package ua.javarush.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
/*
@RequiredArgsConstructor
@EqualsAndHashCode
@Setter
@Getter
@ToString
*/
@Data
public class User {
    private UUID id = UUID.randomUUID();
    private String name;
    private String password;
    private LocalDate birthDate;
    private String email;

    public User(String name, String password, LocalDate dateOfBirth, String email) {
        this.name = name;
        this.password = password;
        this.birthDate = dateOfBirth;
        this.email = email;
    }
}
