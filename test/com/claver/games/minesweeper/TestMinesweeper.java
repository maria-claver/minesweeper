package com.claver.games.minesweeper;

import org.junit.Test;

public class TestMinesweeper {

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMineSweeper() {
    new MineSweeper(3, 4, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBarelyIllegalMineSweeper() {
    new MineSweeper(2, 2, 4);
  }

  @Test
  public void testDefaultMineSweeper() {
    MineSweeper mineSweeper = new MineSweeper();
    assert mineSweeper.getNumRows().equals(MineSweeper.DEFAULT_NUM_ROWS);
    assert mineSweeper.getNumColumns().equals(MineSweeper.DEFAULT_NUM_COLUMNS);
  }

  @Test
  public void testBigMineSweeper() {
    MineSweeper mineSweeper = new MineSweeper(99, 99, 1);
    assert mineSweeper.getNumRows().equals(99);
    assert mineSweeper.getNumColumns().equals(99);
    mineSweeper.reveal(50, 50);
  }

  @Test
  public void testDefaultNumBombs() {
    MineSweeper mineSweeper = new MineSweeper(1, 2, null);
    assert mineSweeper.getNumBombs().equals(1);
    mineSweeper = new MineSweeper(3, 3, null);
    assert mineSweeper.getNumBombs().equals(1);
    mineSweeper = new MineSweeper(2, 5, null);
    assert mineSweeper.getNumBombs().equals(2);
    mineSweeper = new MineSweeper(99, 99, null);
    assert mineSweeper.getNumBombs().equals(981);
  }

  @Test
  public void testCustomMineSweeper() {
    MineSweeper mineSweeper = new MineSweeper(2, 3, 4);
    assert mineSweeper.getNumRows().equals(2);
    assert mineSweeper.getNumColumns().equals(3);
    assert mineSweeper.getNumBombs().equals(4);
  }

  @Test
  public void testRevealEmptyCell() {
    MineSweeper mineSweeper = new MineSweeper(30, 30, 1);
    mineSweeper.reveal(1, 1);
    String result = mineSweeper.toString();
    assert (result.length() - result.replaceAll("-","").length()) > 1;
  }

  @Test(expected = UnsupportedOperationException.class)
  public void testRevealBomb() {
    MineSweeper mineSweeper = new MineSweeper(2, 2, 1);
    mineSweeper.getCells().get(0).setBomb(true);
    mineSweeper.reveal(1, 1);
  }

  @Test
  public void testRevealAllBombs() {
    MineSweeper mineSweeper = new MineSweeper(2, 2, 3);
    mineSweeper.revealBombs();
    String result = mineSweeper.toString();
    System.out.println(result);
    assert (result.length() - result.replaceAll("B","").length()) == 3;
    assert !result.contains("(");
  }


}
