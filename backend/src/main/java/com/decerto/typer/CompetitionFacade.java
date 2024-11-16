package com.decerto.typer;

import com.decerto.typer.application.requests.CreateMatchRequest;
import com.decerto.typer.competition.CompetitionEntity;
import com.decerto.typer.competition.CompetitionRepository;
import com.decerto.typer.schedule.ScheduleDto;
import com.decerto.typer.schedule.ScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Transactional
public class CompetitionFacade {
    private final CompetitionRepository repository;
    private final ScheduleService scheduleService;

    public CompetitionDto createLeague(@NonNull String leagueName, @NonNull List<String> teamsNames) {
        CompetitionEntity entity = CompetitionEntity.ofLeague(leagueName, teamsNames);
        repository.save(entity);

        ScheduleDto schedule = scheduleService.createLeagueSchedule(entity.getId());
        return toDto(entity, schedule);
    }

    public CompetitionDto createMatch(CreateMatchRequest request) {
        CompetitionEntity entity = repository.findById(request.getCompetitionId()).orElseThrow();
        ScheduleDto schedule = scheduleService.createMatch(request);
        return toDto(entity, schedule);
    }


    public CompetitionDto finishMatch(Long id, Long matchId, int scoreA, int scoreB) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        var schedule = scheduleService.finishMatch(id, matchId, scoreA, scoreB);
        return toDto(entity, schedule);
    }


    public CompetitionDto createTournament(String name, Map<String, List<String>> groups) {
        CompetitionEntity entity = CompetitionEntity.ofTournament(name, groups);
        repository.save(entity);
        ScheduleDto schedule = scheduleService.createTournamentSchedule(entity.getId());
        return toDto(entity, schedule);
    }

    public CompetitionDto deleteMatch(Long id, Long matchId) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        ScheduleDto schedule = scheduleService.deleteMatch(entity.getId(), matchId);
        return toDto(entity, schedule);
    }

    private static CompetitionDto toDto(CompetitionEntity entity, ScheduleDto schedule) {
        List<TeamDto> teams = entity.toDto();
        return new CompetitionDto(entity.getId(), teams, schedule.getMatches());
    }
}
