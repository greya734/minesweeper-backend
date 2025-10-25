package com.minesweeper.dto;

import jakarta.validation.constraints.*;

public class GameRequest {
    @NotBlank(message = "La difficulté est obligatoire")
    private String difficulte;

    @Min(value = 5, message = "Minimum 5 lignes")
    @Max(value = 40, message = "Maximum 40 lignes")
    private Integer rows;

    @Min(value = 5, message = "Minimum 5 colonnes")
    @Max(value = 60, message = "Maximum 60 colonnes")
    private Integer cols;

    @Min(value = 1, message = "Minimum 1 mine")
    private Integer mines;

    public GameRequest() {}

    public GameRequest(String difficulte, Integer rows, Integer cols, Integer mines) {
        this.difficulte = difficulte;
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;
    }

    public String getDifficulte() { return difficulte; }
    public void setDifficulte(String difficulte) { this.difficulte = difficulte; }

    public Integer getRows() { return rows; }
    public void setRows(Integer rows) { this.rows = rows; }

    public Integer getCols() { return cols; }
    public void setCols(Integer cols) { this.cols = cols; }

    public Integer getMines() { return mines; }
    public void setMines(Integer mines) { this.mines = mines; }
}