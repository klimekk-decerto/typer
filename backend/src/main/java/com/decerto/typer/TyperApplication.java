package com.decerto.typer;

import com.decerto.typer.user.Role;
import com.decerto.typer.user.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class TyperApplication {
	private final UserService userService;

	@PostConstruct
	public void registerUsers() {
		userService.registerUser("user_1", "user_1", Role.USER);
		userService.registerUser("user_2", "user_2", Role.USER);
		userService.registerUser("admin", "admin", Role.ADMIN);
	}

	public static void main(String[] args) {
		SpringApplication.run(TyperApplication.class, args);
	}

}
