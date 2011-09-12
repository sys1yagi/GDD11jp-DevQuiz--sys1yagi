package devquiz2011.sys1yagi.challenge.bfs;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;
import devquiz2011.sys1yagi.challenge.bfs.util.FileWriter;
import devquiz2011.sys1yagi.challenge.bfs.util.Util;

public class Unanswered {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("引数足りない");
			return;
		}
		FileWriter fw = null;
		try {
			// 出力ファイルのパス
			fw = new FileWriter(args[0]);

			final Quiz quiz = new Quiz();
			// 問題の読み込み
			quiz.load("files/challenge.slidepuzzle.txt");
			// 解答済み結果読み込み
			quiz.loadResults("files/result.txt");

			PuzzleNxN puzzle = null;
			while ((puzzle = quiz.getNext()) != null) {
				Result r = quiz.getResult(puzzle.mNo);
				if (r == null) {
					StringBuilder sb = new StringBuilder();
					for (int i = 0; i < puzzle.mPieces.length; i++) {
						sb.append(puzzle.mPieces[i]);
					}
					fw.write(puzzle.mNo + ":" + puzzle.mWidth + "," + puzzle.mHeight + "," + sb.toString());
					fw.newLine();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(fw);
		}
	}
}
