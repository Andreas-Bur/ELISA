package bgFunc;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.jna.platform.win32.WinReg;

import feedback.AlertController;

public class Settings {

	public static void addAutostart() {
		Advapi32Util.registrySetStringValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run",
				"ELISA", System.getProperty("user.home") + "\\AppData\\Local\\ELISA\\ELISA.exe");
	}

	public static void removeAutostart() {
		Advapi32Util.registryDeleteValue(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run", "ELISA");
	}

	public static boolean isAutostarting() {
		if (Advapi32Util.registryValueExists(WinReg.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\Run",
				"ELISA")) {
			return true;
		}
		return false;
	}

	public static int getHotkeyIndex() {
		String[] lines = MyFiles.getFileContent(Startup.dataDir + "\\settings.txt");

		for (String line : lines) {
			if (line.startsWith("hotkeyIndex|")) {
				return Integer.parseInt(line.split("\\|")[1]);
			}
		}
		AlertController.showErrorDialog("Fehler", "Es gibt einen Fehler in den Einstellungen. Bitte setzen Sie ELISA zurück.");
		return 0;
	}

	public static void setHotkeyIndex(int index) {

		if (!MyFiles.replaceOnceInFile(Startup.dataDir + "\\settings.txt", "hotkeyIndex\\|\\d", "hotkeyIndex\\|" + index)) {
			AlertController.showErrorDialog("Fehler", "Beim Speichern der Einstellungen ist ein Fehler aufgetreten. Sollte dieser weiterhin auftreten, bitte setzen Sie ELISA zurück.");
		}
	}
}
