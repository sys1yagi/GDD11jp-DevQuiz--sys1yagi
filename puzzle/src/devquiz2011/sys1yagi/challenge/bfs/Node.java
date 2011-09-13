package devquiz2011.sys1yagi.challenge.bfs;


import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.score.Score;

public class Node {
	/**
	 * 
	 */
	private static final long serialVersionUID = 85649455139559938L;
	Direction direction;
	int score;
	int zeroIndex;
	char[] work;
	String history = "";
	
	public Node(Direction d, char[] work, int score, int zeroIndex, String history) {
		direction = d;
		this.work = new char[work.length];
		System.arraycopy(work, 0, this.work, 0, work.length);
		this.score = score;
		this.zeroIndex = zeroIndex;
		if(direction != null){
			this.history = history + direction;
		}
	}

	public void createNext(int w, int h, List<Node> queue, int limit) {
		if(this.score + history.length() > limit){
			return;
		}
		
		if (Direction.R.canMove(w, h, zeroIndex, work) && direction != Direction.L) {
			queue.add(new Node(Direction.R, work, score, zeroIndex, history));
		}
		if (Direction.L.canMove(w, h, zeroIndex, work) && direction != Direction.R) {
			queue.add(new Node(Direction.L, work, score, zeroIndex, history));
		}
		if (Direction.U.canMove(w, h, zeroIndex, work) && direction != Direction.D) {
			queue.add(new Node(Direction.U, work, score, zeroIndex, history));
		}
		if (Direction.D.canMove(w, h, zeroIndex, work) && direction != Direction.U) {
			queue.add(new Node(Direction.D, work, score, zeroIndex, history));
		}
	}

	/**
	 * 新しい空白の位置を返す。移動不能な場合-1を返す
	 * 
	 * @param w
	 * @param h
	 * @param zero
	 * @param work
	 * @return
	 */
	public void perform(int w, int h, int depth, Score scorer) {
		if (direction == null) {
			return;
		}
		int zero =  direction.move(w, h, zeroIndex, work);
		score = scorer.calc(w, h,depth,  work);
		zeroIndex = zero;
	}
	
	public static int calcScore2(int w, int h, int depth, char[] work){
		//直線方向にそれぞれ壁がないかチェックする
		int score = depth;
		for (int i = 0; i < work.length; i++) {
			if(work[i] != '='){
				int goal = Piece.getGoalPos(work[i]);
				if(work[i] == '0'){
					goal = w*h-1;
				}
				int x = goal % w;
				int y = goal / w;
				int ix = i % w;
				int iy = i / w;
				score += Math.abs(x - ix);
				score += Math.abs(y - iy);
				System.out.println(work[i] + "=" + (Math.abs(x - ix) + Math.abs(y - iy)) + " goal=" + goal);
			}
		}		
		return score;
	}
	
	public String getHash(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < work.length; i++){
			sb.append(work[i]);
		}
		return sb.toString();
	}
}
