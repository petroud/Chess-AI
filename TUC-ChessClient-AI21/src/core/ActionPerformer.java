package core;
import mcts.*;
import java.util.ArrayList;
import java.util.Random;

import util.*;

public class ActionPerformer {
	
	public final static Algorithms PERFORMED = Algorithms.MONTE_CARLO_TREE_SEARCH;
	
	public ActionPerformer(ArrayList<String> availableMoves, String[][] board) {
		this.availableMoves = availableMoves;
		
		switch (PERFORMED) {
		
		case MINIMAX:
			bestMove = minimax(board);
			break;
		case ALPHA_BETA_PRUNING:
			bestMove = alphaBetaPruning(board);
			break;
		case MONTE_CARLO_TREE_SEARCH:
			bestMove = monteCarloTreeSearch(board);
			break;
		case RANDOM:
			bestMove = selectRandomAction();
			break;
		default:
			throw new Error("Impossible");
		}
	}
	
	private final ArrayList<String> availableMoves;
	private final String bestMove;
	
	private String monteCarloTreeSearch(String[][] board) {
		
		MCTSTree tree = new MCTSTree(board, Client.myColor == 0 ? "PW" : "PB", 100);
				
		return tree.mcts();
	}
	
	private String selectRandomAction() {		
		Random ran = new Random();
		int x = ran.nextInt(availableMoves.size());
		return availableMoves.get(x);
	}
	
	private String alphaBetaPruning(String[][] board) {
		ABPruningTree tree = new ABPruningTree(board, 4);
		
		if (tree.getBestMove() == null) {
			throw new Error();
		}
		
		System.out.println(tree.getBestMove());
		
		return tree.getBestMove();
	}
	
	private String minimax(String[][] board) {
		FutureTree tree = new FutureTree(board, 4, Client.myColor == 0 ? "PW" : "PB");
		
		if (tree.getBestMove() == null) {
			throw new Error();
		}
		return tree.getBestMove();
	}
	
	@Override
	public String toString() {
		return bestMove;
	}
}
