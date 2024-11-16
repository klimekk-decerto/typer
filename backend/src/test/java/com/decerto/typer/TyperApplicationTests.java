package com.decerto.typer;

import com.decerto.typer.competition.Competition;
import com.decerto.typer.competition.CompetitionFacade;
import com.decerto.typer.competition.Match;
import com.decerto.typer.competition.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class TyperApplicationTests {

    @Autowired
    private CompetitionFacade facade;


    @Test
    void shouldCreateLeagueCompetitionWithCorrectNumberOfRoundsAndMatches() {
        // given:
        List<String> teams = List.of("Polska", "Niemcy", "Anglia", "Szwajcaria");

        // when:
        Competition competition = facade.createLeagueCompetition("Puchar świata", teams);

        // then:
        List<Match> allMatches = facade.getMatches(competition.getId());
        Assertions.assertEquals(competition.getRounds().size(), 3);
        Assertions.assertEquals(allMatches.size(), 6);

    }

    private static boolean anyMatchForTeams(List<Match> matches, String firstTeam, String secondTeam) {
        return matches.stream().anyMatch(match -> teamNameEquals(match.getFirstTeam(), firstTeam) && teamNameEquals(match.getFirstTeam(), secondTeam));
    }

    private static boolean teamNameEquals(Team team, String name) {
        return team.getName().equals(name);
    }

}
