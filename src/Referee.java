import java.io.IOException;

/**
 * Represents a referee that watches over the board and players that also calls the start of the game.
 *
 * @author Julia Grab
 * @version 1.0
 * @since January 26, 2019
 */
public class Referee {
    /**
     * The first player who uses the mark X
     */
    Player xPlayer;
    /**
     * The second player who used the mark O
     */
    Player oPlayer;
    /**
     * The board the game is played on
     */
    Board board;

    /**
     * Initializes the game of TicTacToe by setting the player's opponents and allowing them to play.
     */
    void runTheGame(){
        xPlayer.setOpponent(oPlayer);
        oPlayer.setOpponent(xPlayer);

        oPlayer.getPlayerName();
        xPlayer.getPlayerName();

        board.setPrinter(oPlayer.getSocketOut());

        board.display();
        xPlayer.play();
    }

    /**
     * Sets the value of board to the specified value.
     * @param b is the board the game is played on.
     */
    void setBoard(Board b){
        board = b;
    }

    /**
     * Sets the value of oPlayer to the specified value.
     * @param oPlayer is the player who uses the mark X
     */
    void setoPlayer(Player oPlayer){
        this.oPlayer = oPlayer;
    }

    /**
     * Sets the value of xPlayer to the specified value.
     * @param xPlayer is the player who uses the mark O
     */
    void setxPlayer(Player xPlayer){
        this.xPlayer = xPlayer;
    }

    /**
     * Returns the values of the oPlayer
     * @return
     */
    public Player getoPlayer() {
        return oPlayer;
    }

    /**
     * Returns the values of the xPlayer
     * @return
     */
    public Player getxPlayer() {
        return xPlayer;
    }
}
