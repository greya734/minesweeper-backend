package com.minesweeper.dto;

public class ScoreResponse {
    private Long id;
    private String pseudo;
    private String difficulte;
    private Integer temps;
    private String date;
    private Integer rang;

    public ScoreResponse() {}

    public ScoreResponse(Long id, String pseudo, String difficulte, Integer temps, String date, Integer rang) {
        this.id = id;
        this.pseudo = pseudo;
        this.difficulte = difficulte;
        this.temps = temps;
        this.date = date;
        this.rang = rang;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public Integer getTemps() { return temps; }
    public void setTemps(Integer temps) { this.temps = temps; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Integer getRang() { return rang; }
    public void setRang(Integer rang) { this.rang = rang; }
}
