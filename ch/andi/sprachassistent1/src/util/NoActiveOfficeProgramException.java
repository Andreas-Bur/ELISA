package util;

import bgFunc.MyPaths;
import feedback.AlertController;
import feedback.FeedbackController;
import gui.TrayIconController;

public class NoActiveOfficeProgramException extends Exception{

	private static final long serialVersionUID = 1L;

	public NoActiveOfficeProgramException() {}
	
	public NoActiveOfficeProgramException(String input, String activeProgram) {
		super("The command \""+input+"\" is not applicable for the application "+MyPaths.getPathOfForegroundApp());
	}
	
	public void showErrorAlert(String command) {
		String[] parts = MyPaths.getPathOfForegroundApp().split("\\\\");
		new Thread(new FeedbackController(TrayIconController.ERROR_ICON, 5000)).start();
		AlertController.showWrongContextCommandError(command, parts[parts.length-1].split("\\.")[0]);
	}
}
