package devquiz2011.sys1yagi.challenge.bfs.score;

public interface Score {
	public int calc(int w, int h, int depth, char[] work);
	public int addDistance(int w, int h, int depth, char[] work, int currentScore);
}