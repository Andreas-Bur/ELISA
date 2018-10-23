package gui.view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;

public class AnleitungController {
	
	@FXML
	private WebView webview;
	
	@FXML
	private void initialize() {
		File file = new File("res/Anleitung.html");
		try {
			webview.getEngine().load(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
}
