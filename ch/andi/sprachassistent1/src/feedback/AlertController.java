package feedback;

import gui.TrayIconController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import main.Main;

public class AlertController {

	public static void showSprachErrorDialog(String sprache, String name) {
		showErrorDialog("Unbekannte Sprache",
				"Die Sprache \"" + sprache + "\" des Eintrags \"" + name + "\" konnte nicht erkannt werden. Wählen Sie zwischen \"DE\" und \"EN\".");
	}
	
	public static void showNameErrorDialog(String name) {
		showErrorDialog("Ungültiger Name",
				"Der Name \"" + name + "\" ist ungültig. Stellen Sie sicher, dass er nur nicht mehrfach vorkommt und nur gültige Zeichen beinhaltet (Lateinische Buchstaben, Umlaute, Leerzeichen).");
	}

	public static void showProgramPathErrorDialog(String name, String pfad) {
		showErrorDialog("Unbekannte Datei",
				"Das Programm \"" + name + "\" konnte unter dem Pfad \"" + pfad + "\" nicht gefunden werden. Passen Sie den Eintrag in den Einstellungen an.");
	}
	
	public static void showWrongContextCommandError(String command, String programName) {
		showErrorDialog("Befehl nicht anwendbar", "Der Befehl \""+command+"\" kann im aktiven Programm \""+programName+"\" nicht angewendet werden.");

	}

	public static void showErrorDialog(String header, String content) {
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Sound.playErrorSound();
				new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 10000)).start();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(header);
				alert.setContentText(content);

				alert.showAndWait();
			}
		});
	}
	
	public static void confirmStop() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(AlertType.CONFIRMATION, "ELISA stoppen?", ButtonType.YES, ButtonType.NO);
				alert.showAndWait();
				if(alert.getResult() == ButtonType.YES) {
					Main.quitProgram();
				}
			}
		});
	}
	
	public static void showExitDialog(String header, String content) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Info Dialog");
				alert.setHeaderText(header);
				alert.setContentText(content);

				alert.showAndWait();
				Main.quitProgram();
			}
		});
	}
	
	public static void showIOExceptionDialog(String action) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Sound.playErrorSound();
				new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 10000)).start();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText("Fehler");
				alert.setContentText("Beim "+action+" einer Datei ist ein Fehler aufgetreten. Bitte starten Sie ELISA neu. Im Wiederholungsfall veruchen Sie, den Computer neu zu starten.");

				alert.showAndWait();
				Main.quitProgram();
			}
		});
	}
}
