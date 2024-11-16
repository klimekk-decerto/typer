package com.decerto.typer.solution;

import com.decerto.typer.application.requests.AssignRoundToMatchRequest;
import com.decerto.typer.solution.competition.CompetitionEntity;
import com.decerto.typer.solution.competition.FooCompetitionRepository;
import com.decerto.typer.solution.schedule.FinalStageType;
import com.decerto.typer.solution.schedule.MatchEntity;
import com.decerto.typer.solution.schedule.ScheduleDto;
import com.decerto.typer.solution.schedule.ScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
@Transactional
public class FooCompetitionFacade {
    private final FooCompetitionRepository repository;
    private final ScheduleService scheduleService;

    public CompetitionDto createLeague(@NonNull String leagueName, @NonNull List<String> teamsNames) {
        CompetitionEntity entity = CompetitionEntity.ofLeague(leagueName, teamsNames);
        repository.save(entity);

        List<TeamDto> teams = entity.toDto();
        ScheduleDto schedule = scheduleService.createLeagueSchedule(entity.getId(), teams);
        return new CompetitionDto(entity.getId(), teams, schedule.getRounds(), schedule.getMatches());
    }

    public CompetitionDto chooseRoundForMatch(AssignRoundToMatchRequest request) {
        MatchEntity match = scheduleService.getMatch(request.getCompetitionId(), request.getFirstTeamId(), request.getSecondTeamId());
        scheduleService.chooseRoundForMatch(request.getCompetitionId(), request.getRoundId(), match.getId());
        return setMatchDate(request.getCompetitionId(), match.getId(), request.getDate());
    }

    public CompetitionDto chooseRoundForMatch(Long id, Long roundId, Long matchId) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        List<TeamDto> teams = entity.toDto();
        var schedule = scheduleService.chooseRoundForMatch(id, roundId, matchId);
        return new CompetitionDto(entity.getId(), teams, schedule.getRounds(), schedule.getMatches());
    }

    public CompetitionDto finishMatch(Long id, Long matchId, int scoreA, int scoreB) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        List<TeamDto> teams = entity.toDto();
        var schedule = scheduleService.finishMatch(id, matchId, scoreA, scoreB);
        return new CompetitionDto(entity.getId(), teams, schedule.getRounds(), schedule.getMatches());
    }

    public CompetitionDto setMatchDate(Long id, Long matchId, LocalDateTime date) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        List<TeamDto> teams = entity.toDto();
        var schedule = scheduleService.setMatchDate(id, matchId, date);
        return new CompetitionDto(entity.getId(), teams, schedule.getRounds(), schedule.getMatches());
    }

    public CompetitionDto createTournament(String name, Map<String, List<String>> groups, FinalStageType stageType) {
        CompetitionEntity entity = CompetitionEntity.ofTournament(name, groups);
        List<TeamDto> teams = entity.toDto();
        Map<String, List<TeamDto>> teamsMap = teams.stream()
                .collect(Collectors.groupingBy(map -> map.groupName(),
                        Collectors.mapping(Function.identity(), Collectors.toList())));
        ScheduleDto schedule = scheduleService.createTournamentSchedule(entity.getId(), teamsMap, stageType);
        return new CompetitionDto(entity.getId(), teams, schedule.getRounds(), schedule.getMatches());
    }
}
