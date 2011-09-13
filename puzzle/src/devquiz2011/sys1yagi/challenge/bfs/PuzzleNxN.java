package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devquiz2011.sys1yagi.challenge.bfs.score.Score;

public class PuzzleNxN {
	int mNo = 0;
	int mWidth = 0;
	int mHeight = 0;
	char[] mPieces;
	char[] mGoal;
	int mZeroIndex = 0;
	String mResult = "";
	
	public int getNo() {
		return mNo;
	}

	public int getWidth() {
		return mWidth;
	}

	public int getHeight() {
		return mHeight;
	}

	public char[] getPieces() {
		return mPieces;
	}

	public String getResult() {
		return mResult;
	}

	public char[] getGoal() {
		return mGoal;
	}

	public int getZeroIndex() {
		return mZeroIndex;
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

	private void set(int no, int width, int height, char[] pieces) {
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
	public boolean isSolve(char[] work, char[] goal) {
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

	public static boolean REVERSE = false;

	public String start(int limitDepth, int limitWidth, Score scorer) {
		if(mWidth * mHeight > 9){
			return null;
		}
		long time = System.currentTimeMillis();
		System.out.println("start:" + toString() + "::depth" + limitDepth + " width:" + limitWidth);
		
		Map<String, String> history = new HashMap<String, String>();
		List<Node> item = new ArrayList<Node>(limitWidth*4);
		List<Node> item2 = new ArrayList<Node>(limitWidth*4);
		int zero = mZeroIndex;
		int score = scorer.calc(mWidth, mHeight, 0, mPieces); 

		new Node(null, mPieces, score, zero, "").createNext(mWidth, mHeight,
				item, limitDepth);
		for (int d = 0; d < limitDepth; d++) {
			for (int i = 0; i < item.size(); i++) {
				Node n = item.get(i);
				if (n != null) {
					n.perform(mWidth, mHeight, d, scorer);
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
