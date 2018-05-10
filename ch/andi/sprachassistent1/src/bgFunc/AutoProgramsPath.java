package bgFunc;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AutoProgramsPath {

	private final static String GLOBAL_START_MENU_PATH = "C:\\ProgramData\\Microsoft\\Windows\\Start Menu\\Programs\\";
	private final static String USER_DESKTOP_PATH = System.getProperty("user.home") + "\\Desktop\\";
	private final static String AUTO_PROGRAMS_PATH = "data/autoProgramsPath.txt";
	private final static String VOXFORGE_SMALL_DIC = "sphinx_data_small/etc/voxforge_small.dic";
	private final static String MY_MODEL_GRAM = "sphinx_data_small/etc/my_model.gram";

	public static void main(String[] args) {
		setup();
	}

	public static void setup() {

		final List<String[]> shortcutProgramsAndPaths = getAllProgramsAndPaths(GLOBAL_START_MENU_PATH);
		List<String[]> newAutoPrograms = new ArrayList<String[]>();
		final List<String> autoProgramsFileLines = Arrays.asList(MyFiles.getFileContent(AUTO_PROGRAMS_PATH));
		List<String> programPathsFileLines = new ArrayList<>();
		programPathsFileLines.addAll(autoProgramsFileLines);

		aussen: for (int a = 0; a < shortcutProgramsAndPaths.size(); a++) {
			for (int b = 0; b < autoProgramsFileLines.size(); b++) {
				if (shortcutProgramsAndPaths.get(a)[0].equals(autoProgramsFileLines.get(b).split("\\|")[0])) {
					continue aussen;
				}
			}
			newAutoPrograms.add(shortcutProgramsAndPaths.get(a));
		}

		for (int i = 0; i < newAutoPrograms.size(); i++) {
			programPathsFileLines.add(newAutoPrograms.get(i)[0] + "|" + newAutoPrograms.get(i)[1]);
		}
		programPathsFileLines.sort(null);
		MyFiles.writeFile(programPathsFileLines, AUTO_PROGRAMS_PATH);

		String[] programsPronounciation = new String[shortcutProgramsAndPaths.size()];
		for (int i = 0; i < shortcutProgramsAndPaths.size(); i++) {
			
			programsPronounciation[i] = Words.englishWordsToPhonemes(shortcutProgramsAndPaths.get(i)[0]);
		}

		String[] newProgramNames = new String[newAutoPrograms.size()];
		for (int i = 0; i < newAutoPrograms.size(); i++) {
			newProgramNames[i] = "_"+newAutoPrograms.get(i)[0].replaceAll(" ", "_");
		}
		
		addProgramsToDict(newProgramNames, programsPronounciation);

		addProgramsToGram(newProgramNames);
	}

	private static void addProgramsToDict(String[] programNames, String[] programsPronounciation) {
		System.out.println("addProgramsToDict: "+Arrays.toString(programNames));
		List<String> dictLines = new ArrayList<>();
		dictLines.addAll(Arrays.asList(MyFiles.getFileContent(VOXFORGE_SMALL_DIC)));

		for (int i = 0; i < programNames.length; i++) {
			dictLines.add(programNames[i] + " " + programsPronounciation[i]);
		}

		dictLines.sort(null);
		
		System.out.println("addProgramsToDict write: "+dictLines);

		MyFiles.writeFile(dictLines, VOXFORGE_SMALL_DIC);
	}

	private static void addProgramsToGram(String[] programNames) {
		System.out.println("addProgramsToGram: "+Arrays.toString(programNames));
		String[] lines = MyFiles.getFileContent(MY_MODEL_GRAM);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("<autoPrograms>")) {
				for (int j = 0; j < programNames.length; j++) {
					if(lines[i].matches("<autoPrograms>\\s*=\\s*;")) {
						lines[i] = lines[i].replace(";", programNames[j] + ";");
					}else {
						lines[i] = lines[i].replace(";", " | " + programNames[j] + ";");
					}
				}
			}
		}
		
		System.out.println("addProgramsToGram write: "+Arrays.toString(lines));

		MyFiles.writeFile(Arrays.asList(lines), MY_MODEL_GRAM);
	}

	private static List<String[]> getAllProgramsAndPaths(String directory) {

		File curDir = new File(directory);
		File[] filesDirs = curDir.listFiles();

		List<String[]> output = new ArrayList<String[]>();

		for (File curFile : filesDirs) {

			if (isShortcut(curFile) && !isUninstaller(curFile)) {
				// System.out.println("Shortcut name: "+ curFile.getName());

				String target = Shortcut.getTargetPath(curFile);

				if (target == null || !target.endsWith(".exe") || isUninstaller(new File(target))) {
					continue;
				}

				String[] curPair = new String[] { getCleanFileName(curFile.getName()), Shortcut.getTargetPath(curFile) };

				System.out.println("curPair: " + Arrays.toString(curPair));

				output.add(curPair);

			} // else if (curFile.isDirectory()) {
				// output.addAll(getAllProgramsPaths(curFile.getAbsolutePath()));
				// }

		}
		return output;
	}

	private static boolean isUninstaller(File file) {

		if (file.getAbsolutePath().toLowerCase().matches("(?s).*(unins|install|setup).*")) {
			return true;
		}

		return false;
	}

	private static String getCleanFileName(String input) {

		input = input.split("\\.")[0];
		input = input.replaceAll("\\(.*\\)", " ");
		input = input.replaceAll("[^a-zA-Z0-9äöüÄÖÜ]", " ");
		input = input.replaceAll(" +", " ").trim();

		if (input.contains(" ")) {
			input = input.replaceAll(" \\S*\\d\\S*( |$)", " "); // entferne alle
																// Wörter mit
																// Zahlen ausser
																// am Anfang
		} else {
			input = input.replaceAll("\\d", "");
		}

		input = input.replaceAll(" +", " ").trim();

		return input;
	}

	private static boolean isShortcut(File file) {
		return file.isFile() && file.getAbsolutePath().endsWith(".lnk");
	}

}
