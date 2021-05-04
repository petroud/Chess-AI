package util;


public class FutureTree {
	
	public FutureTree(String[][] initialState, int depth, String starting) {
		root = new TreeNode(null, initialState, 0, depth, starting);
		
		bestMove = root.minimaxBestMove;
	}
	
	private TreeNode root;
	private String bestMove;
	
	public String getBestMove() {
		return bestMove;
	}
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		root.print(buffer, "", "");
		return buffer.toString();
	}
}
