package devquiz2011.sys1yagi.challenge.bfs.util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class FileWriter {
	FileOutputStream fos = null;
	OutputStreamWriter osw = null;
	BufferedWriter bw = null;

	public FileWriter(String file) throws IOException {
		fos = new FileOutputStream(file);
		osw = new OutputStreamWriter(fos);
		bw = new BufferedWriter(osw);
	}

	public void write(String line) throws IOException {
		bw.write(line);
	}

	public void newLine() throws IOException {
		bw.newLine();
	}

	public void close() {
		Util.close(bw);
		Util.close(osw);
		Util.close(fos);
	}
}
