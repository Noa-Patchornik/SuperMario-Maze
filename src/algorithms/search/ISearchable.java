package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.List;

public interface ISearchable {

    public MazeState getInitState();
    public MazeState getGoalState();
    public List<Position> getAllSuccessors(Position s);

    void setSuccessor(AState n);

    void setGoal(MazeState n);
}
