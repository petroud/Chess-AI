package mcts;

import java.util.ArrayList;

public class MCTSNode {
	
	public int visits;
	public double value;
	public ArrayList<MCTSNode> children;
	
	public MCTSNode() {
		this.visits = 0;
		this.children = new ArrayList<MCTSNode>();			
	}
	
	public boolean isLeaf() {
		return children.isEmpty();
	}
	
	
	public void updateStatus(double value) {
		this.visits++;
		this.value+=value;
	}

}
