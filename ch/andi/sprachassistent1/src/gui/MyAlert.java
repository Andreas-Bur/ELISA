package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class MyAlert {

	public MyAlert() {
		// TODO Auto-generated constructor stub
	}
	
	public static void showSprachErrorDialog(String sprache, String name) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Unbekannte Sprache");
		alert.setContentText("Die Sprache \""+sprache+"\" des Programs \""+name+"\" konnte nicht erkannt werden.");

		alert.showAndWait();
	}
	
	public static void showProgramPathErrorDialog(String name, String pfad) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText("Unbekannte Datei");
		alert.setContentText("Das Programm \""+name+"\" konnte unter dem Pfad \""+pfad+"\" nicht gefunden werden.");

		alert.showAndWait();
	}
	
}
