package shortcut;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ShortcutsTest {

	private final static String GLOBAL_START_MENU_PATH = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\";

	public static void main(String[] args) {
		setup();
	}

	public static void setup() {

		System.out.println(Arrays.toString(getAllProgramsPaths(GLOBAL_START_MENU_PATH).toArray()));
	}

	private static List<String[]> getAllProgramsPaths(String directory) {

		File curDir = new File(directory);
		File[] filesDirs = curDir.listFiles();

		List<String[]> output = new ArrayList<String[]>();

		for (File curFile : filesDirs) {

			System.out.println(curFile.getName());

			if (isShortcut(curFile) && !curFile.getAbsolutePath().contains("uninst")) {

				String[] curPair = new String[] {getCleanFileName(curFile.getName()), curFile.getAbsolutePath()};
				
				System.out.println("curPair: "+Arrays.toString(curPair));
				
				output.add(curPair);

			} else if (curFile.isDirectory()) {

			}

		}

		return output;
	}

	private static String getCleanFileName(String input) {

		input = input.split("\\.")[0];

		input = input.replaceAll("\\(.*\\)", " ");

		input = input.replaceAll("( |^)\\S*\\d\\S*( |$)", " ");

		input = input.replaceAll("\\W", " ");
		
		input = input.replaceAll(" +", " ");
		
		System.out.println("getCleanFIleName: "+input);

		return input;
	}
	
	private static boolean isShortcut(File file) {
		return file.isFile() && file.getAbsolutePath().endsWith(".lnk");
	}

}
