package bgFunc;

import java.io.File;
import java.util.List;

public class AutoProgramsPath {
	
	private final String GLOBAL_START_MENU_PATH = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\";

	public static void setup() {
		
		
	}
	
	private static List<File> getAllProgramsPaths(String directory){
		
		File curDir = new File(directory);
		File[] filesDirs = curDir.listFiles();
		
		for(File curFile : filesDirs) {
			
		}
		
		return null;
	}

}
