import javax.swing.*;
import java.awt.*;

/**
 * Tile class of Jpanel.
 */
public class Tile extends JPanel {
  private ChessPiece piece;
  Color color;
  private final int row, col;
  private static final Color highlightColor = new Color(255, 255, 0, 128);
  private boolean isHighlighted = false;

  /**
   * Constructs a Tile object to
   * @param row the row of the tile.
   * @param col the colomn of the tile.
   * @param color the color of the tile.
   */
  public Tile(int row, int col, Color color) {
    this.piece = null;
    this.color = color;
    this.row = row;
    this.col = col;
    setBackground(this.color);

  }

  /**
   * painting instructions for highlighting and chess pieces.
   * @param g the <code>Graphics</code> object to protect
   */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    if (isHighlighted) {
      g.setColor(highlightColor);
      g.fillRect(0, 0, getWidth(), getHeight());
    }
    if (piece != null) {
      g.setFont(new Font("SansSerif", Font.BOLD, 40));
      g.setColor(piece.getColour().equals(ChessPiece.Colour.WHITE) ? Color.WHITE : Color.BLACK);
      String symbol = piece.getUnicodeSymbol();
      int stringWidth = g.getFontMetrics().stringWidth(symbol);
      int stringHeight = g.getFontMetrics().getAscent();
      g.drawString(symbol, (getWidth() - stringWidth) / 2, getHeight() / 2
        + stringHeight / 2);
    }
  }

  /**
   * highlight this tile.
   */
  public void highlight() {
    isHighlighted = true;
    repaint();
  }

  /**
   * unhighlight this tile.
   */
  public void unhighlight() {
    isHighlighted = false;
    repaint();
  }

  /**
   * size of each tile in displayer.
   * @return the size of tile.
   */
  public Dimension getPreferredSize() {
    return new Dimension(60, 60);
  }

  public boolean isOccupied() {
    return !(piece == null);
  }

  /**
   * add chess piece to this tile and update the display.
   * @param piece the piece to add.
   */
  public void addPiece(ChessPiece piece) {
    this.piece = piece;
    repaint();
  }

  /**
   * remove piece from tile and update display.
   */
  public void removePiece() {
    this.piece = null;
    repaint();
  }

  /**
   * get piece.
   * @return the piece.
   */
  public ChessPiece getPiece() {
    return piece;
  }

  /**
   * get Row
   * @return the row.
   */
  public int getRow() {
    return row;
  }

  /**
   * get column.
   * @return the column.
   */
  public int getCol() {
    return col;
  }
}
