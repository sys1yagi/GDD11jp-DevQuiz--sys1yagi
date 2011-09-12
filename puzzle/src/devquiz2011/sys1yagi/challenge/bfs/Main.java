package devquiz2011.sys1yagi.challenge.bfs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;

public class Main {
	
	private final static String DUMP = "dump";
	/**
	 * ���s�������̃I�v�V����
	 * @author tyagi
	 *
	 */
	public static class Options{
		int threadCount = 1;
		int limitDepth = 150;
		int limitWidth = 10000;
		int reCalcBorder = 150;
		boolean isSave = true;
		String command = "run";
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
				}
			}
			return o;
		}
	}
	private Options mOptions = null;
	public Main(String[] args){
		mOptions = Options.create(args);
	}
	public void start() throws Exception{
		final Quiz quiz = new Quiz();
		// ���̓ǂݍ���
		quiz.load("files/challenge.slidepuzzle.txt");
		// �𓚍ς݌��ʓǂݍ���
		quiz.loadResults("files/result.txt");
		quiz.printQuiz();
		if(DUMP.equals(mOptions.command)){
			return;
		}
		// �v�Z�ʂ����������Ƀ\�[�g
		Collections.sort(quiz.mPuzzles, new Comparator<PuzzleNxN>() {
			@Override
			public int compare(PuzzleNxN o1, PuzzleNxN o2) {
				return (o1.mHeight * o1.mWidth) - (o2.mHeight * o2.mWidth);
			}
		});
		run(quiz);
	}
	public void run(final Quiz quiz){
		long time = System.currentTimeMillis();
		quiz.index = 0;
		try {
			List<Thread> threads = new ArrayList<Thread>();
			for (int i = 0; i < mOptions.threadCount; i++) {
				Thread t = new Thread() {
					@Override
					public void run() {
						// �v�Z�J�n
						String result = null;
						PuzzleNxN p;
						while ((p = quiz.getNext()) != null) {
							// �v�Z�ς݂̖��̓X�L�b�v
							Result rr = quiz.getResult(p.mNo);
							if (rr == null || rr.result.length() > mOptions.reCalcBorder) {
								result = p.start(mOptions.limitDepth, mOptions.limitWidth);
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
		}
		System.out.println("end:" + (System.currentTimeMillis() - time) + "ms");
	}
	
	/**
	 * 1:"dump"�̏ꍇ��񂾂��o�͂��ďI���
	 * 2:�X���b�h��
	 * 3:�T���[�x
	 * 4:�T����
	 * 5:�Čv�Z���s���萔�{�[�_�[
	 * 6:�ۑ��t���O
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			new Main(args).start();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("args: command threads limit_depath limit_width [recalc_boarder] [isSave]");
		}
	}
}
