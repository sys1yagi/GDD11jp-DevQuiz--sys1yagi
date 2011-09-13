package devquiz2011.sys1yagi.challenge.bfs.score;

import java.util.ArrayList;
import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.Piece;

/**
 * 最短経路
 * @author tyagi
 *
 */
public class SH implements Score {

	public int calc(int w, int h, int depth, char[] work){
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
	@Override
	public int addDistance(int w, int h, int depth, char[] work,
			int currentScore) {
		return calc(w, h, depth, work);
	}
}
