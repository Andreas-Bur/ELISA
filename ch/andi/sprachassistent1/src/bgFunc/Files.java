package bgFunc;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Files {
	
	public final static String PROGRAMS_PATH = "data/programsPath.txt";
	public final static String FILES_PATH = "data/filesPath.txt";

	public static String[] getFileContent(String path) {
		ArrayList<String> list = new ArrayList<>();
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
