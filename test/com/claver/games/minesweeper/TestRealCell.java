package com.claver.games.minesweeper;

import org.junit.Test;

public class TestRealCell {

  @Test
  public void testCreateEmptyRealCell() {
    Cell cell = new RealCell(1, 2);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert !cell.isBomb();
    assert cell.isEmpty();
  }

  @Test
  public void testCreateNonEmptyRealCell() {
    Cell cell = new RealCell(1, 2, false, 3);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert !cell.isBomb();
    assert !cell.isEmpty();
    assert cell.getNumber().equals(3);
  }

  @Test
  public void testCreateBombRealCell() {
    Cell cell = new RealCell(1, 2, true, 0);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert cell.isBomb();
    assert cell.isEmpty();
  }

  @Test
  public void testCreateNonEmptyBombRealCell() {
    Cell cell = new RealCell(1, 2, true, 3);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert cell.isBomb();
    assert cell.isEmpty();
  }

  @Test
  public void testIncrementEmptyRealCell() {
    Cell cell = new RealCell(1, 2);
    cell.incrementNumber();
    assert cell.getNumber().equals(1);
  }

  @Test
  public void testIncrementNonEmptyRealCell() {
    Cell cell = new RealCell(1, 2, false, 3);
    cell.incrementNumber();
    assert cell.getNumber().equals(4);
  }

  @Test
  public void testIncrementBombRealCell() {
    Cell cell = new RealCell(1, 2, true, 3);
    cell.incrementNumber();
    assert cell.isEmpty();
  }

  @Test
  public void testPrintEmptyRealCell() {
    Cell cell = new RealCell(1, 2);
    assert cell.printBody().equals("-");
  }

  @Test
  public void testPrintNonEmptyRealCell() {
    Cell cell = new RealCell(1, 2, false, 3);
    assert cell.printBody().equals("3");
  }

  @Test
  public void testPrintBombRealCell() {
    Cell cell = new RealCell(1, 2, true, 3);
    assert cell.printBody().equals("B");
  }

}
