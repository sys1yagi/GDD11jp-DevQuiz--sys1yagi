package devquiz2011.sys1yagi.challenge.bfs.score;

import devquiz2011.sys1yagi.challenge.bfs.Piece;

public class MD implements Score{
	@Override
	public int calc(int w, int h, int depth, char[] work) {
		int score = depth;
		for (int i = 0; i < work.length; i++) {
			int goal = Piece.getGoalPos(work[i]);
			int x = goal % w;
			int y = goal / h;
			int ix = i % w;
			int iy = i / h;
			score += Math.abs(x - ix);
			score += Math.abs(y - iy);
		}
		return score;
	}
	@Override
	public int addDistance(int w, int h, int depth, char[] work,
			int currentScore) {
		//TODO ·•ª‚ÌŒvŽZ‚·‚é“z‚ðŒã‚Å‚â‚é
		return calc(w, h, depth, work);
	}
}
