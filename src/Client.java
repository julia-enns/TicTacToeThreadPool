import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * The Client class of the game Tic Tac Toe that allows a player to connect and play with an opponent.
 */
public class Client {

    /**
     * The PrintWriter that prints to the screen
     */
    private PrintWriter socketOut;
    /**
     * The socket the client is connected to
     */
    private Socket mySocket;
    /**
     * Reads in the input of the client
     */
    private BufferedReader stdIn;
    /**
     * Reads in the input of the client
     */
    private BufferedReader socketIn;

    /**
     * Constructs an object of type Client to the specified values.
     * @param serverName the server being connected to
     * @param portNumber the port number being connected to
     */
    public Client(String serverName, int portNumber) {
        try {
            mySocket = new Socket(serverName, portNumber);
            stdIn = new BufferedReader(new InputStreamReader(System.in));
            socketIn = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            socketOut = new PrintWriter((mySocket.getOutputStream()), true);
            System.out.println("Waiting for the other player...");
        } catch (IOException e) {
            System.err.println("Error in creating Client " + e.getStackTrace());
        }
    }

    /**
     * Communicates with the server
     */
    public void communicate()  {
        try{
            while(true) {
                String read = "";
                while (true) {
                    read = socketIn.readLine();
                    if (read.contains("\0")) {
                        read = read.replace("\0", "");
                        System.out.println(read);
                        break;
                    }
                    if (read.equals("QUIT")) {
                        return;
                    }
                    System.out.println(read);
                }
                read = stdIn.readLine();
                socketOut.println(read);
                socketOut.flush();
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException  {
        Client myClient = new Client("localhost", 8899);
        myClient.communicate();
    }
}