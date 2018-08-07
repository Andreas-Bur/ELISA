package execute;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import gui.AlertController;

public class OpenFile {

	public static void open(String path) {

		Desktop dt = null;
		if(Desktop.isDesktopSupported()) {
			dt = Desktop.getDesktop();
		}else {
			AlertController.showErrorDialog("Systemfehler", "Diese Platform unterstützt das Öffnen von Dateien durch Java nicht.");
			return;
		}
		
		try {
			dt.open(new File(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
