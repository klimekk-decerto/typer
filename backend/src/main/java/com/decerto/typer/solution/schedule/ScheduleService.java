package com.decerto.typer.solution.schedule;

import com.decerto.typer.solution.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository repository;

    public ScheduleDto createLeagueSchedule(Long competitionId, List<TeamDto> teams) {
        ScheduleEntity entity = ScheduleEntity.ofLeagueSchedule(competitionId, teams);
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
}
