package com.decerto.typer;

import com.decerto.typer.application.requests.CreateMatchRequest;
import com.decerto.typer.schedule.MatchDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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
    void shouldCreateMatchForLeague() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto competition = sut.createLeague("Puchar świata", teams);

        LocalDateTime now = LocalDateTime.now();
        CompetitionDto result = sut.createMatch(CreateMatchRequest.builder()
                .competitionId(competition.getId())
                .date(now)
                .firstTeamId(findTeamIdByName(competition, "Polska"))
                .secondTeamId(findTeamIdByName(competition, "Niemcy"))
                .build());

        MatchDto firstMatch = result.getMatches().getFirst();
        Assertions.assertEquals(now, firstMatch.getDate());
        Assertions.assertEquals(findTeamIdByName(competition, "Polska"), firstMatch.getFirstTeamId());
        Assertions.assertEquals(findTeamIdByName(competition, "Niemcy"), firstMatch.getSecondTeamId());
    }


    @Test
    void shouldDeleteMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto competition = sut.createLeague("Puchar świata", teams);

        LocalDateTime now = LocalDateTime.now();
        MatchDto firstMatch = sut.createMatch(CreateMatchRequest.builder()
                .competitionId(competition.getId())
                .date(now)
                .firstTeamId(findTeamIdByName(competition, "Polska"))
                .secondTeamId(findTeamIdByName(competition, "Niemcy"))
                .build()).getMatches().getFirst();

        CompetitionDto result = sut.deleteMatch(competition.getId(), firstMatch.getMatchId());

        Assertions.assertTrue(result.getMatches().isEmpty());
    }

    @Test
    void shouldBeAbleToFinishMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto competition = sut.createLeague("Puchar świata", teams);
        MatchDto firstMatch = sut.createMatch(CreateMatchRequest.builder()
                .competitionId(competition.getId())
                .date(LocalDateTime.now())
                .firstTeamId(findTeamIdByName(competition, "Polska"))
                .secondTeamId(findTeamIdByName(competition, "Niemcy"))
                .build()).getMatches().getFirst();

        CompetitionDto after = sut.finishMatch(competition.getId(), firstMatch.getMatchId(), 1, 3);

        MatchDto match = after.getMatches().getFirst();
        Assertions.assertEquals(1, match.getFirstTeamScore());
        Assertions.assertEquals(3, match.getSecondTeamScore());
    }

    @Test
    void shouldBeAbleToPredicateMatchAndGetPointsForCorrectAnswerAfterFinishMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto competition = sut.createLeague("Puchar świata", teams);
        MatchDto firstMatch = sut.createMatch(CreateMatchRequest.builder()
                .competitionId(competition.getId())
                .date(LocalDateTime.now().plusHours(1))
                .firstTeamId(findTeamIdByName(competition, "Polska"))
                .secondTeamId(findTeamIdByName(competition, "Niemcy"))
                .build()).getMatches().getFirst();

        sut.predicate("user", competition.getId(), firstMatch.getMatchId(), 1, 3);


        CompetitionDto after = sut.finishMatch(competition.getId(), firstMatch.getMatchId(), 1, 3);

        Assertions.assertEquals(3, sut.getRanking(competition.getId()).getParticipants().getFirst().getPoints());
    }

    @Test
    void shouldUserGetPointsForExactPredicateAndPredicateWinner() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto competition = sut.createLeague("Puchar świata", teams);
        Long firstMatch = createMatchWithDate(competition, LocalDateTime.now().plusHours(1));
        Long secondMatch = createMatchWithDate(competition, LocalDateTime.now().plusHours(1));
        Long thirdMatch = createMatchWithDate(competition, LocalDateTime.now().plusHours(1));
        Long fourthMatch = createMatchWithDate(competition, LocalDateTime.now().plusHours(1));
        Long fifthMatch = createMatchWithDate(competition, LocalDateTime.now().plusHours(1));

        sut.predicate("user", competition.getId(), firstMatch, 1, 3);
        sut.predicate("user", competition.getId(), secondMatch, 5, 2);
        sut.predicate("user", competition.getId(), thirdMatch, 1, 1);
        sut.predicate("user", competition.getId(), fourthMatch, 3, 3);
        sut.predicate("user", competition.getId(), fifthMatch, 0, 1);


        sut.finishMatch(competition.getId(), firstMatch, 1, 3); // 3
        sut.finishMatch(competition.getId(), secondMatch, 3, 2); // 1
        sut.finishMatch(competition.getId(), thirdMatch, 2, 2); // 1
        sut.finishMatch(competition.getId(), fourthMatch, 3, 3); // 3
        sut.finishMatch(competition.getId(), fifthMatch, 10, 0); // 0

        Assertions.assertEquals(8, sut.getRanking(competition.getId()).getParticipants().getFirst().getPoints());
    }

    @Test
    void shouldNotBeAbleToPredicateStartedMatch() {
        List<String> teams = List.of("Polska", "Niemcy", "Anglia");

        CompetitionDto competition = sut.createLeague("Puchar świata", teams);
        Long firstMatch = createMatchWithDate(competition, LocalDateTime.now().minusHours(1));

        Assertions.assertThrows(IllegalStateException.class, () -> sut.predicate("user", competition.getId(), firstMatch, 1, 3));
    }

    private Long createMatch(CompetitionDto competition) {
        return sut.createMatch(CreateMatchRequest.builder()
                .competitionId(competition.getId())
                .date(LocalDateTime.now())
                .firstTeamId(findTeamIdByName(competition, "Polska"))
                .secondTeamId(findTeamIdByName(competition, "Niemcy"))
                .build()).getMatches().getLast().getMatchId();
    }

    private Long createMatchWithDate(CompetitionDto competition, LocalDateTime now) {
        return sut.createMatch(CreateMatchRequest.builder()
                .competitionId(competition.getId())
                .date(now)
                .firstTeamId(findTeamIdByName(competition, "Polska"))
                .secondTeamId(findTeamIdByName(competition, "Niemcy"))
                .build()).getMatches().getLast().getMatchId();
    }

    private static List<String> findTeamNamesForGroup(CompetitionDto result, String grupaA) {
        return result.getTeams().stream()
                .filter(team -> team.groupName().equals(grupaA))
                .map(TeamDto::name)
                .toList();
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
