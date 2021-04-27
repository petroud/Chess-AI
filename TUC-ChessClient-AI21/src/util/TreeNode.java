package util;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class TreeNode {
	
	public TreeNode(String move, String[][] initalState, int depth, int maxDepth) {
		
		this.move = move;
		this.depth = depth;
		
		if (depth > maxDepth) {
			return;
		}
		
		state = new String[initalState.length][initalState[0].length];
		
		calculateChildren();
	}
	
	final int depth;
	String[][] state;
	private String move;
	private ArrayList<TreeNode> children;
	
	private void calculateChildren() {
		children = new ArrayList<TreeNode>();
	}
	
	@Override
	public String toString() {
		return "Move: " + move + "\n" + children + "\n";
	}
	
	void print(StringBuilder buffer, String prefix, String childrenPrefix) {
		buffer.append(new String(prefix.getBytes(), StandardCharsets.UTF_8));
		buffer.append(move+"\n");
		
		for (TreeNode it : children) {
			if (!it.children.isEmpty()) {
				it.print(buffer,  childrenPrefix + "├── ", childrenPrefix + "│   ");
			}
			else {
				it.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
			}
		}
	}
}
