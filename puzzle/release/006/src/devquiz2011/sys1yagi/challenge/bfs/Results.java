package devquiz2011.sys1yagi.challenge.bfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

public class Results {
	public static class Result {
		int no;
		String result;
	}

	Map<Integer, Result> mResults = new HashMap<Integer, Results.Result>();
	String file;
	public void add(Result result) {
		Result r = mResults.get(result.no);
		if (r == null) {
			mResults.put(result.no, result);
			save();
		} else {
			if (r.result.length() >= result.result.length()) {
				return;
			} else {
				mResults.put(result.no, result);
				save();
				return;
			}
		}
	}

	public void load(String file) throws IOException{
		// load quize file.
		this.file = file;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);

			// initialize SlidePuzzle.
			String line;
			int no = 1;
			while ((line = br.readLine()) != null) {
				if(!"".equals(line)){
					Result r = new Result();
					r.no = no;
					r.result = line;
					mResults.put(no, r);
				}
				no++;
			}
		} finally {
			Util.close(br);
			Util.close(isr);
			Util.close(fis);
		}
	}

	public void save() {
		if(file == null){
			return;
		}
//		System.out.println("save");
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			fos = new FileOutputStream(file);
			osw = new OutputStreamWriter(fos);
			bw = new BufferedWriter(osw);
			for (int i = 1; i <= 5000; i++) {
				Result r = mResults.get(i);
				if (r != null) {
					bw.write(r.result);
				}
				bw.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Util.close(bw);
			Util.close(osw);
			Util.close(fos);
		}
	}
}
