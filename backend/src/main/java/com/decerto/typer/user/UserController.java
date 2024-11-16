package com.decerto.typer.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Rejestracja użytkownika z rolą
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestParam String login, @RequestParam String password,
                         @RequestParam(required = false) Role role) {
        return userService.registerUser(login, password, role);
    }

    @PostMapping("/login")
    public ResponseEntity<Role> login(@RequestBody Credentals credentals) {
        User user = userService.login(credentals);
        return ResponseEntity.ok(user.getRole());
    }

}

