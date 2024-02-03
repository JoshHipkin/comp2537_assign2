/**
 * Rook chess piece.
 */
public class Rook extends ChessPiece{
  /**
   * rook constructor.
   * @param colour the colour.
   */
  public Rook(Colour colour) {
    super(colour);
  }

  /**
   * validate movement of piece
   * @param originRow the row of the origin tile
   * @param originCol the column of the origin tile
   * @param destinationRow the row of the destination tile
   * @param destinationCol the column of the destination tile
   * @param capture the move type
   * @return boolean
   */
  @Override
  public boolean isValidMove(int originRow,int originCol, int destinationRow, int destinationCol, boolean capture) {
    return originRow == destinationRow || originCol == destinationCol;
  }

  /**
   * get unicode symbol.
   * @return the unicode symbol.
   */
  @Override
  String getUnicodeSymbol() {
    return getColour().equals(Colour.WHITE) ? "\u2656" : "\u265C";
  }
}
