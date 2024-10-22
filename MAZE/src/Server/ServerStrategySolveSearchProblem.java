package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.util.Arrays;

public class ServerStrategySolveSearchProblem implements IServerStrategy{
    private Object filetolook = new Object();
    private String directory = System.getProperty("java.io.tmpdir");

    @Override
    public void serverstrategy(InputStream inputStream, OutputStream outputStream) throws IOException {
        ObjectInputStream fromClient=null;
        ObjectOutputStream toClient=null;
        try {
            //creating output stream object and input stream object to read the request and send the solution
            fromClient = new ObjectInputStream(inputStream);
            toClient = new ObjectOutputStream(outputStream);
            toClient.flush();
            //get the maze from the client to solve
            Maze maze = (Maze) fromClient.readObject();
            //get solution to the maze
            Solution ans = solveMaze(maze);
            //write the solution to the client
            toClient.writeObject(ans);
            toClient.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
        if (toClient != null)
            toClient.close();
        if (fromClient != null)
            fromClient.close();
    }

    }

    private Solution solveMaze(Maze maze) {
        //get the maze unique name to save in the file
        byte[] array = maze.toByteArray();
        String uniqueName = getUniqueName(array);
        //get the solution using the configuration file
        Solution sol = getSolution(maze);
        //save the solution to a file
        File file = new File(directory,uniqueName);
        synchronized (filetolook) {
            try {
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
                out.writeObject(sol);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return sol;
    }

    //the function get the solution for the maze based on the configuration file
    private Solution getSolution(Maze maze) {
        Configurations con = Configurations.getInstance();
        String solutionType = con.getProp("mazeSearchingAlgorithm");
        ASearchingAlgorithm aSearchingAlgorithm;
        //choose the Solving type using the configuration file
        if(solutionType.equals("BreadthFirstSearch")){
            aSearchingAlgorithm = new BreadthFirstSearch();
        }
        else if(solutionType.equals("BestFirstSearch")){
            aSearchingAlgorithm= new BestFirstSearch();
        }
        else{
            aSearchingAlgorithm = new DepthFirstSearch();
        }
        //creating a Searchable Maze from the maze that the client sent
        SearchableMaze m = new SearchableMaze(maze);
        //if not, solve the maze using the configuration type
        Solution sol = aSearchingAlgorithm.solve(m);
        return sol;
    }

    private String getUniqueName(byte[] arrayofbytes) {
        return Integer.toString((Arrays.toString(arrayofbytes)).hashCode());
    }
}
