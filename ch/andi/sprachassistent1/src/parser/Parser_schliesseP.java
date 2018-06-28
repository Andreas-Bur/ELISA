package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.CloseProgram;

public class Parser_schliesseP {

	public Parser_schliesseP() {
		// TODO Auto-generated constructor stub
	}

	public static void parse(String input) {
		System.out.println("(Parser_schliesse.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, "(?!.*nicht.*).*dies(\\w){0,2} fenster")) {

			String path = MyPaths.getPathOfKnownApp(programName);
			if (path != null) {
				CloseProgram.close(path, programName);
			}
			else {
				path = MyPaths.getPathOfForegroundApp();
				CloseProgram.close(path, path);
			}

		} else if (MyParser.means(args, "(?!.*nicht.*).*dies(\\w){0,2} programm")) {
			String path = MyPaths.getPathOfForegroundApp();
			CloseProgram.quitProgram(path, programName);
		} else { 
			String path = MyPaths.getPathOfKnownApp(programName);
			CloseProgram.quitProgram(path, programName);
		}
	}
	
	public static void main(String[] args) {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		parse("schliesse _firefox");
	}
}
