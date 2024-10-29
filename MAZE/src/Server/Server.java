package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Server {

    private int port;
    private IServerStrategy serverStrategy;
    private int listeningIntervalMS;
    private boolean stop=false;
    private ExecutorService threadPool;


    public Server(int port, int listeningIntervalMS, IServerStrategy strategy) {
        Configurations con = Configurations.getInstance();
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = strategy;
        this.threadPool= Executors.newFixedThreadPool((Integer.parseInt(con.getProp("threadPoolSize"))));
    }

    public void start(){
        new Thread(()->
            startServer()).start();
    }

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            while(!stop) {
                try {
                    Socket clientsocket = serverSocket.accept();
                    //thread from thread pool to handle new client
                    threadPool.submit(() ->
                        handleClient(clientsocket));
                }
                catch(SocketTimeoutException e){
                    stop();
                }
            }
            serverSocket.close();
            threadPool.shutdown();
        }catch (IOException e) {
            e.printStackTrace();
            stop();
        }
    }

    private void handleClient(Socket clientsocket) {
        try{
            this.serverStrategy.serverstrategy(clientsocket.getInputStream(), clientsocket.getOutputStream());
            clientsocket.close();
        }catch (IOException e){
        }
    }

    public void stop() {
        this.stop=true;
    }
}
