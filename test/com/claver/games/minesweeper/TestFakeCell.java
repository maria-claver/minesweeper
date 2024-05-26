package com.claver.games.minesweeper;

import org.junit.Test;

public class TestFakeCell {

  @Test
  public void testCreateFakeCell() {
    Position position = new Position(1, 2);
    Cell cell = new FakeCell(position);
    assert cell.getPosition().row.equals(1);
    assert cell.getPosition().column.equals(2);
    assert !cell.isRevealed();
    assert !cell.isBomb();
    assert cell.getNumber().equals(0);
  }

  @Test
  public void testIncrementFakeCell() {
    Position position = new Position(1, 2);
    Cell cell = new FakeCell(position);
    assert cell.getNumber().equals(0);
    cell.incrementNumber();
    assert cell.getNumber().equals(0);
  }

  @Test
  public void testPrintFakeCell() {
    Position position = new Position(1, 2);
    Cell cell = new FakeCell(position);
    assert cell.printBody().equals("X");
  }

  @Test
  public void testRevealFakeCell() {
    Cell cell = new FakeCell(1, 2);
    assert !cell.isRevealed();
    boolean revealed = cell.reveal();
    assert !cell.isRevealed();
    assert !revealed;
  }

}
