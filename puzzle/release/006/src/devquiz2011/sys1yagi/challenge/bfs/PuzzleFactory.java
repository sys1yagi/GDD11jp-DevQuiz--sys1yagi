package devquiz2011.sys1yagi.challenge.bfs;

public class PuzzleFactory {
	public static PuzzleNxN createForLine(int no, String line){
		String[] items = line.split(",");
		int w = Integer.parseInt(items[0]);
		int h = Integer.parseInt(items[1]);
		PuzzleNxN p = null;
		switch (w*h) {
		case 9: // 3x3=9
			p = new Puzzle3x3();
			break;
		case 12: // 3x4=12
			p =  new Puzzle3x4();
			break;
		case 15:// 3x5=15
			p =  new Puzzle3x5();
			break;
		case 18: // 3x6=18
			p =  new Puzzle3x6();
			break;
		case 16: // 4x4=16
			p =  new Puzzle4x4();
			break;
		case 20: // 4x5=20
			p =  new Puzzle4x5();
			break;
		case 24: // 4x6=24
			p =  new Puzzle4x6();
			break;
		case 25: // 5x5=25
			p =  new Puzzle5x5();
			break;
		case 30: // 5x6=30
			p =  new Puzzle5x6();
			break;
		case 36: // 6x6=36
			p =  new Puzzle6x6();
			break;
		}
		if(p != null){
			String b = items[2];
			char[] pieces = new char[w*h];
			for (int i = 0; i < b.length(); i++) {
				char c = b.charAt(i);
				pieces[i] = Piece.getPiece(c);
			}
			p.set(no, w, h, pieces);
		}
		return p;
	}
}
