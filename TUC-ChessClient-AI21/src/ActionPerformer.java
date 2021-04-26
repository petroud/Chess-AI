import java.util.ArrayList;
import java.util.Random;

import util.*;

public class ActionPerformer {
	
	public final static Algorithms PERFORMED = Algorithms.MINIMAX;
	
	public ActionPerformer(ArrayList<String> availableMoves, String[][] board) {
		this.availableMoves = availableMoves;
		
		switch (PERFORMED) {
		
		case MINIMAX:
			bestMove = minimax(board);
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
	
	private String selectRandomAction() {		
		Random ran = new Random();
		int x = ran.nextInt(availableMoves.size());
		return availableMoves.get(x);
	}
	
	private String minimax(String[][] board) {
		FutureTree tree = new FutureTree(board, 1);
		
		System.out.println(tree);
		
		System.exit(1);
		
		return null;
	}
	
	@Override
	public String toString() {
		return bestMove;
	}
}
