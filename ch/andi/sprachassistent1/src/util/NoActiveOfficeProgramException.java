package util;

import bgFunc.MyPaths;
import feedback.FeedbackController;
import gui.AlertController;
import gui.TrayIconController;

public class NoActiveOfficeProgramException extends Exception{

	public NoActiveOfficeProgramException() {}
	
	public NoActiveOfficeProgramException(String input, String activeProgram) {
		super("The command \""+input+"\" is not applicable for the application "+MyPaths.getPathOfForegroundApp());
	}
	
	public void showErrorAlert(String command) {
		String[] parts = MyPaths.getPathOfForegroundApp().split("\\\\");
		new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 5000)).start();
		AlertController.showErrorDialog("Befehl nicht anwendbar", "Der Befehl \""+command+"\" kann im aktiven Programm \""+parts[parts.length-1].split("\\.")[0]+"\" nicht angewendet werden.");
	}
}
