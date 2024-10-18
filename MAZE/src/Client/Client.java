package Client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client{

    private int ServerPort;
    private IClientStrategy strategy;
    private InetAddress serverIP;

    public Client(InetAddress ip, int port, IClientStrategy s){
        this.ServerPort=port;
        this.strategy=s;
        this.serverIP=ip;
    }

    public void communicateWithServer() {
        try {
            //Connect to Server
            Socket serverSocket = new Socket(this.serverIP, ServerPort);
            strategy.clientStrategy(serverSocket.getInputStream(), serverSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
