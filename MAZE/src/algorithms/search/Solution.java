package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> traversal=null;

    public Solution(ArrayList<AState> ans){
        this.traversal= ans;
    }

    public ArrayList<AState> getSolutionPath() {
        return this.traversal;
    }
}
