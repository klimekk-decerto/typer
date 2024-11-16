package com.decerto.typer.solution.schedule;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity {

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

    public static ScheduleEntity ofLeagueSchedule(Long competitionId) {
        return new ScheduleEntity(null, competitionId, List.of(), List.of());
    }


    public static ScheduleEntity ofTournament(Long id) {
        return new ScheduleEntity(null, id, List.of(), List.of());
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

    public MatchEntity getMatch(long firstTeamId, long secondTeamId) {
        return matches.stream()
                .filter(match -> match.getFirstTeamId().equals(firstTeamId) && match.getSecondTeamId().equals(secondTeamId))
                .findFirst()
                .orElseThrow();
    }
}
