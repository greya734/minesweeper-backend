package com.minesweeper.dto;

import jakarta.validation.constraints.*;

public class ScoreRequest {
    @NotBlank(message = "Le pseudo est obligatoire")
    @Size(max = 50, message = "Le pseudo ne peut pas dépasser 50 caractères")
    private String pseudo;

    @NotBlank(message = "La difficulté est obligatoire")
    private String difficulte;

    @NotNull(message = "Le temps est obligatoire")
    @Min(value = 1, message = "Le temps doit être supérieur à 0")
    private Integer temps;

    public ScoreRequest() {}

    public ScoreRequest(String pseudo, String difficulte, Integer temps) {
        this.pseudo = pseudo;
        this.difficulte = difficulte;
        this.temps = temps;
    }

    public String getPseudo() { return pseudo; }
    public void setPseudo(String pseudo) { this.pseudo = pseudo; }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public Integer getTemps() { return temps; }
    public void setTemps(Integer temps) { this.temps = temps; }
}
