package com.decerto.typer.prediction;

import com.decerto.typer.competition.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, Long> {
    List<Prediction> findByMatch(Match match);
}

