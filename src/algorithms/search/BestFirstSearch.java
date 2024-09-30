package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.*;

public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch() {
        super();
        this.name = "BestFirstSearch";
        this.numberOfNodes = 0;
    }


    private static class mycompar implements Comparator<AState> {
        @Override
        public int compare(AState s1, AState s2) {
            if (s1.getCost()>s2.getCost()) {
                return 1;
            }
            else if(s1.getCost()<s2.getCost()){
                return -1;
            }
            else {
                return 0;
            }
//
        }
    }


    @Override
    public Solution solve(ISearchable s) {
        if(s!=null) {
            ArrayList<AState> ans;
            PriorityQueue<AState> myqueue = new PriorityQueue<>(new mycompar());
            ((SearchableMaze) s).isVisitedReset();
            ans = BestFS(s, myqueue);
            while (ans == null) {
                this.numberOfNodes = 0;
                ((SearchableMaze) s).isVisitedReset();
                ans = BestFS(s, myqueue);
            }
            Solution sol = new Solution(ans);
            return sol;
        }
        return null;
    }

    public  ArrayList<AState> BestFS(ISearchable s,PriorityQueue<AState> myqueue){
        List<AState> visited = new ArrayList<>();
        visited.add(s.getInitState());
        myqueue.add(s.getInitState());
        ((SearchableMaze)s).setIsVisited(s.getInitState());
        List<AState> tmpneigh = new ArrayList<>();
        ArrayList<AState> traversal = new ArrayList<>();
        // sorting in pq gets done by first value of pair

        while (!myqueue.isEmpty()) {
            MazeState curr =(MazeState) myqueue.poll();

            if (s.getGoalState().getR() == curr.getR() && s.getGoalState().getC()== curr.getC()) {
                s.setGoal(curr);
                s.getGoalState().setSum(curr.getSum()+s.getGoalState().getCost());
                traversal= getSolution(s.getGoalState());
                ((SearchableMaze) s).isVisitedReset();
                return traversal;
            }
            tmpneigh = ((SearchableMaze)s).getAllPossibleStates(curr);
            for(int i=0;i< tmpneigh.size();i++) {
                AState tmp = tmpneigh.get(i);
                if (!((SearchableMaze) s).getIsCellVisited(tmp)) {
                    tmp.setSuccessor(curr);
                    this.numberOfNodes++;
                    tmp.setSum(tmp.getSuccessor().getSum() +tmp.getCost());
                    if (s.getGoalState().getR() ==((MazeState) tmp).getR() && s.getGoalState().getC()== ((MazeState)tmp).getC()) {
                        s.setGoal((MazeState) tmp);
                        s.getGoalState().setSum(tmp.getSum()+s.getGoalState().getCost());
                        traversal = getSolution(s.getGoalState());
                        ((SearchableMaze) s).isVisitedReset();
                        return traversal;
                    }

                    ((SearchableMaze)s).setIsVisited(tmp);
                    visited.add(tmp);
                    myqueue.add(tmp);
                }
            }
        }
        return  null;
    }
}

