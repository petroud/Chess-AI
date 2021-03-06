package util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.World;

public class Simulator {
	
	public static HashMap<String,List<String>> neighbours;
	
	public static double calculateScore(String[][] board, String move) {
		double val = 0.0;
		
		int rnow = Character.getNumericValue(move.charAt(0));
		int cnow = Character.getNumericValue(move.charAt(1));

		int rgoal = Character.getNumericValue(move.charAt(2));
		int cgoal = Character.getNumericValue(move.charAt(3));
		
		String typeNow = board[rnow][cnow];
		String typeGoal = board[rgoal][cgoal];
		
		double valueNow = evaluatePosition(typeNow);
		double valueGoal = evaluatePosition(typeGoal);
		
		val = valueGoal;
		
		return val;		
	}
	
	private static double evaluatePosition(String content) {
		
		if(content.length()<2) {
			if(content.contentEquals("P")) {
				return 0.9;
			}else {
				return 0;
			}
		}else if(content.length()==2){
			
			if(content.charAt(1) == 'P') {
				return 1;
			}else if(content.charAt(1) == 'R') {
				return 3;
			}else if(content.charAt(1) == 'K') {
				return 8;
			}else {
				return 0;
			}
		}else {
			return 0;
		}
	}
	
	private static List<String> findNeighbours(int  r, int c, String[][] board) {
		List<String> neighs = new ArrayList<String>();
		for (int nr = Math.max(0, r - 1); nr <= Math.min(r + 1, board.length - 1); ++nr){
		    for (int nc = Math.max(0, c - 1); nc <= Math.min(c + 1, board[0].length - 1); ++nc) {
		        if (!(nr==r && nc==c))  {  
		        	neighs.add(""+nr+""+nc);
		        }
		    }
		}
	    return neighs;
	}
	
	public static void calculateNeighbors(String[][] board) {
		 neighbours = new HashMap<String,List<String>>();
		 
		 for (int row = 0; row < board.length; row++) { 
			 for (int col = 0; col < board[row].length; col++) { 
		          List<String> ns = findNeighbours(row,col,board);
		          neighbours.put(""+row+""+col, ns);
		     } 
		 }

	}

