package com.decerto.typer.prediction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PredictionsRepository extends JpaRepository<Predictions, Long> {
    Predictions findByCompetitionId(Long competitionId);
}
