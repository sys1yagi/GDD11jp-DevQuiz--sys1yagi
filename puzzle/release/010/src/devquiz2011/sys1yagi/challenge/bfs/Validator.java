package devquiz2011.sys1yagi.challenge.bfs;

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
				Result r = quiz.getResult(puzzle.mNo);
				
				if(r != null){
					char[] work = new char[puzzle.mWidth*puzzle.mHeight];
					System.arraycopy(puzzle.mPieces, 0, work, 0, puzzle.mPieces.length);
					int zero = puzzle.mZeroIndex;
					for(int i = 0; i < r.result.length(); i++){
						char c = r.result.charAt(i);
						Direction d = Direction.valueOf(Character.toString(c));
						try{
							zero = d.move(puzzle.mWidth, puzzle.mHeight, zero, work);
						}catch(RuntimeException e){
							//System.out.println("NG:" + puzzle.toString());
							break;
						}
					}
					if(puzzle.isSolve(work, puzzle.mGoal)){
						
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
