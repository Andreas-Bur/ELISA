package gui.view;

import gui.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.Main;

public class IoWindowController {

	MainApp mainApp = new MainApp();

	@FXML
	Button IoOkButton;
	@FXML
	Button IoLeerenButton;
	@FXML
	TextArea outTextArea;
	@FXML
	TextField inTextField;

	public IoWindowController(){

	}

	public void setMainApp(MainApp mainApp){
		this.mainApp = mainApp;
	}

	@FXML
	private void handleIoOkButton(){

		System.out.println(inTextField.getText());
		outTextArea.appendText(inTextField.getText()+"\n");
		inTextField.clear();
	}

	@FXML
	private void handleMenuBeenden(){
		Main.quit = true;
	}
	
	@FXML
	private void handleMenuEinstellungenAllgemein() {
		mainApp.showEinstellungenProgramme();
	}
	
	


	@FXML
	private void clearOutTextArea(){
		outTextArea.clear();
	}

}
