package execute;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import gui.AlertController;

public class OpenWebsite {

	public static void open(String path) {
		Desktop dt = null;
		if(Desktop.isDesktopSupported()) {
			dt = Desktop.getDesktop();
		}else {
			AlertController.showErrorDialog("Systemfehler", "Diese Platform unterstützt das Öffnen von Webseiten durch Java nicht.");
			return;
		}
		
		
		try {
			dt.browse(new URI(path));
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
	}
}
