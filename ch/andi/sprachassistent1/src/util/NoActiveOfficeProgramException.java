package util;

import bgFunc.MyPaths;
import gui.AlertController;

public class NoActiveOfficeProgramException extends Exception{

	public NoActiveOfficeProgramException() {}
	
	public NoActiveOfficeProgramException(String message) {
		super(message);
	}
	
	public NoActiveOfficeProgramException(String message, String input) {
		super(message);
	}
	
	public void showErrorAlert(String command) {
		String[] parts = MyPaths.getPathOfForegroundApp().split("\\\\");
		AlertController.showErrorDialog("Befehl nicht anwendbar", "Der Befehl \""+command+"\" kann im aktiven Programm \""+parts[parts.length-1].split("\\.")[0]+"\" nicht angewendet werden.");
	}
	
	public static void main(String[] args) {
		String command = "öffne xyz";
		try {
			throw new NoActiveOfficeProgramException("The command \""+command+"\" is not applicable for the application "+MyPaths.getPathOfForegroundApp());
		} catch (NoActiveOfficeProgramException e) {
			e.printStackTrace();
			e.showErrorAlert("");
		}
	}

}
