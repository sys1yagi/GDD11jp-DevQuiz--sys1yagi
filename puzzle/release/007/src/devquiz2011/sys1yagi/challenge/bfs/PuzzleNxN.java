package devquiz2011.sys1yagi.challenge.bfs;

abstract public class PuzzleNxN {
	private final static char[] CHARS = { '1', '2', '3', '4', '5', '6', '7',
			'8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
			'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
			'Y', 'Z' , '0'};
	int mNo = 0;
	int mWidth = 0;
	int mHeight = 0;
	char[] mPieces;
//	char[] mWork;
	char[] mGoal;
	int mZeroIndex = 0;
	String mResult = "";

	public PuzzleNxN() {

	}

	public void set(int no, int width, int height, char[] pieces) {
		mNo = no;
		mWidth = width;
		mHeight = height;
		mPieces = pieces;
//		mWork = new char[width * height];
		mGoal = new char[width * height];
		for(int i = 0; i < pieces.length; i++){
			if(pieces[i]=='0'){
				mZeroIndex = i;
				break;
			}
		}
		GOAL:for (int i = 0; i < mGoal.length; i++) {
			if(pieces[i] == '='){
				mGoal[i] = pieces[i];
			}
			else{
				for (int j = 0; j < pieces.length; j++) {
					if(CHARS[i] == pieces[j]){
						mGoal[i] = pieces[j];
						continue GOAL;
					}
				}
				if(i+1 <= mGoal.length){
					mGoal[i] = Piece.BLANK.name;
				}
			}
		}
	}

	/**
	 * mWorkを初期化する
	 */
	void init(char[] work){
		for(int i = 0; i < mWidth*mHeight; i++){
			work[i] = mPieces[i];
		}
	}
	
	/**
	 * ゴールに到達したか判定する
	 * @return
	 */
	boolean isSolve(char[] work, char[] goal){
		for(int i = 0; i < mWidth*mHeight; i++){
			if(work[i] != goal[i]){
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
		return getClass().getSimpleName() + "[no:" + mNo + ", pieces:"
				+ orgArrays.toString() + ", goal:" + goalArrays.toString()
				+ "]";
	}

	abstract public String start();
	
	
	/**
	 * 盤面のスコアを計算し、継続可能かを見きわめる
	 * @return
	 */
	public boolean isContinue(int depth, int lDepth, char[] work){
//		int score = depth;
//		for(int i = 0; i < work.length; i++){
//			int goal = Piece.getGoalPos(work[i]);
//			int x = goal%mWidth;
//			int y = goal/mHeight;
//			int ix = i%mWidth;
//			int iy = i/mHeight;
//			score += Math.abs(x-ix);
//			score += Math.abs(y-iy);
//		}
		//System.out.println("score=" + score);
//		return true;
		return getScore(depth, lDepth, work) <= lDepth;
	}
	public int getScore(int depth,int lDepth, char[] work){
		int score = depth;
		for(int i = 0; i < work.length; i++){
			int goal = Piece.getGoalPos(work[i]);
			int x = goal%mWidth;
			int y = goal/mHeight;
			int ix = i%mWidth;
			int iy = i/mHeight;
			score += Math.abs(x-ix);
			score += Math.abs(y-iy);
		}
		//System.out.print(score+"::");
		return score;
	}
}
