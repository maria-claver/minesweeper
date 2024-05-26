package com.claver.games.minesweeper;

public abstract class Cell {
  private final Position position;

  private boolean bomb;
  private Integer number;
  private boolean revealed;
  private boolean flagged;

  protected Cell(Integer row, Integer column) {
    this(new Position(row, column));
  }

  protected Cell(Position position) {
    this(position, false, 0);
  }

  protected Cell(Position position, boolean bomb, Integer number) {
    this.position = position;
    this.bomb = bomb;
    if (!bomb) {
      this.number = number;
    } else {
      this.number = 0;
    }
    this.revealed = false;
    this.flagged = false;
  }

  public Position getPosition() {
    return this.position;
  }

  public Integer getRow() {
    return this.position.row;
  }

  public Integer getColumn() {
    return this.position.column;
  }

  public boolean isBomb() {
    return this.bomb;
  }

  public Integer getNumber() {
    return this.number;
  }

  public boolean isRevealed() {
    return this.revealed;
  }

  public boolean isFlagged() {
    return this.flagged;
  }

  public void setBomb(boolean bomb) {
    this.bomb = bomb;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public void setRevealed(boolean revealed) {
    this.revealed = revealed;
  }

  public void setFlagged(boolean flagged) {
    this.flagged = flagged;
  }


  public abstract boolean reveal();

  public abstract boolean toggleFlag();

  public boolean isEmpty() {
    return number == 0;
  }

  public abstract void incrementNumber();


  public String toString() {
    return print(false);
  }

  public String print(boolean showAll) {
    String prefix = showAll && !isRevealed() ? "(" : " ";
    String suffix = showAll && !isRevealed() ? ")" : " ";
    String body = " ";
    if (isFlagged()) {
      body = "F";
    } else if (showAll || isRevealed()) {
      body = printBody();
    }
    return prefix + body + suffix;
  }

  public abstract String printBody();
}

