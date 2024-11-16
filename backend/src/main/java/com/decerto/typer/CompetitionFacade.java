package com.decerto.typer;

import com.decerto.typer.application.requests.AssignRoundToMatchRequest;
import com.decerto.typer.competition.CompetitionEntity;
import com.decerto.typer.competition.CompetitionRepository;
import com.decerto.typer.schedule.MatchEntity;
import com.decerto.typer.schedule.ScheduleDto;
import com.decerto.typer.schedule.ScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

        List<TeamDto> teams = entity.toDto();
        ScheduleDto schedule = scheduleService.createLeagueSchedule(entity.getId());
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

    public CompetitionDto createTournament(String name, Map<String, List<String>> groups) {
        CompetitionEntity entity = CompetitionEntity.ofTournament(name, groups);
        repository.save(entity);
        List<TeamDto> teams = entity.toDto();
        ScheduleDto schedule = scheduleService.createTournamentSchedule(entity.getId());
        return new CompetitionDto(entity.getId(), teams, schedule.getRounds(), schedule.getMatches());
    }
}
