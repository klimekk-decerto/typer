package com.decerto.typer.competition;


public class TournamentMatch {
    private String teamA;
    private String teamB;

    // Konstruktor
    public TournamentMatch(String teamA, String teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    // Gettery i settery
    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }
}

