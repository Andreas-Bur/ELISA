package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import bgFunc.Processes;
import execute.OpenProgram;
import execute.WindowController;

public class Parser_öffneP implements BaseParser {

	public void parse(String input, String tag) {

		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, ".*neu(\\w){0,2} fenster")) {

			String path = MyPaths.getPathOfKnownApp(programName);

			// Wenn ein Programmname gesagt wurde
			if (path != null) {
				OpenProgram.open(path);
			}

			// Sonst öffne eine neue Instanz des zurzeit obersten Programmes
			else {
				OpenProgram.open(MyPaths.getPathOfForegroundApp());
			}

		} else {
			String path = MyPaths.getPathOfKnownApp(programName);

			// Wenn das genannte Programm bereits läuft wird es in den
			// Vordergrund gebracht
			if (Processes.isProcessRunning(path)) {
				WindowController.showWindow(path);
			}
			// Sonst wird es neu gestartet
			else {
				OpenProgram.open(path);
			}
		}
	}
}
