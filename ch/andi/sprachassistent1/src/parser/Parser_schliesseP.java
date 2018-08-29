package parser;

import bgFunc.MyParser;
import bgFunc.MyPaths;
import execute.CloseProgram;

public class Parser_schliesseP implements BaseParser {

	public void parse(String input, String tag) {
		System.out.println("(Parser_schliesseP.parse) input: " + input);
		String[] words = input.split(" ");

		String args = input.substring(words[0].length() + 1, input.length());
		String programName = MyParser.getContainedProgramName(args);

		if (MyParser.means(args, ".*fenster")) {
			String path = MyPaths.getPathOfForegroundApp();
			CloseProgram.closeTopWindowOfProgram(path, path);
		} else if (MyParser.means(args, ".*programm")) {
			String path = MyPaths.getPathOfForegroundApp();
			CloseProgram.quitProgram(path);
		} else {
			String path = MyPaths.getPathOfKnownApp(programName);
			CloseProgram.quitProgram(path);
		}
	}
}
