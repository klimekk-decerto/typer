package com.decerto.typer.competition;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@Entity
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private CompetitionType type;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Round> rounds;

    @OneToMany(mappedBy = "competition", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Round> tournamentFinalRounds;

    private CompetitionStatus status = CompetitionStatus.CREATING;


    public void beginCompetition() {
        this.status = CompetitionStatus.FIRST_PHASE;
    }

    public void setMatchResult(Long matchId, Integer scoreA, Integer scoreB) {
        if (status == CompetitionStatus.FIRST_PHASE) {
            setResultForMatch(this.rounds, matchId, scoreA, scoreB);
            if (rounds.stream().allMatch(Round::allMatchesCompleted)) {
                status = CompetitionStatus.FINAL_PHASE;
            }
            return;
        }

        if (status == CompetitionStatus.FINAL_PHASE) {
            setResultForMatch(this.tournamentFinalRounds, matchId, scoreA, scoreB);
            Round latestRound = getLatestFinalRound();
            if (latestRound.allMatchesCompleted()) {
                createNewRoundWithWinnerTeams(latestRound);
            }
        }
    }

    private void createNewRoundWithWinnerTeams(Round latestRound) {
        Round round = new Round();
        round.setName("Runda drzewko");
        round.setCompetition(this);
        List<Team> winnerTeams = latestRound.getMatches().stream()
                .map(Match::getWinnerTeam)
                .flatMap(Optional::stream)
                .toList();

        if (winnerTeams.size() == 1) {
            status = CompetitionStatus.COMPLETED;
            return;
        }

        for (int i=0;i<winnerTeams.size();i+=2) {
            Match match = new Match();
            match.setFirstTeam(winnerTeams.get(i));
            match.setSecondTeam(winnerTeams.get(i+1));
            round.addMatch(match);
        }
    }

    private Round getLatestFinalRound() {
        return tournamentFinalRounds.stream()
                .sorted(Comparator.comparing(Round::numberOfMatches).reversed())
                .findFirst()
                .orElseThrow();
    }

    private void setResultForMatch(List<Round> tournamentFinalRounds, Long matchId, Integer scoreA, Integer scoreB) {
        tournamentFinalRounds.stream()
                .map(round -> round.findMatch(matchId))
                .flatMap(Optional::stream)
                .findFirst()
                .ifPresent(match -> match.setResult(scoreA, scoreB));
    }

    public void addRound(Round round) {
        if (rounds == null) {
            this.rounds = new ArrayList<>();
        }
        rounds.add(round);
        round.setCompetition(this);
    }

}

