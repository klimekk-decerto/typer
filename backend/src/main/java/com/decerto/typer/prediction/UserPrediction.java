package com.decerto.typer.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class UserPrediction {

    private Map<Long, Prediction> predictionsPerMatch;


    public UserPrediction() {
        predictionsPerMatch = new HashMap<>();
    }

    public void addPredication(Long matchId, int scoreA, int scoreB) {
        Prediction matchpredication = Optional.ofNullable(predictionsPerMatch.get(matchId))
                .orElseGet(() -> {
                    Prediction predication = new Prediction();
                    predictionsPerMatch.put(matchId, predication);
                    return predication;
                });
        matchpredication.setFirstTeamScore(scoreA);
        matchpredication.setSecondTeamScore(scoreB);
    }

    public int settlePredication(Long matchId, int scoreA, int scoreB) {
        return Optional.ofNullable(predictionsPerMatch.get(matchId))
                .map(predication -> predication.settle(scoreA, scoreB))
                .orElse(0);

    }
}
