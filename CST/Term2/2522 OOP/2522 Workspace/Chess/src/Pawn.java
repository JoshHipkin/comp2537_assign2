import java.awt.*;

/**
 * Pawn ChessPiece class
 */
public class Pawn extends ChessPiece {
  /**
   * Creates a ChessPiece of type Pawn.
   * @param colour the colour of the piece
   */
  public Pawn(Colour colour) {
    super(colour);
  }

  /**
   * validates piece movement
   * @param originRow the row of the origin tile
   * @param originCol the column of the origin tile
   * @param destinationRow the row of the destination tile
   * @param destinationCol the column of the destination tile
   * @param capture the move type
   * @return boolean
   */
  @Override
  public boolean isValidMove(int originRow, int originCol, int destinationRow,
                             int destinationCol, boolean capture) {
    // Direction of movement depends on the color
    int direction = (colour == Colour.WHITE) ? -1 : 1; // white moves up (+1), black moves down (-1)

    // Normal move (not capturing)
    if (!capture && originCol == destinationCol) {
      // Move one square forward
      if (destinationRow == originRow + direction) {
        return true;
      }
      // first move can move two squares
      if (firstMove && destinationRow == originRow + (2 * direction)) {
        return true;
      }
    }

    // can move diagonally if it is a capturing move
    if (capture && Math.abs(originCol - destinationCol) == 1 && destinationRow == originRow + direction) {
      return true;
    }

    // All other cases are invalid
    return false;
  }

  /**
   * Get unicode symbol for piece colour.
   * @return the unicode symbol.
   */
  @Override
  String getUnicodeSymbol() {
    return getColour().equals(Colour.WHITE) ? "♙" : "♟";
  }

}
