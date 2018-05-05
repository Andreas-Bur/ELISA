package bgFunc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoProgramsPath {
	
	private final static String GLOBAL_START_MENU_PATH = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\";
	private final static String USER_DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop\\";

	public static void main(String[] args) {
		setup();
	}

	public static void setup() {

		List<String[]> paths = getAllProgramsPaths(USER_DESKTOP_PATH);
		String[] lines = new String[paths.size()];
		
		for(int i = 0; i < paths.size(); i++) {
			lines[i]= paths.get(i)[0]+"|"+paths.get(i)[1];
		}
	}

	private static List<String[]> getAllProgramsPaths(String directory) {

		File curDir = new File(directory);
		File[] filesDirs = curDir.listFiles();

		List<String[]> output = new ArrayList<String[]>();

		for (File curFile : filesDirs) {

			if (isShortcut(curFile) && !isUninstaller(curFile)) {
				//System.out.println("Shortcut name: "+ curFile.getName());
				
				String target = Shortcut.getTargetPath(curFile);
				
				if(target == null || !target.endsWith(".exe") || isUninstaller(new File(target))) {
					continue;
				}

				String[] curPair = new String[] {getCleanFileName(curFile.getName()), Shortcut.getTargetPath(curFile)};
				
				System.out.println("curPair: "+Arrays.toString(curPair));
				
				output.add(curPair);

			} //else if (curFile.isDirectory()) {
				//output.addAll(getAllProgramsPaths(curFile.getAbsolutePath()));
			//}

		}
		return output;
	}
	
	private static boolean isUninstaller(File file) {
		
		if(file.getAbsolutePath().toLowerCase().matches("(?s).*(unins|install|setup).*")) {
			return true;
		}
		
		return false;
	}

	private static String getCleanFileName(String input) {

		input = input.split("\\.")[0];
		input = input.replaceAll("\\(.*\\)", " ");
		input = input.replaceAll("[^a-zA-Z0-9äöüÄÖÜ]", " ");
		input = input.replaceAll(" +", " ").trim();
		
		if(input.contains(" ")) {
			input = input.replaceAll(" \\S*\\d\\S*( |$)", " "); //entferne alle Wörter mit Zahlen ausser am Anfang
		}else {
			input = input.replaceAll("\\d", "");
		}
		
		input = input.replaceAll(" +", " ").trim();

		return input;
	}
	
	private static boolean isShortcut(File file) {
		return file.isFile() && file.getAbsolutePath().endsWith(".lnk");
	}

}
