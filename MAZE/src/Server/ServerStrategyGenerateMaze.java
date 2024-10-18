package Server;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void serverstrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            //creating output stream object and input stream object to read the request and send the solution
           ObjectInputStream fromClient=new ObjectInputStream(inputStream);
           ObjectOutputStream toClient=new ObjectOutputStream(outputStream);
           //read the request from the client

            //apply the strategy of generating maze







           toClient.flush();
           fromClient.close();
           toClient.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
