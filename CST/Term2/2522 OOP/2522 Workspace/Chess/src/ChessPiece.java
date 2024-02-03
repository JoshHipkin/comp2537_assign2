import java.awt.*;

/**
 * Abstract Chess piece class.
 */
public abstract class ChessPiece {

  /**
   * Chess piece colour.
   */
  public enum Colour {
    WHITE, BLACK
  }

  protected boolean firstMove;
  protected final Colour colour;

  /**
   * Chess piece super constructor.
   * @param colour the colour of the piece.
   */
  public ChessPiece(Colour colour) {
    this.colour = colour;
    firstMove = true;
  }

  public abstract boolean isValidMove(int originRow, int originCol,
                                      int destinationRow, int destinationCol, boolean capture);
  abstract String getUnicodeSymbol();

  public Colour getColour() {
    return colour;
  }

  public void toggleFirstMove() {
    firstMove = false;
  }
}
