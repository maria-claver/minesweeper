package com.claver.games.minesweeper;

public class RealCell extends Cell {

  public RealCell(Integer row, Integer column) {
    super(row, column);
  }

  public RealCell(Position position) {
    super(position);
  }

  public RealCell(Position position, boolean bomb, Integer number) {
    super(position, bomb, number);
  }

  @Override
  public void incrementNumber() {
    if (!isBomb()) {
      setNumber(getNumber() + 1);
    }
  }

  @Override
  public boolean reveal() {
    if (!isRevealed() && !isFlagged()) {
      if (isBomb()) {
        throw new UnsupportedOperationException();
      }
      setRevealed(true);
      return true;
    }
    return false;
  }

  @Override
  public boolean toggleFlag() {
    if (!isRevealed()) {
      setFlagged(!isFlagged());
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
