package com.decerto.typer.solution.schedule;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchEntity {

    @GeneratedValue
    @Id
    private Long id;
    private Long firstTeamId;
    private Long secondTeamId;
    private Long roundId;
    private int firstTeamScore;
    private int secondTeamScore;
    private boolean completed;

    public MatchEntity(Long firstTeamId, Long secondTeamId) {
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
    }

    public MatchDto toDto() {
        return new MatchDto(id, firstTeamId, secondTeamId, roundId, firstTeamScore, secondTeamScore);
    }

    public void chooseRound(Long roundId) {
        this.roundId = roundId;
    }

    public void finish(int scoreA, int scoreB) {
        this.firstTeamScore = scoreA;
        this.secondTeamScore = scoreB;
        this.completed = true;
    }
}
