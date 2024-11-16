package com.decerto.typer.prediction;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Participant {
    String name;
    int points;


    public void addPoints(int points) {
        this.points += points;
    }
}
