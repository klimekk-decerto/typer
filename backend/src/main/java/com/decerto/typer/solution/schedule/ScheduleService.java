package com.decerto.typer.solution.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleDto createLeagueSchedule(Long competitionId) {
        ScheduleEntity entity = ScheduleEntity.ofLeagueSchedule(competitionId);
        repository.save(entity);
        return entity.toDto();
    }

    public ScheduleDto chooseRoundForMatch(Long id, Long roundId, Long matchId) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        entity.chooseRoundForMatch(roundId, matchId);
        return entity.toDto();
    }

    public ScheduleDto finishMatch(Long id, Long matchId, int scoreA, int scoreB) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        entity.finishMatch(matchId, scoreA, scoreB);
        return entity.toDto();
    }

    public ScheduleDto setMatchDate(Long id, Long matchId, LocalDateTime date) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        entity.setMatchDate(matchId, date);
        return entity.toDto();
    }

    public ScheduleDto createTournamentSchedule(Long id) {
        ScheduleEntity entity = ScheduleEntity.ofTournament(id);
        repository.save(entity);
        return entity.toDto();

    }

    public MatchEntity getMatch(long competitionId, long firstTeamId, long secondTeamId) {
        ScheduleEntity entity = repository.findByCompetitionId(competitionId);
        return entity.getMatch(firstTeamId, secondTeamId);
    }
}
