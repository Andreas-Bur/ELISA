package bgFunc;

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
		
		try {
			list.addAll(Files.readAllLines(Paths.get(path), Charset.forName("ISO-8859-1")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] out = (String[]) list.toArray(new String[list.size()]);
		return out;
	}
	
	public static void writeFile(List<String> lines, String path) {
		Path file = Paths.get(path);
		try {
			Files.write(file, lines, Charset.forName("ISO-8859-1"));
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
