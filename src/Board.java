import java.io.PrintWriter;

/**
 * Creates a game board that will show the marks placed by the players.
 *
 * @author Julia Grab
 * @version 1.0
 * @since January 26, 2019
 */

public class Board implements Constants {
    /**
     * The board the players play on
     */
    private char theBoard[][];
    /**
     * The amount of moves played by the players
     */
    private int markCount;

    /**
     * The PrintWriter that prints to the screen
     */
    private PrintWriter socketOut;

    /**
     * Constructs a Board object with the default values.
     * The values of the date fields are supplied by the given parameters:
     * - zero for markCount
     * - a matrix of spaces for theBoard
     */
    public Board() {
        markCount = 0;
        theBoard = new char[3][];
        for (int i = 0; i < 3; i++) {
            theBoard[i] = new char[3];
            for (int j = 0; j < 3; j++)
                theBoard[i][j] = SPACE_CHAR;
        }
    }

    /**
     * Sets the socketOut value to the specified value.
     * @param print the new value of socketOut
     */
    public void setPrinter(PrintWriter print){
        socketOut = print;
    }

    /**
     * Gets the value from the board at the certain row and column and returns the value.
     * @param row the row of the board
     * @param col the column of the board
     * @return char value for the board position specified
     */
    public char getMark(int row, int col) {
        return theBoard[row][col];
    }

    /**
     * Checks to see if the board is full and cannot have more marks played.
     * @return true if the markCount is 9
     */
    public boolean isFull() {
        return markCount == 9;
    }

    /**
     * Checks to see if the xPlayer has won.
     * @return true if the xPlayer has won
     */
    public boolean xWins() {
        if (checkWinner(LETTER_X) == 1)
            return true;
        else
            return false;
    }

    /**
     * Checks to see if the oPlayer has won.
     * @return true if the oPlayer has won
     */
    public boolean oWins() {
        if (checkWinner(LETTER_O) == 1)
            return true;
        else
            return false;
    }

    /**
     * Displays the board and the marks played.
     */
    public void display() {
        displayColumnHeaders();
        addHyphens();
        for (int row = 0; row < 3; row++) {
            addSpaces();
            sendString("    row " + row + ' ');
            for (int col = 0; col < 3; col++)
                sendString("|  " + getMark(row, col) + "  ");
            sendString("|\n");
            addSpaces();
            addHyphens();
        }
    }

    /**
     * Adds a mark to the row and column specified.
     * @param row the row on the board
     * @param col the column on the board
     * @param mark the mark the player uses
     */
    public void addMark(int row, int col, char mark) {

        theBoard[row][col] = mark;
        markCount++;
    }

    /**
     * Clears the board.
     */
    public void clear() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                theBoard[i][j] = SPACE_CHAR;
        markCount = 0;
    }

    /**
     * checks to see if a player has won by seeing if there's the same mark three times in a row.
     * @param mark the mark the player uses
     * @return value that is either equal to 1 or 0. If a player has won, returns 1. Else returns 0.
     */
    int checkWinner(char mark) {
        int row, col;
        int result = 0;

        for (row = 0; result == 0 && row < 3; row++) {
            int row_result = 1;
            for (col = 0; row_result == 1 && col < 3; col++)
                if (theBoard[row][col] != mark)
                    row_result = 0;
            if (row_result != 0)
                result = 1;
        }


        for (col = 0; result == 0 && col < 3; col++) {
            int col_result = 1;
            for (row = 0; col_result != 0 && row < 3; row++)
                if (theBoard[row][col] != mark)
                    col_result = 0;
            if (col_result != 0)
                result = 1;
        }

        if (result == 0) {
            int diag1Result = 1;
            for (row = 0; diag1Result != 0 && row < 3; row++)
                if (theBoard[row][row] != mark)
                    diag1Result = 0;
            if (diag1Result != 0)
                result = 1;
        }
        if (result == 0) {
            int diag2Result = 1;
            for (row = 0; diag2Result != 0 && row < 3; row++)
                if (theBoard[row][3 - 1 - row] != mark)
                    diag2Result = 0;
            if (diag2Result != 0)
                result = 1;
        }
        return result;
    }

    /**
     * Displays the column titles of the game board.
     */
    void displayColumnHeaders() {
        sendString("          ");
        for (int j = 0; j < 3; j++)
            sendString("|col " + j);
        sendString("\n");
    }

    /**
     * Displays lines on the game board.
     */
    void addHyphens() {
        sendString("          ");
        for (int j = 0; j < 3; j++)
            sendString("+-----");
        sendString("+\n");
    }

    /**
     * Displays spaces on the game board.
     */
    void addSpaces() {
        sendString("          ");
        for (int j = 0; j < 3; j++)
            sendString("|     ");
        sendString("|\n");
    }

    /**
     * Prints words to the screen.
     * @param toSend the words being printed to the screen
     */
    private void sendString (String toSend) {
        socketOut.print(toSend);
        socketOut.flush();
    }

}
