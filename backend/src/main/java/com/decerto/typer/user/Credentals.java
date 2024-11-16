package com.decerto.typer.user;

import lombok.*;
@Value
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Credentals {

    String login;

    String password;
}
