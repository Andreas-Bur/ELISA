package execute;

import java.io.IOException;

import gui.AlertController;

public class OpenProgram {

	public static void open(String path) {

		try {
			if (path.toLowerCase().endsWith("windows\\system32\\cmd.exe")) {
				new ProcessBuilder(path, "/c", "start").start();
			} else {
				new ProcessBuilder(path).start();
			}
			System.out.println("DEBUG: started application: " + path);
		} catch (IOException e) {
			AlertController.showErrorDialog("Programm Fehler", "Das Programm mit dem Pfad \""+path+"\" konnte nicht gefunden werden. Bitte ändern Sie den Eintrag in den Einstellungen. ");
			System.err.println("ERROR: failed to run program located at " + path);
			e.printStackTrace();
		}
	}
}
