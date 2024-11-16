package com.decerto.typer.solution;

import com.decerto.typer.solution.competition.CompetitionEntity;
import com.decerto.typer.solution.competition.FooCompetitionRepository;
import com.decerto.typer.solution.schedule.ScheduleDto;
import com.decerto.typer.solution.schedule.ScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FooCompetitionFacade {
    private final FooCompetitionRepository repository;
    private final ScheduleService scheduleService;

    public CompetitionDto createLeague(@NonNull String leagueName, @NonNull List<String> teamsNames) {
        CompetitionEntity entity = CompetitionEntity.ofLeague(leagueName, teamsNames);
        repository.save(entity);

        List<TeamDto> teams = entity.toDto();
        ScheduleDto schedule = scheduleService.createLeagueSchedule(entity.getId(), teams);
        return new CompetitionDto(teams, schedule.getRounds());
    }
}
