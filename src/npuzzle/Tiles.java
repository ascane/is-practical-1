package npuzzle;

import java.util.LinkedHashSet;
import java.util.Set;

import search.Action;
import search.State;

public class Tiles implements State {
  public static final int EMPTY_TILE = 0;

  protected final int width;
  protected final int[] tiles;
  protected final int emptyTileRow;
  protected final int emptyTileColumn;

  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (that == null) {
      return false;
    }
    if (getClass() != that.getClass()) {
      return false;
    }
    final Tiles other = (Tiles) that;
    if (this.width != other.width
        || this.tiles != other.tiles
        || this.emptyTileRow != other.emptyTileRow
        || this.emptyTileColumn != other.emptyTileColumn) {
      return false;
    }
    return true;
  }

  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + width;
    result = prime * result + tiles.hashCode();
    result = prime * result + emptyTileRow;
    result = prime * result + emptyTileColumn;
    return result;
  }

  public Tiles(int[][] tiles) {
    width = tiles.length;
    this.tiles = new int[width * width];
    int emptyRow = -1;
    int emptyColumn = -1;
    int index=0;
    for (int row = 0; row < width; row++)
      for (int column = 0; column < width; column++) {
        int tile = tiles[row][column];
        if (tile == EMPTY_TILE) {
          emptyRow = row;
          emptyColumn = column;
        }
        this.tiles[index++] = tile;
      }
    emptyTileRow = emptyRow;
    emptyTileColumn = emptyColumn;
  }
  protected Tiles(int width, int[] tiles, int emptyTileRow, int emptyTileColumn) {
    this.width = width;
    this.tiles = tiles;
    this.emptyTileRow = emptyTileRow;
    this.emptyTileColumn = emptyTileColumn;
  }
  public int getWidth() {
    return width;
  }
  public int getEmptyTileRow() {
    return emptyTileRow;
  }
  public int getEmptyTileColumn() {
    return emptyTileColumn;
  }
  public int getTile(int row, int column) {
    return tiles[row * width + column];
  }
  public Set<Action> getApplicableActions() {
    Set<Action> actions = new LinkedHashSet<Action>();
    for (Movement movement : Movement.values()) {
      int newEmptyTileRow = emptyTileRow + movement.deltaRow;
      int newEmptyTileColumn = emptyTileColumn + movement.deltaColumn;
      if (0 <= newEmptyTileRow && newEmptyTileRow < width && 0 <= newEmptyTileColumn & newEmptyTileColumn < width)
        actions.add(movement);
    }
    return actions;
  }
  public State getActionResult(Action action) {
    Movement movement=(Movement)action;
    int newEmptyTileRow = emptyTileRow + movement.deltaRow;
    int newEmptyTileColumn = emptyTileColumn + movement.deltaColumn;
    int[] newTiles = tiles.clone();
    newTiles[emptyTileRow * width + emptyTileColumn] = getTile(newEmptyTileRow, newEmptyTileColumn);
    newTiles[newEmptyTileRow * width + newEmptyTileColumn] = EMPTY_TILE;
    return new Tiles(width, newTiles, newEmptyTileRow, newEmptyTileColumn);
  }
}
