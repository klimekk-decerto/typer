package com.decerto.typer.solution;

import com.decerto.typer.solution.competition.CompetitionDto;
import com.decerto.typer.solution.competition.CompetitionEntity;
import com.decerto.typer.solution.competition.FooCompetitionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class FooCompetitionFacade {
    private final FooCompetitionRepository repository;;

    public CompetitionDto createLeague(@NonNull String leagueName, @NonNull List<String> teams) {
        CompetitionEntity entity = CompetitionEntity.ofLeague(leagueName, teams);
        repository.save(entity);

        return entity.toDto();
    }
}
