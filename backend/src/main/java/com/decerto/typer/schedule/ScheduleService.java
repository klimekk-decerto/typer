package com.decerto.typer.schedule;

import com.decerto.typer.application.requests.CreateMatchRequest;
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


    public ScheduleDto finishMatch(Long id, Long matchId, int scoreA, int scoreB) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        entity.finishMatch(matchId, scoreA, scoreB);
        repository.save(entity);
        return entity.toDto();
    }


    public ScheduleDto createTournamentSchedule(Long id) {
        ScheduleEntity entity = ScheduleEntity.ofTournament(id);
        repository.save(entity);
        return entity.toDto();

    }


    public ScheduleDto createMatch(CreateMatchRequest request) {
        ScheduleEntity entity = repository.findByCompetitionId(request.getCompetitionId());
        entity.addMatch(request);
        repository.save(entity);
        return entity.toDto();
    }

    public ScheduleDto deleteMatch(Long id, Long matchId) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        entity.removeMatch(matchId);
        return entity.toDto();
    }

    public ScheduleDto get(Long id) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        return entity.toDto();
    }

    public boolean isMatchStarted(Long id, Long matchId) {
        ScheduleEntity entity = repository.findByCompetitionId(id);
        return entity.isMatchStarted(matchId, LocalDateTime.now());
    }
}
