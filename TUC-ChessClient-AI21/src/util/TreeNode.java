package util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import core.Client;

public class TreeNode {
	
	public static int INF = 1_000_000;
	public static int NEG_INF = -1_000_000;
	
	public TreeNode(String move, String[][] initalState, int depth, int maxDepth, String who) {
		
		this.move = move;
		this.depth = depth;
		
		if (move != null) {
			int x1 = Integer.parseInt(Character.toString(move.charAt(0)));
			int y1 = Integer.parseInt(Character.toString(move.charAt(1)));
			int x2 = Integer.parseInt(Character.toString(move.charAt(2)));
			int y2 = Integer.parseInt(Character.toString(move.charAt(3)));
			evaluationScore = Simulator.calculateScore(initalState, move);
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
			return;
		}
		
		ArrayList<String> moves = Simulator.calculateAvailableMoves(state, who);
		
		children = new ArrayList<TreeNode>();
		
		for (String mv : moves) {
			children.add(new TreeNode(mv, state, depth+1, maxDepth, who.contentEquals("PB") ? "PW" : "PB"));
		}
		
		if (children == null || children .size() == 0) {
			return;
		}
		if ((Client.myColor == 0 && who.contentEquals("PW")) || (Client.myColor == 1 && who.contentEquals("PB"))) {
			// maximize
			
			TreeNode max = Collections.max(children, new Comparator<TreeNode>() {

				@Override
				public int compare(TreeNode t1, TreeNode t2) {
					if (t1.evaluationScore > t2.evaluationScore) {
						return 1;
					}
					else if (t1.evaluationScore == t2.evaluationScore) {
						return 0;
					}
					else {
						return -1;
					}
				}
				
			});
			
			evaluationScore = max.evaluationScore;
			minimaxBestMove = max.move;
			
		}
		else {
			// minimize
			
			TreeNode min = Collections.min(children, new Comparator<TreeNode>() {

				@Override
				public int compare(TreeNode t1, TreeNode t2) {
					if (t1.evaluationScore > t2.evaluationScore) {
						return 1;
					}
					else if (t1.evaluationScore == t2.evaluationScore) {
						return 0;
					}
					else {
						return -1;
					}
				}
				
			});
			
			evaluationScore = min.evaluationScore;
			minimaxBestMove = min.move;
		}
		
		//System.out.println(moves);
	}
	
	double evaluationScore;
	final int depth;
	String[][] state;
	private String move;
	private ArrayList<TreeNode> children;
	
	String minimaxBestMove;
	
	@Override
	public String toString() {
		return "Move: " + move + " " + "[ " + (children != null ? children.size() : "null") +  " ]" + " score: " + evaluationScore;
	}
	
	void print(StringBuilder buffer, String prefix, String childrenPrefix) {
		buffer.append(new String(prefix.getBytes(), StandardCharsets.UTF_8));
		buffer.append(this+"\n");
		
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
