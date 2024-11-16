package com.decerto.typer.schedule;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
    private int firstTeamScore;
    private int secondTeamScore;
    private boolean completed;
    private LocalDateTime date;

    public MatchEntity(Long firstTeamId, Long secondTeamId, LocalDateTime date) {
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
        this.date = date;
    }

    public MatchDto toDto() {
        return new MatchDto(id, firstTeamId, secondTeamId, firstTeamScore, secondTeamScore, date);
    }


    public void finish(int scoreA, int scoreB) {
        this.firstTeamScore = scoreA;
        this.secondTeamScore = scoreB;
        this.completed = true;
    }


}
