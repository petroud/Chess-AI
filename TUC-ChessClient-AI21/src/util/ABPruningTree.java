package util;

import core.Client;

public class ABPruningTree {
	
	public ABPruningTree(String[][] board, int maxDepth) {
		root = new ABPruningNode(board, null, 0, maxDepth, Client.myColor == 0 ? "PW" : "PB", Integer.MIN_VALUE + 7, Integer.MAX_VALUE - 7);
		
		bestMove = root.bestMove;
	}

	ABPruningNode root;
	
	private String bestMove;
	
	public String getBestMove() {
		return bestMove;
	}
}
