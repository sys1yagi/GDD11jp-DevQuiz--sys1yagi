package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleNxN {
	int mNo = 0;
	int mWidth = 0;
	int mHeight = 0;
	char[] mPieces;
	// char[] mWork;
	char[] mGoal;
	int mZeroIndex = 0;
	String mResult = "";

	public PuzzleNxN() {

	}
	public static PuzzleNxN createForLine(int no, String line) {
		String[] items = line.split(",");
		int w = Integer.parseInt(items[0]);
		int h = Integer.parseInt(items[1]);
		PuzzleNxN p = new PuzzleNxN();
		if (p != null) {
			String b = items[2];
			char[] pieces = new char[w * h];
			for (int i = 0; i < b.length(); i++) {
				char c = b.charAt(i);
				pieces[i] = Piece.getPiece(c);
			}
			p.set(no, w, h, pieces);
		}
		return p;
	}

	public void set(int no, int width, int height, char[] pieces) {
		mNo = no;
		mWidth = width;
		mHeight = height;
		mPieces = pieces;
		mGoal = new char[width * height];
		GOAL: for (int i = 0; i < mGoal.length; i++) {
			if (pieces[i] == '=') {
				mGoal[i] = pieces[i];
			} else {
				for (int j = 0; j < pieces.length; j++) {
					if (Piece.CHARS[i] == pieces[j]) {
						mGoal[i] = pieces[j];
						continue GOAL;
					}
				}
				if (i + 1 <= mGoal.length) {
					mGoal[i] = Piece.BLANK.name;
				}
			}
		}

		if (REVERSE) {
			char[] tmp = mPieces;
			mPieces = mGoal;
			mGoal = tmp;
		}
		for (int i = 0; i < mPieces.length; i++) {
			if (mPieces[i] == '0') {
				mZeroIndex = i;
				break;
			}
		}
	}

	/**
	 * ワークメモリを初期化する
	 */
	void init(char[] work) {
		for (int i = 0; i < mWidth * mHeight; i++) {
			work[i] = mPieces[i];
		}
	}

	/**
	 * ゴールに到達したか判定する
	 * 
	 * @return
	 */
	boolean isSolve(char[] work, char[] goal) {
		for (int i = 0; i < mWidth * mHeight; i++) {
			if (work[i] != goal[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder orgArrays = new StringBuilder();
		StringBuilder goalArrays = new StringBuilder();
		for (int i = 0; i < mHeight * mWidth; i++) {
			orgArrays.append(mPieces[i]);
			goalArrays.append(mGoal[i]);
		}
		return getClass().getSimpleName() + "[no:" + mNo 
			+", " + mWidth + "x" + mHeight
			+", pieces:"+ orgArrays.toString() 
			+", goal:" + goalArrays.toString()
			+", zero:" + mZeroIndex 
			+ "]";
	}

	/**
	 * 盤面のスコアを計算し、継続可能かを見きわめる
	 * 
	 * @return
	 */
	public boolean isContinue(int depth, int lDepth, char[] work) {
		return Node.calcScore(mWidth, mHeight, depth,  work) <= lDepth;
	}

//	public int getScore(int depth, int lDepth, char[] work) {
//		int score = depth;
//		for (int i = 0; i < work.length; i++) {
//			int goal = Piece.getGoalPos(work[i]);
//			int x = goal % mWidth;
//			int y = goal / mHeight;
//			int ix = i % mWidth;
//			int iy = i / mHeight;
//			score += Math.abs(x - ix);
//			score += Math.abs(y - iy);
//		}
//		// System.out.print(score+"::");
//		return score;
//	}

	//public static int LIMIT_DEPTH = 1;
	//public static int LIMIT_WIDTH = 10000;
	public static boolean REVERSE = false;

	static class Item {
		int score;
		char[] work;
	}

	public String start(int limitDepth, int limitWidth) {
		System.out.println("start:" + toString() + "::depth" + limitDepth + " width:" + limitWidth);

		Map<String, String> history = new HashMap<String, String>();

		long time = System.currentTimeMillis();

		List<Node> item = new ArrayList<Node>();
		List<Node> item2 = new ArrayList<Node>();
		int zero = mZeroIndex;
		int score = Node.calcScore(mWidth, mHeight,0, mPieces);//getScore(0, limit_depth, mPieces);

		new Node(null, mPieces, score, zero, "").createNext(mWidth, mHeight,
				item, limitDepth);
		for (int d = 0; d < limitDepth; d++) {
			for (int i = 0; i < item.size(); i++) {
				Node n = item.get(i);
				if (n != null) {
					n.perform(mWidth, mHeight, d);
					if (isSolve(n.work, mGoal)) {
						mResult = n.history;
						if (REVERSE) {
							mResult = reverse(n.history);
						}
						System.out.println("end:"
								+ (System.currentTimeMillis() - time) + ":"
								+ mResult);
						return mResult;
					}
				}
			}
			Collections.sort(item, COMPARE);
			int count = Math.min(limitWidth, item.size());
			for (int i = 0; i < count; i++) {
				Node n = item.get(i);
				if (n.history.length() < limitDepth) {
					// ハッシュ参照
					// n.createNext(mWidth, mHeight, item2, limit_depth);

					String hash = n.getHash();
					if (history.get(hash) == null) {
						n.createNext(mWidth, mHeight, item2, limitDepth);
						history.put(hash, "");
					}
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

	/**
	 * リバースモードの場合に利用するResultをひっくり返すメソッド
	 * @param result
	 * @return
	 */
	public String reverse(String result) {
		StringBuilder sb = new StringBuilder();
		for (int ii = 0; ii < result.length(); ii++) {
			char c = result.charAt(ii);
			switch (c) {
			case 'L':
				c = 'R';
				break;
			case 'R':
				c = 'L';
				break;
			case 'U':
				c = 'D';
				break;
			case 'D':
				c = 'U';
				break;
			}
			sb.append(c);
		}
		return sb.reverse().toString();
	}
	
	/**
	 * ノードをスコア順にソートするComparator
	 */
	public final static Comparator<Node> COMPARE = new Comparator<Node>() {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.score - o2.score;
		}
	};
}
