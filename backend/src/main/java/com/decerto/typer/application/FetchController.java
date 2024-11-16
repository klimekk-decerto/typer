package com.decerto.typer.application;

import com.decerto.typer.CompetitionDetails;
import com.decerto.typer.CompetitionDto;
import com.decerto.typer.CompetitionFacade;
import com.decerto.typer.competition.CompetitionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/fetch")
@RequiredArgsConstructor
public class FetchController {

    private final CompetitionRepository competitionRepository;
    private final CompetitionFacade competitionFacade;

    @GetMapping("/competition/{id}")
    public CompetitionDto getCompetition(@PathVariable long id) {
        return competitionFacade.get(id);
    }

    @GetMapping("/competitions")
    public List<CompetitionDetails> getCompetitions() {
        return competitionRepository.findAll()
                .stream()
                .map(entity -> new CompetitionDetails(entity.getId(), entity.getName()))
                .toList();
    }

}
