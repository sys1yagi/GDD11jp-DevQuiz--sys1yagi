package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			final Quiz quiz = new Quiz();
			// 問題の読み込み
			quiz.load("files/challenge.slidepuzzle.txt");
			// 解答済み結果読み込み
			quiz.loadResults("files/result.txt");

			quiz.printQuiz();

			if (args.length > 0) {
				if ("dump".equals(args[0])) {
					return;
				}
			}

			// 計算量が小さい順にソート
			Collections.sort(quiz.mPuzzles, new Comparator<PuzzleNxN>() {
				@Override
				public int compare(PuzzleNxN o1, PuzzleNxN o2) {
					return (o1.mHeight * o1.mWidth) - (o2.mHeight * o2.mWidth);
				}
			});
			int i = 1;
			if(args.length >= 3){
				i = Integer.parseInt(args[2]);
			}
			System.out.println("init depth:"+ i);
			while(true){
				Puzzle3x3.LIMIT_DEPTH = i;
				System.out.println("depth:" + i);
				start(args, quiz);
				i++;
			}
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

			final int reCalcBorder = 150;
			int THREADS = tSize;
			List<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < THREADS; i++) {
				Thread t = new Thread() {
					@Override
					public void run() {
						// 計算開始
						String result = null;
						PuzzleNxN p;
						while ((p = quiz.getNext()) != null) {
							// 計算済みの問題はスキップ
							Result rr = quiz.getResult(p.mNo); 
							if (rr == null || rr.result.length() > reCalcBorder) {
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
