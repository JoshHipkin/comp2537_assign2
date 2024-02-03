/**
 * Knight chess piece.
 */
public class Knight extends ChessPiece{

  public Knight(Colour colour) {
    super(colour);
  }

  /**
   * Knight move validation
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

    return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
  }

  /**
   * get unicode symbol.
   * @return unicode.
   */
  @Override
  String getUnicodeSymbol() {
    return getColour().equals(Colour.WHITE) ? "♘" : "♞";
  }
}
