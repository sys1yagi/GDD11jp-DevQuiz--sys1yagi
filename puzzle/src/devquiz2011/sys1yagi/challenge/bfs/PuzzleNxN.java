package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PuzzleNxN {
	private final static char[] CHARS = { '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z', '0' };
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
		// mWork = new char[width * height];
		mGoal = new char[width * height];
		GOAL: for (int i = 0; i < mGoal.length; i++) {
			if (pieces[i] == '=') {
				mGoal[i] = pieces[i];
			} else {
				for (int j = 0; j < pieces.length; j++) {
					if (CHARS[i] == pieces[j]) {
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
	 * ���[�N������������������
	 */
	void init(char[] work) {
		for (int i = 0; i < mWidth * mHeight; i++) {
			work[i] = mPieces[i];
		}
	}

	/**
	 * �S�[���ɓ��B���������肷��
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
	 * �Ֆʂ̃X�R�A���v�Z���A�p���\����������߂�
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

	public static int LIMIT_DEPTH = 1;
	public static int LIMIT_WIDTH = 10000;
	public static boolean REVERSE = false;

	public int getLimitDepth() {
		return LIMIT_DEPTH;
	}

	public int getMaxCount() {
		return LIMIT_WIDTH;
	}

	static class Item {
		int score;
		char[] work;
	}

	public String start() {
		System.out.println("start:" + toString() + "::" + LIMIT_DEPTH);

		Map<String, String> history = new HashMap<String, String>();

		long time = System.currentTimeMillis();
		int limit_width = getMaxCount();
		int limit_depth = getLimitDepth();
		List<Node> item = new ArrayList<Node>();
		List<Node> item2 = new ArrayList<Node>();
		int zero = mZeroIndex;
		int score = Node.calcScore(mWidth, mHeight,0, mPieces);//getScore(0, limit_depth, mPieces);

		new Node(null, mPieces, score, zero, "").createNext(mWidth, mHeight,
				item, limit_depth);
		for (int d = 0; d < limit_depth; d++) {
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
			int count = Math.min(limit_width, item.size());
			for (int i = 0; i < count; i++) {
				Node n = item.get(i);
				if (n.history.length() < limit_depth) {
					// �n�b�V���Q��
					// n.createNext(mWidth, mHeight, item2, limit_depth);

					String hash = n.getHash();
					if (history.get(hash) == null) {
						n.createNext(mWidth, mHeight, item2, limit_depth);
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
	 * ���o�[�X���[�h�̏ꍇ�ɗ��p����Result���Ђ�����Ԃ����\�b�h
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
	 * �m�[�h���X�R�A���Ƀ\�[�g����Comparator
	 */
	public final static Comparator<Node> COMPARE = new Comparator<Node>() {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.score - o2.score;
		}
	};
}
