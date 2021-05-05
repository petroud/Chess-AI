package mcts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import util.Simulator;

public class MCTSTree {

	private static double Cp = 1.0;
	private final long timeLimit;	
	
	public MCTSTree(String[][] board, String startingPlayer, long timeLimit) {
		
		this.timeLimit = timeLimit;
		
		root = new MCTSNode(null, null, board, null);
		
		ArrayList<String> moves = Simulator.calculateAvailableMoves(board, startingPlayer);
		
		root.children = new ArrayList<MCTSNode>(moves.size());
		
		// Initially expand tree to depth = 1
		// Notice that the root has depth = 0
		
		for (String m : moves) {
			root.children.add(new MCTSNode(root, m, root.state, startingPlayer));
		}
	}
	
	public static double uct(MCTSNode node, int parentVisits) {
		if (node.visits == 0) {
			return Double.POSITIVE_INFINITY;
		}
		return node.evaluationScore + Cp * Math.sqrt(Math.log(node.parent.visits)/node.visits);
	}
	
	private boolean timeLeft(long start, long breakTime) {
		return System.currentTimeMillis() - start <= breakTime;
	}
	
	public String mcts() {
		
		long start = System.currentTimeMillis();
		
		while (timeLeft(start, timeLimit)) {
			MCTSNode newNode = selectAndExpand();
			
			double result = simulate(newNode);
			
			backpropagate(newNode, result);
		}
		
		return getBestMove();
	}
	
	private String getBestMove() {
		double max = 0.0;
		MCTSNode best = null;
		for (MCTSNode node : root.children) {
			if (node.ratio() > max) {
				max = node.ratio();
				best = node;
			}
		}
		
		return best.move;
	}

	private void backpropagate(MCTSNode node, double result) {
		while (node != null) {
			node.updateStatus(result);
			node = node.parent;
		}
	}

	private double simulate(MCTSNode node) {
		return 1.0*node.evaluationScore;
	}

	private MCTSNode selectAndExpand() {

		MCTSNode current = root;
		
		while (!current.isLeaf()) {
			int visits = current.visits;
			current = Collections.max(current.children, Comparator.comparing(c -> uct(c, visits)));
		}
		
		expand(current);
		try {
			current = getRandomElement(current.children);
		}
		catch (Exception e) {
			
		}
		
		
		return current;
	}
	
	private MCTSNode getRandomElement(ArrayList<MCTSNode> children) {
		return children.get(random(0, children.size()));
	}
	
	static Random rand = new Random();

	private int random(int min, int max) {
		if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return rand.nextInt(max - min) + min;
	}

	private void expand(MCTSNode node) {
		
		if (!node.isLeaf()) {
			throw new Error();
		}
		
		ArrayList<String> moves = Simulator.calculateAvailableMoves(node.state, node.player.contentEquals("PW") ? "PB" : "PW");
		
		for (String move : moves) {
			node.children.add(new MCTSNode(node, move, node.state, node.player.contentEquals("PW") ? "PB" : "PW"));
		}
	}
	
	MCTSNode root;
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		root.print(buffer, "", "");
		return buffer.toString();
	}
}
