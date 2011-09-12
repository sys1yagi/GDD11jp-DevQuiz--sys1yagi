package devquiz2011.sys1yagi.challenge.bfs;

import java.io.Serializable;

public enum Direction implements Serializable {

	L {
		@Override
		public int move(int w, int h, int zeroIndex, char[] pieces) {
			if (zeroIndex % w > 0) {
				return swap(zeroIndex, zeroIndex - 1, pieces);
			}
			throw new RuntimeException();
			//return -1;
		}

		@Override
		public int moveZeroIndex(int w, int h, int zeroIndex, char[] pieces) {
			return zeroIndex - 1;
		}

		@Override
		public boolean canMove(int w, int h, int zeroIndex, char[] pieces) {
			return zeroIndex % w > 0 && pieces[zeroIndex - 1] != '=';
		}
	},
	R {
		@Override
		public int move(int w, int h, int zeroIndex, char[] pieces) {
			if ((zeroIndex % w) + 1 < w) {
				return swap(zeroIndex, zeroIndex + 1, pieces);
			}
			throw new RuntimeException();
			//return -1;
		}

		@Override
		public int moveZeroIndex(int w, int h, int zeroIndex, char[] pieces) {
			return zeroIndex + 1;
		}

		@Override
		public boolean canMove(int w, int h, int zeroIndex, char[] pieces) {
			return ((zeroIndex % w) + 1 < w) && pieces[zeroIndex + 1] != '=';
		}
	},
	U {
		@Override
		public int move(int w, int h, int zeroIndex, char[] pieces) {
			if (zeroIndex - w >= 0) {
				return swap(zeroIndex, zeroIndex - w, pieces);
			}
			throw new RuntimeException(zeroIndex + ":" + w + ":"+(zeroIndex-w));
			//return -1;
		}

		@Override
		public int moveZeroIndex(int w, int h, int zeroIndex, char[] pieces) {
			return zeroIndex - w;
		}
		@Override
		public boolean canMove(int w, int h, int zeroIndex, char[] pieces) {
			boolean b= ((zeroIndex - w) >= 0) && pieces[zeroIndex - w] != '=';
			//System.out.println(zeroIndex + ":" + w + ":" + b);
			return b;
		}
	},
	D {
		@Override
		public int move(int w, int h, int zeroIndex, char[] pieces) {
			if (zeroIndex + w < h * w) {
				return swap(zeroIndex, zeroIndex + w, pieces);
			}
			throw new RuntimeException();
			//return -1;
		}

		@Override
		public int moveZeroIndex(int w, int h, int zeroIndex, char[] pieces) {
			return zeroIndex + w;
		}
		@Override
		public boolean canMove(int w, int h, int zeroIndex, char[] pieces) {
			return ((zeroIndex + w) < h*w) && pieces[zeroIndex + w] != '=';
		}
	},
	;

	// /////////////////////
	public boolean canMove(int w, int h, int zeroIndex, char[] pieces) {
		return false;
	}

	public int move(int w, int h, int zeroIndex, char[] pieces) {
		// throw Exception?
		return -2;
	}

	public int moveZeroIndex(int w, int h, int zeroIndex, char[] pieces) {
		return -1;
	}

	public static int swap(int src, int dest, char[] pieces) {
		if (pieces[dest] != '=') {
			char p = pieces[src];
			pieces[src] = pieces[dest];
			pieces[dest] = p;
			return dest;
		} else {
			return -1;
		}
	}
}
