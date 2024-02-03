import java.awt.*;

/**
 * Chess Board.
 */
public class ChessBoard extends Board {

  /**
   * Constructs a chess board and adds tile objects and calls setup method to
   * place chess Pieces in starting positions.
   */
  public ChessBoard() {
    boardSize = 8;
    tiles = new Tile[boardSize][boardSize];
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        tiles[i][j] = new Tile(i, j, (i + j) % 2 == 0 ? new Color(248, 222, 179) :
                new Color(139, 69, 19));
        add(tiles[i][j]);
      }
    }
  }

  /**
   * set up the board with pieces in typical chess starting position.
   */
  public void setUp() {
    for (int i = 0; i < boardSize; i++) {
      for (int j = 0; j < boardSize; j++) {
        // Place pieces based on their starting positions:
        if (i == 0) {
          switch (j) {
            case 0, 7 ->
                    tiles[i][j].addPiece(new Rook(ChessPiece.Colour.BLACK));
            case 1, 6 ->
                    tiles[i][j].addPiece(new Knight(ChessPiece.Colour.BLACK));
            case 2, 5 ->
                    tiles[i][j].addPiece(new Bishop(ChessPiece.Colour.BLACK));
            case 3 -> tiles[i][j].addPiece(new Queen(ChessPiece.Colour.BLACK));
            case 4 -> tiles[i][j].addPiece(new King(ChessPiece.Colour.BLACK));
          }
        } else if (i == 1) {
          tiles[i][j].addPiece(new Pawn(ChessPiece.Colour.BLACK));
        } else if (i == 6) {
          tiles[i][j].addPiece(new Pawn(ChessPiece.Colour.WHITE));
        } else if (i == 7) {
          switch (j) {
            case 0, 7 ->
                    tiles[i][j].addPiece(new Rook(ChessPiece.Colour.WHITE));
            case 1, 6 ->
                    tiles[i][j].addPiece(new Knight(ChessPiece.Colour.WHITE));
            case 2, 5 ->
                    tiles[i][j].addPiece(new Bishop(ChessPiece.Colour.WHITE));
            case 3 -> tiles[i][j].addPiece(new Queen(ChessPiece.Colour.WHITE));
            case 4 -> tiles[i][j].addPiece(new King(ChessPiece.Colour.WHITE));
          }
        }
      }
    }
  }


  /**
   * get tile at coordinates.
   *
   * @param row the row of the target tile.
   * @param col the column of the target tile.
   * @return the tile
   */
  public Tile getTile(int row, int col) {
    return tiles[row][col];
  }


  @Override
  public Board[] get3DBoard() {
    return new Board[0];
  }

  /**
   * Check path type and if path is clear
   * @param from the tile the piece is moving from.
   * @param to the tile the piece is moving to.
   * @return boolean
   */
  public boolean checkPath(Tile from, Tile to) {
    // Check horizontal path
    if (from.getRow() == to.getRow()) {
      int row = from.getRow();
      int left = Math.min(from.getCol(), to.getCol());
      int right = Math.max(from.getCol(), to.getCol());
      for (int col = left + 1; col < right; col++) {
        //check if any tile within path is occupied.
        if (tiles[row][col].isOccupied()) {
          return false;
        }
      }
    }

    // Check vertical path
    else if (from.getCol() == to.getCol()) {
      int col = from.getCol();
      int top = Math.min(from.getRow(), to.getRow());
      int bottom = Math.max(from.getRow(), to.getRow());
      for (int row = top + 1; row < bottom; row++) {
        //check if any tile within path is occupied.
        if (tiles[row][col].isOccupied()) {
          return false; // Path is not clear
        }
      }
    }

    // Check diagonal path
    else if (Math.abs(to.getRow() - from.getRow()) == Math.abs(to.getCol() - from.getCol())) {
      int rowStep = Integer.signum(to.getRow() - from.getRow());
      int colStep = Integer.signum(to.getCol() - from.getCol());
      int currRow = from.getRow() + rowStep;
      int currCol = from.getCol() + colStep;
      while (currRow != to.getRow() && currCol != to.getCol()) {
        //check if any tile within path is occupied.
        if (tiles[currRow][currCol].isOccupied()) {
          return false; // Path is not clear
        }
        currRow += rowStep;
        currCol += colStep;
      }
    }

    return true;
  }
}
