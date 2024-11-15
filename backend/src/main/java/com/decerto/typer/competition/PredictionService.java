package com.decerto.typer.competition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PredictionService {
    private final PredictionRepository predictionRepository;
    private final MatchRepository matchRepository;

    public Prediction placePrediction(String username, Long matchId, Integer scoreA, Integer scoreB, boolean isDraw) {
        Match match = matchRepository.findById(matchId).orElseThrow();

        if (match.getMatchDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot predict for a match that has already started.");
        }

        Prediction prediction = new Prediction();
        prediction.setUsername(username);
        prediction.setMatch(match);
        prediction.setPredictedScoreA(scoreA);
        prediction.setPredictedScoreB(scoreB);
        prediction.setDraw(isDraw);

        return predictionRepository.save(prediction);
    }
}

