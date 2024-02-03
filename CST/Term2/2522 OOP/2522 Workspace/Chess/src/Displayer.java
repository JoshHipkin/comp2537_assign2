import javax.swing.*;
import java.awt.*;

/**
 * Swing displayer.
 */
public class Displayer extends JFrame {
  Game game;
  JFrame frame;

  /**
   * Constructs a displayer for any given game.
   * @param game the game to be displayed.
   */
  public Displayer(Game game) {
    frame = new JFrame("Chess");
    this.game = game;
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1200, 600);

    Board[] chessBoards = game.getBoard();
    frame.setLayout(new GridLayout(1, chessBoards.length));
    for (Board chessBoard : chessBoards) {
      chessBoard.setLayout(new GridLayout(8, 8));
      chessBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

      for (int i = 0; i < 8; i++) {
        for (int j = 0; j < 8; j++) {
          Tile tile = chessBoard.getTile(i, j);
          tile.addMouseListener(game);
          chessBoard.add(tile); // Add tile directly to Board
        }
      }
      frame.add(chessBoard); // Add Board directly to JFrame
    }
    frame.setVisible(true);
  }


  /**
   * calls the repaint method for the swing frame.
   */
  public void display() {
    frame.repaint();
  }
}
