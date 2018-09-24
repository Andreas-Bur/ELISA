package gui.view;

import java.io.File;
import java.net.MalformedURLException;

import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class AboutController {
	
	private Stage progEinstStage;
	
	@FXML
	private WebView webview;
	
	@FXML
	private void initialize() {
		File file = new File("res/About.html");
		try {
			webview.getEngine().load(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}
}
