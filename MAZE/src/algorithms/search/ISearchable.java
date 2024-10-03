package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.List;

public interface ISearchable {

    MazeState getInitState();
    MazeState getGoalState();
    void setGoal(MazeState n);
}
