package com.decerto.typer.schedule;

import com.decerto.typer.application.requests.CreateMatchRequest;
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
    private List<MatchEntity> matches;

    public static ScheduleEntity ofLeagueSchedule(Long competitionId) {
        return new ScheduleEntity(null, competitionId, List.of());
    }


    public static ScheduleEntity ofTournament(Long id) {
        return new ScheduleEntity(null, id, List.of());
    }

    public ScheduleDto toDto() {
        return new ScheduleDto(
                matches.stream().map(MatchEntity::toDto).toList()
        );
    }


    public void finishMatch(Long matchId, int scoreA, int scoreB) {
        findMatch(matchId)
                .ifPresent(match -> match.finish(scoreA, scoreB));
    }


    private Optional<MatchEntity> findMatch(Long matchId) {
        return this.matches.stream()
                .filter(match -> match.getId().equals(matchId))
                .findFirst();
    }


    public MatchEntity addMatch(CreateMatchRequest request) {
        MatchEntity match = new MatchEntity(request.getFirstTeamId(), request.getSecondTeamId(), request.getDate());
        matches.add(match);
        return match;
    }

    public void removeMatch(Long matchId) {
        matches.stream()
                .filter(match -> match.getId().equals(matchId))
                .findFirst()
                .ifPresent(matches::remove);
    }

    public boolean isMatchStarted(Long matchId, LocalDateTime now) {
        return findMatch(matchId)
                .map(match -> match.getDate())
                .map(date -> now.isAfter(date))
                .orElse(false);
    }
}
