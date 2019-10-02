import java.io.*;
import java.net.Socket;

/**
 * Represents a person that will play against another person in a game of TicTacToe.
 * Both persons will have their own name and mark for the game.
 *
 * @author Julia Grab
 * @version 1.0
 * @since January 26, 2019
 */
public class Player {
    /**
     * The name of the player
     */
    String name;
    /**
     * The board the game is played on
     */
    Board board;
    /**
     * The person the player plays TicTacToe against
     */
    Player opponent;
    /**
     * The mark the player uses to play.
     */
    char mark;
    /**
     * The socket the player is connected to
     */
    private Socket aSocket;
    /**
     * The BufferedReader that accepts the users inputs
     */
    private BufferedReader socketIn;
    /**
     * The PrintWriter that prints to the users screen
     */
    private PrintWriter socketOut;

    /**
     * Constructs a Player object with the specified values for name and mark.
     * The values of the data fields are supplied by the given parameters:
     * @param mark the Player object's mark
     * @param s the Player's socket
     */
    public Player (Socket s, char mark){
        aSocket = s;
        this.mark = mark;
        try{
            socketIn = new BufferedReader(new InputStreamReader(aSocket.getInputStream()));
            socketOut = new PrintWriter(aSocket.getOutputStream());
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Allows the player to take their turn and decides whether or not the game is over.
     */
    void play(){
        board.setPrinter(socketOut);
        board.display();
        makeMove();
        board.display();

        if(board.xWins() || board.oWins()) {
            sendString("\nTHE GAME IS OVER: " + name + " is the winner!\nGame Ended...");
            board.setPrinter(opponent.socketOut);
            board.display();
            opponent.sendString("\nTHE GAME IS OVER: " + name + " is the winner!\nGame Ended...");
            System.out.println("\nTHE GAME IS OVER: " + name + " is the winner!\nGame Ended...");
            board.clear();
        }
        else
        if(board.isFull()) {
            sendString("\nTHE GAME IS OVER: The game ended in a tie!\nGame Ended...");
            board.setPrinter(opponent.socketOut);
            board.display();
            opponent.sendString("\nTHE GAME IS OVER: The game ended in a tie!\nGame Ended...");
            System.out.println("\nTHE GAME IS OVER: The game ended in a tie!\nGame Ended...");
            board.clear();
        }
        else {
            sendString("Waiting for other player to make their move.\n");
            opponent.sendString("It is now your turn.\n");
            opponent.play();
        }

    }

    /**
     * Asks the player where they would like to place their mark and marks the board where they specified.
     */
    void makeMove(){
        int row = 0, col = 0;

        sendString(name + ", what row should your next X be placed in?\0");

        while(true){
            try {
                row = Integer.parseInt(socketIn.readLine());

                if(row < 3 && row >= 0)
                    break;

                sendString("Please try again:\0");
            } catch (NumberFormatException n) {
                sendString("Please try again:\0");
            } catch (IOException e){
                e.printStackTrace();
            }
       }

        sendString(name + ", what column should your next X be placed in?\0");

       while(true){
            try {
                col = Integer.parseInt(socketIn.readLine());

                if(col < 3 && col >= 0)
                    break;

                sendString("Please try again:\0");
            } catch (NumberFormatException n) {
                sendString("Please try again:\0");
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        while(board.getMark(row,col) == 'X' || board.getMark(row,col) == 'O') {
            sendString("\nA player already played in this row and column, select a different row and column.\n");
            makeMove();
        }
        board.addMark(row, col, mark);
    }

    /**
     * Sets the value of opponent to the specified value.
     * @param opponent the opponent of the player
     */
    void setOpponent(Player opponent){
        this.opponent = opponent;
    }

    /**
     * Sets the value of board to the specified value.
     * @param theBoard the board the game is played on
     */
    void setBoard(Board theBoard){
        this.board = theBoard;
    }

    /**
     * Prints words to the users screen.
     * @param toSend the words being printed
     */
    private void sendString (String toSend) {
        socketOut.println(toSend);
        socketOut.flush();
    }

    /**
     * Receives the name of the current player from the user.
     */
    public void getPlayerName(){
        try{
            sendString("Please enter the name of the " + mark + " player. \0");
            name = socketIn.readLine();
            while(name == null){
                sendString("Please try again. \0");
                name = socketIn.readLine();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the variable socketOut
     * @return
     */
    public PrintWriter getSocketOut() {
        return socketOut;
    }
}
