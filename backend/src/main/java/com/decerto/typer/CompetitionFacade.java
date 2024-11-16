package com.decerto.typer;

import com.decerto.typer.application.requests.CreateMatchRequest;
import com.decerto.typer.competition.CompetitionEntity;
import com.decerto.typer.competition.CompetitionRepository;
import com.decerto.typer.prediction.PredicationRank;
import com.decerto.typer.prediction.Predictions;
import com.decerto.typer.prediction.PredictionsRepository;
import com.decerto.typer.schedule.MatchDto;
import com.decerto.typer.schedule.ScheduleDto;
import com.decerto.typer.schedule.ScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Component
@Transactional
public class CompetitionFacade {
    private final CompetitionRepository repository;
    private final ScheduleService scheduleService;
    private final PredictionsRepository predictionsRepository;


    public CompetitionDto get(Long id) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        ScheduleDto scheduleDto = scheduleService.get(id);
        return toDto(entity, scheduleDto);
    }

    public CompetitionDto createLeague(@NonNull String leagueName, @NonNull List<String> teamsNames) {
        CompetitionEntity entity = CompetitionEntity.ofLeague(leagueName, teamsNames);
        repository.save(entity);

        ScheduleDto schedule = scheduleService.createLeagueSchedule(entity.getId());
        predictionsRepository.save(new Predictions(entity.getId()));
        return toDto(entity, schedule);
    }

    public CompetitionDto createMatch(CreateMatchRequest request) {
        CompetitionEntity entity = repository.findById(request.getCompetitionId()).orElseThrow();
        var pair = scheduleService.createMatch(request);
        Predictions predictions = predictionsRepository.findByCompetitionId(request.getCompetitionId());
        predictions.addMatch(pair.getSecond());
        return toDto(entity, pair.getFirst());
    }


    public CompetitionDto finishMatch(Long id, Long matchId, int scoreA, int scoreB) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        var schedule = scheduleService.finishMatch(id, matchId, scoreA, scoreB);
        Predictions predictions = predictionsRepository.findByCompetitionId(id);
        predictions.settlePredications(matchId, scoreA, scoreB);
        return toDto(entity, schedule);
    }


    public CompetitionDto createTournament(String name, Map<String, List<String>> groups) {
        CompetitionEntity entity = CompetitionEntity.ofTournament(name, groups);
        repository.save(entity);
        ScheduleDto schedule = scheduleService.createTournamentSchedule(entity.getId());
        predictionsRepository.save(new Predictions(entity.getId()));
        return toDto(entity, schedule);
    }

    public CompetitionDto deleteMatch(Long id, Long matchId) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        ScheduleDto schedule = scheduleService.deleteMatch(entity.getId(), matchId);
        Predictions predictions = predictionsRepository.findByCompetitionId(id);
        predictions.deleteMatch(matchId);
        return toDto(entity, schedule);
    }

    private CompetitionDto toDto(CompetitionEntity entity, ScheduleDto schedule) {
        List<TeamDto> teams = entity.toDto();
        Predictions predictions = predictionsRepository.findByCompetitionId(entity.getId());
        return new CompetitionDto(entity.getId(), teams, schedule.getMatches(), getMatchesForPredicate(entity.getId()), predictions.getRank());
    }

    public CompetitionDto predicate(String userName, Long id, Long matchId, int scoreA, int scoreB) {
        CompetitionEntity entity = repository.findById(id).orElseThrow();
        Predictions predictions = predictionsRepository.findByCompetitionId(id);
        if (scheduleService.isMatchStarted(id, matchId)) {
            throw new IllegalStateException("You cannot predicate started match");
        }
        predictions.addPredication(userName, matchId, scoreA, scoreB);
        ScheduleDto schedule = scheduleService.get(id);
        return toDto(entity, schedule);
    }

    public PredicationRank getRanking(Long id) {
        Predictions predictions = predictionsRepository.findByCompetitionId(id);
        return predictions.getRank();
    }

    public List<MatchDto> getMatchesForPredicate(Long id) {
        Predictions predictions = predictionsRepository.findByCompetitionId(id);
        Set<Long> matches = predictions.getAllMatchesAvailableForPredication();
        ScheduleDto schedule = scheduleService.get(id);
        return schedule.findMatches(matches);
    }
}
