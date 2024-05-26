package com.claver.games.minesweeper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MineSweeperMain {

  public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

  public static void main(String[] args) {
    System.out.println("***************************");
    System.out.println("* Welcome to MineSweeper! *");
    System.out.println("***************************");

    try {
      play();
    } catch (IOException e) {
      System.out.println("Ooopsie! Error reading from terminal! :S\n" + e.getMessage());
    }
  }

  private static void play() throws IOException {
    MineSweeper mineSweeper = createParametrizedMineSweeper();

    System.out.println("Let's play!");

    boolean finished = false;
    while (!finished) {
      System.out.println(mineSweeper);
      System.out.println("Number of unrevealed cells: " + mineSweeper.getUnrevealedCells());
      System.out.println("Number of flagged cells: " + mineSweeper.getFlagCounter());
      System.out.println("> Enter row of cell : ");
      Integer row = readIntegerFromTerminal(mineSweeper.getNumRows());
      System.out.println("> Enter column of cell : ");
      Integer column = readIntegerFromTerminal(mineSweeper.getNumColumns());
      System.out.println("Flag/UnFlag (F) or Reveal the cell (any other key) ?");
      boolean flag = readBooleanFromTerminal("F");
      if (flag) {
        mineSweeper.toggleFlag(row, column);
      } else {
        try {
          mineSweeper.reveal(row, column);
        } catch (UnsupportedOperationException e) {
          mineSweeper.revealBombs();
          System.out.println(mineSweeper);
          System.out.println("KABOOM!!! Sorry, you lost :(\n");
          finished = true;
        }
        if (mineSweeper.areAllCellsRevealed()) {
          System.out.println(mineSweeper);
          System.out.println("YAYYY!!! YOU WON! :D\n");
          finished = true;
        }
      }
    }

    System.out.println("Wanna play again (Y) or exit (any other key) ?");
    if (readBooleanFromTerminal("Y")) {
      play();
    } else {
      quit();
    }
  }

  private static MineSweeper createParametrizedMineSweeper() throws IOException {
    System.out.println("Let's create the board:");

    Integer numRows = 0;
    Integer numColumns = 0;
    boolean correctSize = false;
    while (!correctSize) {
      numRows = getNumRows();
      numColumns = getNumColumns();
      if (numRows * numColumns <= 1) {
        System.out.println("Board is too small!, please try another size:");
      } else {
        correctSize = true;
      }
    }
    Integer numBombs = getNumBombs(numRows, numColumns);

    return new MineSweeper(numRows, numColumns, numBombs);
  }

  private static Integer getNumRows() throws IOException {
    Integer defaultNumRows = MineSweeper.DEFAULT_NUM_ROWS;
    Integer minRows = MineSweeper.MIN_ROWS;
    Integer maxRows = MineSweeper.MAX_ROWS;
    System.out.println("> Enter number of rows [" + minRows + " - " + maxRows + "]" +
        " (or press Enter for default=" + defaultNumRows + "): ");
    return readIntegerFromTerminal(defaultNumRows, minRows, maxRows);
  }

  private static Integer getNumColumns() throws IOException {
    Integer defaultNumColumns = MineSweeper.DEFAULT_NUM_COLUMNS;
    Integer minColumns = MineSweeper.MIN_COLUMNS;
    Integer maxColumns = MineSweeper.MAX_COLUMNS;
    System.out.println("> Enter number of columns [" + minColumns + " - " + maxColumns + "]" +
        " (or press Enter for default=" + defaultNumColumns + "): ");
    return readIntegerFromTerminal(defaultNumColumns, minColumns, maxColumns);
  }

  private static Integer getNumBombs(Integer numRows, Integer numColumns) throws IOException {
    Integer defaultNumBombs = MineSweeper.defaultNumBombs(numRows, numColumns);
    Integer minBombs = MineSweeper.MIN_BOMBS;
    Integer maxBombs = MineSweeper.maxNumBombs(numRows, numColumns);
    System.out.println("> Enter number of bombs [" + minBombs + " - " + maxBombs + "]" +
        " (or press Enter for default=" + defaultNumBombs + "): ");
    return readIntegerFromTerminal(defaultNumBombs, minBombs, maxBombs);
  }

  private static Integer readIntegerFromTerminal(Integer defaultValue, Integer minValue, Integer maxValue) throws IOException {
    Integer value = null;
    while (value == null) {
      try {
        String stringValue = reader.readLine();
        if (stringValue.isEmpty()) {
          value = defaultValue;
        } else {
          value = Integer.valueOf(stringValue);
          if (value < minValue) {
            System.out.println("Too small! Please, try again: ");
            value = null;
          } else if (value > maxValue) {
            System.out.println("Too big! Please, try again: ");
            value = null;
          }
        }
      } catch (NumberFormatException e) {
        System.out.println("That's not a valid number! Please, try again: ");
      }
    }
    return value;
  }

  private static Integer readIntegerFromTerminal(Integer maxValue) throws IOException {
    Integer value = null;
    while (value == null) {
      try {
        String stringValue = reader.readLine();
        value = Integer.valueOf(stringValue);
        if (value < 1) {
          System.out.println("Too small! Please, try again: ");
          value = null;
        } else if (value > maxValue) {
          System.out.println("Too big! Please, try again: ");
          value = null;
        }
      } catch (NumberFormatException e) {
        System.out.println("Sorry, didn't catch that, could you please try again?");
      }
    }
    return value;
  }

  private static boolean readBooleanFromTerminal(String key) throws IOException {
    try {
      String stringValue = reader.readLine();
      return stringValue.equalsIgnoreCase(key);
    } catch (IOException e) {
      System.out.println("ERROR reading from terminal: " + e.getMessage());
      throw e;
    }
  }

  private static void quit() {
    System.out.println("Thank you for playing with me! Bye!");
    System.out.println("             _ o . o _/¨           ");
    System.exit(0);
  }

}
