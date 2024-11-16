package com.decerto.typer.application.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ScoreRequest {

    int scoreA;
    int scoreB;

}
