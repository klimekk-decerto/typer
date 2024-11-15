package com.decerto.typer.competition;


import jakarta.persistence.*;


@Entity
public class Prediction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Integer predictedScoreA;
    private Integer predictedScoreB;
    private Boolean isDraw;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPredictedScoreA() {
        return predictedScoreA;
    }

    public void setPredictedScoreA(Integer predictedScoreA) {
        this.predictedScoreA = predictedScoreA;
    }

    public Integer getPredictedScoreB() {
        return predictedScoreB;
    }

    public void setPredictedScoreB(Integer predictedScoreB) {
        this.predictedScoreB = predictedScoreB;
    }

    public Boolean getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(Boolean isDraw) {
        this.isDraw = isDraw;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}

