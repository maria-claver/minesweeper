package com.claver.games.minesweeper;

import org.junit.Test;

public class TestFakeCell {

  @Test
  public void testCreateFakeCell() {
    Cell cell = new FakeCell(1, 2);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert !cell.isBomb();
    assert cell.getNumber().equals(0);
  }

  @Test
  public void testIncrementFakeCell() {
    Cell cell = new FakeCell(1, 2);
    assert cell.getNumber().equals(0);
    cell.incrementNumber();
    assert cell.getNumber().equals(0);
  }

  @Test
  public void testPrintFakeCell() {
    Cell cell = new FakeCell(1, 2);
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
