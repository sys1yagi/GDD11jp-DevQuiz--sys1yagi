package devquiz2011.sys1yagi.challenge.bfs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import devquiz2011.sys1yagi.challenge.bfs.Results.Result;
import devquiz2011.sys1yagi.challenge.bfs.util.Util;

public class Quiz {
	List<PuzzleNxN> mPuzzles = new ArrayList<PuzzleNxN>();
	Status mStatus = null;
	Results mResults = null;
	int index = 0;

	public void load(String file) throws Exception {
		// load quize file.
		mStatus = new Status();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			// initialize Status.
			mStatus.setLimits(br.readLine());
			mStatus.setCount(br.readLine());

			// initialize SlidePuzzle.
			String line;
			int no = 1;
			while ((line = br.readLine()) != null) {
				mPuzzles.add(PuzzleNxN.createForLine(no, line));
				no++;
			}
		} finally {
			Util.close(br);
			Util.close(isr);
			Util.close(fis);
		}
	}

	public void loadResults(String file) throws IOException {
		mResults = new Results();
		mResults.load("files/result.txt");
	}

	public synchronized PuzzleNxN getNext() {
		if (index < mPuzzles.size()) {
			// if(index < 1){
			PuzzleNxN n = mPuzzles.get(index);
			index++;
			//System.out.println(index + "/" + mPuzzles.size());
			return n;
		} else {
			return null;
		}
	}

	/**
	 * 既存の答えをゲット
	 * 
	 * @param no
	 * @return
	 */
	public Result getResult(int no) {
		return mResults.mResults.get(no);
	}

	public void addResult(Result r) {
		mResults.add(r);
	}

	public void printQuiz() {
		// Status
		System.out.println(mStatus.toString());
		// 解答数/問題数
		Map<Integer, Integer> qC = new HashMap<Integer, Integer>(5000);
		for (PuzzleNxN p : mPuzzles) {
			Integer i = qC.get(p.mWidth * p.mHeight);
			if (i == null) {
				i = 0;
			}
			i++;
			qC.put(p.mWidth * p.mHeight, i);
		}
		Map<Integer, Integer> rC = new HashMap<Integer, Integer>();
		Map<Integer, Integer> min = new HashMap<Integer, Integer>();
		Map<Integer, Integer> max = new HashMap<Integer, Integer>();
		int lx = mStatus.lx;
		int rx = mStatus.rx;
		int ux = mStatus.ux;
		int dx = mStatus.dx;
		for (Integer i : mResults.mResults.keySet()) {
			Result r = mResults.mResults.get(i);
			for(int j = 0; j < r.result.length(); j++){
				switch(r.result.charAt(j)){
				case 'L':
					lx--;
					break;
				case 'R':
					rx--;
					break;
				case 'U':
					ux--;
					break;
				case 'D':
					dx--;
					break;
				}
			}
			
			PuzzleNxN p = mPuzzles.get(r.no-1);
			int key = p.mWidth * p.mHeight;
			//カウント
			Integer c = rC.get(key);
			if (c == null) {
				c = 0;
			}
			c++;
			rC.put(key, c);
			
			//min
			Integer mn = min.get(key);
			if (mn == null || mn > r.result.length()) {
				min.put(key, r.result.length());	
			}
			//max
			Integer mx = max.get(key);
			if (mx == null || mx < r.result.length()) {
				max.put(key, r.result.length());	
			}
		}
		
		// レベルの解答数,最短解、最長解 (開始深さ、制限深さの参考にする)
		System.out.println("3x3=" + rC.get(3 * 3) + "/" + qC.get(3 * 3) + " min=" + min.get(3*3) + " max=" + max.get(3*3));
		System.out.println("3x4=" + rC.get(3 * 4) + "/" +qC.get(3 * 4) + " min=" + min.get(3*4) + " max=" + max.get(3*4));
		System.out.println("3x5=" + rC.get(3 * 5) + "/" +qC.get(3 * 5) + " min=" + min.get(3*5) + " max=" + max.get(3*5));
		System.out.println("3x6=" + rC.get(3 * 6) + "/" +qC.get(3 * 6) + " min=" + min.get(3*6) + " max=" + max.get(3*6));
		System.out.println("4x4=" + rC.get(4 * 4) + "/" +qC.get(4 * 4)  + " min=" + min.get(4*4) + " max=" + max.get(4*4));
		System.out.println("4x5=" + rC.get(4 * 5) + "/" +qC.get(4 * 5) + " min=" + min.get(4*5) + " max=" + max.get(4*5));
		System.out.println("4x6=" + rC.get(4 * 6) + "/" +qC.get(4 * 6) + " min=" + min.get(4*6) + " max=" + max.get(4*6));
		System.out.println("5x5=" + rC.get(5 * 5) + "/" +qC.get(5 * 5) + " min=" + min.get(5*5) + " max=" + max.get(5*5));
		System.out.println("5x6=" + rC.get(5 * 6) + "/" +qC.get(5 * 6) + " min=" + min.get(5*6) + " max=" + max.get(5*6));
		System.out.println("6x6=" + rC.get(6 * 6) + "/" +qC.get(6 * 6) + " min=" + min.get(6*6) + " max=" + max.get(6*6));

		System.out.println("points:" + mResults.mResults.size());
		
		//残り手数
		
		System.out.println("L:" + (mStatus.lx-lx) + "/" + mStatus.lx + "=" + lx);
		System.out.println("R:"+ (mStatus.rx-rx) + "/" + mStatus.rx + "=" + rx);
		System.out.println("U:"+ (mStatus.ux-ux) + "/" + mStatus.ux + "=" + ux); 
		System.out.println("D:"+ (mStatus.dx-dx) + "/" + mStatus.dx + "=" + dx);
	}
}
