package bgFunc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MyFiles {
	
	public final static String PROGRAMS_PATH = "data/programsPath.txt";
	public final static String FILES_PATH = "data/filesPath.txt";

	public static String[] getFileContent(String path) {
		List<String> list = new ArrayList<>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(path));
			String line = br.readLine();

			while (line != null) {
				list.add(line);
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String[] out = (String[]) list.toArray(new String[list.size()]);
		return out;
	}
	
	public static void writeFile(List<String> lines, String path) {
		Path file = Paths.get(path);
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] getAllNames(String path) {
		String[] in = getFileContent(path);
		String[] out = new String[in.length];
		for(int i = 0; i < in.length; i++) {
			out[i] = in[i].split("\\|")[0];
		}
		return out;
	}
	
	public static String[] getAllPaths(String path) {
		String[] in = getFileContent(path);
		String[] out = new String[in.length];
		for(int i = 0; i < in.length; i++) {
			out[i] = in[i].split("\\|")[1];
		}
		return out;
	}

}
