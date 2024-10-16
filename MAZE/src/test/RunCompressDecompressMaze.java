package test;
import IO.SimpleCompressorOutputStream;
import IO.SimpleDecompressorInputStream;
import algorithms.mazeGenerators.*;
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.util.Arrays;

//public class RunCompressDecompressMaze {
//
//        private static int testCounter = 0;
//        private static int failureCounter = 0;
//
//        public static void main(String[] args) {
//            for (int i = 0; i < 10; i++) {
//                runTest();
//            }
//            System.out.println("Total tests: " + testCounter);
//            System.out.println("Failed tests: " + failureCounter);
//        }
//
//        private static void runTest() {
//            testCounter++;
//            String mazeFileName = "savedMaze_" + testCounter + ".maze";
//            AMazeGenerator mazeGenerator = new MyMazeGenerator();
//            Maze originalMaze = mazeGenerator.generate(25, 25);
//            System.out.println("original byte array: " + Arrays.toString(originalMaze.toByteArray()));
//
//            try {
//                // Compress
//                byte[] originalBytes = originalMaze.toByteArray();
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                OutputStream out = new SimpleCompressorOutputStream(baos);
//                out.write(originalBytes);
//                out.flush();
//                out.close();
//                byte[] compressedBytes = baos.toByteArray();
//
//                // Decompress
//                ByteArrayInputStream bais = new ByteArrayInputStream(compressedBytes);
//                InputStream in = new SimpleDecompressorInputStream(bais);
//                byte[] decompressedBytes = new byte[originalBytes.length];
//                in.read(decompressedBytes);
//
//                in.close();
//
//                // Compare
//                Maze reconstructedMaze = new Maze(decompressedBytes);
//                boolean areEqual = originalMaze.equals(reconstructedMaze);
//
//                if (!areEqual) {
//                    failureCounter++;
//                    logFailure(originalMaze, reconstructedMaze, originalBytes, compressedBytes, decompressedBytes);
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private static void logFailure(Maze original, Maze reconstructed, byte[] originalBytes, byte[] compressedBytes, byte[] decompressedBytes) {
//            System.out.println("Test " + testCounter + " failed");
//            System.out.println("Original maze:");
//            printMazeInfo(original);
//            System.out.println("Reconstructed maze:");
//            printMazeInfo(reconstructed);
//            System.out.println("Original bytes: " + Arrays.toString(Arrays.copyOf(originalBytes, Math.min(50, originalBytes.length))));
//            System.out.println("Compressed bytes: " + Arrays.toString(Arrays.copyOf(compressedBytes, Math.min(50, compressedBytes.length))));
//            System.out.println("Decompressed bytes: " + Arrays.toString(Arrays.copyOf(decompressedBytes, Math.min(50, decompressedBytes.length))));
//            saveFailedCase(original, reconstructed, originalBytes, compressedBytes, decompressedBytes);
//        }
//
//        private static void printMazeInfo(Maze maze) {
//            System.out.println("Dimensions: " + maze.getRows() + "x" + maze.getCols());
//            System.out.println("Start: " + maze.getStartPosition());
//            System.out.println("Goal: " + maze.getGoalPosition());
//            // Print a small section of the maze for visual comparison
//            for (int i = 0; i < maze.getRows(); i++) {
//                for (int j = 0; j < maze.getCols(); j++) {
//                    System.out.print(maze.getCellMaze(i, j) + " ");
//                }
//                System.out.println();
//            }
//        }
//
//        private static void saveFailedCase(Maze original, Maze reconstructed, byte[] originalBytes, byte[] compressedBytes, byte[] decompressedBytes) {
//            try {
//                String filename = "failed_case_" + testCounter + ".txt";
//                PrintWriter writer = new PrintWriter(new FileWriter(filename));
//                writer.println("Original Maze:");
//                writeMazeToFile(original, writer);
//                writer.println("\nReconstructed Maze:");
//                writeMazeToFile(reconstructed, writer);
//                writer.println("\nOriginal Bytes: " + Arrays.toString(originalBytes));
//                writer.println("Compressed Bytes: " + Arrays.toString(compressedBytes));
//                writer.println("Decompressed Bytes: " + Arrays.toString(decompressedBytes));
//                writer.close();
//                System.out.println("Failed case saved to " + filename);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private static void writeMazeToFile(Maze maze, PrintWriter writer) {
//            writer.println("Dimensions: " + maze.getRows() + "x" + maze.getCols());
//            writer.println("Start: " + maze.getStartPosition());
//            writer.println("Goal: " + maze.getGoalPosition());
//            for (int i = 0; i < maze.getRows(); i++) {
//                for (int j = 0; j < maze.getCols(); j++) {
//                    writer.print(maze.getCellMaze(i, j) + " ");
//                }
//                writer.println();
//            }
//        }
//    }


public class RunCompressDecompressMaze {
    public static void main(String[] args) {
        int counter = 0;
        for (int i = 0; i < 20; i++) {
            String mazeFileName = "savedMaze.maze";
            AMazeGenerator mazeGenerator = new MyMazeGenerator();
            Maze maze = mazeGenerator.generate(1000, 1000); // Generate new maze

            // 1. Print the size of the maze in memory before compression
            System.out.println("Maze size in bytes before compression: " + maze.toByteArray().length + "\n");

            try {
                // 2. Save the compressed maze to a file
                OutputStream out = new SimpleCompressorOutputStream(new FileOutputStream(mazeFileName));
                out.write(maze.toByteArray());
                out.flush();
                out.close();

                // 3. Print the size of the saved file
                File file = new File(mazeFileName);
                System.out.println("Compressed maze file size in bytes: " + file.length());


            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] savedMazeBytes = new byte[0];
            try {
                // 4. Read the maze from the file
                InputStream in = new SimpleDecompressorInputStream(new FileInputStream(mazeFileName));
                savedMazeBytes = new byte[maze.toByteArray().length];
                in.read(savedMazeBytes);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 5. Print the size of the decompressed maze
            System.out.println("Maze size after decompression: " + savedMazeBytes.length + "\n");


            Maze loadedMaze = new Maze(savedMazeBytes);
            byte[] loadbtyearray = loadedMaze.toByteArray();
            byte[] originalbytearray = maze.toByteArray();
//            System.out.println("original maze: " + Arrays.toString(originalbytearray) + "\n\n");
//            System.out.println("after compress and decompress maze: " + Arrays.toString(loadbtyearray) + "\n\n");
            boolean areMazesEquals = Arrays.equals(loadbtyearray, originalbytearray);
            if(areMazesEquals){
                counter++;
            }
            // 6. Print whether the original and decompressed mazes are identical
            System.out.printf("Are the original and loaded mazes equal: %s%n", areMazesEquals);
        }
        System.out.println("Number of success: " + counter);
    }


}