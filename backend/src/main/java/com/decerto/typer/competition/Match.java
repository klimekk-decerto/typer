package com.decerto.typer.competition;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Setter
@Getter
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "first_team_id")
    private Team firstTeam;

    @ManyToOne
    @JoinColumn(name = "second_team_id")
    private Team secondTeam;

    private Integer scoreA;
    private Integer scoreB;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;

    private String groupName;
    private LocalDateTime matchDate;


    private MatchStatus status = MatchStatus.NOT_STARTED;

    public void setResult(Integer scoreA, Integer scoreB) {
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.status = MatchStatus.COMPLETED;
    }

    public boolean isCompleted() {
        return status == MatchStatus.COMPLETED;
    }

    public Optional<Team> getWinnerTeam() {
        if (status == MatchStatus.COMPLETED) {
            return scoreA > scoreB
                    ? Optional.of(firstTeam)
                    : Optional.of(secondTeam);
        }

        return Optional.empty();
    }
}

