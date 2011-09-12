package devquiz2011.sys1yagi.challenge.bfs;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Node implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 85649455139559938L;
	// Node l, r, u, d;
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
		// TODO �X�R�A����
		// this.score�������l�ȉ��Ȃ炠�ځ[��Ƃ��B
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
			//System.out.println("create:" + zeroIndex + ":" + w + ":"+ history);
			queue.add(new Node(Direction.U, work, score, zeroIndex, history));
		}
		if (Direction.D.canMove(w, h, zeroIndex, work) && direction != Direction.U) {
			queue.add(new Node(Direction.D, work, score, zeroIndex, history));
		}
	}

	/**
	 * �V�����󔒂̈ʒu��Ԃ��B�ړ��s�\�ȏꍇ-1��Ԃ�
	 * 
	 * @param w
	 * @param h
	 * @param zero
	 * @param work
	 * @return
	 */
	public void perform(int w, int h) {
		if (direction == null) {
			return;
		}
		int zero =  direction.move(w, h, zeroIndex, work);
		score += calcScore(w, h, zero, zeroIndex, work);
		zeroIndex = zero;
	}
	
	public static int calcScore(int w, int h, int src, int dist, char[] work){
		int goal=Piece.getGoalPos(work[dist]);
		int goalx = goal%w;
		int goaly = goal/h;
		int currentx = dist%w;
		int currenty = dist/h;
		
		int currentd = Math.abs(goalx-currentx) + Math.abs(goaly-currenty);
		int srcx = src%w;
		int srcy = src/h;
		
		int srcd = Math.abs(goalx-srcx) + Math.abs(goaly-srcy);
		
		//���������ɂ��ꂼ��ǂ��Ȃ����`�F�b�N����
		
		
		return currentd - srcd;
	}
	
	public String getHash(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < work.length; i++){
			sb.append(work[i]);
		}
		return sb.toString();
	}
}
