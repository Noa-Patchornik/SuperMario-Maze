package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {

    private ArrayList<AState> traversal=null;

    public Solution(ArrayList<AState> ans){
        this.traversal= ans;
    }

    public ArrayList<AState> getSolutionPath() {
        return this.traversal;
    }
}
