package com.claver.games.minesweeper;

public class FakeCell extends Cell {

  public FakeCell(Integer row, Integer column) {
    super(row, column);
  }

  @Override
  public void incrementNumber() {
    // empty method, does nothing
  }

  @Override
  public boolean reveal() {
    return false;
  }

  @Override
  public boolean isEmpty() {
    return false;
  }

  @Override
  public String printBody() {
    return "X";
  }

  @Override
  public String print(boolean showAll) {
    return " X ";
  }

}
