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
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, false, 3);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert !cell.isBomb();
    assert !cell.isEmpty();
    assert cell.getNumber().equals(3);
  }

  @Test
  public void testCreateBombRealCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, true, 0);
    assert cell.getRow().equals(1);
    assert cell.getColumn().equals(2);
    assert !cell.isRevealed();
    assert cell.isBomb();
    assert cell.isEmpty();
  }

  @Test
  public void testCreateNonEmptyBombRealCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, true, 3);
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
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, false, 3);
    cell.incrementNumber();
    assert cell.getNumber().equals(4);
  }

  @Test
  public void testIncrementBombRealCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, true, 3);
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
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, false, 3);
    assert cell.printBody().equals("3");
  }

  @Test
  public void testPrintBombRealCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, true, 3);
    assert cell.printBody().equals("B");
  }

  @Test
  public void testFlagUnflagCell() {
    Position position = new Position(1, 1);
    Cell cell = new RealCell(position, false, 2);
    assert !cell.isFlagged();
    cell.toggleFlag();
    assert cell.isFlagged();
    cell.toggleFlag();
    assert !cell.isFlagged();
  }

  @Test
  public void testRevealAlreadyRevealedCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, false, 2);
    cell.setRevealed(true);
    assert !cell.reveal();
    assert cell.isRevealed();
  }

  @Test
  public void testRevealFlaggedCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, false, 2);
    cell.setFlagged(true);
    assert !cell.reveal();
    assert !cell.isRevealed();
  }

  @Test
  public void testFlagAlreadyRevealedCell() {
    Position position = new Position(1, 2);
    Cell cell = new RealCell(position, false, 2);
    cell.setRevealed(true);
    assert !cell.toggleFlag();
    assert !cell.isFlagged();
  }

}
