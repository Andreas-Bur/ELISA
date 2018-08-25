package gui.view;

import java.io.IOException;

import bgFunc.Settings;
import bgFunc.Startup;
import gui.AlertController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import jna.key.KeyHook;

public class SettingsController {
	
	private Stage progEinstStage;
	
	@FXML
	CheckBox autostartBox;
	@FXML
	ChoiceBox<String> hotkeyBox;
	
	@FXML
	private void initialize() {
		autostartBox.setSelected(Settings.isAutostarting());
		hotkeyBox.setItems(FXCollections.observableArrayList("Ctrl rechts","Ctrl links"));
		hotkeyBox.getSelectionModel().select(Settings.getHotkeyIndex());
	}
	
	@FXML
	private void closeWindow() {
		progEinstStage.close();
	}
	
	@FXML
	private void saveSettings() {
		if(autostartBox.isSelected()) {
			if(!Settings.isAutostarting()) {
				Settings.addAutostart();
			}
		}else {
			if(Settings.isAutostarting()) {
				Settings.removeAutostart();
			}
		}
		
		Settings.setHotkeyIndex(hotkeyBox.getSelectionModel().getSelectedIndex());
		KeyHook.index = hotkeyBox.getSelectionModel().getSelectedIndex();
		
		closeWindow();
	}
	
	@FXML
	private void resetElisa() {
		try {
			Startup.deleteFolderAndFiles();
			Startup.createFoldersAndFiles();
			AlertController.showExitDialog("Neustart", "ELISA beendet sich jetzt. Bitte starten Sie das Programm neu.");
		} catch (IOException e) {
			AlertController.showIOExceptionDialog("Lesen oder Schreiben");
			e.printStackTrace();
		}
		
	}
	
	public void setProgEinstStage(Stage progEinstStage) {
		this.progEinstStage = progEinstStage;
	}
}
