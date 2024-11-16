package com.decerto.typer.integration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class FootballPredictionItem {

    String prediction;
    long id;
    String start_date;
    String last_update_at;
    String home_team;
    String away_team;
    LinkedHashMap<String, Double> odds;

    public FootballPredictionOdd calculate() {
        double home = odds.get("1");
        double draw = odds.get("X");
        double away = odds.get("2");
        double sum = home + draw + away;
        return FootballPredictionOdd.builder()
                .home(BigDecimal.valueOf(home / sum).setScale(2, RoundingMode.CEILING).multiply(new BigDecimal("100")))
                .draw(BigDecimal.valueOf(draw / sum).setScale(2, RoundingMode.CEILING).multiply(new BigDecimal("100")))
                .away(BigDecimal.valueOf(away / sum).setScale(2, RoundingMode.CEILING).multiply(new BigDecimal("100")))
                .build();
    }
}
