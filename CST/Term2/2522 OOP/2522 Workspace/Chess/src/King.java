/**
 * King chess piece.
 */
public class King extends ChessPiece{
  /**
   * King constructor.
   * @param colour the colour of the piece.
   */
  public King(Colour colour) {
    super(colour);
  }

  /**
   * move validation
   * @param originRow the row of the origin tile
   * @param originCol the column of the origin tile
   * @param destinationRow the row of the destination tile
   * @param destinationCol the column of the destination tile
   * @param capture the move type
   * @return boolean
   */
  @Override
  public boolean isValidMove(int originRow, int originCol, int destinationRow, int destinationCol, boolean capture) {
    int rowDiff = Math.abs(destinationRow - originRow);
    int colDiff = Math.abs(destinationCol - originCol);
    return rowDiff < 2 && colDiff < 2;
  }

  /**
   * get unicode.
   * @return the unicode symbol.
   */
  @Override
  String getUnicodeSymbol() {
    return getColour().equals(Colour.WHITE) ? "\u2654" : "\u265A";
  }
}