	public static ArrayList<String> calculateAvailableMoves(String[][] board, String player) {
		
		ArrayList<String> availableMoves = new ArrayList<String>();

		if (player.contentEquals("PW")) {
			String firstLetter = "";
			String secondLetter = "";
			String move = "";
					
			for(int i=0; i<World.rows; i++)
			{
				for(int j=0; j<World.columns; j++)
				{
					firstLetter = Character.toString(board[i][j].charAt(0));
					
					// if it there is not a white chess part in this position then keep on searching
					if(firstLetter.equals("B") || firstLetter.equals(" ") || firstLetter.equals("P"))
						continue;
					
					// check the kind of the white chess part
					secondLetter = Character.toString(board[i][j].charAt(1));
					
					if(secondLetter.equals("P"))	// it is a pawn
					{
						
						// check if it can move one vertical position ahead
						firstLetter = Character.toString(board[i-1][j].charAt(0));
						
						if(firstLetter.equals(" ") || firstLetter.equals("P"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i-1) + Integer.toString(j);
							
							availableMoves.add(move);
						}
						
						// check if it can move crosswise to the left
						if(j!=0 && i!=0)
						{
							firstLetter = Character.toString(board[i-1][j-1].charAt(0));						
							if(!(firstLetter.equals("W") || firstLetter.equals(" ") || firstLetter.equals("P"))) {
								move = Integer.toString(i) + Integer.toString(j) + 
										   Integer.toString(i-1) + Integer.toString(j-1);
									
								availableMoves.add(move);
							}											
						}
						
						// check if it can move crosswise to the right
						if(j!=World.columns-1 && i!=0)
						{
							firstLetter = Character.toString(board[i-1][j+1].charAt(0));
							if(!(firstLetter.equals("W") || firstLetter.equals(" ") || firstLetter.equals("P"))) {
								
								move = Integer.toString(i) + Integer.toString(j) + 
										   Integer.toString(i-1) + Integer.toString(j+1);							
								availableMoves.add(move);
							}
						}
					}
					else if(secondLetter.equals("R"))	// it is a rook
					{
						// check if it can move upwards
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((i-(k+1)) < 0)
								break;
							
							firstLetter = Character.toString(board[i-(k+1)][j].charAt(0));
							
							if(firstLetter.equals("W"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i-(k+1)) + Integer.toString(j);
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("B") || firstLetter.equals("P"))
								break;
						}
						
						// check if it can move downwards
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((i+(k+1)) == World.rows)
								break;
							
							firstLetter = Character.toString(board[i+(k+1)][j].charAt(0));
							
							if(firstLetter.equals("W"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i+(k+1)) + Integer.toString(j);
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("B") || firstLetter.equals("P"))
								break;
						}
						
						// check if it can move on the left
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((j-(k+1)) < 0)
								break;
							
							firstLetter = Character.toString(board[i][j-(k+1)].charAt(0));
							
							if(firstLetter.equals("W"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j-(k+1));
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("B") || firstLetter.equals("P"))
								break;
						}
						
						// check of it can move on the right
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((j+(k+1)) == World.columns)
								break;
							
							firstLetter = Character.toString(board[i][j+(k+1)].charAt(0));
							
							if(firstLetter.equals("W"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j+(k+1));
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("B") || firstLetter.equals("P"))
								break;
						}
					}
					else // it is the king
					{
						// check if it can move upwards
						if((i-1) >= 0)
						{
							firstLetter = Character.toString(board[i-1][j].charAt(0));
							
							if(!firstLetter.equals("W"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i-1) + Integer.toString(j);
									
								availableMoves.add(move);	
							}
						}
						
						// check if it can move downwards
						if((i+1) < World.rows)
						{
							firstLetter = Character.toString(board[i+1][j].charAt(0));
							
							if(!firstLetter.equals("W"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i+1) + Integer.toString(j);
									
								availableMoves.add(move);	
							}
						}
						
						// check if it can move on the left
						if((j-1) >= 0)
						{
							firstLetter = Character.toString(board[i][j-1].charAt(0));
							
							if(!firstLetter.equals("W"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i) + Integer.toString(j-1);
									
								availableMoves.add(move);	
							}
						}
						
						// check if it can move on the right
						if((j+1) < World.columns)
						{
							firstLetter = Character.toString(board[i][j+1].charAt(0));
							
							if(!firstLetter.equals("W"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i) + Integer.toString(j+1);
									
								availableMoves.add(move);	
							}
						}
					}			
				}	
			}
		}
		else if (player.contentEquals("PB")) {
			String firstLetter = "";
			String secondLetter = "";
			String move = "";
					
			for(int i=0; i<World.rows; i++)
			{
				for(int j=0; j<World.columns; j++)
				{
					firstLetter = Character.toString(board[i][j].charAt(0));
					
					// if it there is not a black chess part in this position then keep on searching
					if(firstLetter.equals("W") || firstLetter.equals(" ") || firstLetter.equals("P"))
						continue;
					
					// check the kind of the white chess part
					secondLetter = Character.toString(board[i][j].charAt(1));
					
					if(secondLetter.equals("P"))	// it is a pawn
					{
						
						// check if it can move one vertical position ahead
						firstLetter = Character.toString(board[i+1][j].charAt(0));
						
						if(firstLetter.equals(" ") || firstLetter.equals("P"))
						{
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i+1) + Integer.toString(j);
							
							availableMoves.add(move);
						}
						
						// check if it can move crosswise to the left
						if(j!=0 && i!=World.rows-1)
						{
							firstLetter = Character.toString(board[i+1][j-1].charAt(0));
							
							if(!(firstLetter.equals("B") || firstLetter.equals(" ") || firstLetter.equals("P"))) {
								move = Integer.toString(i) + Integer.toString(j) + 
										   Integer.toString(i+1) + Integer.toString(j-1);
									
								availableMoves.add(move);
							}																	
						}
						
						// check if it can move crosswise to the right
						if(j!=World.columns-1 && i!=World.rows-1)
						{
							firstLetter = Character.toString(board[i+1][j+1].charAt(0));
							
							if(!(firstLetter.equals("B") || firstLetter.equals(" ") || firstLetter.equals("P"))) {
								move = Integer.toString(i) + Integer.toString(j) + 
										   Integer.toString(i+1) + Integer.toString(j+1);
									
								availableMoves.add(move);
							}
								
							
							
						}
					}
					else if(secondLetter.equals("R"))	// it is a rook
					{
						// check if it can move upwards
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((i-(k+1)) < 0)
								break;
							
							firstLetter = Character.toString(board[i-(k+1)][j].charAt(0));
							
							if(firstLetter.equals("B"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i-(k+1)) + Integer.toString(j);
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("W") || firstLetter.equals("P"))
								break;
						}
						
						// check if it can move downwards
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((i+(k+1)) == World.rows)
								break;
							
							firstLetter = Character.toString(board[i+(k+1)][j].charAt(0));
							
							if(firstLetter.equals("B"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i+(k+1)) + Integer.toString(j);
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("W") || firstLetter.equals("P"))
								break;
						}
						
						// check if it can move on the left
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((j-(k+1)) < 0)
								break;
							
							firstLetter = Character.toString(board[i][j-(k+1)].charAt(0));
							
							if(firstLetter.equals("B"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j-(k+1));
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("W") || firstLetter.equals("P"))
								break;
						}
						
						// check of it can move on the right
						for(int k=0; k<World.rookBlocks; k++)
						{
							if((j+(k+1)) == World.columns)
								break;
							
							firstLetter = Character.toString(board[i][j+(k+1)].charAt(0));
							
							if(firstLetter.equals("B"))
								break;
							
							move = Integer.toString(i) + Integer.toString(j) + 
								   Integer.toString(i) + Integer.toString(j+(k+1));
							
							availableMoves.add(move);
							
							// prevent detouring a chesspart to attack the other
							if(firstLetter.equals("W") || firstLetter.equals("P"))
								break;
						}
					}
					else // it is the king
					{
						// check if it can move upwards
						if((i-1) >= 0)
						{
							firstLetter = Character.toString(board[i-1][j].charAt(0));
							
							if(!firstLetter.equals("B"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i-1) + Integer.toString(j);
									
								availableMoves.add(move);	
							}
						}
						
						// check if it can move downwards
						if((i+1) < World.rows)
						{
							firstLetter = Character.toString(board[i+1][j].charAt(0));
							
							if(!firstLetter.equals("B"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i+1) + Integer.toString(j);
									
								availableMoves.add(move);	
							}
						}
						
						// check if it can move on the left
						if((j-1) >= 0)
						{
							firstLetter = Character.toString(board[i][j-1].charAt(0));
							
							if(!firstLetter.equals("B"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i) + Integer.toString(j-1);
									
								availableMoves.add(move);	
							}
						}
						
						// check if it can move on the right
						if((j+1) < World.columns)
						{
							firstLetter = Character.toString(board[i][j+1].charAt(0));
							
							if(!firstLetter.equals("B"))
							{
								move = Integer.toString(i) + Integer.toString(j) + 
									   Integer.toString(i) + Integer.toString(j+1);
									
								availableMoves.add(move);	
							}
						}
					}			
				}	
			}
		}
		else {
			assert(false);
			throw new Error("Invalid player");
		}
		
		
		
		
		
		
		return availableMoves;
	}
	
	public static String[][] makeMove(int x1, int y1, int x2, int y2, int prizeX, int prizeY, String[][] state) {
		
		String[][] board = new String[state.length][state[0].length];
		
		for (int i=0; i<state.length; i++) {
			for (int j=0; j<state[0].length; j++) {
				board[i][j] = state[i][j];
			}
		}
		
		String chesspart = Character.toString(board[x1][y1].charAt(1));
		
		boolean pawnLastRow = false;
		
		// check if it is a move that has made a move to the last line
		if(chesspart.equals("P"))
			if( (x1==World.rows-2 && x2==World.rows-1) || (x1==1 && x2==0) )
			{
				board[x2][y2] = " ";	// in a case an opponent's chess part has just been captured
				board[x1][y1] = " ";
				pawnLastRow = true;
			}
		
		// otherwise
		if(!pawnLastRow)
		{
			board[x2][y2] = board[x1][y1];
			board[x1][y1] = " ";
		}
		
		// check if a prize has been added in the game
		if(prizeX != World.noPrize)
			board[prizeX][prizeY] = "P";
		
		return board;
	}
}
