package com.decerto.typer;

import com.decerto.typer.schedule.MatchDto;
import com.decerto.typer.schedule.RoundDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class TyperApplicationTests {

    @Autowired
    CompetitionFacade sut;

    @Test
    void shouldCreateLeagueCompetitionWithTeams() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia", "Szwajcaria");

        CompetitionDto result = sut.createLeague("Puchar świata", teams);

        Assertions.assertEquals(result.getTeams().stream().map(TeamDto::name).toList(), teams);
    }

    @Test
    void shouldCreateTournamentCompetitionWithTeamsWithGroupSeparation() {
        Map<String, List<String>> groups = Map.of(
                "Grupa A", List.of("Polska", "Niemcy"),
                "Grupa B", List.of("Anglia", "Szwajcaria")
        );

        CompetitionDto result = sut.createTournament("Puchar świata", groups);


        Assertions.assertEquals(findTeamNamesForGroup(result, "Grupa A"), List.of("Polska", "Niemcy"));
        Assertions.assertEquals(findTeamNamesForGroup(result, "Grupa B"), List.of("Anglia", "Szwajcaria"));
    }


    @Test
    void shouldChooseRoundForMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto result = sut.createLeague("Puchar świata", teams);
        RoundDto firstRound = result.getRounds().getFirst();
        MatchDto firstMatch = result.getMatches().getFirst();

        CompetitionDto after = sut.chooseRoundForMatch(result.getId(), firstRound.id(), firstMatch.getMatchId());

        Assertions.assertEquals(firstRound.id(), after.getMatches().getFirst().getRoundId());
    }

    private static List<String> findTeamNamesForGroup(CompetitionDto result, String grupaA) {
        return result.getTeams().stream()
                .filter(team -> team.groupName().equals(grupaA))
                .map(TeamDto::name)
                .toList();
    }

    @Test
    void shouldBeAbleToFinishMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto result = sut.createLeague("Puchar świata", teams);
        MatchDto firstMatch = result.getMatches().getFirst();

        CompetitionDto after = sut.finishMatch(result.getId(), firstMatch.getMatchId(), 1, 3);

        MatchDto match = after.getMatches().getFirst();
        Assertions.assertEquals(1, match.getFirstTeamScore());
        Assertions.assertEquals(3, match.getSecondTeamScore());
    }

    private boolean areTeamsCorrect(MatchDto match, Long polska, Long anglia) {
        return match.getFirstTeamId().equals(polska) && match.getSecondTeamId().equals(anglia);
    }

    private boolean areTeamsCorrectIgnoringPosition(MatchDto match, Long polska, Long anglia) {
        return areTeamsCorrect(match, polska, anglia) || areTeamsCorrect(match, anglia, polska);
    }

    private static Long findTeamIdByName(CompetitionDto result, String polska) {
        return result.getTeams().stream()
                .filter(team -> team.name().equals(polska))
                .findFirst()
                .map(TeamDto::id)
                .orElseThrow();
    }

}
