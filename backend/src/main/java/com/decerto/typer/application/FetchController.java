package com.decerto.typer.application;

import com.decerto.typer.competition.Competition;
import com.decerto.typer.competition.CompetitionRepository;
import com.decerto.typer.solution.CompetitionDto;
import com.decerto.typer.solution.competition.CompetitionEntity;
import com.decerto.typer.solution.competition.FooCompetitionRepository;
import com.decerto.typer.solution.schedule.ScheduleEntity;
import com.decerto.typer.solution.schedule.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/fetch")
@RequiredArgsConstructor
public class FetchController {

    private final FooCompetitionRepository fooCompetitionRepository;
    private final ScheduleRepository scheduleRepository;
    private final CompetitionMapper competitionMapper;

    @GetMapping("/competition/{id}")
    public CompetitionDto getCompetition(@PathVariable long id) {
        CompetitionEntity competition = fooCompetitionRepository.findById(id).orElseThrow();
        ScheduleEntity schedule = scheduleRepository.findById(id).orElseThrow();
        return competitionMapper.map(competition, schedule);
    }

}
