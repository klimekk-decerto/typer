package com.decerto.typer.competition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RankingService {
    private final UserRankingRepository rankingRepository;

    public void processPredictions(List<Prediction> predictions, Match match) {
        for (Prediction prediction : predictions) {
            UserRanking ranking = rankingRepository.findByUsername(prediction.getUsername())
                    .orElseGet(() -> {
                        UserRanking newRanking = new UserRanking();
                        newRanking.setUsername(prediction.getUsername());
                        return newRanking;
                    });

            if (match.getScoreA().equals(prediction.getPredictedScoreA()) &&
                    match.getScoreB().equals(prediction.getPredictedScoreB())) {
                ranking.addPoints(3); // Dokładny wynik
            } else if (match.getScoreA() > match.getScoreB() && prediction.getPredictedScoreA() > prediction.getPredictedScoreB() ||
                    match.getScoreA() < match.getScoreB() && prediction.getPredictedScoreA() < prediction.getPredictedScoreB() ||
                    match.getScoreA().equals(match.getScoreB()) && prediction.isDraw()) {
                ranking.addPoints(1); // Trafiona drużyna wygrana lub remis
            }

            rankingRepository.save(ranking);
        }
    }

    public List<UserRanking> getRanking() {
        return rankingRepository.findAllByOrderByPointsDesc();
    }
}

