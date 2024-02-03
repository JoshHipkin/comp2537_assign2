import javax.swing.*;
import java.awt.*;

public abstract class Board extends JPanel {
  protected Tile[][] tiles;
  protected Tile highlightedTile;
  protected int boardSize;


  public abstract void setUp();

  public abstract boolean checkPath(Tile from, Tile to);

  public abstract Tile getTile(int row, int col);

  public abstract Board[] get3DBoard();

}
