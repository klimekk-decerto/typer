package com.decerto.typer.integration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FootballPredictionData {

    FootballPredictionItem[] data;

}
