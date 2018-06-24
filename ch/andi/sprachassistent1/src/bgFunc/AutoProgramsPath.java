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

		final List<String[]> shortcutProgramsAndPaths = getAllProgramsAndPaths(GLOBAL_START_MENU_PATH);
		List<String[]> newAutoPrograms = new ArrayList<String[]>();
		final List<String> removedPrograms = Arrays.asList(MyFiles.getFileContent(MyFiles.REMOVED_PROGRAMS_PATHS));
		final List<String> oldAutoProgramsFileLines = Arrays.asList(MyFiles.getFileContent(MyFiles.AUTO_PROGRAMS_PATH));
		final List<String> oldPermProgramsFileLines = Arrays.asList(MyFiles.getFileContent(MyFiles.PROGRAMS_PATH));
		List<String> newAutoProgramsFileLines = new ArrayList<>();
		newAutoProgramsFileLines.addAll(oldAutoProgramsFileLines);

		aussen: for (int a = 0; a < shortcutProgramsAndPaths.size(); a++) {
			for (int b = 0; b < oldAutoProgramsFileLines.size(); b++) {
				if (shortcutProgramsAndPaths.get(a)[0].equalsIgnoreCase(oldAutoProgramsFileLines.get(b).split("\\|")[0].replaceAll("_", " ").trim())) {
					continue aussen;
				}
				if (shortcutProgramsAndPaths.get(a)[1].equalsIgnoreCase(oldAutoProgramsFileLines.get(b).split("\\|")[1])) {
					continue aussen;
				}
			}
			for (int b = 0; b < oldPermProgramsFileLines.size(); b++) {
				if (shortcutProgramsAndPaths.get(a)[0].equalsIgnoreCase(oldPermProgramsFileLines.get(b).split("\\|")[0].replaceAll("_", " ").trim())) {
					continue aussen;
				}
				if (shortcutProgramsAndPaths.get(a)[1].equalsIgnoreCase(oldPermProgramsFileLines.get(b).split("\\|")[1])) {
					continue aussen;
				}
			}
			for(int b = 0; b < removedPrograms.size(); b++) {
				if (shortcutProgramsAndPaths.get(a)[1].equalsIgnoreCase(removedPrograms.get(b).split("\\|")[1])) {
					continue aussen;
				}
			}
			newAutoPrograms.add(shortcutProgramsAndPaths.get(a));
		}

		for (int i = 0; i < newAutoPrograms.size(); i++) {
			newAutoProgramsFileLines.add("_"+newAutoPrograms.get(i)[0].replaceAll(" ", "_") + "|" + newAutoPrograms.get(i)[1] + "|EN|Y");
		}
		newAutoProgramsFileLines.sort(null);
		MyFiles.writeFile(newAutoProgramsFileLines, MyFiles.AUTO_PROGRAMS_PATH);

		String[] programsPronounciation = new String[newAutoPrograms.size()];
		for (int i = 0; i < newAutoPrograms.size(); i++) {
			System.out.println("DEBUG: "+newAutoPrograms.get(i)[0]);
			programsPronounciation[i] = Words.englishWordsToPhonemes(newAutoPrograms.get(i)[0]);
		}

		String[] newProgramNames = new String[newAutoPrograms.size()];
		for (int i = 0; i < newAutoPrograms.size(); i++) {
			newProgramNames[i] = "_"+newAutoPrograms.get(i)[0].replaceAll(" ", "_");
		}
		
		MyFiles.addEntriesToDict(newProgramNames, programsPronounciation);

		MyFiles.addEntryToGram("program", newProgramNames);
	}

	private static List<String[]> getAllProgramsAndPaths(String directory) {

		File curDir = new File(directory);
		File[] filesDirs = curDir.listFiles();

		List<String[]> output = new ArrayList<String[]>();

		for (File curFile : filesDirs) {

			if (isShortcut(curFile) && !isUninstaller(curFile)) {
				// System.out.println("Shortcut name: "+ curFile.getName());

				String target = Shortcut.getTargetPath(curFile);

				if (target == null || !target.endsWith(".exe") || !new File(target).exists() || isUninstaller(new File(target))) {
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
