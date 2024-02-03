/**
 * Bishop piece.
 */
public class Bishop extends ChessPiece{
  public Bishop(Colour colour) {
    super(colour);
  }
/**
 * validate piece move.
 * @param originRow the row of the origin tile
 * @param originCol the column of the origin tile
 * @param destinationRow the row of the destination tile
 * @param destinationCol the column of the destination tile
 * @param capture the move type
 * @return boolean
 */
  @Override
  public boolean isValidMove(int originRow, int originCol, int destinationRow, int destinationCol, boolean capture) {
    return (Math.abs(destinationCol - originCol)
            == Math.abs(destinationRow - originRow)
            && (destinationCol != originCol && destinationRow != originRow));
  }

  /**
   * get unicode symbol.
   * @return unicode.
   */
  @Override
  String getUnicodeSymbol() {
    return getColour().equals(Colour.WHITE) ? "\u2657" : "\u265D";
  }
}
