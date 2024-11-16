package com.decerto.typer.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class FootballPredictionOdd {

    BigDecimal home;
    BigDecimal draw;
    BigDecimal away;

}