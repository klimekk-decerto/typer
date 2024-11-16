package com.decerto.typer.user;


import lombok.SneakyThrows;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String login, String password, Role role) {
        if (userRepository.findByLogin(login).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setLogin(login);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role != null ? role : Role.USER);  // Domyślnie przypisuje rolę USER
        return userRepository.save(user);
    }

    @SneakyThrows
    public User login(Credentals credentals) {
        Optional<User> loggedUser = userRepository.findByLogin(credentals.getLogin())
                .filter(user -> passwordEncoder.matches(credentals.getPassword(), user.getPassword()));
        if (loggedUser.isEmpty()) {
            throw new AuthenticationException("not found user");
        }
        return loggedUser.get();
    }
}
