package gui;

import feedback.FeedbackController;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertController {

	public static void showSprachErrorDialog(String sprache, String name) {

		showErrorDialog("Unbekannte Sprache",
				"Die Sprache \"" + sprache + "\" des Programs \"" + name + "\" konnte nicht erkannt werden.");
	}

	public static void showProgramPathErrorDialog(String name, String pfad) {

		showErrorDialog("Unbekannte Datei",
				"Das Programm \"" + name + "\" konnte unter dem Pfad \"" + pfad + "\" nicht gefunden werden.");
	}

	public static void showErrorDialog(String header, String content) {

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 10000)).start();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error Dialog");
				alert.setHeaderText(header);
				alert.setContentText(content);

				alert.showAndWait();
			}
		});
	}
}
