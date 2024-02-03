import java.awt.*;

/**
 * 3D version of a chessboard.
 */
public class ChessBoard3D extends Board{
  protected Board[] board;

  /**
   * Constructs an array of chessboards.
   */
  public ChessBoard3D() {
    board = new ChessBoard[3];
    for (int i = 0; i < board.length; i++) {
      board[i] = new ChessBoard();
    }
  }

  /**
   * Sets up pieces for the bottom board only.
   */
  @Override
  public void setUp() {
    board[0].setUp();
  }

  /**
   * Checks if the selected path is either 3D or is on same board and calls
   * method already defined in ChessBoard if movement is on same board. If path
   * is 3D, calls helper method 'check3DPath'.
   * @param from the origin tile of the moving piece.
   * @param to the destination tile of the moving piece.
   * @return boolean
   */
  @Override
  public boolean checkPath(Tile from, Tile to) {
    if (from.getParent().equals(to.getParent())) {
      Board chessBoard = (Board) from.getParent();
      return chessBoard.checkPath(from, to);
    }
    return check3DPath((Board)from.getParent(), from, (Board)to.getParent(), to);
  }

  /**
   * helper method for checking 3D path.
   * @param fromB the origin board of the moving piece.
   * @param fromT  the origin tile of the moving piece.
   * @param toB the destination board of the moving piece.
   * @param toT the destination tile of the moving piece.
   * @return boolean.
   */
  private boolean check3DPath(Board fromB, Tile fromT, Board toB, Tile toT) {
    //checks if path is straight line first. If not, no need to check for blocks.
    if (!isStraightLine(fromT, toT)) {
      return true;
    }

    try {
      int boardDiff = Math.abs((getIndex(fromB)) - getIndex(toB));
      int RowDiff = Math.abs(toT.getRow() - fromT.getRow());
      int colDiff = Math.abs(toT.getCol() - fromT.getCol());

      //checks if move is valid within the 'stepping' parameters.
      if (boardDiff == RowDiff || boardDiff == colDiff) {

        //board difference is only 1 so no tiles in between.
        if (boardDiff == 1) {
          return true;
        } else {
          int checkRow = (toT.getRow() + fromT.getRow()) / 2;
          int checkCol = (toT.getCol() + fromT.getCol()) / 2;
          return !(board[1].getTile(checkRow, checkCol).isOccupied());
        }
      }
    } catch (Exception e) {
      System.out.println("Clicked Board not found!");
    }
    return false;
  }

  // this method is never called on a 3D board.
  @Override
  public Tile getTile(int row, int col) {
    return null;
  }

  /**
   * Helper method for finding straight line between tiles.
   * @param from the origin tile of the moving piece.
   * @param to the destination tile of the moving piece.
   * @return boolean.
   */
  private boolean isStraightLine(Tile from, Tile to) {
    return from.getRow() == to.getRow() || from.getCol() == to.getCol()
            || Math.abs(to.getRow() - from.getRow()) == Math.abs(to.getCol() - from.getCol());
  }

  /**
   * Accessor method for 3D chessBoard.
   * @return array of ChessBoards
   */
  public Board[] get3DBoard() {
    return board;
  }

  /**
   * Finds the index of an instance of a clicked board.
   * @param board the board thats been clicked.
   * @return int
   */
  private int getIndex(Board board) throws Exception {
    for (int i = 0; i < this.board.length; i++) {
      if (board.equals(this.board[i])) {
        return i;
      }
    }
    throw new Exception("Board not found!");
  }
}
