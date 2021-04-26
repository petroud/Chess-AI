import java.util.ArrayList;
import java.util.Random;

public class ActionPerformer {
	
	public ActionPerformer(ArrayList<String> availableMoves) {
		Random ran = new Random();
		int x = ran.nextInt(availableMoves.size());
		
		bestMove = availableMoves.get(x);
	}
	
	private final String bestMove;
	
	@Override
	public String toString() {
		return bestMove;
	}
}
