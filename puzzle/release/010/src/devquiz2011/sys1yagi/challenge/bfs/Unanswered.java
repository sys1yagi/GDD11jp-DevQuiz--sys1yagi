package devquiz2011.sys1yagi.challenge.bfs;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;
import devquiz2011.sys1yagi.challenge.bfs.util.FileWriter;
import devquiz2011.sys1yagi.challenge.bfs.util.Util;

public class Unanswered {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("��������Ȃ�");
			return;
		}
		FileWriter fw = null;
		try {
			// �o�̓t�@�C���̃p�X
			fw = new FileWriter(args[0]);

			final Quiz quiz = new Quiz();
			// ���̓ǂݍ���
			quiz.load("files/challenge.slidepuzzle.txt");
			// �𓚍ς݌��ʓǂݍ���
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
