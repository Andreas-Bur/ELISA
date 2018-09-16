package gui.view;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AnleitungController {
	
	private Stage progEinstStage;
	
	@FXML
	private void initialize() {

	}
	
	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}
	
	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}
}
