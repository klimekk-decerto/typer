package com.decerto.typer.competition;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            teams.add(team);
        }
        competition.setTeams(teams);

        competitionRepository.save(competition);
        generateLeagueSchedule(competition);
        return competition;
    }

    public Competition createTournamentCompetition(String name, Map<String, List<String>> groupTeams) {
        Competition competition = new Competition();
        competition.setName(name);
        competition.setType(CompetitionType.TOURNAMENT);

        List<Team> teams = new ArrayList<>();
        for (Map.Entry<String, List<String>> groupEntry : groupTeams.entrySet()) {
            String groupName = groupEntry.getKey();
            List<String> teamNames = groupEntry.getValue();

            for (String teamName : teamNames) {
                Team team = new Team();
                team.setName(teamName);
                team.setGroupName(groupName);
                team.setCompetition(competition);
                teams.add(team);
            }
        }
        competition.setTeams(teams);

        competitionRepository.save(competition);
        generateTournamentSchedule(competition, groupTeams);
        return competition;
    }

    private void generateLeagueSchedule(Competition competition) {
        List<Team> teams = competition.getTeams();
        int totalRounds = teams.size() - 1;

        for (int roundNumber = 1; roundNumber <= totalRounds; roundNumber++) {
            Round round = new Round();
            round.setName("Runda " + roundNumber);
            round.setCompetition(competition);

            for (int i = 0; i < teams.size() / 2; i++) {
                int teamAIndex = (roundNumber + i) % teams.size();
                int teamBIndex = (teams.size() - 1 - i + roundNumber) % teams.size();

                Match match = new Match();
                match.setTeamA(teams.get(teamAIndex).getName());
                match.setTeamB(teams.get(teamBIndex).getName());
                match.setRound(round);
                match.setCompetition(competition);
                matchRepository.save(match);
            }
        }
    }

    private void generateTournamentSchedule(Competition competition, Map<String, List<String>> groupTeams) {
        for (Map.Entry<String, List<String>> groupEntry : groupTeams.entrySet()) {
            String groupName = groupEntry.getKey();
            List<String> teamNames = groupEntry.getValue();

            for (int i = 0; i < teamNames.size(); i++) {
                for (int j = i + 1; j < teamNames.size(); j++) {
                    Match match = new Match();
                    match.setTeamA(teamNames.get(i));
                    match.setTeamB(teamNames.get(j));
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
        for (TournamentMatch tournamentMatch : bracketMatches) {
            Team teamA = findTeamByName(competition, tournamentMatch.getTeamA());
            Team teamB = findTeamByName(competition, tournamentMatch.getTeamB());

            // Utwórz nowy mecz
            Match match = new Match();
            match.setTeamA(teamA.getName());
            match.setTeamB(teamB.getName());
            match.setCompetition(competition);
            match.setGroupName(null);  // W przypadku drabinki nie ma grup
            match.setStatus(MatchStatus.NOT_STARTED);  // Mecz jeszcze nie został rozegrany
            matchRepository.save(match);
            tournamentMatches.add(match);
        }

        return tournamentMatches;
    }

    // Pomocnicza metoda do wyszukiwania drużyny po nazwie
    private Team findTeamByName(Competition competition, String teamName) {
        return competition.getTeams().stream()
                .filter(team -> team.getName().equals(teamName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Team " + teamName + " not found in competition"));
    }

    public void beginCompetition(Long competitionId) {
        Competition competition = competitionRepository.findById(competitionId)
                .orElseThrow(() -> new IllegalArgumentException("Competition not found"));

        competition.beginCompetition();

    }
}



