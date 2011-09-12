package devquiz2011.sys1yagi.challenge.bfs.util;

import devquiz2011.sys1yagi.challenge.bfs.Direction;
import devquiz2011.sys1yagi.challenge.bfs.PuzzleNxN;
import devquiz2011.sys1yagi.challenge.bfs.Quiz;
import devquiz2011.sys1yagi.challenge.bfs.Results.Result;

public class Validator {
	public static void main(String[] args) {
		try {

			final Quiz quiz = new Quiz();
			// ñ‚ëËÇÃì«Ç›çûÇ›
			quiz.load("files/challenge.slidepuzzle.txt");
			// âìöçœÇ›åãâ ì«Ç›çûÇ›
			quiz.loadResults("files/result.txt");

			PuzzleNxN puzzle = null;
			while ((puzzle = quiz.getNext()) != null) {
				Result r = quiz.getResult(puzzle.getNo());
				
				if(r != null){
					char[] work = new char[puzzle.getWidth()*puzzle.getHeight()];
					System.arraycopy(puzzle.getPieces(), 0, work, 0, puzzle.getPieces().length);
					int zero = puzzle.getZeroIndex();
					for(int i = 0; i < r.getResult().length(); i++){
						char c = r.getResult().charAt(i);
						Direction d = Direction.valueOf(Character.toString(c));
						try{
							zero = d.move(puzzle.getWidth(), puzzle.getHeight(), zero, work);
						}catch(RuntimeException e){
							//System.out.println("NG:" + puzzle.toString());
							break;
						}
					}
					if(puzzle.isSolve(work, puzzle.getGoal())){
						
					}
					else{
						System.out.println("NG:" + puzzle.toString());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}
}
