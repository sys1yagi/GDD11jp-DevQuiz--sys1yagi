package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	public void perform(int w, int h, int depth) {
		if (direction == null) {
			return;
		}
		int zero =  direction.move(w, h, zeroIndex, work);
		score = calcScore(w, h,depth,  work);
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
	public static int calcScore(int w, int h, int depth, char[] work){
		//直線方向にそれぞれ壁がないかチェックする
		int score = depth;
		for (int i = 0; i < work.length; i++) {
			int dis = minDistance(w, h, work, i);
			//System.out.println(work[i]  + "=" + dis);
			score += dis;
		}		
		return score;
	}
	public static int minDistance(int w, int h, char[] source, int target){
		//壁の場合
		if(source[target] == '='){
			return 0;
		}
		int limit = 24;
		int zero = target;
		int goal = Piece.getGoalPos(source[target]);
		if(source[target] == '0'){
			goal = w*h-1;
		}
		//既にゴール位置にいる場合
		if(target == goal){
			return 0;
		}
		//System.out.println("calc:" + source[target]);
		char[] work = new char[w*h];
		System.arraycopy(source, 0, work, 0, source.length);
		
		List<ScoreNode> item = new ArrayList<ScoreNode>();
		List<ScoreNode> item2 = new ArrayList<ScoreNode>();
		new ScoreNode(null, 0, zero, work).createNext(w, h, item, limit);
		for (int d = 0; d < limit; d++) {
			for (int i = 0; i < item.size(); i++) {
				ScoreNode n = item.get(i);
				if (n != null) {
					n.perform(w, h, d);
					if(n.zeroIndex == goal){
						return n.distance;
					}
				}
			}
			//Collections.sort(item, COMPARE);
			int count = Math.min(10000, item.size());
			for (int i = 0; i < count; i++) {
				ScoreNode n = item.get(i);
				if (n.distance < limit) {
					n.createNext(w, h, item2, limit);
				}
			}
			item.clear();
			List<ScoreNode> i = item2;
			item2 = item;
			item = i;
		}
		return -1;
	}
	
	
	
	public String getHash(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < work.length; i++){
			sb.append(work[i]);
		}
		return sb.toString();
	}
	
	public static final Comparator<ScoreNode> COMPARE = new Comparator<ScoreNode>() {
		
		@Override
		public int compare(ScoreNode o1, ScoreNode o2) {
			return o1.distance - o2.distance;
		}
	};
}
