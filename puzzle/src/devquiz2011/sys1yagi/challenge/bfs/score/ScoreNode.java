package devquiz2011.sys1yagi.challenge.bfs.score;

import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.Direction;

/**
 * 壁を考慮したスコア計算を行う為のクラス
 * 
 * @author tyagi
 * 
 */
public class ScoreNode {
	Direction direction;
	public int distance;
	int zeroIndex;
	char[] work;

	public ScoreNode(Direction direction, int score, int zeroIndex, char[] work) {
		this.direction = direction;
		this.distance = score;
		this.zeroIndex = zeroIndex;
		this.work = work;
	}

	public void perform(int w, int h, int depth) {
		if (direction == null) {
			return;
		}
		int zero = direction.move(w, h, zeroIndex, work);
		zeroIndex = zero;
		distance++;
	}

	public void createNext(int w, int h, List<ScoreNode> queue, int limit) {
		if (this.distance > limit) {
			return;
		}
		if (Direction.R.canMove(w, h, zeroIndex, work)
				&& direction != Direction.L) {
			queue.add(new ScoreNode(Direction.R, distance, zeroIndex, work));
		}
		if (Direction.L.canMove(w, h, zeroIndex, work)
				&& direction != Direction.R) {
			queue.add(new ScoreNode(Direction.L, distance, zeroIndex, work));
		}
		if (Direction.U.canMove(w, h, zeroIndex, work)
				&& direction != Direction.D) {
			queue.add(new ScoreNode(Direction.U, distance, zeroIndex, work));
		}
		if (Direction.D.canMove(w, h, zeroIndex, work)
				&& direction != Direction.U) {
			queue.add(new ScoreNode(Direction.D, distance, zeroIndex, work));
		}
	}
}
