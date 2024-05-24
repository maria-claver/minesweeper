package com.claver.games.minesweeper;

public class RealCell extends Cell {
  public RealCell(Integer row, Integer column) {
    super(row, column);
  }

  public RealCell(Integer row, Integer column, boolean bomb, Integer number) {
    super(row, column, bomb, number);
  }

  @Override
  public void incrementNumber() {
    if (!isBomb()) {
      setNumber(getNumber() + 1);
    }
  }

  @Override
  public boolean reveal() {
    if (isBomb()) {
      throw new UnsupportedOperationException();
    }
    if (!isRevealed()) {
      setRevealed(true);
      return true;
    }
    return false;
  }

  @Override
  public String printBody() {
    if (isBomb()) {
      return "B";
    } else if (getNumber() > 0) {
      return getNumber() + "";
    } else {
      return "-";
    }
  }
}