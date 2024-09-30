package algorithms.search;

import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.List;

public class Solution {

    private ArrayList<AState> traversal=null;

    public Solution(ArrayList<AState> ans){
        this.traversal= ans;
    }

    public List<AState> getTraversal() {
        return traversal;
    }

    public ArrayList<AState> getSolutionPath() {
        return this.traversal;
    }
}
