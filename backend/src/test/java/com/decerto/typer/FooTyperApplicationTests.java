package com.decerto.typer;

import com.decerto.typer.solution.CompetitionDto;
import com.decerto.typer.solution.FooCompetitionFacade;
import com.decerto.typer.solution.TeamDto;
import com.decerto.typer.solution.schedule.FinalStageType;
import com.decerto.typer.solution.schedule.MatchDto;
import com.decerto.typer.solution.schedule.RoundDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class FooTyperApplicationTests {

    @Autowired
    FooCompetitionFacade sut;

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

        CompetitionDto result = sut.createTournament("Puchar świata", groups, FinalStageType.ONE_FOUR);


        Assertions.assertEquals(findTeamNamesForGroup(result, "Grupa A"), List.of("Polska", "Niemcy"));
        Assertions.assertEquals(findTeamNamesForGroup(result, "Grupa B"), List.of("Anglia", "Szwajcaria"));
    }

    @Test
    void shouldCreateRoundsForTournamentWithOneFourFinalStage() {
        Map<String, List<String>> groups = Map.of(
                "Grupa A", List.of("Polska", "Niemcy", "Anglia", "Szwajcaria"),
                "Grupa B", List.of("Francja", "Irlandia", "USA", "Japonia")
        );

        CompetitionDto result = sut.createTournament("Puchar świata", groups, FinalStageType.ONE_FOUR);


        List<RoundDto> rounds = result.getRounds();
        Assertions.assertEquals(5, rounds.size());
    }

    @Test
    void shouldCreateRoundsForTournamentWithOneEightinalStage() {
        Map<String, List<String>> groups = Map.of(
                "Grupa A", List.of("Polska", "Niemcy", "Anglia", "Szwajcaria"),
                "Grupa B", List.of("Francja", "Irlandia", "USA", "Japonia")
        );

        CompetitionDto result = sut.createTournament("Puchar świata", groups, FinalStageType.ONE_EIGHT);


        List<RoundDto> rounds = result.getRounds();
        Assertions.assertEquals(6, rounds.size());
    }

    @Test
    void shouldGenerateRoundsForLeagueWithDoubleMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia", "Szwajcaria");

        CompetitionDto result = sut.createLeague("Puchar świata", teams);

        Assertions.assertEquals(6, result.getRounds().size());
    }

    @Test
    void shouldGenerateMatchForEachWithEachOneForLeagueWithDoubleMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto result = sut.createLeague("Puchar świata", teams);

        Long polska = findTeamIdByName(result, "Polska");
        Long anglia = findTeamIdByName(result, "Anglia");
        Long niemcy = findTeamIdByName(result, "Niemcy");

        List<MatchDto> allMatches = result.getMatches();

        Assertions.assertEquals(allMatches.size(), 6);
        Assertions.assertTrue(allMatches.stream().anyMatch(match -> areTeamsCorrect(match, polska, anglia)));
        Assertions.assertTrue(allMatches.stream().anyMatch(match -> areTeamsCorrect(match, polska, niemcy)));
        Assertions.assertTrue(allMatches.stream().anyMatch(match -> areTeamsCorrect(match, anglia, polska)));
        Assertions.assertTrue(allMatches.stream().anyMatch(match -> areTeamsCorrect(match, niemcy, polska)));
        Assertions.assertTrue(allMatches.stream().anyMatch(match -> areTeamsCorrect(match, anglia, niemcy)));
        Assertions.assertTrue(allMatches.stream().anyMatch(match -> areTeamsCorrect(match, niemcy, anglia)));
    }

    private static List<String> findTeamNamesForGroup(CompetitionDto result, String grupaA) {
        return result.getTeams().stream()
                .filter(team -> team.groupName().equals(grupaA))
                .map(TeamDto::name)
                .toList();
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

    private static Long findTeamIdByName(CompetitionDto result, String polska) {
        return result.getTeams().stream()
                .filter(team -> team.name().equals(polska))
                .findFirst()
                .map(TeamDto::id)
                .orElseThrow();
    }

}
