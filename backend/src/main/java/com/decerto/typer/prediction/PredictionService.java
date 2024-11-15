package com.decerto.typer.prediction;

import com.decerto.typer.competition.Match;
import com.decerto.typer.competition.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        prediction.setIsDraw(isDraw);

        return predictionRepository.save(prediction);
    }
}

