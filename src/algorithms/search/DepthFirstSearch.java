package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm{

    public DepthFirstSearch(){
        super();
        this.name = "DepthFirstSearch";
        this.numberOfNodes=0;
    }
    @Override
    public Solution solve(ISearchable s) {
        ArrayList<AState> ans=null;
        if(s!=null) {
            ans = DFS(s.getInitState(), s, s.getGoalState());
            while (ans == null) {
                ((SearchableMaze) s).isVisitedReset();
                this.numberOfNodes = 0;
                ans = DFS(s.getInitState(), s, s.getGoalState());
            }
            Solution sol = new Solution(ans);
            return sol;
        }
        return new Solution(ans);
    }

    public ArrayList<AState> DFS(AState init,ISearchable s,AState goal)
    {
        MazeState n;
        MazeState g= (MazeState)goal;
        List<AState> visited = new ArrayList<>();
        Stack<AState> stack = new Stack<>();
        stack.push(init);
        visited.add(init);
        AState tmp;
        List<AState> tmpneigh;
        ArrayList<AState> traversal;
        while(!stack.empty()){
            n = (MazeState) stack.pop();
            if(n.getR()==g.getR() && n.getC()==g.getC()){
                s.setGoal(n);
                traversal= getSolution(n);
//                PrintList(traversal);
                return traversal;
            }
            tmpneigh = ((SearchableMaze)s).getAllPossibleStates(n);
            for (int i = 0; i < tmpneigh.size(); i++) {
                tmp = tmpneigh.get(i);
                if (!visited.contains(tmp)) {
                    this.numberOfNodes++;
                    tmp.setSuccessor(n);
                    visited.add(tmp);
                    stack.push(tmp);
                }
                if(((MazeState)tmp).getR()==g.getR() && ((MazeState)tmp).getC()==g.getC()) {
                    s.setGoal(((MazeState)tmp));
                    tmp.setSuccessor(n);
                    traversal= getSolution(tmp);
//                    PrintList(traversal);
                    return traversal;
                }
            }
        }
        return null;
    }

    private void PrintList(ArrayList<AState> traversal) {
        for(int i=0;i<traversal.size();i++)
            System.out.print ("  " + ((MazeState)traversal.get(i)).getR() + "," + ((MazeState)traversal.get(i)).getC());
        System.out.println();
    }

    @Override
    public int getNumberOfVisitedNodes() {
        return this.numberOfNodes;
    }

}
