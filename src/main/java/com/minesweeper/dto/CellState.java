package com.minesweeper.dto;

public class CellState {
    private boolean revealed;
    private boolean flagged;
    private boolean isMine;
    private int adjacentMines;

    public CellState() {}

    public CellState(boolean revealed, boolean flagged, boolean isMine, int adjacentMines) {
        this.revealed = revealed;
        this.flagged = flagged;
        this.isMine = isMine;
        this.adjacentMines = adjacentMines;
    }

    public boolean isRevealed() { return revealed; }
    public void setRevealed(boolean revealed) { this.revealed = revealed; }

    public boolean isFlagged() { return flagged; }
    public void setFlagged(boolean flagged) { this.flagged = flagged; }

    public boolean isMine() { return isMine; }
    public void setMine(boolean mine) { isMine = mine; }

    public int getAdjacentMines() { return adjacentMines; }
    public void setAdjacentMines(int adjacentMines) { this.adjacentMines = adjacentMines; }
}
