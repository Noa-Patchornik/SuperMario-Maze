# Super Mario Maze Generation and Solver Project

🧩 Maze Game – JavaFX Search Algorithms Visualizer  
🎮 Developed as part of a university project in Software Engineering

## Overview
This project showcases a fully interactive maze game built in Java, where users can generate, solve, and interact with mazes of different types.  
It was designed using the **MVVM architecture**, with a strong emphasis on separation of concerns, modularity, and performance.

Users can play through the maze using keyboard controls and visualize solving algorithms such as **DFS**, **BFS**, and **Best-First Search**, supported by real-time client-server communication and compression via **Run-Length Encoding (RLE)**.

The GUI is inspired by classic Super Mario themes, providing a nostalgic and engaging user experience.


## Features
- 🧱 Generate mazes using multiple algorithms  
- 🧭 Solve mazes using DFS / BFS / Best-First Search  
- 🖥️ Interactive GUI with player movement and real-time feedback  
- 💾 Compression and decompression with Run-Length Encoding  
- 📡 Client-server communication for maze operations  
- 🌐 Responsive design using JavaFX and Canvas  
- 🎨 Super Mario-themed graphics and sound effects  
- 🔍 Search algorithm comparison & visualization

## 📁 Project Structure
The project is organized into the following components:

- Maze Generators
  - AMazeGenerator.java: Abstract class that defines the structure for maze generation.
  - EmptyMazeGenerator.java: Creates an empty maze layout.
  - MyMazeGenerator.java: Custom maze generation algorithm inspired by Mario levels.
  - SimpleMazeGenerator.java: Basic maze generation for quick demos.
- Maze Representation
  - Maze.java: Defines the maze structure and associated functions.
  - Position.java: Represents individual positions in the maze.

- Search Algorithms
  - DepthFirstSearch.java: Depth-first search for solving mazes.
  - BestFirstSearch.java: Optimized best-first search algorithm.
  - ISearchable.java: Interface for making mazes searchable.
  - ISearchingAlgorithm.java: Interface for search algorithms.
  - SearchableMaze.java: Adapts a maze for search compatibility.
  - MazeState.java: Represents a single state in the maze search process.

- Compression
  - SimpleCompressorOutputStream.java: Uses run-length encoding for maze compression.
  - SimpleDecompressorInputStream.java: Decompresses maze data to its original form.

- Server and Client Communication
  - Server.java / Client.java: Manages interactions between the server and client.
  - IServerStrategy.java / IClientStrategy.java: Interfaces for communication strategies.
  - ServerStrategyGenerateMaze.java: Server-side maze generation.
  - ServerStrategySolveSearchProblem.java: Server-side maze-solving logic.

- GUI Components
  - MazeDisplayer.java: Custom component that renders the maze visually.
  - MyViewController.java / MyView.fxml / MainStyle.css: Defines the graphical user interface, styled to look like classic Super Mario.
  - CSS Styling: Icons from the Super-Mario game for a playful appearance.

- Utility and Configuration
  - Configurations.java: Manages configurations across the application.

- Demo and Execution
  - RunMazeGenerator.java / RunSearchOnMaze.java: Example classes to test maze generation and solving.
  - RunCompressDecompressMaze.java: Shows compression and decompression in action.
  - RunCommunicateWithServers.java: Demonstrates server-client communication.
  - Main.java: Application entry point.

- Resources
  - Images: Mario, wall,solution (the starts from the game), solvedMaze (princess peach) to enhance the Mario experience.
  - Audio: Super Mario theme music for background ambiance.
  - Video: A demo video file (GIF.mp4) showing maze generation and solving with Mario-themed graphics.

## How does it look:
![Super Mario Maze GUI](MAZE/resources/application.png)
