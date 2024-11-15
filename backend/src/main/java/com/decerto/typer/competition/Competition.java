package com.decerto.typer.competition;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private CompetitionType type;  // CompetitionType (LEAGUE or TOURNAMENT)

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Round> rounds;
    private CompetitionStatus status = CompetitionStatus.CREATING;


    public void beginCompetition() {
        this.status = CompetitionStatus.IN_PROGRESS;
    }
}

