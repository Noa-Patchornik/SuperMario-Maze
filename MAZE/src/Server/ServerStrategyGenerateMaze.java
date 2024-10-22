package Server;

import IO.SimpleCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;

public class ServerStrategyGenerateMaze implements IServerStrategy{
    @Override
    public void serverstrategy(InputStream inputStream, OutputStream outputStream) {
        try {
            //creating output stream object and input stream object to read the request and send the solution
            ObjectInputStream fromClient=new ObjectInputStream(inputStream);
            ObjectOutputStream toClient=new ObjectOutputStream(outputStream);
            toClient.flush();
            //read the size of the maze from the client
            int[] sizeofmaze = (int[]) fromClient.readObject();
            Configurations con = Configurations.getInstance();
            AMazeGenerator mazeGenerator;
            //generate the maze according to the Configuration file strategy
            String generateType = con.getProp("mazeGeneratingAlgorithm"); //the type to generate the maze
            if(generateType.equals("SimpleMaze")){
                mazeGenerator= new SimpleMazeGenerator();
            }
            else if(generateType.equals("EmptyMaze")){
                mazeGenerator= new EmptyMazeGenerator();
            }
            else{
                mazeGenerator=new MyMazeGenerator();
            }
            //creating the maze using the size from the client and the strategy from the config file
            Maze maze = mazeGenerator.generate(sizeofmaze[0],sizeofmaze[1]);
            // Compress the maze using the SimpleCompressorOutputStream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            SimpleCompressorOutputStream compressor = new SimpleCompressorOutputStream(byteArrayOutputStream);
            compressor.write(maze.toByteArray());  // Compress the maze
            compressor.flush();

            // Get the compressed byte array
            byte[] compressedMaze = byteArrayOutputStream.toByteArray();
            // Send the compressed maze byte array to the client
            toClient.writeObject(compressedMaze);
            toClient.flush();
            fromClient.close();
            toClient.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
