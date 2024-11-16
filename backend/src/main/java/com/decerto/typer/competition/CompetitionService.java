package com.decerto.typer.competition;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionService {
    private final CompetitionRepository competitionRepository;
    private final MatchRepository matchRepository;

    public CompetitionService(CompetitionRepository competitionRepository, MatchRepository matchRepository) {
        this.competitionRepository = competitionRepository;
        this.matchRepository = matchRepository;
    }

    public Competition createLeagueCompetition(String name, List<String> teamNames) {
        Competition competition = new Competition();
        competition.setName(name);
        competition.setType(CompetitionType.LEAGUE);

        List<Team> teams = new ArrayList<>();
        for (String teamName : teamNames) {
            Team team = new Team();
            team.setName(teamName);
            team.setCompetition(competition);
            team.setGroupName("Liga");
            teams.add(team);
        }
        competition.setTeams(teams);

        competitionRepository.save(competition);
        generateLeagueSchedule(competition);
        return competition;
    }

    private void generateLeagueSchedule(Competition competition) {
        List<Team> teams = competition.getTeams();
        // Generowanie całej ligi => *2
        int totalRounds = (teams.size() - 1);

        for (int roundNumber = 1; roundNumber <= totalRounds; roundNumber++) {
            Round round = new Round();
            round.setName("Runda " + roundNumber);
            competition.addRound(round);

            for (int i = 0; i < teams.size() / 2; i++) {
                int teamAIndex = (roundNumber + i) % teams.size();
                int teamBIndex = (teams.size() - 1 - i + roundNumber) % teams.size();

                Match match = new Match();
                match.setFirstTeam(teams.get(teamAIndex));
                match.setSecondTeam(teams.get(teamBIndex));
                match.setCompetition(competition);
                matchRepository.save(match);
            }
        }
    }

    public Competition createTournamentCompetition(String name, Map<String, List<String>> groupTeams) {
        Competition competition = new Competition();
        competition.setName(name);
        competition.setType(CompetitionType.TOURNAMENT);

        List<Team> teams = new ArrayList<>();
        Map<String, List<Team>> groupTeamsObj = new HashMap<>();
        for (Map.Entry<String, List<String>> groupEntry : groupTeams.entrySet()) {
            String groupName = groupEntry.getKey();
            List<String> teamNames = groupEntry.getValue();
            List<Team> teamsPerGroup = new ArrayList<>();

            for (String teamName : teamNames) {
                Team team = new Team();
                team.setName(teamName);
                team.setGroupName(groupName);
                team.setCompetition(competition);
                teams.add(team);
                teamsPerGroup.add(team);
            }
            groupTeamsObj.put(groupName, teamsPerGroup);
        }
        competition.setTeams(teams);

        competitionRepository.save(competition);
        generateTournamentSchedule(competition, groupTeamsObj);
        return competition;
    }

    private void generateTournamentSchedule(Competition competition, Map<String, List<Team>> groupTeams) {
        for (Map.Entry<String, List<Team>> groupEntry : groupTeams.entrySet()) {
            String groupName = groupEntry.getKey();
            List<Team> teamNames = groupEntry.getValue();

            for (int i = 0; i < teamNames.size(); i++) {
                for (int j = i + 1; j < teamNames.size(); j++) {
                    Match match = new Match();
                    match.setFirstTeam(teamNames.get(i));
                    match.setSecondTeam(teamNames.get(j));
                    match.setCompetition(competition);
                    match.setGroupName(groupName);
                    matchRepository.save(match);
                }
            }
        }
    }


    public List<Match> setupTournamentBracket(Long competitionId, List<TournamentMatch> bracketMatches) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        if (!competition.getType().equals(CompetitionType.TOURNAMENT)) {
            throw new IllegalArgumentException("This method only applies to tournament competitions");
        }

        // Utwórz mecze w drabince turniejowej
        List<Match> tournamentMatches = new ArrayList<>();
        Round round = new Round();
        round.setCompetition(competition);
        round.setName("Runda drabinka - 1");
        competition.getTournamentFinalRounds().add(round);
        for (TournamentMatch tournamentMatch : bracketMatches) {
            Match match = new Match();
            match.setFirstTeam(findTeamById(competition, tournamentMatch.getFirstTeam()));
            match.setSecondTeam(findTeamById(competition, tournamentMatch.getSecondTeam()));
            match.setCompetition(competition);
            match.setGroupName(null);
            match.setRound(round);
            match.setStatus(MatchStatus.NOT_STARTED);
            matchRepository.save(match);
            tournamentMatches.add(match);
        }

        return tournamentMatches;
    }

    // Pomocnicza metoda do wyszukiwania drużyny po nazwie
    private Team findTeamById(Competition competition, Long teamId) {
        return competition.getTeams().stream()
                .filter(team -> team.getId().equals(teamId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Team " + teamId + " not found in competition"));
    }

    public void beginCompetition(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        competition.beginCompetition();

    }
}



