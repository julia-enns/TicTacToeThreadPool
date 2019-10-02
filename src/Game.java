
import java.io.*;

/**
 * Makes a game of TicTacToe and calls other classes such as Player, Referee, Board and Constants to help create the game.
 *
 * @author Julia Grab
 * @version 1.0
 * @since January 26, 2019
 */
public class Game implements Constants, Runnable{

    /**
     * The board the game is played on
     */
    private Board theBoard;
    /**
     * The referee that officiates the game
     */
    private Referee theRef;

    /**
     * Constructs a Game object with the default values.
     * The value of the board is given by the parameter:
     * - new Board() = theBoard
     */
    public Game() {
        theBoard  = new Board();
    }

    /**
     * Creates a referee and calls to allow the referee to start the game.
     * @param r the new referee
     * @throws IOException
     */
    public void appointReferee(Referee r) throws IOException {
        theRef = r;
    }

    /**
     * Starts a game of Tic Tac Toe
     */
    public void run() {
        theRef.setBoard(theBoard);
        theRef.getoPlayer().setBoard(theBoard);
        theRef.getxPlayer().setBoard(theBoard);
        theRef.runTheGame();
    }
}

