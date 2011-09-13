package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;

public class Main {
	public static int RE_CALC_BORDER = 150;
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			final Quiz quiz = new Quiz();
			// ���̓ǂݍ���
			quiz.load("files/challenge.slidepuzzle.txt");
			// �𓚍ς݌��ʓǂݍ���
			quiz.loadResults("files/result.txt");

			quiz.printQuiz();

			if (args.length > 0) {
				if ("dump".equals(args[0])) {
					return;
				}
			}

			// �v�Z�ʂ����������Ƀ\�[�g
			Collections.sort(quiz.mPuzzles, new Comparator<PuzzleNxN>() {
				@Override
				public int compare(PuzzleNxN o1, PuzzleNxN o2) {
					return (o1.mHeight * o1.mWidth) - (o2.mHeight * o2.mWidth);
				}
			});
			int limitDepth = 1;
			if(args.length >= 3){
				limitDepth = Integer.parseInt(args[2]);
			}
			System.out.println("init depth:"+ limitDepth);
			int limitWidth = 10000;
			if(args.length >= 4){
				limitWidth = Integer.parseInt(args[3]);
			}
			System.out.println("init width:" + limitWidth);
			
			if(args.length >= 5){
				RE_CALC_BORDER = Integer.parseInt(args[4]);
			}
			System.out.println("recalc:" + RE_CALC_BORDER);
			//while(true){
				Puzzle3x3.LIMIT_DEPTH = limitDepth;
				Puzzle3x3.LIMIT_WIDTH = limitWidth;
				System.out.println("depth:" + limitDepth);
				start(args, quiz);
				limitDepth+=10;
			//}
//			while(true){
//				start(args, quiz);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

	public static void start(String[] args, final Quiz quiz) {
		long time = System.currentTimeMillis();
		quiz.index = 0;
		try {
			int tSize = 1;
			if (args.length >= 2) {
				tSize = Integer.parseInt(args[1]);
			}

			final int reCalcBorder = RE_CALC_BORDER;
			int THREADS = tSize;
			List<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < THREADS; i++) {
				Thread t = new Thread() {
					@Override
					public void run() {
						// �v�Z�J�n
						String result = null;
						PuzzleNxN p;
						while ((p = quiz.getNext()) != null) {
							// �v�Z�ς݂̖��̓X�L�b�v
							Result rr = quiz.getResult(p.mNo); 
							if (rr == null || rr.result.length() > reCalcBorder) {
//								if(rr != null && rr.result.length() > reCalcBorder){
//									System.out.println("recalc:" + rr.result.length());
//								}
								result = p.start();
								if (result != null) {
									Results.Result r = new Results.Result();
									r.result = result;
									r.no = p.mNo;
									quiz.addResult(r);
								}
								result = null;
							}
						}
					}
				};
				threads.add(t);
				t.start();
			}
			for (Thread t : threads) {
				t.join();
			}
			System.out.println(quiz.mResults.mResults.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		System.out.println("end:" + (System.currentTimeMillis() - time) + "ms");
	}
}
