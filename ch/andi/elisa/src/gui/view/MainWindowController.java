package gui.view;

import gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import main.Main;

public class MainWindowController {

	MainApp mainApp = new MainApp();

	@FXML
	Button IoLeerenButton;
	@FXML
	TextArea outTextArea;

	public void setMainApp(MainApp mainApp) {
		this.mainApp = mainApp;
		populateTextArea();
	}

	public void populateTextArea() {
		outTextArea.clear();
		for (String line : mainApp.getExecutedCommands()) {
			outTextArea.appendText(line + System.lineSeparator());
		}
		outTextArea.setScrollTop(Double.MAX_VALUE);
	}

	@FXML
	private void handleMenuBeenden() {
		Main.quitProgram();
	}

	@FXML
	private void handleMenuAnleitung() {
		mainApp.showDialog("Anleitung", "view/AnleitungDialog.fxml");
	}

	@FXML
	private void handleMenuBefehle() {
		mainApp.showDialog("Alle Befehle", "view/BefehleDialog.fxml");
	}

	@FXML
	private void handleMenuAbout() {
		mainApp.showDialog("Über ELISA", "view/AboutDialog.fxml");
	}

	@FXML
	private void handleMenuEinstellungenAllgemein() {
		mainApp.showSettings();
	}

	@FXML
	private void handleMenuEinstellungenProgramme() {
		mainApp.showEinstellungenEntry(0);
	}

	@FXML
	private void handleMenuEinstellungenDateien() {
		mainApp.showEinstellungenEntry(1);
	}

	@FXML
	private void handleMenuEinstellungenWebseiten() {
		mainApp.showEinstellungenEntry(2);
	}

	@FXML
	private void clearOutTextArea() {
		outTextArea.clear();
	}
}
