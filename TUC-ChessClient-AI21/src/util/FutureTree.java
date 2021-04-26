package util;


public class FutureTree {
	
	public FutureTree(String[][] initialState, int depth) {
		root = new TreeNode(null, initialState, 0, depth);
	}
	
	private TreeNode root;
	
	@Override
	public String toString() {
		StringBuilder buffer = new StringBuilder();
		root.print(buffer, "", "");
		return buffer.toString();
	}
}
