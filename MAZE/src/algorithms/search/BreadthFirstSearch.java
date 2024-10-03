package algorithms.search;
import algorithms.mazeGenerators.*;

import java.util.*;

public class BreadthFirstSearch extends ASearchingAlgorithm implements ISearchingAlgorithm {

    public BreadthFirstSearch() {
        super();
        this.name = "BreadthFirstSearch";
        this.numberOfNodes = 0;
    }


    @Override
    public Solution solve(ISearchable s) {
        Queue<AState> myqueue = new LinkedList<>();
        this.numberOfNodes = 0;
        ArrayList<AState> ans = BFS(s, myqueue);

        while (ans == null) {
            this.numberOfNodes = 0;
            ((SearchableMaze) s).isVisitedReset();
            ans = BFS(s, myqueue);
        }
        Solution sol = new Solution(ans);
        return sol;
    }

    //the BFS function that search in the maze the end state with a queue
    protected ArrayList<AState> BFS(ISearchable s, Queue<AState> myqueue) {
        //Create a queue for BFS
        List<AState> visited = new ArrayList<>();

        // Mark the current node as visited and enqueue it
        visited.add(s.getInitState());
        myqueue.add(s.getInitState());
        ArrayList<AState> traversal = new ArrayList<>();
        List<AState> tmpneigh = new ArrayList<>();
        MazeState curr;
        // Iterate over the queue
        while (!myqueue.isEmpty()) {
            curr =(MazeState) myqueue.poll();
            if(myqueue instanceof PriorityQueue<AState>){
                if(curr.getSuccessor()!=null) {
                    curr.setSum(curr.getSuccessor().getSum()+curr.getCost());
                }
            }
            if(((MazeState)curr).getR() == s.getGoalState().getR() && ((MazeState)curr).getC() == s.getGoalState().getC()){
                s.setGoal(curr);
                traversal= getSolution(curr);
                return traversal;
            }
            traversal.add(curr);
            tmpneigh = ((SearchableMaze)s).getAllPossibleStates(curr);
            for(int i=0;i< tmpneigh.size();i++){
                AState tmp =  tmpneigh.get(i);
                if (!visited.contains(tmp)) {
                    visited.add(tmp);
                    myqueue.add(tmp);
                    this.numberOfNodes++;
                    tmp.setSuccessor(curr);
                }
                if(((MazeState)tmp).getR() == s.getGoalState().getR() && ((MazeState)tmp).getC() == s.getGoalState().getC() ) {
                    s.setGoal((MazeState) tmp);
                    traversal= getSolution(tmp);
                    return traversal;
                }
            }
        }
        return null;
    }
}
