package com.claver.games.minesweeper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MineSweeper {
  // grid of n*m cells
  // some cells in grid are bombs, rest are number of adjacent bombs or empty
  // cell, can be hidden or revealed. is bomb, number or empty
  // action: reveal a cell when you click on it:
  //  if bomb, explode
  //  if number, set as revealed and show number
  //  if empty, set as revealed and show itself and reveal all contiguous cells

  public static final Integer DEFAULT_NUM_COLUMNS = 10;
  public static final Integer DEFAULT_NUM_ROWS = 10;
  public static final Integer MAX_COLUMNS = 99;
  public static final Integer MAX_ROWS = 99;
  public static final Integer MIN_BOMBS = 1;
  public static final Integer MIN_COLUMNS = 1;
  public static final Integer MIN_ROWS = 1;

  private static final Random random = new Random();

  public static Integer defaultNumBombs(Integer rows, Integer columns) {
    return rows * columns / 10 + 1;
  }
  public static Integer maxNumBombs(Integer rows, Integer columns) {
    return rows * columns - 1;
  }

  private final Integer numRows;
  private final Integer numColumns;
  private final Integer numBombs;

  private final List<List<Cell>> rows = new ArrayList<>();
  private final List<Cell> cells = new ArrayList<>();
  private final Set<Integer> bombPositions = new HashSet<>();

  private Integer unrevealedCells = 0;
  private Integer flagCounter = 0;

  public MineSweeper() {
    numRows = DEFAULT_NUM_ROWS;
    numColumns = DEFAULT_NUM_COLUMNS;
    numBombs = defaultNumBombs(numRows, numColumns);
    init();
  }

  public MineSweeper(Integer numRows, Integer numColumns, Integer numBombs) {
    this.numRows = numRows != null ? numRows : DEFAULT_NUM_ROWS;
    this.numColumns = numColumns != null ? numColumns : DEFAULT_NUM_COLUMNS;
    this.numBombs = numBombs != null ? numBombs : defaultNumBombs(this.numRows, this.numColumns);
    init();
  }

  public Integer getNumRows() {
    return numRows;
  }
  public Integer getNumColumns() {
    return numColumns;
  }
  public Integer getNumBombs() {
    return numBombs;
  }
  public List<Cell> getCells() {
    return cells;
  }
  public Integer getUnrevealedCells() {
    return unrevealedCells;
  }
  public Integer getFlagCounter() {
    return flagCounter;
  }

  public void init() {
    validateParameters();
    initUnrevealedCells();
    createCells();
    setUpRandomBombPositions();
    placeBombs();
  }

  private void validateParameters() {
    validateNumRows(numRows);
    validateNumColumns(numColumns);
    validateNumBombs(numBombs);
    if (numRows.equals(MIN_ROWS) && numColumns.equals(MIN_COLUMNS)) {
      throw new IllegalArgumentException("Too few cells!");
    }
    if (numBombs > maxNumBombs(numRows, numColumns)) {
      throw new IllegalArgumentException("Too many bombs!");
    }
  }

  private static void validateNumRows(Integer numRows) {
    if (numRows < MIN_ROWS) {
      throw new IllegalArgumentException("Too few rows!");
    }
    if (numRows > MAX_ROWS) {
      throw new IllegalArgumentException("Too manny rows!");
    }
  }

  private static void validateNumColumns(Integer numColumns) {
    if (numColumns < MIN_COLUMNS) {
      throw new IllegalArgumentException("Too few columns!");
    }
    if (numColumns > MAX_COLUMNS) {
      throw new IllegalArgumentException("Too manny columns!");
    }
  }

  private static void validateNumBombs(Integer numBombs) {
    if (numBombs < MIN_BOMBS) {
      throw new IllegalArgumentException("Too few bombs!");
    }
  }

  private void initUnrevealedCells() {
    unrevealedCells = numRows * numColumns - numBombs;
  }

  private void createCells() {
    for (int row = 0; row <= numRows + 1; row++) {
      rows.add(new ArrayList<>());
      for (int column = 0; column <= numColumns + 1; column++) {
        Cell cell;
        Position position = new Position(row, column);
        if (row == 0 || column == 0 || row > numRows || column > numColumns) {
          cell = new FakeCell(position);
        } else {
          cell = new RealCell(position);
          cells.add(cell);
        }
        rows.get(row).add(cell);
      }
    }
  }

  private void setUpRandomBombPositions() {
    for (int i=0; i<numBombs; i++) {
      boolean newPosition;
      do {
        newPosition = bombPositions.add(random.nextInt(numRows * numColumns));
      } while (!newPosition);
    }
  }

  private void placeBombs() {
    for (Integer bombPosition : bombPositions) {
      Cell bombCell = cells.get(bombPosition);
      bombCell.setBomb(true);
      Integer row = bombCell.getRow();
      Integer column = bombCell.getColumn();
      rows.get(row - 1).get(column - 1).incrementNumber();
      rows.get(row - 1).get(column).incrementNumber();
      rows.get(row - 1).get(column + 1).incrementNumber();
      rows.get(row).get(column - 1).incrementNumber();
      rows.get(row).get(column + 1).incrementNumber();
      rows.get(row + 1).get(column - 1).incrementNumber();
      rows.get(row + 1).get(column).incrementNumber();
      rows.get(row + 1).get(column + 1).incrementNumber();
    }
  }


  public void reveal(Integer row, Integer column) {
    Cell cell = rows.get(row).get(column);
    if (cell.isFlagged()) {
      System.out.println("Can't reveal a flagged cell! Unflag it first if you really want to reveal it");
    } else if (cell.isRevealed()) {
      System.out.println("Can't reveal an already revealed cell!");
    } else {
      revealCell(cell);
    }
  }

  private void revealCell(Cell cell) {
    Set<Cell> cellsToReveal = new HashSet<>();
    cellsToReveal.add(cell);
    boolean done = false;
    while (!done) {
      Set<Cell> neighbourCells = new HashSet<>();
      for (Cell cellToReveal : cellsToReveal) {
        if (!cellToReveal.isRevealed()) {
          if (cellToReveal.reveal()) {
            unrevealedCells--;
          }
          if (cellToReveal.isEmpty()) {
            neighbourCells.addAll(getNeighbourCellsToReveal(cellToReveal));
          }
        }
      }

      cellsToReveal.clear();
      cellsToReveal.addAll(neighbourCells);
      done = cellsToReveal.isEmpty();
    }
  }

  private Set<Cell> getNeighbourCellsToReveal(Cell cell) {
    List<Position> neighbouringPositions = getNeighbouringPositions(cell.getRow(), cell.getColumn());
    Set<Cell> neighbourCells = new HashSet<>();
    for (Position position : neighbouringPositions) {
      Cell neighbourCell = rows.get(position.row).get(position.column);
      if (!neighbourCell.isRevealed() && !neighbourCell.isFlagged() && neighbourCell instanceof RealCell) {
        neighbourCells.add(neighbourCell);
      }
    }
    return neighbourCells;
  }

  private static List<Position> getNeighbouringPositions(Integer row, Integer column) {
    List<Position> neighbouringPositions = new ArrayList<>();
    neighbouringPositions.add(new Position(row-1, column-1));
    neighbouringPositions.add(new Position(row-1, column));
    neighbouringPositions.add(new Position(row-1, column+1));
    neighbouringPositions.add(new Position(row, column-1));
    neighbouringPositions.add(new Position(row, column+1));
    neighbouringPositions.add(new Position(row+1, column-1));
    neighbouringPositions.add(new Position(row+1, column));
    neighbouringPositions.add(new Position(row+1, column+1));
    return neighbouringPositions;
  }

  public void revealBombs() {
    bombPositions.forEach(bombPosition -> cells.get(bombPosition).setRevealed(true));
  }

  public boolean areAllCellsRevealed() {
    return unrevealedCells <= 0;
  }


  public void toggleFlag(Integer row, Integer column) {
    // To flag a cell is to mark it as possibly a bomb, to avoid revealing it
    // To un-flag it is to reverse the flagging, to set it as revealable again
    Cell cell = rows.get(row).get(column);
    if (!cell.isRevealed()) {
      boolean wasFlagged = cell.isFlagged();
      boolean flagged = cell.toggleFlag();
      if (flagged) {
        if (!wasFlagged && cell.isFlagged()) {
          flagCounter++;
        } else if (wasFlagged && !cell.isFlagged()) {
          flagCounter--;
        }
      }
    } else {
      System.out.println("Can't flag / unflag this cell, it's already revealed!");
    }
  }


  public String toString() {
    return print(false, false);
  }

  public String print(boolean withMargin, boolean showAll) {
    int init = withMargin ? 0 : 1;
    int end = withMargin ? 1 : 0;
    StringBuilder builder = new StringBuilder("    ");
    for (int column = init; column <= numColumns + end; column++) {
      if (column < 10) {
        builder.append("  ");
      } else {
        builder.append(" ");
      }
      builder.append(column).append(" ");
    }
    builder.append("\n    ");
    for (int column = init; column <= numColumns + end; column++) {
      builder.append("----");
    }
    builder.append("-\n");
    for (int row = init; row <= numRows + end; row++) {
      if (row < 10) {
        builder.append("  ");
      } else {
        builder.append(" ");
      }
      builder.append(row).append(" |");
      for (int column = init; column <= numColumns + end; column++) {
        builder.append(rows.get(row).get(column).print(showAll)).append("|");
      }
      builder.append("\n");
    }
    builder.append("    ");
    for (int column = init; column <= numColumns + end; column++) {
      builder.append("----");
    }
    return builder.append("-\n").toString();
  }

}

