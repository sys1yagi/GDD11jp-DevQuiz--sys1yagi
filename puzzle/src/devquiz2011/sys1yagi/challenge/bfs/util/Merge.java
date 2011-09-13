package devquiz2011.sys1yagi.challenge.bfs.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import devquiz2011.sys1yagi.challenge.bfs.Results;
import devquiz2011.sys1yagi.challenge.bfs.Results.Result;

public class Merge {
	public static void main(String[] args) {

		if (args.length == 3) {
			FileOutputStream fos = null;
			OutputStreamWriter osw = null;
			BufferedWriter bw = null;
			try {
				Results result1 = new Results();
				result1.load(args[0]);
				Results result2 = new Results();
				result2.load(args[1]);
				fos = new FileOutputStream(args[2]);
				osw = new OutputStreamWriter(fos);
				bw = new BufferedWriter(osw);
				for (int i = 1; i <= 5000; i++) {
					Result r1 = result1.getResults().get(i);
					Result r2 = result2.getResults().get(i);
					if (r1 != null && r2 != null) {
						if (r1.getResult().length() < r2.getResult().length()) {
							bw.write(r1.getResult());
						} else {
							bw.write(r2.getResult());
						}
					} else if (r1 != null) {
						bw.write(r1.getResult());
					} else if (r2 != null) {
						bw.write(r2.getResult());
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
		} else {
			System.out.println("args: input1 input2 output");
		}
	}
}
