import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The server class to the game of Tic Tac Toe that connects the players together
 */
public class Server {
    /**
     * The server socket the players connect to
     */
    private ServerSocket serverSocket;
    /**
     * The pool of different games of Tic Tac Toe
     */
    private ExecutorService pool;

    /**
     * Constructs an object of type Server with the specified value
     * @param portNumber the port number being connected to
     */
    public Server(int portNumber){
        try{
            serverSocket = new ServerSocket(portNumber);
            pool = Executors.newCachedThreadPool();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Communicates with the client to start a game of Tic Tac Toe
     */
    public void communicate(){
        try{
            while(true){
                Referee theRef = new Referee();
                Player xPlayer = new Player(serverSocket.accept(), 'X');
                Player oPlayer = new Player(serverSocket.accept(), 'O');

                theRef.setoPlayer(oPlayer);
                theRef.setxPlayer(xPlayer);

                Game theGame = new Game();
                theGame.appointReferee(theRef);

                System.out.println("A game has been started.");

                pool.execute(theGame);
            }
        } catch(Exception e){
            e.printStackTrace();
            pool.shutdown();
        }
    }

    public static void main(String[] args) throws IOException{
        Server server = new Server(8899);
        System.out.println("Server is now running.");
        server.communicate();
    }
}
