package com.decerto.typer.competition;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Setter
@Getter
@Entity
public class Round {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @OneToMany(mappedBy = "round", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Match> matches;


    public Optional<Match> findMatch(Long matchId) {
        return matches.stream()
                .filter(match -> match.getId().equals(matchId))
                .findFirst();
    }

    public int numberOfMatches() {
        return matches.size();
    }

    public boolean allMatchesCompleted() {
        return matches.stream().allMatch(Match::isCompleted);
    }

    public void addMatch(Match match) {
        this.matches.add(match);
        match.setRound(this);
        match.setCompetition(competition);
    }
}

