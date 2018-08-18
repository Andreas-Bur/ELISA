package bgFunc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import gui.AlertController;

public class Processes {

	public static String getWordPath() {
		return getOfficePath() + "\\WINWORD.EXE";
	}

	public static String getExcelPath() {
		return getOfficePath() + "\\EXCEL.EXE";
	}

	public static String getPowerPointPath() {
		return getOfficePath() + "\\POWERPNT.EXE";
	}

	private static String getOfficePath() {
		String[] lines = MyFiles.getFileContent(MyFiles.SETTINGS_FILE);
		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("officePath")) {
				return lines[i].split("\\|")[1];
			}
		}

		AlertController.showErrorDialog("Fehler",
				"Der Pfad für die Microsoft Office Programme konnte nicht gefunden werden.\r\nBitte starten Sie ELISA neu.");

		try {
			Files.deleteIfExists(Paths.get(MyFiles.SETTINGS_FILE));
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Löschen");
			e.printStackTrace();
		}
		return null;
	}

	public static String[] getPathsOfRunningProcesses() {

		ArrayList<String> list = new ArrayList<>();

		try {
			String line;
			Process p = Runtime.getRuntime().exec("wmic process get ExecutablePath");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			line = input.readLine(); // skip first line containing title

			while ((line = input.readLine()) != null) {
				if (!line.trim().equals("")) {
					list.add(line.trim());
				}
			}
			input.close();
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen");
			e.printStackTrace();
		}

		String[] out = (String[]) list.toArray(new String[list.size()]);

		return out;
	}

	public static int[] getPIDsOfProcess(String name) {
		ArrayList<Integer> pids = new ArrayList<>();
		try {
			Process p = Runtime.getRuntime().exec("wmic process where name=\"" + name + "\" get processid");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			input.readLine(); // skip first line with title
			input.readLine(); // skip second empty line

			String line = input.readLine();
			while (line != null) {
				if (!line.equals("")) {
					pids.add(Integer.parseInt(line.trim()));
				}
				line = input.readLine();
			}

			input.close();
		} catch (Exception err) {
			AlertController.showIOExceptionDialog("Lesen");
			err.printStackTrace();
		}

		int[] out = new int[pids.size()];
		for (int i = 0; i < out.length; i++) {
			out[i] = pids.get(i);
		}

		return out.length > 0 ? out : null;
	}

	public static String getTitleOfProcess(String name) {

		try {
			String line;

			Process p = Runtime.getRuntime().exec("tasklist /v /fo csv /fi \"IMAGENAME eq " + name + "\"");
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), "UTF-8"));
			line = input.readLine(); // skip first line with title

			while ((line = input.readLine()) != null) {
				System.out.println(line);
				String[] fields = line.split(",");
				if (fields[0].substring(1, fields[0].length() - 1).toLowerCase().equals(name.toLowerCase())) {
					String titleField = fields[fields.length - 1].substring(1, fields[fields.length - 1].length() - 1);
					return titleField;
				}
			}
			input.close();
		} catch (Exception err) {
			AlertController.showIOExceptionDialog("Lesen");
			err.printStackTrace();
		}

		return null;
	}

	public static boolean isProcessRunning(String path) {
		String[] processes = getPathsOfRunningProcesses();
		for (String process : processes) {
			if (process.toLowerCase().equals(path.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
}
