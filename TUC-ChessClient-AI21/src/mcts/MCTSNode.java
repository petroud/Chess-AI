package mcts;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import util.Simulator;

public class MCTSNode {
	
	public MCTSNode(MCTSNode parent, String move, String[][] initialState, String player) {
		this.parent = parent;
		this.visits = 1;
		this.player = player;
		value = 0;
		this.move = move;
		this.children = new ArrayList<MCTSNode>();

		// calculate state
		if (move != null) {
			int x1 = Integer.parseInt(Character.toString(move.charAt(0)));
			int y1 = Integer.parseInt(Character.toString(move.charAt(1)));
			int x2 = Integer.parseInt(Character.toString(move.charAt(2)));
			int y2 = Integer.parseInt(Character.toString(move.charAt(3)));
			evaluationScore = Simulator.calculateScore(initialState, move);
			state = Simulator.makeMove(x1, y1, x2, y2, 9, 9, initialState);
		}
		else {
			state = new String[initialState.length][initialState[0].length];
			
			for (int i=0; i<initialState.length; i++) {
				for (int j=0; j<initialState[0].length; j++) {
					state[i][j] = initialState[i][j];
				}
			}
		}
	}
	
	public double ratio() {
		double v = 1.0 * visits;
		return v / value;
	}
	
	final String player;
	double evaluationScore;
	String[][] state;
	final String move;
	int visits;
	double value;
	ArrayList<MCTSNode> children;
	MCTSNode parent;
	
	public boolean isLeaf() {
		return children == null || children.isEmpty();
	}
	
	double calculateValue() {
		return 0.0;
	}
	
	public void updateStatus(double value) {
		this.visits++;
		this.value+=value;
	}
	
	@Override
	public String toString() {
		return "Node with ratio: " + visits + "/" + value;
	}

	void print(StringBuilder buffer, String prefix, String childrenPrefix) {
		buffer.append(new String(prefix.getBytes(), StandardCharsets.UTF_8));
		buffer.append(this+"\n");
		
		if (children != null) {
			for (Iterator<MCTSNode> it = children.iterator(); it.hasNext();) {
				MCTSNode next = it.next();
				
				if (it.hasNext()) {
					next.print(buffer, childrenPrefix + "|-- ", childrenPrefix + "|   ");
				}
				else {
					next.print(buffer, childrenPrefix + "'-- ", childrenPrefix + "    ");
				}
			}
		}
	}
}
