package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Stack;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    String name;
    int numberOfNodes;

    @Override
    public Solution solve(ISearchable s) {
        return null;
    }

    @Override
    public int getNumberOfVisitedNodes() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return this.numberOfNodes;
    }

    public ArrayList<AState> getSolution(AState n) {
        ArrayList<AState> sol = new ArrayList<>();
        Stack<AState> stackSol = new Stack<>();
        if (n != null) {
            while (n.getSuccessor() != null) {
                stackSol.push(n);
                n = n.getSuccessor();
            }
            stackSol.push(n);
        }
        while(!stackSol.isEmpty()) {
            n = stackSol.pop();
            sol.add(n);
        }
        return sol;
    }


}

