package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import bgFunc.Processes;
import execute.OpenProgram;
import execute.WindowController;

public class Parser_öffneP implements BaseParser {

	public void parse(String input, String tag) {
		System.out.println("(Parser_öffneP.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, ".*neu(\\w){0,2} fenster")) {
			System.out.println("(Parser_öffneP.parse) neues Fenster");

			String path = MyPaths.getPathOfKnownApp(programName);
			// if args contain a program name -> run that program
			if (path != null) {
				OpenProgram.open(path);
			}
			// else -> run new instance of program in foreground
			else {
				OpenProgram.open(MyPaths.getPathOfForegroundApp());
			}

		} else {
			System.out.println("(Parser_öffneP.parse) kein neues Fenster");
			String path = MyPaths.getPathOfKnownApp(programName);

			// if programName is running -> move into foreground
			if (Processes.isProcessRunning(path)) {
				System.out.println("(Parser_öffneP.parse) is already running");
				WindowController.showWindow(path);

				System.out.println("(Parser_öffne.parse) moved to foregound");

			} else {
				OpenProgram.open(path);
			}
		}
	}
}
