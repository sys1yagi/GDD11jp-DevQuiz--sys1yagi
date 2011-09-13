package devquiz2011.sys1yagi.challenge.bfs;

/**
 * Game Status
 * @author sylc.yagi
 */
public class Status {
	int lx;
	int rx;
	int ux;
	int dx;
	/** number of puzzles. */
	int count;
	
	public void setLimits(String line){
		String[] items = line.split(" ");
		if(items.length == 4){
			lx = Integer.parseInt(items[0]);
			rx = Integer.parseInt(items[1]);
			ux = Integer.parseInt(items[2]);
			dx = Integer.parseInt(items[3]);
		}
		else{
			throw new IllegalArgumentException(line);
		}
	}
	public void setCount(String line){
		count = Integer.parseInt(line);
	}
	@Override
	public String toString() {
		return "Status [lx=" + lx + ", rx=" + rx + ", ux=" + ux + ", dx=" + dx + ", count=" + count + "]";
	}
	
}
