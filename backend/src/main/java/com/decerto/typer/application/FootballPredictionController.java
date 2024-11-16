package com.decerto.typer.application;

import com.decerto.typer.integration.FootballPrediction;
import com.decerto.typer.integration.FootballPredictionItem;
import com.decerto.typer.integration.FootballPredictionOdd;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/prediction")
@RequiredArgsConstructor
public class FootballPredictionController {

    private final FootballPrediction footballPrediction;

    @GetMapping
    public FootballPredictionOdd getPredictions(@RequestParam String team) {
        return footballPrediction.getPredictions(team).map(FootballPredictionItem::calculate).orElse(null);
    }

}
