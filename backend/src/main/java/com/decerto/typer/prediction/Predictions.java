package com.decerto.typer.prediction;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Predictions {

    @GeneratedValue
    @Id
    private Long id;
    private Long competitionId;

    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, UserPrediction> predictionsPerUser;

    @JdbcTypeCode(SqlTypes.JSON)
    private Set<Long> settled;

    @JdbcTypeCode(SqlTypes.JSON)
    private PredicationRank rank;

    public Predictions(Long id) {
        this(null, id, new HashMap<>(), new HashSet<>(), new PredicationRank());
    }

    public void settlePredications(Long matchId, int scoreA, int scoreB) {
        if (settled.contains(matchId)) {
            throw new IllegalArgumentException("Cannot settle settled match=%s".formatted(matchId));
        }
        this.settled.add(matchId);
        predictionsPerUser.entrySet()
                .forEach(entry -> rank.addPredicationPoints(entry.getKey(), entry.getValue().settlePredication(matchId, scoreA, scoreB)));

    }

    public void addPredication(String username, Long matchId, int scoreA, int scoreB) {
        if (settled.contains(matchId)) {
            throw new IllegalArgumentException("Cannot predicate settled match=%s".formatted(matchId));
        }
        UserPrediction userPrediction = Optional.ofNullable(predictionsPerUser.get(username))
                .orElseGet(() -> {
                    UserPrediction predication = new UserPrediction();
                    predictionsPerUser.put(username, predication);
                    return predication;
                });
        userPrediction.addPredication(matchId, scoreA, scoreB);
    }
}
