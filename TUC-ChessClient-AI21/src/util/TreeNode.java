package util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;

import core.Client;

public class TreeNode {
	
	public TreeNode(String move, String[][] initalState, int depth, int maxDepth) {
		
		this.move = move;
		this.depth = depth;
		
		if (depth > maxDepth) {
			return;
		}
		
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
		
		ArrayList<String> moves = Simulator.calculateAvailableMoves(state, (Client.myColor == 0 ? "PW" : "PB"));
		
		children = new ArrayList<TreeNode>();
		
		for (String mv : moves) {
			children.add(new TreeNode(mv, state, depth+1, maxDepth));
		}
		
		//System.out.println(moves);
	}
	
	final int depth;
	String[][] state;
	private String move;
	private ArrayList<TreeNode> children;
	
	@Override
	public String toString() {
		return "Move: " + move + "\n" + children + "\n";
	}
	
	void print(StringBuilder buffer, String prefix, String childrenPrefix) {
		buffer.append(new String(prefix.getBytes(), StandardCharsets.UTF_8));
		buffer.append(move+"\n");
		
		if (children != null) {
			for (Iterator<TreeNode> it = children.iterator(); it.hasNext();) {
				TreeNode next = it.next();
				
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
