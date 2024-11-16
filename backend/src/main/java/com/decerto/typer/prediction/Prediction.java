package com.decerto.typer.prediction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Prediction {
    int firstTeamScore;
    int secondTeamScore;


    public int settle(int scoreA, int scoreB) {
        if (firstTeamScore == scoreA && secondTeamScore == scoreB) {
            return 3;
        }

        boolean firstTeamWon = scoreA > scoreB;
        boolean secondTeamWon = scoreB > scoreA;
        boolean draw = scoreA == scoreB;

        if (
                (firstTeamWon && firstTeamScore > secondTeamScore) ||
                        (secondTeamWon && secondTeamScore > firstTeamScore) ||
                        (draw && firstTeamScore == secondTeamScore)

        ) {
            return 1;
        }

        return 0;
    }
}
