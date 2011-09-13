package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Puzzle pieces.
 * 
 * @author sylc.yagi
 * 
 */
public class Piece {
	public static char[] CHARS = { '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
			'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
			'Z', '0' , '='};
	public static Map<Character, Integer> CHARS_MAP = new HashMap<Character,Integer>(){{
		for(int i = 0; i < CHARS.length; i++){
			this.put(CHARS[i], i);
		}
	}};
	//TODO Map‚É‚·‚é
	public static int getGoalPos(char item){
		return CHARS_MAP.get(item);
//		return -1;
	}
	public static Piece BLANK = new Piece('0');
	public static Piece WALL = new Piece('=');
	private static List<Piece> PIECE_SET = new ArrayList<Piece>() {
		{
			for (int i = 1; i < 10; i++) {
				this.add(new Piece(Character.toChars('0' + i)[0]));
			}
			for (int i = 0; i < 26; i++) {
				this.add(new Piece(Character.toChars('A' + i)[0]));
			}
		}
	};

	public static char getPiece(char name) {
		if (BLANK.name == name) {
			return BLANK.name;
		}
		if (WALL.name == name) {
			return WALL.name;
		}
		for (Piece p : PIECE_SET) {
			if (p.name == name) {
				return p.name;
			}
		}
		System.out.println("key is invalid:" + name);
		return '-';
	}

	char name;

	public Piece(char n) {
		name = n;
	}
}
