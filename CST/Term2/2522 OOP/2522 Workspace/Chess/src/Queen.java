/**
 * Queen chesspiece.
 */
public class Queen extends ChessPiece{
  public Queen(Colour colour) {
    super(colour);
  }

  /**
   * move validation.
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
     if (Math.abs(destinationCol - originCol)
            == Math.abs(destinationRow - originRow)
            && (destinationCol != originCol && destinationRow != originRow)) {
       return true;
     }
     if (originRow == destinationRow || originCol == destinationCol) {
       return true;
     }
    return false;
  }

  /**
   * get unicode symbol.
   * @return unicode
   */
  @Override
  String getUnicodeSymbol() {
    return getColour().equals(Colour.WHITE) ? "\u2655" : "\u265B";
  }
}
