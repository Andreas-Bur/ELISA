package commands;

import bgFunc.MyParser;
import bgFunc.Paths;
import execute.CloseProgram;

public class Parser_schliesse {

	public Parser_schliesse() {
		// TODO Auto-generated constructor stub
	}

	public static void parse(String input) {
		System.out.println("(Parser_schliesse.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, "(?!.*nicht.*).*dies(\\w){0,2} (bildschirm)?fenster")) {

			String path = Paths.getPathOfKnownApp(programName);
			if (path != null) {
				CloseProgram.close(path, programName);
			}
			else {
				path = Paths.getPathOfForegroundApp();
				CloseProgram.close(path, path);
			}

		} else if (MyParser.means(args, "(?!.*nicht.*).*dies(\\w){0,2} programm")) {
			String path = Paths.getPathOfForegroundApp();
			CloseProgram.quit(path, programName);
		} else {
			String path = Paths.getPathOfKnownApp(programName);
			CloseProgram.quit(path, programName);
		}
	}
}
