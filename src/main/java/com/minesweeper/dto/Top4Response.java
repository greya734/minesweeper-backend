package com.minesweeper.dto;

import java.util.List;

public class Top4Response {
    private String difficulte;
    private List<ScoreResponse> scores;

    public Top4Response() {}

    public Top4Response(String difficulte, List<ScoreResponse> scores) {
        this.difficulte = difficulte;
        this.scores = scores;
    }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public List<ScoreResponse> getScores() { return scores; }
    public void setScores(List<ScoreResponse> scores) { this.scores = scores; }
}