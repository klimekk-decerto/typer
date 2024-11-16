package com.decerto.typer.solution.schedule;

import com.decerto.typer.solution.TeamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
