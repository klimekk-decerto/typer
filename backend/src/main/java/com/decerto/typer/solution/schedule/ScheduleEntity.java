package com.decerto.typer.solution.schedule;

import com.decerto.typer.solution.TeamDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class ScheduleEntity {

    @GeneratedValue
    @Id
    private Long id;
    private Long competitionId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_id")
    private List<RoundEntity> rounds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "schedule_id")
    private List<MatchEntity> matches;

    public static ScheduleEntity ofLeagueSchedule(Long competitionId, List<TeamDto> teams) {
        List<RoundEntity> rounds = new ArrayList<>();
        int numberOfRounds = 2 * (teams.size() - 1);
        for (int i = 1; i <= numberOfRounds; i++) {
            rounds.add(new RoundEntity(null, i));
        }
        List<MatchEntity> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.size(); j++) {
                if (i != j) {
                    MatchEntity match = new MatchEntity(teams.get(i).id(), teams.get(j).id());
                    matches.add(match);
                }
            }
        }
        return new ScheduleEntity(null, competitionId, rounds, matches);
    }

    public ScheduleDto toDto() {
        return new ScheduleDto(
                rounds.stream().map(RoundEntity::toDto).toList(),
                matches.stream().map(MatchEntity::toDto).toList()
        );
    }

    public void chooseRoundForMatch(Long roundId, Long matchId) {
        findMatch(matchId)
                .ifPresent(match -> match.chooseRound(roundId));
    }

    public void finishMatch(Long matchId, int scoreA, int scoreB) {
        findMatch(matchId)
                .ifPresent(match -> match.finish(scoreA, scoreB));
    }

    public void setMatchDate(Long matchId, LocalDateTime date) {
        findMatch(matchId)
                .ifPresent(match -> match.setMatchDate(date));
    }

    private Optional<MatchEntity> findMatch(Long matchId) {
        return this.matches.stream()
                .filter(match -> match.getId().equals(matchId))
                .findFirst();
    }
}
