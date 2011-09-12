package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Puzzle3x3 extends PuzzleNxN {
	public static int LIMIT_DEPTH = 1;
	public int getLimitDepth() {
		return LIMIT_DEPTH;
	}

	public int getMaxCount() {
		return 20000;
	}

	static class Item {
		int score;
		char[] work;
	}

	@Override
	public String start() {
		System.out.println("start:" + toString() + "::"+ +LIMIT_DEPTH);
		long time = System.currentTimeMillis();
		int limit_width = getMaxCount();
		int limit_depth = getLimitDepth();
		List<Node> item = new ArrayList<Node>();
		List<Node> item2 = new ArrayList<Node>();
		int zero = mZeroIndex;
		int score = getScore(0, limit_depth, mPieces);
		
		new Node(null, mPieces, score, zero, "").createNext(mWidth, mHeight,
				item, limit_depth);
		for (int d = 0; d < limit_depth; d++) {
			for (int i = 0; i < item.size(); i++) {
				Node n = item.get(i);
				if (n != null) {
					//System.out.println("score:" + n.score + ":" + n.zeroIndex + ":" + n.direction);
					n.perform(mWidth, mHeight);
					if (isSolve(n.work, mGoal)) {
						mResult = n.history;
						System.out.println("end:"
								+ (System.currentTimeMillis() - time) + ":"
								+ mResult);
						return mResult;
					}
				}
			}
			Collections.sort(item, COMPARE);
			int count = Math.min(limit_width, item.size());
			for (int i = 0; i < count ; i++) {
				Node n = item.get(i);
				if (n.history.length() < limit_depth) {
					n.createNext(mWidth, mHeight, item2, limit_depth);
				}
			}
			item.clear();
			List<Node> i = item2;
			item2 = item; 
			item = i;
		}
		System.out.println("end:" + (System.currentTimeMillis() - time));
		return null;
	}
	Comparator<Node> COMPARE = new Comparator<Node>() {
		
		@Override
		public int compare(Node o1, Node o2) {
			return o1.score - o2.score;
		}
	};
}