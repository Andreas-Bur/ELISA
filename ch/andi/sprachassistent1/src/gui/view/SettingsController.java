package gui.view;

import java.io.IOException;

import gui.AlertController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;
import main.Startup;

public class SettingsController {
	
	private Stage progEinstStage;
	
	@FXML
	CheckBox autostartBox;
	
	@FXML
	private void initialize() {
		autostartBox.setSelected(Startup.isAutostarting());
	}
	
	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}
	
	@FXML
	private void saveSettings() {
		if(autostartBox.isSelected()) {
			if(!Startup.isAutostarting()) {
				Startup.addAutostart();
			}
		}else {
			if(Startup.isAutostarting()) {
				Startup.removeAutostart();
			}
		}
		closeWindow();
	}
	
	@FXML
	private void resetElisa() {
		try {
			Startup.deleteFolderAndFiles();
			Startup.createFoldersAndFiles();
			AlertController.showExitDialog("Neustart", "ELISA beendet sich jetzt. Bitte starten Sie das Programm neu.");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}

}
