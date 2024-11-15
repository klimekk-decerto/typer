package com.decerto.typer.competition;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamA;
    private String teamB;

    private Integer scoreA;
    private Integer scoreB;

    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @ManyToOne
    @JoinColumn(name = "round_id")
    private Round round;

    private String groupName;  // For tournaments, the group where this match belongs
    private LocalDateTime matchDate;


    private MatchStatus status = MatchStatus.NOT_STARTED;

}

