package algorithms.search;

import algorithms.mazeGenerators.Position;

public interface ISearchingAlgorithm {

    Solution solve(ISearchable s) ;

    int getNumberOfVisitedNodes();

    String getName();

    int getNumberOfNodesEvaluated();
}
