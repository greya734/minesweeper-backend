package com.minesweeper.dto;

public class GameState {
    private String gameId;
    private int rows;
    private int cols;
    private int mineCount;
    private int revealedCount;
    private int flags;
    private boolean gameOver;
    private boolean won;
    private int seconds;
    private CellState[][] board;

    public GameState() {}

    public GameState(String gameId, int rows, int cols, int mineCount, int revealedCount,
                     int flags, boolean gameOver, boolean won, int seconds, CellState[][] board) {
        this.gameId = gameId;
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        this.revealedCount = revealedCount;
        this.flags = flags;
        this.gameOver = gameOver;
        this.won = won;
        this.seconds = seconds;
        this.board = board;
    }

    public String getGameId() { return gameId; }
    public void setGameId(String gameId) { this.gameId = gameId; }

    public int getRows() { return rows; }
    public void setRows(int rows) { this.rows = rows; }

    public int getCols() { return cols; }
    public void setCols(int cols) { this.cols = cols; }

    public int getMineCount() { return mineCount; }
    public void setMineCount(int mineCount) { this.mineCount = mineCount; }

    public int getRevealedCount() { return revealedCount; }
    public void setRevealedCount(int revealedCount) { this.revealedCount = revealedCount; }

    public int getFlags() { return flags; }
    public void setFlags(int flags) { this.flags = flags; }

    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }

    public boolean isWon() { return won; }
    public void setWon(boolean won) { this.won = won; }

    public int getSeconds() { return seconds; }
    public void setSeconds(int seconds) { this.seconds = seconds; }

    public CellState[][] getBoard() { return board; }
    public void setBoard(CellState[][] board) { this.board = board; }
}
