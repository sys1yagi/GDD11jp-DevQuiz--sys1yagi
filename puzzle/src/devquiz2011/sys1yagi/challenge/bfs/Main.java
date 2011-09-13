package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;
import devquiz2011.sys1yagi.challenge.bfs.score.MD;
import devquiz2011.sys1yagi.challenge.bfs.score.SH;
import devquiz2011.sys1yagi.challenge.bfs.score.Score;

public class Main {
	
	private final static String DUMP = "dump";
	/**
	 * 実行時引数のオプション
	 * @author tyagi
	 *
	 */
	public static class Options{
		private Options(){}
		/** dumpの時スコア情報だけ出力して終わる。他の場合は普通に走る */
		String command = "run";
		/** スレッド数 */
		int threadCount = 1;
		/** 探索する上限深度 */
		int limitDepth = 150;
		/** キューの最大サイズ */
		int limitWidth = 10000;
		/** 再計算を行う手数。0なら全部再計算する。 */
		int reCalcBorder = 150;
		/** 結果を保存するかどうか */
		boolean isSave = true;
	
		/** 評価関数の種類 MD=マンハッタン距離,SH=最短距離 */
		Score scoreMethod = new MD();
		
		/** 幅優先、双方向 */ 
		String searchMethod = "";
		
		@Override
		public String toString() {
			return "Options [command=" + command + ", threadCount="
					+ threadCount + ", limitDepth=" + limitDepth
					+ ", limitWidth=" + limitWidth + ", reCalcBorder="
					+ reCalcBorder + ", isSave=" + isSave + ", scoreMethod="
					+ scoreMethod + ", searchMethod=" + searchMethod + "]";
		}

		public static Options create(String[] args){
			Options o = new Options();
			for(int i = 0; i < args.length; i++){
				switch(i){
				case 0:
					o.command = args[0];
					break;
				case 1:
					o.threadCount = Integer.parseInt(args[1]);
					break;
				case 2:
					o.limitDepth = Integer.parseInt(args[2]);
					break;
				case 3:
					o.limitWidth = Integer.parseInt(args[3]);
					break;
				case 4:
					o.reCalcBorder = Integer.parseInt(args[4]);
					break;
				case 5:
					if("false".equals(args[5])){
						o.isSave = false;
					}
					break;
				case 6:
					if("SH".equals(args[6])){
						o.scoreMethod = new SH();
					}

					break;
				}
			}
			return o;
		}
	}
	public void start(String[] args) throws Exception{
		Options options = Options.create(args);
		System.out.println(options.toString());
		final Quiz quiz = new Quiz();
		// 問題の読み込み
		quiz.load("files/challenge.slidepuzzle.txt");
		// 解答済み結果読み込み
		quiz.loadResults("files/result.txt");
		quiz.printQuiz();
		if(DUMP.equals(options.command)){
			return;
		}
		quiz.setSave(options.isSave);
		// 計算量が小さい順にソート
		Collections.sort(quiz.mPuzzles, new Comparator<PuzzleNxN>() {
			@Override
			public int compare(PuzzleNxN o1, PuzzleNxN o2) {
				return (o1.mHeight * o1.mWidth) - (o2.mHeight * o2.mWidth);
			}
		});
		run(quiz, options);
	}
	public void run(final Quiz quiz,final Options options){
		long time = System.currentTimeMillis();
		quiz.index = 0;
		try {
			List<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < options.threadCount; i++) {
				Thread t = new Thread() {
					@Override
					public void run() {
						// 計算開始
						String result = null;
						PuzzleNxN p;
						while ((p = quiz.getNext()) != null) {
							// 計算済みの問題はスキップ
							Result rr = quiz.getResult(p.mNo);
							if (rr == null || rr.result.length() > options.reCalcBorder) {
								result = p.start(options.limitDepth, options.limitWidth, options.scoreMethod);
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
			System.out.println(quiz.mResults.getResults().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end:" + (System.currentTimeMillis() - time) + "ms");
	}
	
	/**
	 * 1:"dump"の場合情報だけ出力して終わる
	 * 2:スレッド数
	 * 3:探索深度
	 * 4:探索幅
	 * 5:再計算を行う手数ボーダー
	 * 6:保存フラグ
	 * 7:評価関数
	 * 8:探索方法
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			new Main().start(args);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("args: command threads limit_depath limit_width [recalc_boarder] [isSave]");
		}
	}
}
