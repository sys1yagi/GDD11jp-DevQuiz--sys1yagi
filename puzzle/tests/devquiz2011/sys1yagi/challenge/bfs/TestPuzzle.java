package devquiz2011.sys1yagi.challenge.bfs;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import devquiz2011.sys1yagi.challenge.bfs.score.MD;
import devquiz2011.sys1yagi.challenge.bfs.score.Score;

public class TestPuzzle {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	public void doTestNormal(int no, String line) {
		long time = System.currentTimeMillis();
		PuzzleNxN puzzle = PuzzleNxN.createForLine(no, line);
		String result = puzzle.start(130, 10000, new MD());
		System.out.println("NORM:" + result + ":"
				+ (System.currentTimeMillis() - time) + "ms");
	}

//	@Test
//	public void testDistance() {
//		//PuzzleNxN.LIMIT_DEPTH = 120;
//		String line = "4,4,14=256=89A=CDEF0";
//		//doTestNormal(0, line);
//		PuzzleNxN puzzle = PuzzleNxN.createForLine(0, line);
//		int result = Node.calcScore(puzzle.mWidth, puzzle.mHeight, 0, puzzle.mPieces);
//		System.out.println(result);
//		assertTrue("error:4::" + result, 4 == result);
//		result = Node.calcScore2(puzzle.mWidth, puzzle.mHeight, 0, puzzle.mPieces);
//		System.out.println(result);
//		assertTrue("error2:16::" + result, 16 == result);
//	}
//
//	@Test
//	public void testDistance2(){
//		String line = "5,3,14=2567=9ACBDE0";
//		//doTestNormal(0, line);
//		PuzzleNxN puzzle = PuzzleNxN.createForLine(0, line);
//		int result = Node.calcScore(puzzle.mWidth, puzzle.mHeight, 0, puzzle.mPieces);
//		System.out.println(result);
//		assertTrue("error:6::" + result, 6 == result);
//		result = Node.calcScore2(puzzle.mWidth, puzzle.mHeight, 0, puzzle.mPieces);
//		System.out.println(result);
//		assertTrue("error2:14::" + result, 14 == result);
//	}
	
	@Test
	public void testPuzzle123() {
		//doTest(123, "6,5,EF123=KD==4587GSNTQP=BH0JRMIOC");
	}
}
