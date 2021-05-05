package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import core.Client;

public class ABPruningNode {

	public ABPruningNode(String[][] initalState, String move, int depth, int maxDepth, String who, double alpha, double beta) {
		this.move = move;
		if (move != null) {
			int x1 = Integer.parseInt(Character.toString(move.charAt(0)));
			int y1 = Integer.parseInt(Character.toString(move.charAt(1)));
			int x2 = Integer.parseInt(Character.toString(move.charAt(2)));
			int y2 = Integer.parseInt(Character.toString(move.charAt(3)));
			state = Simulator.makeMove(x1, y1, x2, y2, 9, 9, initalState);
		}
		else {
			state = new String[initalState.length][initalState[0].length];
			
			for (int i=0; i<initalState.length; i++) {
				for (int j=0; j<initalState[0].length; j++) {
					state[i][j] = initalState[i][j];
				}
			}
		}
		
		if (depth > maxDepth) {
			value = Simulator.calculateScore(initalState, move);
			return;
		}
		
		if (Client.myColor == 0 && who.contentEquals("PW") || Client.myColor == 1 && who.contentEquals("PB")) {
			// maximize
			
			value = Integer.MIN_VALUE + 7;
			
			ArrayList<String> availMoves = Simulator.calculateAvailableMoves(state, who);
			
			children = new ArrayList<ABPruningNode>(availMoves.size());
			
			for (String mv : availMoves) {
				
				ABPruningNode newNode = new ABPruningNode(state, mv, depth+1, maxDepth, who.contentEquals("PB") ? "PW" : "PB", alpha, beta);
				
				children.add(newNode);
				double ret = newNode.value;
				
				value = Math.max(value, ret);
				
				alpha = Math.max(alpha, value);
				
				if (alpha >= beta) {
					break;
				}
			}
			
			if (children == null || children.isEmpty()) {
				return;
			}
			ABPruningNode max = Collections.max(children, new Comparator<ABPruningNode>() {

				@Override
				public int compare(ABPruningNode t1, ABPruningNode t2) {
					if (t1.value > t2.value) {
						return 1;
					}
					else if (t1.value == t2.value) {
						return 0;
					}
					else {
						return -1;
					}
				}
				
			});
			
			bestMove = max.move;
		}
		else {
			// minimize
			
			value = Integer.MAX_VALUE - 7;
			
			ArrayList<String> availMoves = Simulator.calculateAvailableMoves(state, who);
			
			children = new ArrayList<ABPruningNode>(availMoves.size());
			
			for (String mv : availMoves) {
				ABPruningNode newNode = new ABPruningNode(state, mv, depth+1, maxDepth, who.contentEquals("PB") ? "PW" : "PB", alpha, beta);
				
				children.add(newNode);
				double ret = newNode.value;
				
				value = Math.min(value, ret);
				
				beta = Math.min(beta, value);
				
				if (beta <= alpha) {
					break;
				}
			}
		}
	}
	
	ArrayList<ABPruningNode> children;
	private double value;
	String[][] state;
	
	String bestMove;
	String move;
}
