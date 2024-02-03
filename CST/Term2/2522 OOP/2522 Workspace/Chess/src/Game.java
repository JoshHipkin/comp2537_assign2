import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Chess game class
 */
public class Game extends MouseAdapter {

  /**
   * the currently selected tile
   */
  private Tile selectedTile;
  protected Board chessBoard;
  private final Displayer displayer;
  private ChessPiece.Colour turn;

  /**
   * Creates a new game
   */
  public Game() {
    chessBoard = new ChessBoard3D();
    chessBoard.setUp();
    displayer = new Displayer(this);
    selectedTile = null;
    turn = ChessPiece.Colour.WHITE;
  }

  /**
   * Calls display method from displayer class.
   */
  public void display() {
    displayer.display();
  }

  /**
   * Validates whether a piece can be moved according to the boards and pieces
   * rules and moves said piece.
   * @param from the origin tile of the piece to be moved
   * @param to the destination tile of the piece to be moved.
   */
  public void movePiece(Tile from, Tile to) {
    // check if move is a capture
    boolean capture = to.isOccupied();
    //Check for appropriate turn
    if (selectedTile.getPiece().getColour().equals(turn)) {
      //board validating path
      if (chessBoard.checkPath(from, to)) {
        //piece validating move
        if (from.getPiece().isValidMove(from.getRow(), from.getCol(),
                to.getRow(), to.getCol(), capture)) {

          if (capture) {
            to.removePiece();
          }
          to.addPiece(from.getPiece());
          from.removePiece();
          selectedTile.unhighlight();
          selectedTile = null;
          to.unhighlight();
          if (to.getPiece().firstMove) {
            to.getPiece().toggleFirstMove();
          }
          //Move is successful, swap turns.
          takeTurn();
        }
      }
    }
  }

  /**
   * getter for games chess board.
   * @return board
   */
  public Board[] getBoard() {
    return chessBoard.get3DBoard();
  }

  /**
   * Handles both first and second click.
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    if (!(e.getSource() instanceof Tile clickedTile)) {
      return;
    }
      if (selectedTile != null && selectedTile == clickedTile) {
        // Clicked the same tile, unselect it.
        selectedTile.unhighlight();
        selectedTile = null;
      } else if (selectedTile != null && selectedTile.isOccupied() && clickedTile.isOccupied() &&
              !clickedTile.getPiece().getColour().equals(selectedTile.getPiece().getColour())) {
        // Clicked an occupied tile of a different color, attempt to capture.
        movePiece(selectedTile, clickedTile);
      } else if (selectedTile != null && selectedTile.isOccupied() && !clickedTile.isOccupied()) {
        // Clicked an empty tile, attempt to move.
        movePiece(selectedTile, clickedTile);
      } else if (clickedTile.isOccupied() && clickedTile.getPiece().getColour().equals(turn)) {
        // Clicked a new tile with a piece of the correct color, select it.
        if (selectedTile != null) {
          selectedTile.unhighlight(); // Unhighlight the previously selected tile.
        }
        clickedTile.highlight();
        selectedTile = clickedTile;
      } else {
        // Clicked a non-selectable tile or a piece of the opposite color, ignore or reset selection.
        if (selectedTile != null) {
          selectedTile.unhighlight();
          selectedTile = null;
        }
      }
  }

  /**
   * alternates turn.
   */
  public void takeTurn() {
    turn = (turn == ChessPiece.Colour.WHITE) ? ChessPiece.Colour.BLACK : ChessPiece.Colour.WHITE;

  }
}
